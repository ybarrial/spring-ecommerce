package com.curso.ecommerce.service.impl;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.repository.IDetalleOrdenRepository;
import com.curso.ecommerce.service.IDetalleOrdenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleOrdenServiceImpl implements IDetalleOrdenService {

    @Autowired
    private IDetalleOrdenRepository detalleOrdenRepository;

    @Override
    public DetalleOrden save(DetalleOrden detalleOrden) {
        return detalleOrdenRepository.save(detalleOrden);
    }
}
