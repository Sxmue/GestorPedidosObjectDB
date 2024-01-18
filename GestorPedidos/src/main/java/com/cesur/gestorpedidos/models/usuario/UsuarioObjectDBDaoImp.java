package com.cesur.gestorpedidos.models.usuario;

import com.cesur.gestorpedidos.ObjectDB.databaseObjectDB.ObjectDBUtil;
import com.cesur.gestorpedidos.Session;
import com.cesur.gestorpedidos.models.pedido.Pedido;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;


/**
 * Implementacion del dao de Usuario con ObjectDB
 */
public class UsuarioObjectDBDaoImp implements UsuarioDAO{

    /**
     * Metodo para leer un Usuario de la base de datos
     *
     * @param nombre nombre del usuario
     * @param pass   contraseña del usuario
     * @return el usuario de la base de datos
     */
    @Override
    public Usuario load(String nombre, String pass) {
        Usuario usuario = null;

        try {
            EntityManager em = ObjectDBUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.nombre = :nombre AND u.pass = :pass", Usuario.class);
            query.setParameter("nombre", nombre);
            query.setParameter("pass", pass);

            try {
                usuario = (Usuario) query.getSingleResult();

            } catch (NoResultException ignored) {

            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }

    /**
     * Metodo para borrar un pedido del usuario
     *
     * @param pedido el pedido que queremos borrar
     * @return el Usuario con la lista de pedidos actualizada
     */
    @Override
    public Usuario borrarPedido(Pedido pedido) {
        Usuario usuario = null;

        try {
            EntityManager em = ObjectDBUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Usuario usuarioLogueado = em.find(Usuario.class, Session.getUsuarioLogueado().getId());

            usuarioLogueado.getPedidos().remove(pedido);

            usuario = usuarioLogueado;

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
