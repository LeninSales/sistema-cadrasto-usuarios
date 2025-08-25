package usuario.cadrasto.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import usuario.cadrasto.dtos.UsuarioDto;
import usuario.cadrasto.models.UsuarioModel;
import usuario.cadrasto.repository.UsuarioRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository userRepository;

    @Autowired
    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    // ver se o nome do metodo tem que ser referente ao do service ou nao

    @Test
    @DisplayName("Se o email não existir ele salva o usuario.")
    void shouldSaveUserWhenEmailDoesNotExist() {
        UsuarioDto dto = new UsuarioDto("jorge", 12, "jorge@gmail.com");

        when(userRepository.existsByEmail(dto.email())).thenReturn(false);

        UsuarioModel user = new UsuarioModel();
        user.setId(UUID.randomUUID());
        user.setEmail(dto.email());
        user.setNome(dto.nome());
        user.setIdade(dto.idade());

        when(userRepository.save(any(UsuarioModel.class))).thenReturn(user);

        UsuarioModel userResultado = usuarioService.salvar(dto);

        assertNotNull(userResultado.getId());
        assertEquals(dto.email(), userResultado.getEmail());
        assertEquals(dto.nome(), userResultado.getNome());
        assertEquals(dto.idade(), userResultado.getIdade());

        verify(userRepository, times(1)).existsByEmail(eq(dto.email()));
        verify(userRepository, times(1)).save(any(UsuarioModel.class));
    }


    @Test
    @DisplayName("Deve lançar uma exececao se o email existir.")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        UsuarioDto dto = new UsuarioDto("jorge", 12, "jorge@gmail.com");

        when(userRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> usuarioService.salvar(dto));

        verify(userRepository, never()).save(any(UsuarioModel.class));
    }

    @Test
    @DisplayName("Deve retornar uma lista quando existe users.")
    void shouldReturnUserListWhenUsersExist() {
        UsuarioModel user1 = new UsuarioModel();
        user1.setId(UUID.randomUUID());
        user1.setNome("João");
        user1.setEmail("joao@gmail.com");
        user1.setIdade(20);

        UsuarioModel user2 = new UsuarioModel();
        user2.setId(UUID.randomUUID());
        user2.setNome("Maria");
        user2.setEmail("maria@gmail.com");
        user2.setIdade(25);

        List<UsuarioModel> user = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(user);

        List<UsuarioModel> resultado = usuarioService.listaTodosUsuarios();

        assertNotNull(resultado);
        assertEquals("João", resultado.get(0).getNome());
        assertEquals("Maria", resultado.get(1).getNome());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve lançar exececao quando retorna uma lista vazia.")
    void shouldReturnEmptyListWhenNoUsersExist() {

        when(userRepository.findAll()).thenReturn(List.of());

        List<UsuarioModel> resultado = usuarioService.listaTodosUsuarios();

        assertNotNull(resultado);
        assertEquals(0, resultado.size());
        assertTrue(resultado.isEmpty());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve atualizar quando o id existe.")
    void shouldUpdateUserWhenIdExists() {
        UUID id = UUID.randomUUID();
        UsuarioModel user = new UsuarioModel();
        user.setId(id);
        user.setIdade(20);
        user.setNome("Jorge");
        user.setEmail("jorge@gmail.com");

        UsuarioDto dto = new UsuarioDto("Maria", 25, "maria@gmail.com");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(UsuarioModel.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        UsuarioModel userAtualizado = usuarioService.atualizaUsuario(id, dto);

        assertNotNull(userAtualizado);
        assertEquals("Maria", userAtualizado.getNome());
        assertEquals("maria@gmail.com", userAtualizado.getEmail());
        assertEquals(25, userAtualizado.getIdade());

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(any(UsuarioModel.class));

    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException se usuário não existir.")
    void shouldThrowExceptionWhenUpdatingNonexistentUser() {
        UUID id = UUID.randomUUID();
        UsuarioDto dto = new UsuarioDto("Maria", 25, "maria@gmail.com");

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> usuarioService.atualizaUsuario(id, dto));


        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any(UsuarioModel.class));

    }


    @Test
    @DisplayName("Deve deletar usuário quando o id existe.")
    void shouldDeleteUserWhenIdExists() {
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id)).thenReturn(true);

        usuarioService.deletaUsuario(id);

        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar usuário inexistente")
    void shouldThrowExceptionWhenDeletingNonexistentUser() {
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id)).thenReturn(false);

        usuarioService.deletaUsuario(id);

        verify(userRepository, times(1)).existsById(id);
        verify(userRepository, never()).deleteById(any());
    }

}