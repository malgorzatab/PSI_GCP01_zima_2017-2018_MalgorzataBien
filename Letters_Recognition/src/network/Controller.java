package network;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class Controller implements Initializable {

    @FXML private TextArea textArea;
    @FXML private Button runButton;

   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Network network = new Network(this);
		network.letterRecognition();
			
	}
	public void setText(String logg){
        textArea.appendText(logg);
    }
 
	@FXML
    void startNetwork(ActionEvent event) throws IOException {		

    }
}
