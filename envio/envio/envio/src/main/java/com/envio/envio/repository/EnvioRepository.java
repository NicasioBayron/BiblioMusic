package com.envio.envio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.envio.envio.model.Envio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface EnvioRepository extends JpaRepository<Envio, Long> {

    @Query("SELECT e FROM Envio e WHERE MONTH(e.fecha_envio) = :mes")
    List<Envio> findByMes(@Param("mes") int mes);

    @Query("SELECT e FROM Envio e WHERE e.id_usuario = :idUsuario")
    List<Envio> findByIdUsuario(@Param("idUsuario") Long idUsuario);

}
