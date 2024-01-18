package com.cesur.gestorpedidos.controllers;

import com.cesur.gestorpedidos.App;
import com.cesur.gestorpedidos.Session;
import com.cesur.gestorpedidos.models.pedido.Pedido;
import com.cesur.gestorpedidos.models.pedido.PedidoDAO;
import com.cesur.gestorpedidos.models.pedido.PedidoDAOImp;
import com.cesur.gestorpedidos.models.pedido.PedidoObjectDBDaoImp;
import com.cesur.gestorpedidos.models.usuario.UsuarioObjectDBDaoImp;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;

import java.net.URL;
import java.util.*;

/**
 * Clase que representa la ventana principal en nuestra aplicacion
 */
public class PrincipalView implements Initializable {

    /*Variable utilizada para la obtencion de los pedidos de la base de datos*/
    PedidoObjectDBDaoImp pedidoDAO = new PedidoObjectDBDaoImp();

    /*Variable utilizada para trabajar con los usuarios en la bbdd*/
    UsuarioObjectDBDaoImp usuarioDao = new UsuarioObjectDBDaoImp();
    ObservableList<Pedido> observablePedidos;


    @javafx.fxml.FXML
    private TableView<Pedido> tablaPedidos;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cCodigo;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> cFecha;
    @javafx.fxml.FXML
    private TableColumn<Pedido, Integer> cTotal;
    @javafx.fxml.FXML
    private Label txtBienvenido;
    @javafx.fxml.FXML
    private MenuItem acercaDe;
    @javafx.fxml.FXML
    private MenuItem logOut;
    @javafx.fxml.FXML
    private ComboBox<String> comboFecha;
    @javafx.fxml.FXML
    private Button btnCrear;
    @javafx.fxml.FXML
    private Button btnEliminar1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Observable con los pedidos del ususario
        observablePedidos = FXCollections.observableArrayList(Session.getUsuarioLogueado().getPedidos());


        txtBienvenido.setText("Bienvenido" + "\n" + Session.getUsuarioLogueado().getNombre());


        //Inicializacion de la tabla Pedidos
        inicializadorTabla();


        //---------Combobox---------/

        //Inicializacion del combobox
        inicializadorCombobox(observablePedidos);


        //Listener a la propiedad del combo para cambiar los datos mostrados en la tabla segun lo seleccionado
        comboFecha.valueProperty().addListener((observableValue, s, t1) -> {

            //Observable espejo que utilizamos para mostar solo los pedidos de la fecha seleccionada
            ObservableList<Pedido> observableFecha = FXCollections.observableArrayList(Session.getUsuarioLogueado().getPedidos());

            //Si esta seleccionada una opcion que no sea Cualquiera en el combo
            if (!Objects.equals(t1, "Cualquiera")) {

                //Recorremos todos los pedidos
                for (Pedido r : observablePedidos) {

                    //Si el pedido no tiene la fecha seleccionada
                    if (!r.getFecha().contains(t1)) {

                        //lo eliminamos de la lista espejo
                        observableFecha.remove(r);

                    }

                }
                //Limpiamos la tabla
                tablaPedidos.getItems().clear();
                //Le pasamos la espejo con los pedidos de la decha que queremos
                tablaPedidos.getItems().addAll(observableFecha);
            } else {

                //En cambio si esta cualquiera la tabla mostrara la lista original
                tablaPedidos.getItems().clear();
                tablaPedidos.getItems().addAll(observablePedidos);
            }

        });


    }

    /**
     * Metodo para mostrar la informacion del pedido con un doble click
     *
     * @param event Listener de la propiedad
     */
    @javafx.fxml.FXML
    public void pedidoInfo(Event event) {
        if (((MouseEvent) event).getClickCount() == 2 && (tablaPedidos.getSelectionModel().getSelectedItem() != null)) {

            Session.setPedidoactual(tablaPedidos.getSelectionModel().getSelectedItem());

            App.loadFXML("product-view.fxml");

        }
    }

    /**
     * Metodo para la inicializacion de la tabla pedidos
     */
    private void inicializadorTabla() {
        tablaPedidos.getItems().clear();
        tablaPedidos.refresh();
        cCodigo.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getCodigo()));
        cFecha.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getFecha()));
        cTotal.setCellValueFactory(fila -> new SimpleObjectProperty<>((fila.getValue().getTotal())));
        tablaPedidos.getItems().addAll(observablePedidos);
    }

    /**
     * Metodo para la inicialización del combo box
     *
     * @param observablePedidos observable con la lista de pedidos
     */
    private void inicializadorCombobox(ObservableList<Pedido> observablePedidos) {
        Set<String> anhos = new HashSet<>();
        anhos.add("Cualquiera");

        for (Pedido s : observablePedidos) {

            String[] d = s.getFecha().split("-");

            anhos.add(d[0]);
        }
        comboFecha.getItems().addAll(anhos);
    }

    /**
     * Metodo para mostrar el mensaje "Acerca de"
     *
     * @param actionEvent listener del evento
     */
    @javafx.fxml.FXML
    public void mostrarAcercaDe(ActionEvent actionEvent) {

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Informacion de la Aplicacion");
        alert.setContentText("Creado por Samuel Leiva Álvarez con todo el cariño del mundo para Francisco");
        alert.showAndWait();


    }

    /**
     * Metodo para hacer Logout
     *
     * @param actionEvent listener del evento
     */
    @javafx.fxml.FXML
    public void logOut(ActionEvent actionEvent) {

        App.loadFXML("login.fxml");
        Session.setUsuarioLogueado(null);

    }

    /**
     * Metodo para eliminar un pedido
     *
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void delete(ActionEvent actionEvent) {

        Pedido p = tablaPedidos.getSelectionModel().getSelectedItem();

        if (p != null) {
            var alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("CUIDADO");
            alert.setContentText("¿Estas seguro de que deseas borrar este pedido? Los cambios seran permanentes");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {

                    //Borramos el pedido de la base de datos
                    pedidoDAO.borrarPedido(p);

                    //Le actualizamos al usuario logueado la lista de pedidos con el pedido borrado
                    Session.setUsuarioLogueado(usuarioDao.borrarPedido(p));

                    //Lo quitamos del observable y actualizamos la tabla
                    observablePedidos.remove(p);
                    tablaPedidos.getItems().clear();
                    tablaPedidos.getItems().addAll(observablePedidos);
                    tablaPedidos.refresh();

                }
            });
        }
    }

    /**
     * Listener para crear un pedido
     *
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void crearPedido(ActionEvent actionEvent) {

        Pedido p = new Pedido();


        //Le añadimos el pedido creado al usuario en local
        Session.getUsuarioLogueado().getPedidos().add(p);

        //Guardamos el pedido en la base de datos
        Pedido t = pedidoDAO.guardarPedido(p);

        //Lo ponemos de pedido actual
        Session.setPedidoactual(t);

        //cambiamos de ventana
        App.loadFXML("product-view.fxml");

    }

}