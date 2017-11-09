package network;


public class Sigmoidal {
	
	private Controller myController;	
	private double BETA = 1;
	public static final double LEARNING_RATE=0.05;								//wspó³czynnik uczenia
	public char[] charLetter = {'A','B','C','D','E','F','G','H','I','J','a','b','c','d','e','f','g','h','i','j'};		//litera w formacie ASCII
	static String filename = "C:\\Users\\Gosia\\Desktop\\PSIprojects\\Letters_Recognition\\src\\network\\alfabet.txt";
	private double MAPE=0.0; //procentowy blad
	private double MSE=0.0; //sredniokwadratowy
	
	public Sigmoidal(Controller controller) {
		this.myController = controller;
	}
	
	public void startSigmoidal(double[]weights){	
													
			double[] inputSum = new double[20];				//tablica dla sumy ka¿dej litery
			double sum=0.0;			
			double[] Sum = new double[20];	
			int epoka=0;									//licznik iteracji
			boolean errorFlag = false;
			double delta = 0.0;
			double mseerror=0;
			double[] adjustedWeights;				//tablica wag po korekcie			
			
			Alphabet alphabet = new Alphabet();
			Letter[] letters = alphabet.inputAlphabet();
			int[] result = Letter.result;
			int [][]letter = alphabet.letterSet;
			
			if (letters == null) {
	            System.out.println("LoadFile error!");	            
	        }
			myController.setText("\nSIGMOIDAL");
	        myController.setText("\n----------------------");
	        myController.setText("-------LEARNING-----------------------\n");
	
	        do{			
	        	epoka++;
				MSE=0.0;
				MAPE=0.0;			
				errorFlag=false;				
				int k=0;
				int p=0;
				for(int i=0; i<20; i++){
					p++;						
					sum = letters[i].processCellNode(weights);
					double output = activationFunction(sum);
					
					//myController.setText("output: "+p+": "+output+"\n");					
					delta = result[i] - output;
					k=0;
					//updating weights
	                for (int m = 0; m < 7; m++) {
	                    for (int n = 0; n < 5; n++) {
	                        weights[k] += LEARNING_RATE * delta * letters[i].getLetter(m, n);
	                        k++;
	                    }
	                }														 
					mseerror=0.5*(result[0] - sum)*(result[0] - sum);
					mseerror /=20;
	                MSE += (delta*delta);
	                MAPE += (Math.abs(delta)/output);
	                MSE /= 20;
	                MAPE /= 20;	               
				}	
				
	            System.out.println("Err: "+mseerror);

			
			}while(MSE> 0.05);
			
	        myController.setText("Epoka "+epoka+"\n");
	        myController.setText("MSE: "+MSE+"\n");
	        myController.setText("MAPE: "+MAPE+"\n");
	        
			
			
	}
	
	
	public void testing(double[]weights){
		Alphabet alphabet = new Alphabet();
		Letter[] testletters = alphabet.inputTestAlphabet();
		double sum=0.0;
		double[] suma = new double[20];
		int output=0;
		double out=0;
		 this.myController.setText("\n-------Testing-------\n");
		 for (int i = 0; i < 20; i++) {

	            myController.setText(" TEST:  " + charLetter[i]+ "\n");
	            suma[i] = testletters[i].processCellNode(weights);
	            out = activationFunction(suma[i]);
	         
	           // myController.setText("output: "+out);
	            if(out >= 0.9976){
	                myController.setText("LETTER: UPPERCASE\n\n");
	            }else{

	                myController.setText("LETTER: LOWERCASE\n\n");
	            }
	        }
	}

	
	
	   private double activationFunction(double sum){

	        return ( 1 / ( 1 + Math.exp(-BETA * sum)));
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
	
	public double calculateSum(int[] tab, double[] weights){
        double sum = 0;
        for(int x=0; x < tab.length; x++)
            sum += tab[x] * weights[x];
        return sum;
    }
	 
	
	
}
