package network;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args){
		launch(args);		

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(Controller.class.getResource("documentFXML.fxml"));
		primaryStage.setTitle("Letters Recognition");
		primaryStage.setScene(new Scene(root,600,500));
		primaryStage.show();
	}

}
