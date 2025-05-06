package com.tribuna360.service;

import com.tribuna360.enums.UsuarioRolEnum;
import com.tribuna360.model.Usuario;
import com.tribuna360.repository.UsuarioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;  // Simulamos el repositorio

    @InjectMocks
    private UsuarioService usuarioService;  // El servicio que vamos a probar

    private Usuario usuarioMock;

    @Before
    public void setUp() {
        // Creamos un usuario mock para las pruebas
        usuarioMock = new Usuario();
        usuarioMock.setIdUsuario(1L);
        usuarioMock.setCedula("123456789");
        usuarioMock.setNombre("Juan Pérez");
        usuarioMock.setTelefono("3001234567");
        usuarioMock.setEmail("juanperez@example.com");
        usuarioMock.setContrasena("password");
        usuarioMock.setFechaRegistro(LocalDate.now());
        usuarioMock.setRol(UsuarioRolEnum.USER);
    }

    @Test
    public void testCrearUsuario() {
        // Simulamos que el repositorio guarda el usuario correctamente
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioMock);

        // Llamamos al método del servicio
        Usuario usuarioCreado = usuarioService.crearUsuario(usuarioMock);

        // Verificamos que el repositorio haya sido llamado una vez
        verify(usuarioRepository, times(1)).save(any(Usuario.class));

        // Aseguramos que el usuario creado no sea nulo
        assertNotNull(usuarioCreado);

        // Aseguramos que los datos del usuario son correctos
        assertEquals("Juan Pérez", usuarioCreado.getNombre());
        assertEquals("juanperez@example.com", usuarioCreado.getEmail());
    }

    @Test
    public void testObtenerUsuarioPorId() {
        // Simulamos que el repositorio devuelve un usuario cuando se busca por ID
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(usuarioMock));

        // Llamamos al método del servicio
        Usuario usuarioEncontrado = usuarioService.obtenerUsuarioPorId(1L);

        // Verificamos que el repositorio haya sido llamado una vez
        verify(usuarioRepository, times(1)).findById(1L);

        // Aseguramos que el usuario encontrado sea el correcto
        assertNotNull(usuarioEncontrado);
        assertEquals("Juan Pérez", usuarioEncontrado.getNombre());
    }

    @Test
    public void testEliminarUsuario() {
        // Simulamos que el repositorio elimina el usuario correctamente
        doNothing().when(usuarioRepository).deleteById(1L);

        // Llamamos al método del servicio
        usuarioService.eliminarUsuario(1L);

        // Verificamos que el repositorio haya sido llamado una vez
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testObtenerUsuarioPorEmail() {
        // Simulamos que el repositorio devuelve un usuario por email
        when(usuarioRepository.findByEmail("juanperez@example.com")).thenReturn(usuarioMock);

        // Llamamos al método del servicio
        Usuario usuarioEncontrado = usuarioService.obtenerUsuarioPorEmail("juanperez@example.com");

        // Verificamos que el repositorio haya sido llamado una vez
        verify(usuarioRepository, times(1)).findByEmail("juanperez@example.com");

        // Aseguramos que el usuario encontrado sea el correcto
        assertNotNull(usuarioEncontrado);
        assertEquals("Juan Pérez", usuarioEncontrado.getNombre());
    }
}
