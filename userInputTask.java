package pruebaInsertarJDBC;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class userInputTask implements Callable<String>{

    @Override
    public String call() throws Exception {

        String nombrePais;
        System.out.print("Nombre del pais que quiere agregar: ");
        try(Scanner scanner = new Scanner(System.in)){
            nombrePais = scanner.next();
        }
        
        return nombrePais;
    }
    
}