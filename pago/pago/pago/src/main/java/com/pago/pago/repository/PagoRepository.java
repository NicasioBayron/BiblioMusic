package com.pago.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pago.pago.model.Pago;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByIdCarritoIn(List<Long> idCarritos);

    @Query("SELECT p FROM Pago p WHERE MONTH(p.fechaPago) = :mes")
    List<Pago> findByMes(@Param("mes") int mes);

}
