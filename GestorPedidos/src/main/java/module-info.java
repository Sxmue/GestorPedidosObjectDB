module com.cesur.gestortareas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires lombok;
    requires java.sql;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires itextpdf;
    requires jasperreports;

    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires java.desktop;
    requires javax.persistence;


    opens img;
    exports com.cesur.gestorpedidos;
    opens com.cesur.gestorpedidos.controllers;
    opens com.cesur.gestorpedidos.models.usuario;
    opens com.cesur.gestorpedidos.models.item;
    opens com.cesur.gestorpedidos.models.pedido;
    opens com.cesur.gestorpedidos.models.producto;
    exports com.cesur.gestorpedidos.traza;
    opens com.cesur.gestorpedidos.traza to javafx.fxml;
    exports com.cesur.gestorpedidos.controllers;
    opens com.cesur.gestorpedidos;
}