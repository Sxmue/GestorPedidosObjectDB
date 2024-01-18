package com.cesur.gestorpedidos.models.producto;


import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * Implementacion del DAO producto
 */
public class ProductoDAOImp implements ProductoDAO{

    /* Log para trazar la clase */
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ProductoDAOImp.class);

    @Override
    public ArrayList<Producto> allProductos() {
        return null;
    }
}
