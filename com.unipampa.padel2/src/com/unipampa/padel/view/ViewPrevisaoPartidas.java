package com.unipampa.padel.view;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class ViewPrevisaoPartidas extends Application {

	private static Stage stage;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		ViewPrevisaoPartidas.stage = stage;
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// Read file fxml and draw interface.
			Parent root = FXMLLoader.load(getClass().getResource("/com/unipampa/padel/view/ViewPrevisaoPartidas.fxml"));
			primaryStage.setTitle("Previsão Partidas");
			primaryStage.setScene(new Scene(root));
			setStage(primaryStage);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sair() {
		ViewPrevisaoPartidas.getStage().close();
//    		Menu m = new Menu();
//    		m.start(Menu.getStage());
	}

}