package com.cesur.gestorpedidos.models.pedido;

import com.cesur.gestorpedidos.ObjectDB.databaseObjectDB.ObjectDBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Random;

/**
 * Implementacion del dao de Pedido con ObjectDB
 */
public class PedidoObjectDBDaoImp implements PedidoDAO{

    /**
     * Metodo para borrar un pedido
     * @param pedido pedido que queremos borrar
     */
    @Override
    public void borrarPedido(Pedido pedido) {
        try {
            EntityManager em = ObjectDBUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Pedido pedidoPersistido = em.find(Pedido.class, pedido.getId());

            em.remove(pedidoPersistido);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para guardar un pedido
     * @param pedido pedido que queremos guardar
     * @return el pedido persistido
     */
    @Override
    public Pedido guardarPedido(Pedido pedido) {
        Pedido pedidoGuardado = null;

        try {
            EntityManager em = ObjectDBUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            em.persist(pedido);

            pedido.setCodigo(pedido.getCodigo() +  (new Random().nextInt(600) + 1));

            pedidoGuardado = pedido;

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pedidoGuardado;
    }

    /**
     * Metodo para actualizar un pedido
     * @param pedido el pedido que queremos actualizar
     */
    @Override
    public void actualizarPedido(Pedido pedido) {
        try {
            EntityManager em = ObjectDBUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            em.merge(pedido);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
