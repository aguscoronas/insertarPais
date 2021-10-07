package pruebaInsertarJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

public class insertarPais{
    private static String INSERT_INTO_PAISES_SQL = "INSERT INTO paises VALUES (null,?)";
    private static String SELECT_FROM_PAISES_SQL = "SELECT paisID, nombre FROM paises";
    private static int PADDING = -24;

    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        userInputTask obtenerNombrePais = new userInputTask();
        Future<String> futuroNombrePais = executor.submit(obtenerNombrePais);
        connectionTask obtenerConnection = new connectionTask();
        Future<Connection> futuroConnection = executor.submit(obtenerConnection);
        
        try {
            insertCountryUsingConnection(futuroNombrePais.get(), futuroConnection.get());
        } catch (InterruptedException | ExecutionException | SQLException e) {
            e.printStackTrace();
        }finally{
        executor.shutdownNow();
        }
        return;
    }

    private static void insertCountryUsingConnection(String nombrePais, Connection connection) throws SQLException{
        
        try(PreparedStatement insertStatement = connection.prepareStatement(INSERT_INTO_PAISES_SQL)){
            insertStatement.setString(1, nombrePais);
            insertStatement.executeUpdate();
        }
        try(PreparedStatement selectStatement = connection.prepareStatement(SELECT_FROM_PAISES_SQL)){
            ResultSet tabla = selectStatement.executeQuery();
            System.out.println("________________________");
            System.out.println("|     TABLA   PAISES    |");
            System.out.println("________________________");
            while(tabla.next()){
                int paisID = tabla.getInt("paisID");
                String nombre = tabla.getString("nombre");
                String linea = "| " + paisID + " | " + nombre;
                linea = String.format("%" + (PADDING) + "s", linea) + "|";
                System.out.println(linea);
                System.out.println("________________________");
            } 
        }
        connection.close();

        
    }

    

}