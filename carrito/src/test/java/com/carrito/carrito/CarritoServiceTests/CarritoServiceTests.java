package com.carrito.carrito.CarritoServiceTests;

import com.carrito.carrito.dto.CarritoDTO;
import com.carrito.carrito.model.Carrito;
import com.carrito.carrito.model.ItemCarrito;
import com.carrito.carrito.repository.CarritoRepository;
import com.carrito.carrito.service.CarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTests {

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    private Carrito carritoPrueba;

    @BeforeEach
    void setUp() {
        carritoPrueba = new Carrito();
        carritoPrueba.setIdCarrito(1L);
        carritoPrueba.setIdUsuario(100L);

        ItemCarrito item1 = new ItemCarrito();
        item1.setIdItemCarrito(10);
        item1.setIdProducto(200L);
        item1.setNombreProducto("Guitarra");
        item1.setPrecio(1500.0);
        item1.setCantidad(1);

        carritoPrueba.setItems(Arrays.asList(item1));
    }

}
