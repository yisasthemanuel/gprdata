package org.jlobato.gpro.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ImageLoader {
	
	private Connection connection;
	
	public ImageLoader() {
		
	}
	
	private String user = "gprodata";
	private String password = "gprodata";
	private String url = "jdbc:postgresql://localhost:5432/gprodata";

	public static void main(String[] args) {
		ImageLoader loader = new ImageLoader();
		loader.connect();
		
		System.out.println(System.getProperty("user.dir"));
		
		loader.guardarfoto("4", "Diego", "./src/main/webapp/images/dmahecha.jpg");
		loader.guardarfoto("1", "Jesus", "./src/main/webapp/images/jperez.jpg");
		loader.guardarfoto("5", "Edwin", "./src/main/webapp/images/esilva.jpg");
		loader.guardarfoto("6", "Erwin", "./src/main/webapp/images/everkade.jpg");
		loader.guardarfoto("7", "Geoff", "./src/main/webapp/images/gatkin.jpg");
		loader.guardarfoto("8", "Mikko", "./src/main/webapp/images/msuhonen.jpg");
		loader.guardarfoto("10", "Pablo", "./src/main/webapp/images/pnavarrete.jpg");
		loader.guardarfoto("9", "Nevzat", "./src/main/webapp/images/nkasapoglu.jpg");
		loader.guardarfoto("2", "Carlos", "./src/main/webapp/images/cesparza.jpg");
		loader.guardarfoto("3", "Chris", "./src/main/webapp/images/cseifreidsberger.jpg");

	}
	
	public void connect()
    {
      try{
         Class.forName("org.postgresql.Driver");         
         connection = DriverManager.getConnection(url, user , password);
      }catch(Exception e){
         System.err.println(e.getMessage());
      }
    }

	public boolean guardarfoto(String id, String name, String ruta) {
        FileInputStream fis = null;
        try {
             File file = new File(ruta);
             fis = new FileInputStream(file);
             PreparedStatement pstm = connection.prepareStatement("UPDATE manager set avatar_img = ? where id_manager = ?");
             pstm.setBinaryStream(1, fis,(int) file.length());
             pstm.setInt(2, new Integer(id));
             pstm.execute();
             pstm.close();
             return true;
        } catch (FileNotFoundException e) {
             System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
               fis.close();
             } catch (IOException e) {
               System.out.println(e.getMessage());
             }
        }
        return false;
   }
}
