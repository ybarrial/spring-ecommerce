package com.curso.ecommerce.service;

import com.curso.ecommerce.model.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Optional<Usuario> findById(Integer id);

}
