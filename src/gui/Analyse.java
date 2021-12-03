package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class Analyse extends Application{
	@Override public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Analyse.class.getResource("gui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainController controller = fxmlLoader.getController();
        controller.setStage(stage);
        MenuBar menuBar = controller.getMenuBar();
        menuBar.setUseSystemMenuBar(true);
        stage.setTitle("Network Packet Analyser");
        stage.setScene(scene);
        stage.show();
    }
	
	public static void main(String[] args) {
        launch();
    }
}
