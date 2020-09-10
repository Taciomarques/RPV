package com.unipampa.padel.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewRanking extends Application{
	
	private static Stage stage;

	public static Stage getStage() {
		return stage;
	}

	public static void setStage(Stage stage) {
		ViewRanking.stage = stage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			// Read file fxml and draw interface.
			Parent root = FXMLLoader.load(getClass().getResource("/com/unipampa/padel/view/ViewRanking.fxml"));
			primaryStage.setTitle("Ranking");
			primaryStage.setScene(new Scene(root));
			setStage(primaryStage);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void sair() {
		ViewRanking.getStage().close();
	}

}
