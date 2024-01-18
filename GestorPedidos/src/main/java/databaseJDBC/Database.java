package databaseJDBC;
import com.cesur.gestorpedidos.App;
import lombok.Getter;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    //Logger para trazar la aplicacion, estatico para llamar siempre al mismo logger
    static final org.slf4j.Logger LOG= LoggerFactory.getLogger(Database.class);

    /**
     * -- GETTER --
     *  Metodo para devolver la conexion
     *
     * @return
     */
    @Getter
    private static final Connection connection;

    static{


        //String que almacena la url donde nos conectamos
        String url;
        //Variable para el usuario que por defecto es root
        String user;
        //Variable para la contraseña
        String password;

        //Nos traemos el archivo properties
        var config = new Properties();

        try {
            //Cargamos el fichero properties que esta en la carpeta resource
            config.load(App.class.getClassLoader().getResourceAsStream("ddbb.properties"));
            //Depuramos con el logger
            LOG.info("Configuracion cargada");
            //Hacemos la url
            url="jdbc:mysql://"+config.getProperty("host")+":"+config.getProperty("port")+"/"+config.getProperty("dbname");
            //depuramos
            LOG.info(url);
            //pasamos el user
            user=config.getProperty("user");
            //pasamos el pass
            password=config.getProperty("pass");



        } catch (IOException e) {

            LOG.info("Error cargando la configuracion");
            throw new RuntimeException(e);

        }



        try {

            //Con el driver pasamos la conexion a la variable
            connection = DriverManager.getConnection(url, user, password);

            //Trazamos con el logger
            LOG.info("Conectada correctamente");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }
}


