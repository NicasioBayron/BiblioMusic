package com.resenas.resenas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.resenas.resenas.model.Resenas;
import com.resenas.resenas.repository.ResenasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ResenasServiceTest {

    @Mock
    private ResenasRepository resenasRepository;

    @InjectMocks
    private ResenasService resenasService;

    private Resenas resenaModel;

    @BeforeEach
    void setUp() {
        resenaModel = new Resenas();
        resenaModel.setIdResena(1L);
        resenaModel.setIdProducto(100L);
        resenaModel.setIdUsuario(50L);
        resenaModel.setCalificacion(5); // 5 estrellitas como nosotras
        resenaModel.setComentario("¡Excelente servicio, el pollo frito estaba mundial!");
    }

    @Test
    void testListarResenas() {
        when(resenasRepository.findAll()).thenReturn(List.of(resenaModel));

        List<Resenas> result = resenasService.listarResenas();

        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getIdProducto());
    }

    @Test
    void testBuscarPorId() {
        when(resenasRepository.findById(1L)).thenReturn(Optional.of(resenaModel));

        Resenas result = resenasService.buscarPorId(1L);

        assertNotNull(result);
        assertEquals(5, result.getCalificacion());
    }

    @Test
    void testCrearResena() {
        when(resenasRepository.save(any(Resenas.class))).thenReturn(resenaModel);

        Resenas result = resenasService.crearResena(resenaModel);

        assertNotNull(result);
        assertEquals("¡Excelente servicio, el pollo frito estaba mundial!", result.getComentario());
    }

    @Test
    void testActualizarResena() {
        Resenas resenaActualizada = new Resenas();
        resenaActualizada.setIdProducto(100L);
        resenaActualizada.setIdUsuario(50L);
        resenaActualizada.setCalificacion(1);
        resenaActualizada.setComentario("Pésimo, llegó frío.");

        when(resenasRepository.findById(1L)).thenReturn(Optional.of(resenaModel));
        when(resenasRepository.save(any(Resenas.class))).thenReturn(resenaActualizada);

        Resenas result = resenasService.actualizarResena(1L, resenaActualizada);

        assertNotNull(result);
        assertEquals(1, result.getCalificacion());
        assertEquals("Pésimo, llegó frío.", result.getComentario());
    }

    @Test
    void testEliminarResena() {
        when(resenasRepository.existsById(1L)).thenReturn(true);
        doNothing().when(resenasRepository).deleteById(1L);

        boolean result = resenasService.eliminarResena(1L);

        assertTrue(result);
        verify(resenasRepository, times(1)).deleteById(1L);
    }
}
