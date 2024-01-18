package com.cesur.gestorpedidos.models.pedido;

/**
 * Interfaz para definir el DAO de pedido
 */
public interface PedidoDAO {

    public void borrarPedido(Pedido p);

    public Pedido guardarPedido(Pedido p);

    public void  actualizarPedido(Pedido p);

}
