package com.login.login.DTO;

import lombok.Data;

@Data
public class RegisterDTO {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
}