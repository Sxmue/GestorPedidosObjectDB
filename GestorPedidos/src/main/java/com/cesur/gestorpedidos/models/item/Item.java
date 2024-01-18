package com.cesur.gestorpedidos.models.item;

import com.cesur.gestorpedidos.models.pedido.Pedido;
import com.cesur.gestorpedidos.models.producto.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import java.io.Serializable;

/**
 * Clase que representa un Item de la base de datos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Entity
@javax.persistence.Table(name = "item")
public class Item implements Serializable {

    @javax.persistence.Id
    @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Integer id;

    @javax.persistence.ManyToOne
    @javax.persistence.JoinColumn(name="codigoPedido",referencedColumnName = "codigo")
    private Pedido pedido;

    private Integer cantidad;

    @javax.persistence.ManyToOne
    @JoinColumn(name="idProducto")
    private Producto producto;


    /**
     * toString de la clase Item
     * @return String con los datos de la clase Item
     */
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", pedido=" + pedido.getCodigo() +
                ", cantidad=" + cantidad +
                ", producto=" + producto.getNombre() +
                '}';
    }
}
