package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

	@InjectMocks
	private AdocaoService adocaoService;

	@Mock
	private Pet pet;

	@Mock
	private Tutor tutor;

	@Mock
	private Adocao adocao;

	@Mock
	private Abrigo abrigo;

	@Mock
	private AdocaoRepository adocaoRepository;

	@Mock
	private PetRepository petRepository;

	@Mock
	private TutorRepository tutorRepository;

	@Mock
	private EmailService emailService;

	@Spy
	private List<ValidacaoSolicitacaoAdocao> validacoes = new ArrayList<>();

	@Mock
	private ValidacaoSolicitacaoAdocao validador1;

	@Mock
	private ValidacaoSolicitacaoAdocao validador2;

	private SolicitacaoAdocaoDto dto;

	private AprovacaoAdocaoDto aprovacaoAdocaoDto;

	private ReprovacaoAdocaoDto reprovacaoAdocaoDto;

	@Captor
	private ArgumentCaptor<Adocao> adocaoCaptor;

	@Test
	void deveriaSalvarAdocaoAoSolicitar() {
		// ARRANGE
		this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo qualquer");
		given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
		given(pet.getAbrigo()).willReturn(abrigo);

		// ACT
		adocaoService.solicitar(dto);

		// ASSERT
		then(adocaoRepository).should().save(adocaoCaptor.capture());
		var adocaoSalva = adocaoCaptor.getValue();
		assertEquals(tutor, adocaoSalva.getTutor());
		assertEquals(pet, adocaoSalva.getPet());
		assertEquals(dto.motivo(), adocaoSalva.getMotivo());
	}

	@Test
	void deveriaChamarValidadoresDeAdocaoAoSolicitar() {
		// ARRANGE
		this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo qualquer");
		given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
		given(pet.getAbrigo()).willReturn(abrigo);
		validacoes.add(validador1);
		validacoes.add(validador2);
		// ACT
		adocaoService.solicitar(dto);

		// ASSERT
		validacoes.forEach(v -> then(v).should().validar(dto));
	}
	@Test
	void deveriaEnviarEmailAoSolicitarAdocao() {
		// ARRANGE
		this.dto = new SolicitacaoAdocaoDto(10l, 20l, "motivo qualquer");
		given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
		given(tutorRepository.getReferenceById(dto.idTutor())).willReturn(tutor);
		given(pet.getAbrigo()).willReturn(abrigo);

		// ACT
		adocaoService.solicitar(dto);

		// ASSERT
		then(adocaoRepository).should().save(adocaoCaptor.capture());
		var adocaoSalva = adocaoCaptor.getValue();
		then(emailService).should().enviarEmail(
				adocaoSalva.getPet().getAbrigo().getEmail(),
				"Solicitação de adoção",
				"Olá " +adocaoSalva.getPet().getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocaoSalva.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação."
		);
	}

	@Test
	void deveriaMarcarComoAprovadoAoAprovar() {
		// ARRANGE
		this.aprovacaoAdocaoDto = new AprovacaoAdocaoDto(1L);
		given(adocaoRepository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
		given(adocao.getPet()).willReturn(pet);
		given(adocao.getData()).willReturn(LocalDateTime.now());
		given(pet.getAbrigo()).willReturn(abrigo);
		given(adocao.getTutor()).willReturn(tutor);

		// ACT
		adocaoService.aprovar(aprovacaoAdocaoDto);

		//ASSERT
		then(adocao).should().marcarComoAprovada();
	}

	@Test
	void deveriaEnviarEmailAoAprovar() {
		// ARRANGE
		this.aprovacaoAdocaoDto = new AprovacaoAdocaoDto(1L);
		given(adocaoRepository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
		given(adocao.getPet()).willReturn(pet);
		given(adocao.getData()).willReturn(LocalDateTime.now());
		given(pet.getAbrigo()).willReturn(abrigo);
		given(adocao.getTutor()).willReturn(tutor);

		// ACT
		adocaoService.aprovar(aprovacaoAdocaoDto);

		//ASSERT
		then(emailService).should().enviarEmail(
				adocao.getPet().getAbrigo().getEmail(),
				"Adoção aprovada",
				"Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet."
		);
	}

	@Test
	void deveriaMarcarComoReprovadoAoReprovar() {
		// ARRANGE
		var justificativa = "Justificativa";
		this.reprovacaoAdocaoDto = new ReprovacaoAdocaoDto(1L, justificativa);
		given(adocaoRepository.getReferenceById(reprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
		given(adocao.getPet()).willReturn(pet);
		given(adocao.getData()).willReturn(LocalDateTime.now());
		given(pet.getAbrigo()).willReturn(abrigo);
		given(adocao.getTutor()).willReturn(tutor);

		// ACT
		adocaoService.reprovar(reprovacaoAdocaoDto);

		//ASSERT
		then(adocao).should().marcarComoReprovada(justificativa);
	}

	@Test
	void deveriaEnviarEmailAoReprovar() {
		// ARRANGE
		var justificativa = "Justificativa";
		this.reprovacaoAdocaoDto = new ReprovacaoAdocaoDto(1L, justificativa);
		given(adocaoRepository.getReferenceById(reprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
		given(adocao.getPet()).willReturn(pet);
		given(adocao.getData()).willReturn(LocalDateTime.now());
		given(pet.getAbrigo()).willReturn(abrigo);
		given(adocao.getTutor()).willReturn(tutor);

		// ACT
		adocaoService.reprovar(reprovacaoAdocaoDto);

		//ASSERT
		then(emailService).should().enviarEmail(
				adocao.getPet().getAbrigo().getEmail(),
				"Solicitação de adoção",
				"Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: " +adocao.getJustificativaStatus()
		);
	}



}