package com.cesur.gestorpedidos.models.usuario;

import com.cesur.gestorpedidos.models.pedido.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un Usuario de la base de datos
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Entity
@javax.persistence.Table(name="usuario")
public class Usuario implements Serializable {

    @javax.persistence.Id
    @javax.persistence.Column(name="ID")
    private Integer id;
    private String nombre;
    private String pass;
    private String email;

    @javax.persistence.OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos = new ArrayList<>();

    /**
     * Metodo toString de la clase Usuario
     * @return String con los datos del usuario
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", pedidos=" + pedidos +
                '}';
    }
}
