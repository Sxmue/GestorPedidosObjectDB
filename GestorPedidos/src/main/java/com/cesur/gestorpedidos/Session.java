package com.cesur.gestorpedidos;

import com.cesur.gestorpedidos.models.usuario.Usuario;
import com.cesur.gestorpedidos.models.pedido.Pedido;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase para almacenar datos que usamos en diferentes Stages
 */
public class Session {

    /* Elemento statico para almacenar al Usuario logueado */
    @Getter
    @Setter
    private static Usuario usuarioLogueado;

    /* Elemento statico que usamos almacenar el pedido seleccionado */
    @Getter
    @Setter
    private static Pedido pedidoactual;
    
}
