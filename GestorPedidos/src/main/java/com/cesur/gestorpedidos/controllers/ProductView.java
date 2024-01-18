package com.cesur.gestorpedidos.controllers;

import com.cesur.gestorpedidos.models.item.ItemObjectDBDaoImp;
import com.cesur.gestorpedidos.models.pedido.PedidoObjectDBDaoImp;
import com.cesur.gestorpedidos.models.producto.Producto;
import com.cesur.gestorpedidos.models.producto.ProductoObjectDBDaoImp;
import databaseJDBC.Database;
import com.cesur.gestorpedidos.App;
import com.cesur.gestorpedidos.Session;
import com.cesur.gestorpedidos.models.item.Item;
import com.cesur.gestorpedidos.models.item.ItemDAOImp;
import com.cesur.gestorpedidos.models.pedido.PedidoDAOImp;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import com.itextpdf.text.Document;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class ProductView implements Initializable {

    /*Obervable para el trabajo con la lista*/
    private ObservableList<Item> observableItems;

    HashMap hm;

    Connection c;

    JasperPrint jasperPrint;

    /*Variable para trabajar con el dao Items*/
     ItemObjectDBDaoImp itemDAOImp = new ItemObjectDBDaoImp();

    /*Variable para trabajar con el dao Pedidos*/
    PedidoObjectDBDaoImp pedidoDAO = new PedidoObjectDBDaoImp();

    /*Variable para trabajar con el dao de Productos*/
    ProductoObjectDBDaoImp productoDao = new ProductoObjectDBDaoImp();

    /*Lista para guardar los items creados*/
    List<Item> items = new ArrayList<>();

    @javafx.fxml.FXML
    private TableView<Item> tablaProductos;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cNombre;
    @javafx.fxml.FXML
    private TableColumn<Item, Double> cPrecio;
    @javafx.fxml.FXML
    private TableColumn<Item, Integer> cCantidad;

    @javafx.fxml.FXML
    private TableColumn<Item, Double> cTotal;
    @javafx.fxml.FXML
    private Label txtPedido;
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private MenuItem acercaDe;
    @javafx.fxml.FXML
    private MenuItem logOut;
    @javafx.fxml.FXML
    private MenuItem pdf;
    @javafx.fxml.FXML
    private ComboBox<Producto> comboProductos;
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerCantidad;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private Button btnEliminar;
    @javafx.fxml.FXML
    private MenuItem menuVisualizar;
    @javafx.fxml.FXML
    private MenuItem menuExportar;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Inicializaciones para la generacion de informes
         c = Database.getConnection();
        hm = new HashMap<>();

        hm.put("idPedido",Session.getPedidoactual().getCodigo());

        try {
            jasperPrint = JasperFillManager.fillReport("src/main/resources/Informe.jasper",hm,c);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }


        if (comboProductos.getValue() != null) {
            spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, comboProductos.getValue().getCantidad(), 1, 1));
        }

        txtPedido.setText("Pedido:" + "\n" + Session.getPedidoactual().getCodigo());


        //Inicializamos la tabla
        inicializadorTablaItems();


        //Mapeo del combo para que utilice el nombre de los productos
        comboProductos.setConverter(new StringConverter<Producto>() {

            @Override
            public String toString(Producto producto) {
                if (producto != null) {

                    return producto.getNombre();
                } else {

                    return null;
                }
            }

            @Override
            public Producto fromString(String s) {
                return null;
            }
        });

        //Inicializacion del combo
        comboProductos.getItems().addAll(productoDao.allProductos());


        //Listener a la propiedad del combo para cambiar los datos mostrados en la tabla segun lo seleccionado
        comboProductos.valueProperty().addListener((observableValue, s, t1) -> {

            spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, comboProductos.getValue().getCantidad(), 1, 1));


        });

    }

    /**
     * Metodo inicializador de la tabla Items
     */
    private void inicializadorTablaItems() {
        observableItems = FXCollections.observableArrayList(Session.getPedidoactual().getItems());

        cNombre.setCellValueFactory(fila -> new SimpleStringProperty(fila.getValue().getProducto().getNombre()));

        cPrecio.setCellValueFactory(fila -> new SimpleObjectProperty<>(fila.getValue().getProducto().getPrecio()));

        cCantidad.setCellValueFactory(fila -> new SimpleObjectProperty<>(fila.getValue().getCantidad()));

        cTotal.setCellValueFactory(fila -> new SimpleObjectProperty<>((fila.getValue().getCantidad() * fila.getValue().getProducto().getPrecio())));

        tablaProductos.getItems().addAll(observableItems);
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {

        App.loadFXML("principal-view.fxml");

    }

    @javafx.fxml.FXML
    public void mostrarAcercaDe(ActionEvent actionEvent) {

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Informacion de la Aplicacion");
        alert.setContentText("Creado por Samuel Leiva Álvarez con todo el cariño del mundo para Francisco");
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void logOut(ActionEvent actionEvent) {

        App.loadFXML("login.fxml");
        Session.setUsuarioLogueado(null);
        Session.setPedidoactual(null);
    }

    @javafx.fxml.FXML
    public void exportarPDF(ActionEvent actionEvent) {
        try (FileOutputStream fileOut = new FileOutputStream("TablaProductos.pdf")) {
            Document document = new Document();
            PdfWriter.getInstance(document, fileOut);
            (document).open();

            // Crear una tabla en el documento PDF
            PdfPTable pdfTable = new PdfPTable(4);
            pdfTable.addCell("Nombre");
            pdfTable.addCell("Precio");
            pdfTable.addCell("Cantidad");
            pdfTable.addCell("Total");

            observableItems.forEach(item -> {
                pdfTable.addCell(item.getProducto().getNombre()); // Nombre
                pdfTable.addCell(String.valueOf(item.getProducto().getPrecio())); // Precio
                pdfTable.addCell(String.valueOf(item.getCantidad())); // Cantidad
                pdfTable.addCell(String.valueOf(item.getCantidad() * item.getProducto().getPrecio())); // Total
            });

            document.add(pdfTable);
            document.close();
        } catch (DocumentException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void saveItems(ActionEvent actionEvent) {

        //Para cada item creado en la lista
        for (Item it : items) {
            //cambiamos el total del pedido
            Session.getPedidoactual().setTotal((int) (Session.getPedidoactual().getTotal() + it.getProducto().getPrecio() * it.getCantidad()));

            //Se lo añadimos en local a la lista
            Session.getPedidoactual().getItems().add(it);

            //Persistimos dicho item
            itemDAOImp.addItem(it);
        }

        //Sincronizamos el objeto en la base de datos con el pedido actual
        pedidoDAO.actualizarPedido(Session.getPedidoactual());

        Session.setPedidoactual(null);

        App.loadFXML("principal-view.fxml");
    }

    @javafx.fxml.FXML
    public void añadir(ActionEvent actionEvent) {

        //Creamos un nuevo item
        Item it = new Item();

        //Setteamos sus valores
        it.setCantidad(spinnerCantidad.getValue()); //Poner otro combo para setear la cantidad
        it.setPedido(Session.getPedidoactual());
        it.setProducto(comboProductos.getValue());

        //Lo añadimos a la lista de productos creados
        items.add(it);

        //Lo añadimos a la tabla tambien
        tablaProductos.getItems().add(it);


    }

    @javafx.fxml.FXML
    public void eliminar(ActionEvent actionEvent) {

        var alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("CUIDADO");
        alert.setContentText("¿Estas seguro de que deseas borrar este producto del pedido? Los cambios seran permanentes");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    if (tablaProductos.getSelectionModel().getSelectedItem() != null) {

                        //Borramos el item de la base de datos
                        itemDAOImp.delete(tablaProductos.getSelectionModel().getSelectedItem());

                        //Le restamos el precio del item al pedido en local
                            Session.getPedidoactual().setTotal((int) (Session.getPedidoactual().getTotal() - tablaProductos.getSelectionModel().getSelectedItem().getProducto().getPrecio()));
                         if(Session.getPedidoactual().getTotal()<0) Session.getPedidoactual().setTotal(0);
                         if(Session.getPedidoactual().getItems().isEmpty()) Session.getPedidoactual().setTotal(0);

                        //Le quitamos el item en local al pedido
                        Session.getPedidoactual().getItems().remove(tablaProductos.getSelectionModel().getSelectedItem());

                        //Lo quitamos de la tabla y lo refrescamos
                        tablaProductos.getItems().remove(tablaProductos.getSelectionModel().getSelectedItem());
                        tablaProductos.refresh();

                    }
                } catch (IllegalArgumentException ignored) {
                }


            }
        });


    }

    @javafx.fxml.FXML
    public void visualizarAlbaran(ActionEvent actionEvent) throws JRException {
        JRViewer viewer = new JRViewer(jasperPrint);
        JFrame frame = new JFrame("Albarán");
        frame.getContentPane().add(viewer);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }

    @javafx.fxml.FXML
    public void exportarAlbaran(ActionEvent actionEvent) throws JRException {

        JRPdfExporter exp = new JRPdfExporter();
        exp.setExporterInput(new SimpleExporterInput(jasperPrint));
        exp.setExporterOutput(new SimpleOutputStreamExporterOutput("Albarán-"+Session.getPedidoactual().getCodigo()+".pdf"));
        exp.setConfiguration(new SimplePdfExporterConfiguration());
        exp.exportReport();

    }
}