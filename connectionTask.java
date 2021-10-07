package pruebaInsertarJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.Callable;

public class connectionTask implements Callable<Connection>{
    static private String url = "jdbc:mysql://localhost:3306/dcc";
    static private String username ="agusdcc";
    static private String password = "password";

    @Override
    public Connection call() throws Exception {        
        return DriverManager.getConnection(url, username, password);
    }

   
    
    
}