package gui;

import java.io.File;
import java.util.List;

import Couches.Trame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pobj.lecture.FileReader;
import pobj.output.Output;

public class MainController {
	private Stage stage;
	@FXML
    private MenuItem close;

    @FXML
    private ListView<Trame> listTrame;
    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem open;

    @FXML
    private MenuItem quit;

    @FXML
    private MenuItem save;

    @FXML
    private TextArea trameAnalysed ;

    @FXML
    private TextArea trameBrut ;
    
    private List<Trame> lTrame;
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    @FXML
    void closeFileOnClick(ActionEvent event) {

    }

    @FXML
    void loadListTrame(ActionEvent event) {
    	
    }

    @FXML
    void loadTrameAnalyse(MouseEvent event) {

    }

    @FXML
    void openFileOnClick(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Network Frame File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File packetFile = fileChooser.showOpenDialog(stage);
        FileReader f1 = new FileReader(packetFile);
        
        
        
        try {
			lTrame =  Trame.generateListTrame(f1.getMapTrames());
			// listTrame = new ListView<>();
			
			ObservableList<Trame> items = FXCollections.observableArrayList(lTrame);
			
			listTrame.setItems(items);
			
			
			listTrame.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Trame>() {

				@Override
				public void changed(ObservableValue<? extends Trame> arg0, Trame arg1, Trame arg2) {
					String[] result= arg2.resultattAnalyse();
					StringBuilder sb = new StringBuilder();
					for(int i = 1 ; i < result.length ; i++) {
						sb.append(result[i]);
					}
					
					trameBrut.setText(result[0]);
					trameAnalysed.setText(sb.toString());
					
				}
				
		});
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }

    @FXML
    void quitProgramOnclick(ActionEvent event) {
    	stage.close();
    }

    @FXML
    void saveAnalyseOnClick(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save...");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("txt", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File exportFile = fileChooser.showSaveDialog(stage);
        StringBuilder sb = new StringBuilder();
        
        for(Trame t : lTrame) {
        	String[] result = t.resultattAnalyse();
        	String res = String.join("", result);
        	sb.append(res);
        	
        }
        
        Output.outputStream(sb.toString(), exportFile);
    }

}
