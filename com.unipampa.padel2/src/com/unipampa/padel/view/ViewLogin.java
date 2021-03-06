package com.unipampa.padel.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewLogin extends Application{

	private static Stage stage;
	  
	 public static Stage getStage() {
			return stage;
		}

		public static void setStage(Stage stage) {
			ViewLogin.stage = stage;
		}

		public static void main(String[] args) {
	        launch(args);
	    }
	
	 @Override
	    public void start(Stage primaryStage) {
	        try {
	            // Read file fxml and draw interface.
	            Parent root = FXMLLoader.load(getClass().getResource("/com/unipampa/padel/view/ViewLogin.fxml"));
	            primaryStage.setTitle("Login");
	            primaryStage.setScene(new Scene(root));
	            primaryStage.setResizable(false);
	            primaryStage.show();
	            setStage(primaryStage);
	         
	        } catch(Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public static void sair() {
			ViewLogin.getStage().close();
		}

	
}
