package com.cesur.gestorpedidos.models.pedido;

import com.cesur.gestorpedidos.databaseHibernate.HibernateUtil;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;


/**
 * Implementacion del DAO pedido
 */
public class PedidoDAOImp implements PedidoDAO {

    /* Log para trazar la clase */
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PedidoDAOImp.class);


    /**
     * Metodo para borrar un pedido
     * @param p pedido que queremos borrar
     */
    @Override
    public void borrarPedido(Pedido p) {

        //Lambda para acceder a la base de datos
        HibernateUtil.getSessionFactory().inTransaction(session -> {

            //Traemos el objeto a borrar para que sea persistente
            Pedido pd = session.get(Pedido.class, p.getId());

            //Lo eliminamos
            session.remove(pd);

        });

    }

    /**
     * Metodo para guardar un pedido
     * @param p pedido que queremos guardar
     * @return el pedido persistido
     */
    @Override
    public Pedido guardarPedido(Pedido p) {

        Pedido pSave = null;

        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();


            s.persist(p);

            p.setCodigo(p.getCodigo() + p.getId());


            pSave = p;

            t.commit();


        } catch (Exception e) {

            LOG.error(e.getMessage());

        }

        return pSave;

    }

    /**
     * Metodo para actualizar un pedido
     * @param p el pedido que queremos actualizar
     */
    @Override
    public void  actualizarPedido(Pedido p) {


        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {

            Transaction t = s.beginTransaction();

            Pedido update = s.get(Pedido.class, p.getId());

            Pedido.merge(update, p);

            t.commit();


        }
    }
}
