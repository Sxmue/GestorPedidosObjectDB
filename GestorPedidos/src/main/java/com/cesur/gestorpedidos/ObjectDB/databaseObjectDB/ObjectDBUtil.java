package com.cesur.gestorpedidos.ObjectDB.databaseObjectDB;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ObjectDBUtil {
    private static final EntityManagerFactory emf;

    static{
        emf = Persistence.createEntityManagerFactory("data.odb");
    }

    public static EntityManager getEntityManager(){
        EntityManager em = emf.createEntityManager();
        return em;
    }




}
