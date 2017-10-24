package neural;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class NeuralFXMLController implements Initializable{

	 @FXML private TextArea logTextArea;
	 @FXML private Button runButton;
	 @FXML private Button testButton;	
	
	    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Perceptron perceptron = new Perceptron(this);
       perceptron.logicalFunction();
		
	}
	
	public void setText(String logg){
        logTextArea.appendText(logg);
    }
	
	 public void setText(int[][]data, double[] weights, int result, double error, double weightedSum,double[] adjustedWeights){  	
	    	logTextArea.appendText(" "+String.format("%.2f",weights[0])+ " | "+String.format("%.2f",weights[1])+" |   "+data[0][0]+"  |   "+data[0][1]+"    |        "+data[1][0]+"            |     "+result+"     |   "+error+"    |        "+String.format("%.2f", weightedSum)+"         |        "+String.format("%.2f", adjustedWeights[0])+"       |      "+String.format("%.2f", adjustedWeights[1]));
	    }
	 
	   @FXML
	    void startPerceptron(ActionEvent event) {
		   logTextArea.clear();
		   Perceptron perceptron = new Perceptron(this);
		   perceptron.logicalFunction();
	    }
	   
	   @FXML
	    void testPerceptron(ActionEvent event) {
		   
		   logTextArea.clear();
		   Perceptron perceptron = new Perceptron(this);		  
		   perceptron.Testing();
		   
	    }

	public void setTestText(int x,int y, int result){
		logTextArea.appendText("x1: "+x+"  x2: "+y+ " result: "+result);
	}
}