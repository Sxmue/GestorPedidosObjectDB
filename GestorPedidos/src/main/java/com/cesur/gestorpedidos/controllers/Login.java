package com.cesur.gestorpedidos.controllers;

import com.cesur.gestorpedidos.App;
import com.cesur.gestorpedidos.ObjectDB.databaseObjectDB.ObjectDBUtil;
import com.cesur.gestorpedidos.Session;
import com.cesur.gestorpedidos.models.producto.Producto;
import com.cesur.gestorpedidos.models.usuario.Usuario;
import com.cesur.gestorpedidos.models.usuario.UsuarioObjectDBDaoImp;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Login implements Initializable {
    static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Login.class);
    ArrayList<Usuario> users;

    private final UsuarioObjectDBDaoImp usuarioDAO = new UsuarioObjectDBDaoImp();

    @javafx.fxml.FXML
    private ImageView imgTop;
    @javafx.fxml.FXML
    private Label txtMarca;
    @javafx.fxml.FXML
    private ImageView userImg;
    @javafx.fxml.FXML
    private TextField txtUser;
    @javafx.fxml.FXML
    private ImageView imgPass;
    @javafx.fxml.FXML
    private PasswordField txtPass;
    @javafx.fxml.FXML
    private Button btnLogin;
    @javafx.fxml.FXML
    private MenuItem acercaDe;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EntityManager em = ObjectDBUtil.getEntityManager();

        try {
            em.getTransaction().begin();

            Usuario u1 =new Usuario(1,"Usuario1", "contraseña123", "usuario1@example.com",new ArrayList<>());
            Usuario u2 = new Usuario(2,"Usuario2", "password456", "usuario2@example.com",new ArrayList<>());


            em.persist(u1);
            em.persist(u2);


            em.persist(new Producto(1,"Smartphone Samsung Galaxy S22", 799.99, 15));
            em.persist(new Producto(2,"Laptop Dell XPS 15", 1499.99, 10));
            em.persist(new Producto(3,"PlayStation 5 Pro", 599.99, 8));
            em.persist(new Producto(4,"Apple iPad Pro", 899.00, 12));
            em.persist(new Producto(5,"Smartwatch Apple Watch Series 7", 399.50, 20));
            em.persist(new Producto(6,"Wireless Earbuds Sony WF-1000XM4", 249.99, 30));
            em.persist(new Producto(7,"Gaming Mouse Logitech G Pro X", 89.99, 50));
            em.persist(new Producto(8,"4K Ultra HD TV LG OLED CX", 1299.99, 18));
            em.persist(new Producto(9,"Nintendo Switch OLED Model", 349.00, 25));
            em.persist(new Producto(10,"Smart Home Speaker Amazon Echo", 99.95, 40));
            em.persist(new Producto(11,"Bluetooth Keyboard Logitech K811", 79.99, 35));
            em.persist(new Producto(12,"Portable External SSD Samsung T7", 199.99, 22));
            em.persist(new Producto(13,"Wireless Router ASUS RT-AX86U", 179.95, 28));
            em.persist(new Producto(14,"Nvidia GeForce RTX 3080", 699.99, 15));
            em.persist(new Producto(15,"Smart Thermostat Google Nest", 249.00, 20));
            em.persist(new Producto(16,"Bluetooth Soundbar Sony HT-X9000F", 399.99, 12));
            em.persist(new Producto(17,"DJI Mavic Air 2 Drone", 799.00, 7));
            em.persist(new Producto(18,"Fitness Tracker Fitbit Charge 5", 149.95, 30));
            em.persist(new Producto(19,"GoPro Hero 10 Black", 499.99, 9));
            em.persist(new Producto(29,"Desktop Gaming PC Alienware Aurora", 1999.99, 5));


            em.getTransaction().commit();


        }catch( Exception e){


        }
        finally {
            em.close();
        }


        LOG.info("Ventana de Login cargada correctamente");

    }

    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {

        String name = txtUser.getText();
        String pass = txtPass.getText();

        Usuario u = usuarioDAO.load(name, pass);

        if (!name.isEmpty() && !pass.isEmpty()) {
            LOG.info("Datos validos");

            if (u!=null) {

                LOG.info("Usuario Valido");

                Session.setUsuarioLogueado(u);
                App.loadFXML("principal-view.fxml");
                LOG.info("Usuario logueado");

            } else {

                LOG.error("Usuario no valido");

                var alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("usuario o contraseña incorrectos");
                alert.showAndWait();

            }

        } else {

            LOG.error("Datos no correctos");
            var alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Introducir datos validos por favor");
            alert.showAndWait();

        }


    }


    @javafx.fxml.FXML
    public void mostrarAcercaDe(ActionEvent actionEvent) {

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Informacion de la Aplicacion");
        alert.setContentText("Creado por Samuel Leiva Álvarez con todo el cariño del mundo para Francisco");
        alert.showAndWait();

    }
}
