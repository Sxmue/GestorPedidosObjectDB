package com.cesur.gestorpedidos.models.item;

import com.cesur.gestorpedidos.ObjectDB.databaseObjectDB.ObjectDBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;

/**
 * Implementacion del DAO de Items
 */
public class ItemObjectDBDaoImp implements ItemDAO{

    /**
     * Metodo para obtener todos los items
     * @return
     */
    @Override
    public ArrayList<Item> allItems() {
        ArrayList<Item> items = new ArrayList<>();

        try {
            EntityManager em = ObjectDBUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Query query = em.createQuery("SELECT i FROM Item i");
            items = (ArrayList<Item>) query.getResultList();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    /**
     * Metodo para guardar un item
     * @param item
     */
    @Override
    public void addItem(Item item) {
        try {
            EntityManager em = ObjectDBUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            em.persist(item);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para borrar un item
     * @param item
     */
    @Override
    public void delete(Item item) {
        try {
            EntityManager em = ObjectDBUtil.getEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            Item mergedItem = em.merge(item);
            em.remove(mergedItem);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
