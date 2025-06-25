package com.pdv.papelaria;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pdv.papelaria.entities.Usuario;
import com.pdv.papelaria.repository.UsuarioRepository;
import com.pdv.papelaria.service.UsuarioService;

@ExtendWith(MockitoExtension.class) // ✅ Necessário para ativar Mockito no JUnit 5
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService; 

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder; // ✅ Mock para evitar NullPointerException

    @BeforeEach
    public void setUp() {
        when(passwordEncoder.encode(anyString())).thenReturn("senha-criptografada");
    }

    @Test
    public void testFindAll() {
        // Criando um usuário fictício
        Usuario usuario = new Usuario();
        usuario.setNome("Teste");
        usuario.setSenha("123");
        usuario.setRole("ADMIN");

        // Configurando o comportamento do mock
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Chamando o método a ser testado
        List<Usuario> usuarios = usuarioService.findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Teste", usuarios.get(0).getNome());

        // Testando o método save
        Usuario usuarioSalvo = usuarioService.save(usuarios.get(0));
        assertNotNull(usuarioSalvo);
        assertEquals("senha-criptografada", usuarioSalvo.getSenha());

        // Verificando se o save foi chamado corretamente
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }
}
