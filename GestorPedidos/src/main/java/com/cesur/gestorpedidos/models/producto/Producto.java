package com.cesur.gestorpedidos.models.producto;

import com.cesur.gestorpedidos.models.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un producto de la base de datos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Entity
@javax.persistence.Table(name = "producto")
public class Producto implements Serializable {

    @javax.persistence.Id
    private Integer id;
    private String nombre;
    private Double precio;
    private Integer cantidad;

}
