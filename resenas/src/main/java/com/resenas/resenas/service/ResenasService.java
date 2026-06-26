package com.resenas.resenas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.resenas.resenas.model.Resenas;
import com.resenas.resenas.repository.ResenasRepository;

@Service
public class ResenasService {

    private ResenasRepository resenasRepository;

    public List<Resenas> listarResenas() {
        return resenasRepository.findAll();
    }

    public Resenas buscarPorId(Long id) {
        return resenasRepository.findById(id).orElse(null);
    }

    public Resenas crearResena(Resenas resena) {
        return resenasRepository.save(resena);
    }

    public Resenas actualizarResena(Long id, Resenas resenaActualizada) {
        Resenas resenaExistente = buscarPorId(id);

        if (resenaExistente == null) {
            return null;
        }

        resenaExistente.setIdProducto(resenaActualizada.getIdProducto());
        resenaExistente.setIdUsuario(resenaActualizada.getIdUsuario());
        resenaExistente.setCalificacion(resenaActualizada.getCalificacion());
        resenaExistente.setComentario(resenaActualizada.getComentario());

        return resenasRepository.save(resenaExistente);
    }

    public boolean eliminarResena(Long id) {
        if (!resenasRepository.existsById(id)) {
            return false;
        }

        resenasRepository.deleteById(id);
        return true;
    }
}