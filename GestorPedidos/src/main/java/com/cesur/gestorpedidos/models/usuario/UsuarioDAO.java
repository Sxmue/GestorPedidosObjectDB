package com.cesur.gestorpedidos.models.usuario;


import com.cesur.gestorpedidos.models.pedido.Pedido;

/**
 * Interfaz para definir el DAO de usuario
 */
public interface UsuarioDAO {

    public Usuario load(String nombre,String pass);
    public Usuario borrarPedido(Pedido p);
}
