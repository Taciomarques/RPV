package com.unipampa.padel.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CadastrarDupla extends Application {

	private static Stage stage;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		CadastrarDupla.stage = stage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// Read file fxml and draw interface.
			Parent root = FXMLLoader.load(getClass().getResource("/com/unipampa/padel/view/ViewCadastroDupla.fxml"));
			primaryStage.setTitle("Inscrição Dupla");
			primaryStage.setScene(new Scene(root));
			setStage(primaryStage);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sair() {
		CadastrarDupla.getStage().close();
//    		Menu m = new Menu();
//    		m.start(Menu.getStage());
	}

}