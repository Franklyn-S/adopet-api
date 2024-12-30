package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

	@InjectMocks
	private TutorService tutorService;

	@Mock
	private TutorRepository tutorRepository;

	@Mock
	private CadastroTutorDto cadastroTutorDto;

	@Mock
	private Tutor tutor;

	@Mock
	private AtualizacaoTutorDto atualizacaoTutorDto;

	@Test
	@DisplayName("Não deveria cadastrar tutor com email ou telefone já cadastrado")
	void naoDeveriaCadastrarTutorComEmailOuTelefoneJaCadastrado() {
		given(tutorRepository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email())).willReturn(true);
		assertThrows(ValidacaoException.class, () -> tutorService.cadastrar(cadastroTutorDto));
	}

	@Test
	@DisplayName("Deveria cadastrar um novo tutor")
	void cadastrar() {
		given(tutorRepository.existsByTelefoneOrEmail(cadastroTutorDto.telefone(), cadastroTutorDto.email())).willReturn(false);
		assertDoesNotThrow(() -> tutorService.cadastrar(cadastroTutorDto));
		then(tutorRepository).should().save(new Tutor(cadastroTutorDto));
	}

	@Test
	@DisplayName("Deveria atualizar um tutor")
	void atualizar() {
		given(tutorRepository.getReferenceById(atualizacaoTutorDto.id())).willReturn(tutor);
		tutorService.atualizar(atualizacaoTutorDto);
		then(tutor).should().atualizarDados(atualizacaoTutorDto);
	}
}