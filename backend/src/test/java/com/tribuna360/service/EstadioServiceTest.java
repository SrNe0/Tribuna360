package com.tribuna360.service;

import com.tribuna360.model.Estadio;
import com.tribuna360.repository.EstadioRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class EstadioServiceTest {

    @Mock
    private EstadioRepository estadioRepository;  // Simulamos el repositorio

    @InjectMocks
    private EstadioService estadioService;  // El servicio que vamos a probar

    private Estadio estadioMock;

    @Before
    public void setUp() {
        // Creamos un estadio mock para las pruebas
        estadioMock = new Estadio();
        estadioMock.setIdEstadio(1L);
        estadioMock.setNombre("Estadio Nacional");
        estadioMock.setUbicacion("Calle Ficticia 123");
        estadioMock.setCapacidad(50000);
    }

    @Test
    public void testCrearEstadio() {
        // Simulamos que el repositorio guarda el estadio correctamente
        when(estadioRepository.save(any(Estadio.class))).thenReturn(estadioMock);

        // Llamamos al método del servicio
        Estadio estadioCreado = estadioService.crearEstadio(estadioMock);

        // Verificamos que el repositorio haya sido llamado una vez
        verify(estadioRepository, times(1)).save(any(Estadio.class));

        // Aseguramos que el estadio creado no sea nulo
        assertNotNull(estadioCreado);

        // Aseguramos que los datos del estadio son correctos
        assertEquals("Estadio Nacional", estadioCreado.getNombre());
        assertEquals(50000, estadioCreado.getCapacidad());
    }

    @Test
    public void testObtenerEstadioPorId() {
        // Simulamos que el repositorio devuelve un estadio cuando se busca por ID
        when(estadioRepository.findById(1L)).thenReturn(java.util.Optional.of(estadioMock));

        // Llamamos al método del servicio
        Estadio estadioEncontrado = estadioService.obtenerEstadioPorId(1L);

        // Verificamos que el repositorio haya sido llamado una vez
        verify(estadioRepository, times(1)).findById(1L);

        // Aseguramos que el estadio encontrado sea el correcto
        assertNotNull(estadioEncontrado);
        assertEquals("Estadio Nacional", estadioEncontrado.getNombre());
    }

    @Test
    public void testEliminarEstadio() {
        // Simulamos que el repositorio elimina el estadio correctamente
        doNothing().when(estadioRepository).deleteById(1L);

        // Llamamos al método del servicio
        estadioService.eliminarEstadio(1L);

        // Verificamos que el repositorio haya sido llamado una vez
        verify(estadioRepository, times(1)).deleteById(1L);
    }
}
