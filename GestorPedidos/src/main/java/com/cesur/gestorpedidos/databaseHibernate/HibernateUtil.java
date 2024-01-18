package com.cesur.gestorpedidos.databaseHibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.LoggerFactory;

public class HibernateUtil {

    /* Log para trazar la clase */
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HibernateUtil.class);

    private static SessionFactory sf = null;

    static{

        try {
            //-------Configuracion hibernate--------

            Configuration cfg = new Configuration();
            cfg.configure();

            //Session factory
            sf = cfg.buildSessionFactory();

            LOG.info("Session Factory creada con exito");

        }catch(Exception e){
            System.out.println(e.getMessage());
            LOG.error("Error al crear session factory");

        }

    }

    public static SessionFactory getSessionFactory() {
        return sf;
    }




}
