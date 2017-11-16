package network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Adaline {
	
	private Controller myController;																
	public static final double LEARNING_RATE=0.0025; //wspó³czynnik uczenia
	public char[] charLetter = {'A','B','C','D','E','F','G','H','I','J','a','b','c','d','e','f','g','h','i','j'};		//litera w formacie ASCII
	private double MSE=0.0; //sredniokwadratowy
	
	public Adaline(Controller controller) {
		this.myController = controller;
	}
	
	public void startAdaline(double[]weights){	
												
			double[] inputSum = new double[20];	//tablica dla sumy ka¿dej litery
			double[] Sum = new double[20];			
			int iteracja=0;	//licznik iteracji
			boolean errorFlag = false;
			double theta = 0.0;//ró¿nica miêdzy wartoœci¹ oczekiwan¹ a obecn¹
			double mseerror = 0.0;//b³¹d MSE
			double[] adjustedWeights = null;//tablica wag po korekcie			
			
			Alphabet alphabet = new Alphabet();
			Letter[] letters = alphabet.inputAlphabet();
			int[] result = Letter.result;
			int [][]letter = alphabet.letterSet;
			
			if (letters == null) {
	            System.out.println("Error reading file");	            
	        }
			myController.setText("--ADALINE--\n");
	        myController.setText("--LEARNING--\n");
			
			while(!errorFlag){
				iteracja++;
				MSE=0.0;				
				errorFlag=false;			
				int k=0;
				for(int i=0; i<20; i++){
					k++;	
					int[] tmp = letter[i];
					//obliczanie sumy po³¹czenia na wejœciu neuronu
					Sum[i]= calculateSum(tmp,weights);		
					//sprawdzanie ró¿nicy miêdzy wartoœci¹ oczekiwan¹ a aktualn¹ otrzyman¹
					theta = result[i]-Sum[i];		
					//korekta wag
					adjustedWeights = calculateWeight(tmp, weights, theta);
					weights=adjustedWeights;										
	                //MSE +=(theta*theta);	             
	                //MSE /= 20;
	                mseerror +=0.5*(theta*theta);
					mseerror /=20;
				}
				//obieg pêtli zakoñczy siê gdy b³¹d bêdzie w przybli¿eniu równy wartoœci 0.00000001
				if(mseerror>0.000000001) errorFlag = false;
	            else errorFlag=true;
			}				
			
	        myController.setText("Epoch "+iteracja+"\n");
	        myController.setText("MSE error: "+mseerror+"\n");	        				
	}
	
	
	public void testing(double[]weights){
		Alphabet alphabet = new Alphabet();
		Letter[] testletters = alphabet.inputTestAlphabet();	
		double[] suma = new double[20];		
		int out=0;
		//wczytanie liter
		int [][]letterTest = alphabet.letterSet;
		 this.myController.setText("\n-------Testing-------\n");
		 for (int i = 0; i < 20; i++) {
			 int[] tmp = letterTest[i];
	            myController.setText(" TEST:  " + charLetter[i]+ "\n");   
	            //obliczanie sumy po³¹czenia
	            suma[i] = calculateSum(tmp,weights);
	            //przes³anie sumy do funkcji aktywacji
	            out = activationFunction(suma[i]);
	            if(out == -1)
	            {
	                myController.setText("LETTER: LOWERCASE\n\n");
	            }else
	            {

	                myController.setText("LETTER: UPERRCASE\n\n");
	            }
	        }
	}

	
	//funkcja aktywacji
	public int activationFunction(double cellNode){
		int result;
		if(cellNode >= 0) 
			result = 1;		
		else result=-1;
		
		return result;
	}
	
	//funckja obliczaj¹ca korekte wag *gdy u¿ywam obiektu klasy Letter
	public double[] calculateWeight(Letter[] letters, double[]weights, double error){
		double[] inputWeights =  new double[weights.length];
		for(int i=0; i<7; i++){
			for(int j=0; j<5; j++){
				inputWeights[i] = LEARNING_RATE *error * letters[i].getLetter(i,j) + weights[i];
			}			 
		}
		return inputWeights;
	}
	
	//funckja obliczaj¹ca korekte wag *gdy u¿ywam tablicy 
		public double[] calculateWeight(int[] letters, double[]weights, double error){
			double[] inputWeights =  new double[weights.length];
			for(int i=0; i<7; i++){
				for(int j=0; j<5; j++){
					inputWeights[i] = LEARNING_RATE *error * letters[i] + weights[i];
				}			 
			}
			return inputWeights;
		}
		
		//funkcja sumuj¹ca 
	public double calculateSum(int[] tab, double[] weights){
        double sum = 0;
        for(int i=0; i < tab.length; i++)
            sum += tab[i] * weights[i];
        return sum;
    }
	
	
}
