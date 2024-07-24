package com.serviceschedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.serviceschedule.exception.HorarioDisponivelException;
import com.serviceschedule.exception.HorarioDuplicadoException;
import com.serviceschedule.exception.HorarioNaoEncontradoException;
import com.serviceschedule.exception.PrestadorDuplicadoException;
import com.serviceschedule.exception.PrestadorNaoEncontradoException;
import com.serviceschedule.model.Horario;
import com.serviceschedule.model.ServiceScheduleModel;
import com.serviceschedule.repository.ServiceScheduleRepository;
import com.serviceschedule.service.KafkaProducerService;
import com.serviceschedule.service.ServiceScheduleService;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ServiceScheduleApplicationTests {

	@Mock
	private ServiceScheduleRepository serviceScheduleRepository;

	@Mock
	private KafkaProducerService kafkaProducerService;

	@InjectMocks
	private ServiceScheduleService serviceScheduleService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		serviceScheduleService = new ServiceScheduleService(serviceScheduleRepository, kafkaProducerService);
	}
	@Test
	void testGetAllPrestadores() {
		ServiceScheduleModel prestador1 = new ServiceScheduleModel();
		prestador1.setId(1L);
		prestador1.setNome("Prestador 1");
		prestador1.setCategoria("Categoria 1");
		prestador1.setValorServico(100.0);

		Horario horario1 = new Horario();
		horario1.setId(1L);
		horario1.setPrestador(prestador1);
		horario1.setDisponivel(true);
		horario1.setHora("10:00");
		horario1.setDiaSemana("Segunda");
		prestador1.setHorarios(Arrays.asList(horario1));

		ServiceScheduleModel prestador2 = new ServiceScheduleModel();
		prestador2.setId(2L);
		prestador2.setNome("Prestador 2");
		prestador2.setCategoria("Categoria 2");
		prestador2.setValorServico(150.0);

		Horario horario2 = new Horario();
		horario2.setId(2L);
		horario2.setPrestador(prestador2);
		horario2.setDisponivel(true);
		horario2.setHora("11:00");
		horario2.setDiaSemana("Terça");
		prestador2.setHorarios(Arrays.asList(horario2));

		doReturn(Arrays.asList(prestador1, prestador2)).when(serviceScheduleRepository).findAll();

		List<ServiceScheduleModel> result = serviceScheduleService.getAllPrestadores();

		result.forEach(prestador -> System.out.println("Prestador no resultado: " + prestador.getNome()));

		assertEquals(2, result.size(), "O tamanho da lista de prestadores deve ser 2");
		assertEquals("Prestador 1", result.get(0).getNome(), "O nome do primeiro prestador deve ser 'Prestador 1'");
		assertEquals("Prestador 2", result.get(1).getNome(), "O nome do segundo prestador deve ser 'Prestador 2'");

		verify(serviceScheduleRepository, times(1)).findAll();
	}

	@Test
	void testGetPrestadorById() {
		ServiceScheduleModel prestador = new ServiceScheduleModel();
		when(serviceScheduleRepository.findById(anyLong())).thenReturn(Optional.of(prestador));

		Optional<ServiceScheduleModel> result = serviceScheduleService.getPrestadorById(1L);

		assertTrue(result.isPresent());
		verify(serviceScheduleRepository, times(1)).findById(1L);
	}

	@Test
	void testSavePrestador() {
		ServiceScheduleModel prestador = new ServiceScheduleModel();
		prestador.setNome("Test");
		when(serviceScheduleRepository.existsByNome(anyString())).thenReturn(false);
		when(serviceScheduleRepository.save(any(ServiceScheduleModel.class))).thenReturn(prestador);

		ServiceScheduleModel result = serviceScheduleService.savePrestador(prestador);

		assertNotNull(result);
		verify(serviceScheduleRepository, times(1)).existsByNome("Test");
		verify(serviceScheduleRepository, times(1)).save(prestador);
	}

	@Test
	void testAlterarDisponibilidadeHorario() {
		ServiceScheduleModel prestador = new ServiceScheduleModel();
		prestador.setId(1L);
		prestador.setNome("Prestador 1");
		prestador.setCategoria("Categoria 1");
		prestador.setValorServico(100.0);

		Horario horario = new Horario();
		horario.setId(1L);
		horario.setPrestador(prestador);
		horario.setDisponivel(true);
		horario.setHora("10:00");
		horario.setDiaSemana("Segunda");

		prestador.setHorarios(Arrays.asList(horario));

		when(serviceScheduleRepository.findById(1L)).thenReturn(Optional.of(prestador));
		when(serviceScheduleRepository.save(prestador)).thenReturn(prestador);

		serviceScheduleService.alterarDisponibilidadeHorario(1L, 1L);

		assertFalse(horario.isDisponivel(), "O horário deve estar indisponível após a chamada do método");

		verify(kafkaProducerService, times(1)).sendMessage(eq("agendamentos"), anyString());
		verify(serviceScheduleRepository, times(1)).save(prestador);
	}

	@Test
	void testGetHorariosDisponiveisByPrestadorId() {
		ServiceScheduleModel prestador = new ServiceScheduleModel();
		prestador.setId(1L);
		prestador.setNome("Prestador 1");
		prestador.setCategoria("Categoria 1");
		prestador.setValorServico(100.0);

		Horario horario1 = new Horario();
		horario1.setId(1L);
		horario1.setPrestador(prestador);
		horario1.setDisponivel(true);
		horario1.setHora("10:00");
		horario1.setDiaSemana("Segunda");

		Horario horario2 = new Horario();
		horario2.setId(2L);
		horario2.setPrestador(prestador);
		horario2.setDisponivel(false);
		horario2.setHora("11:00");
		horario2.setDiaSemana("Terça");

		prestador.setHorarios(Arrays.asList(horario1, horario2));

		when(serviceScheduleRepository.findById(1L)).thenReturn(Optional.of(prestador));

		List<Horario> result = serviceScheduleService.getHorariosDisponiveisByPrestadorId(1L);

		assertEquals(1, result.size(), "Deve haver 1 horário disponível");
		assertEquals(horario1.getId(), result.get(0).getId(), "O ID do horário disponível deve ser 1");

		when(serviceScheduleRepository.findById(2L)).thenReturn(Optional.empty());

		Exception exception = assertThrows(PrestadorNaoEncontradoException.class, () -> {
			serviceScheduleService.getHorariosDisponiveisByPrestadorId(2L);
		});

		String expectedMessage = "{\"error\": \"Prestador não encontrado com o ID\" : " + 2L + "}";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage), "A mensagem da exceção deve conter 'Prestador não encontrado com ID: 2'");
	}

	@Test
	void testAssociarHorariosAoPrestador() {
		when(serviceScheduleRepository.findById(1L)).thenReturn(Optional.empty());

		List<Horario> novosHorarios = new ArrayList<>();
		Horario novoHorario = new Horario();
		novoHorario.setDiaSemana("Segunda");
		novoHorario.setHora("10:00");
		novosHorarios.add(novoHorario);

		assertThrows(PrestadorNaoEncontradoException.class, () -> {
			serviceScheduleService.associarHorariosAoPrestador(1L, novosHorarios);
		});

		ServiceScheduleModel prestador = new ServiceScheduleModel();
		prestador.setId(2L);
		prestador.setNome("Prestador 2");
		prestador.setCategoria("Categoria 2");
		prestador.setValorServico(150.0);

		Horario horarioExistente = new Horario();
		horarioExistente.setDiaSemana("Segunda");
		horarioExistente.setHora("10:00");
		horarioExistente.setPrestador(prestador);
		horarioExistente.setDisponivel(true);

		prestador.setHorarios(new ArrayList<>(Arrays.asList(horarioExistente)));

		when(serviceScheduleRepository.findById(2L)).thenReturn(Optional.of(prestador));

		assertThrows(HorarioDuplicadoException.class, () -> {
			serviceScheduleService.associarHorariosAoPrestador(2L, novosHorarios);
		});

		Horario horarioNovo = new Horario();
		horarioNovo.setDiaSemana("Terça");
		horarioNovo.setHora("11:00");
		novosHorarios.clear();
		novosHorarios.add(horarioNovo);

		serviceScheduleService.associarHorariosAoPrestador(2L, novosHorarios);

		assertEquals(2, prestador.getHorarios().size(), "O prestador deve ter 2 horários após adicionar o novo horário.");
		assertTrue(prestador.getHorarios().stream().anyMatch(h -> h.getDiaSemana().equals("Terça") && h.getHora().equals("11:00")), "O novo horário deve estar presente nos horários do prestador.");

		verify(serviceScheduleRepository, times(2)).findById(2L); // Chamado duas vezes, uma para duplicado e outra para sucesso
		verify(serviceScheduleRepository, times(1)).save(prestador);
	}

	@Test
	void testAlterarDisponibilidadeHorarioParaTrue() {
		when(serviceScheduleRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(PrestadorNaoEncontradoException.class, () -> {
			serviceScheduleService.alterarDisponibilidadeHorarioParaTrue(1L, 1L);
		});

		ServiceScheduleModel prestador = new ServiceScheduleModel();
		prestador.setId(2L);
		prestador.setNome("Prestador 2");
		prestador.setHorarios(new ArrayList<>());

		when(serviceScheduleRepository.findById(2L)).thenReturn(Optional.of(prestador));

		assertThrows(HorarioNaoEncontradoException.class, () -> {
			serviceScheduleService.alterarDisponibilidadeHorarioParaTrue(2L, 1L);
		});

		Horario horarioExistente = new Horario();
		horarioExistente.setId(3L);
		horarioExistente.setDisponivel(true);

		prestador.setHorarios(Arrays.asList(horarioExistente));

		assertThrows(HorarioDisponivelException.class, () -> {
			serviceScheduleService.alterarDisponibilidadeHorarioParaTrue(2L, 3L);
		});

		Horario horarioIndisponivel = new Horario();
		horarioIndisponivel.setId(4L);
		horarioIndisponivel.setDisponivel(false);
		horarioIndisponivel.setHora("10:00");
		prestador.setHorarios(Arrays.asList(horarioIndisponivel));

		serviceScheduleService.alterarDisponibilidadeHorarioParaTrue(2L, 4L);

		assertTrue(horarioIndisponivel.isDisponivel(), "O horário deve estar disponível após a alteração.");
		verify(serviceScheduleRepository, times(3)).findById(2L); // Chamado três vezes, uma para não encontrado, uma para disponível e uma para sucesso
		verify(serviceScheduleRepository, times(1)).save(prestador);
		verify(kafkaProducerService, times(1)).sendMessage("cancelamentos", "Cancelamento registrado: Prestador=Prestador 2, Horário=10:00");
	}

	@Test
	void testDeletePrestador() {
		Long prestadorIdNaoEncontrado = 1L;
		when(serviceScheduleRepository.existsById(prestadorIdNaoEncontrado)).thenReturn(false);

		assertThrows(PrestadorNaoEncontradoException.class, () -> {
			serviceScheduleService.deletePrestador(prestadorIdNaoEncontrado);
		});
		verify(serviceScheduleRepository, times(1)).existsById(prestadorIdNaoEncontrado);
		verify(serviceScheduleRepository, times(0)).deleteById(prestadorIdNaoEncontrado);

		Long prestadorIdEncontrado = 2L;
		when(serviceScheduleRepository.existsById(prestadorIdEncontrado)).thenReturn(true);

		serviceScheduleService.deletePrestador(prestadorIdEncontrado);

		verify(serviceScheduleRepository, times(1)).existsById(prestadorIdEncontrado);
		verify(serviceScheduleRepository, times(1)).deleteById(prestadorIdEncontrado);
	}

	@Test
	void testNomePrestadorJaCadastrado() {
		String nomePrestador = "Prestador Existente";

		when(serviceScheduleRepository.existsByNome(nomePrestador)).thenReturn(true);

		ServiceScheduleModel novoPrestador = new ServiceScheduleModel();
		novoPrestador.setNome(nomePrestador);

		assertThrows(PrestadorDuplicadoException.class, () -> {
			serviceScheduleService.savePrestador(novoPrestador);
		});

		verify(serviceScheduleRepository, times(1)).existsByNome(nomePrestador);
	}

}