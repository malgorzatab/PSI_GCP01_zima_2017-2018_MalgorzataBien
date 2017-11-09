package network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Adaline {
	
	private Controller myController;																
	public static final double LEARNING_RATE=0.1;								//wspó³czynnik uczenia
	public char[] charLetter = {'A','B','C','D','E','F','G','H','I','J','a','b','c','d','e','f','g','h','i','j'};		//litera w formacie ASCII
	static String filename = "C:\\Users\\Gosia\\Desktop\\PSIprojects\\Letters_Recognition\\src\\network\\alfabet.txt";
	private double MAPE=0.0; //procentowy blad
	private double MSE=0.0; //sredniokwadratowy
	
	public Adaline(Controller controller) {
		this.myController = controller;
	}
	
	public void startAdaline(double[]weights){	
												
			double[] inputSum = new double[20];				//tablica dla sumy ka¿dej litery
			double[] Sum = new double[20];	
			double sum=0.0;			
			int iteracja=0;									//licznik iteracji
			boolean errorFlag = false;
			double theta = 0.0;
			double mseerror = 0.0;
			double[] adjustedWeights = null;				//tablica wag po korekcie			
			
			Alphabet alphabet = new Alphabet();
			Letter[] letters = alphabet.inputAlphabet();
			int[] result = Letter.result;
			int [][]letter = alphabet.letterSet;
			
			if (letters == null) {
	            System.out.println("LoadFile error!");	            
	        }

	        myController.setText("\n----------------------");
	        myController.setText("-------LEARNING-----------------------\n");
			
			while(!errorFlag){
	        
	       // do{
				iteracja++;
				MSE=0.0;
				MAPE=0.0;
				myController.setText("\n");
				errorFlag=false;			
				int k=0;
				for(int i=0; i<20; i++){
					k++;	
					int[] tmp = letter[i];
					Sum[i]= calculateSum(tmp,weights);
					inputSum[i] = letters[i].processCellNode(weights);			
					int output = activationFunction(inputSum[i]);
					myController.setText("output: "+k+": "+output+"\n");					
					//theta = result[i] - inputSum[i];	
					theta = result[i]-Sum[i];
					//adjustedWeights = calculateWeight(letters, weights, theta);
					adjustedWeights = calculateWeight(tmp, weights, theta);
					weights=adjustedWeights;										
	                MSE +=(theta*theta);
	                MAPE += (Math.abs(theta)/output);
	                MSE /= 20;
	                MAPE /= 20;
				}
				
				mseerror=0.5*(result[0] - inputSum[0])*(result[0] - inputSum[0]);
				mseerror /=20;
				
				if(mseerror>0.00001) errorFlag = false;
	            else errorFlag=true;
			}	
			//}while(MSE>0.00001 && iteracja==1000);
			
	        myController.setText("Epoch "+iteracja+"\n");
	        myController.setText("MSError: "+mseerror+"\n");
	        myController.setText("MSE: "+MSE+"\n");
	        myController.setText("MAPE: "+MAPE+"\n");
	        
	        
			
			
	}
	
	
	public void testing(double[]weights){
		Alphabet alphabet = new Alphabet();
		Letter[] testletters = alphabet.inputTestAlphabet();	
		double[] suma = new double[20];		
		int out=0;
		int [][]letterTest = alphabet.letterSet;
		 this.myController.setText("\n-------Testing-------\n");
		 for (int i = 0; i < 20; i++) {
			 int[] tmp = letterTest[i];
	            myController.setText(" TEST:  " + charLetter[i]+ "\n");            
	            suma[i] = calculateSum(tmp,weights);
	            out = activationFunction(suma[i]);
	          // myController.setText("output: "+out);
	            if(activationFunction(suma[i]) == 1)
	            {
	                myController.setText("LETTER: UPPERCASE\n\n");
	            }else
	            {

	                myController.setText("LETTER: LOWERCASE\n\n");
	            }
	        }
	}

	
	//funkcja aktywacji
	public int activationFunction(double cellNode){
		int result;
		if(cellNode >= 1) 
			result = 1;		
		else result=0;
		
		return result;
	}
	
	//funckja obliczaj¹ca korekte wag
	public double[] calculateWeight(Letter[] letters, double[]weights, double error){
		double[] inputWeights =  new double[weights.length];
		for(int i=0; i<7; i++){
			for(int j=0; j<5; j++){
				inputWeights[i] = LEARNING_RATE *error * letters[i].getLetter(i,j) + weights[i];
			}			 
		}
		return inputWeights;
	}
	
	//funckja obliczaj¹ca korekte wag
		public double[] calculateWeight(int[] letters, double[]weights, double error){
			double[] inputWeights =  new double[weights.length];
			for(int i=0; i<7; i++){
				for(int j=0; j<5; j++){
					inputWeights[i] = LEARNING_RATE *error * letters[i] + weights[i];
				}			 
			}
			return inputWeights;
		}
	public double calculateSum(int[] tab, double[] weights){
        double sum = 0;
        for(int x=0; x < tab.length; x++)
            sum += tab[x] * weights[x];
        return sum;
    }
	
	
}
