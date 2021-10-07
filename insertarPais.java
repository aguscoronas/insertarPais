package pruebaInsertarJDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

public class insertarPais{
    private static String insertIntoPaisesSQL = "INSERT INTO paises VALUES (null,?)";
    private static String selectPaisesSQL = "SELECT paisID, nombre FROM paises";

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
        ResultSet tabla;
        try(PreparedStatement insertStatement = connection.prepareStatement(insertIntoPaisesSQL)){
            insertStatement.setString(1, nombrePais);
            insertStatement.executeUpdate();
        }
        try(PreparedStatement selectStatement = connection.prepareStatement(selectPaisesSQL)){
            tabla = selectStatement.executeQuery();                        
        }
        connection.close();

        while(tabla.next()){
            int paisID = tabla.getInt("paisID");
            String nombre = tabla.getString("nombre");
            System.out.println(paisID + " " + nombre);
        } 
    }

    

}