package com.curso.ecommerce.service.impl;

import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.repository.ProductoRepository;
import com.curso.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public Producto save(Producto producto) {
        return this.productoRepository.save(producto);
    }

    @Override
    public Optional<Producto> get(Integer id) {
        return this.productoRepository.findById(id);
    }

    @Override
    public void update(Producto producto) {
        this.productoRepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        this.productoRepository.deleteById(id);
    }
}
