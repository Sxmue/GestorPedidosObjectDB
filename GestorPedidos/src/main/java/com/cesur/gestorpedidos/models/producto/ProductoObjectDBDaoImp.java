package com.cesur.gestorpedidos.models.producto;

import com.cesur.gestorpedidos.ObjectDB.databaseObjectDB.ObjectDBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Implementacion del DAO de productos con ObjectDB
 */
public class ProductoObjectDBDaoImp implements ProductoDAO{


    /**
     * Metodo para obtener todos los productos
     * @return Lista con todos los productos
     */
    @Override
    public ArrayList<Producto> allProductos() {
        ArrayList<Producto> productos = new ArrayList<>();

        try {
            EntityManager em = ObjectDBUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Query query = em.createQuery("SELECT p FROM Producto p");
            productos = (ArrayList<Producto>) query.getResultList();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productos;
    }
}
