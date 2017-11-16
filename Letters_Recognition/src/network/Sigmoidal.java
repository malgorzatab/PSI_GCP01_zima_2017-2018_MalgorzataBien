package network;


public class Sigmoidal {
	
	private Controller myController;	
	private double BETA = 1;
	public static final double LEARNING_RATE=0.1;								//wspó³czynnik uczenia
	public char[] charLetter = {'A','B','C','D','E','F','G','H','I','J','a','b','c','d','e','f','g','h','i','j'};		//litera w formacie ASCII
	private double MSE=0.0; //sredniokwadratowy
	
	public Sigmoidal(Controller controller) {
		this.myController = controller;
	}
	
	public void startSigmoidal(double[]weights){	
			double sum=0.0;			
			double[] Sum = new double[20];	
			int epoka=0;		//licznik iteracji-epok
			double delta = 0.0;
			double mseerror=0;
			double[] adjustedWeights;//tablica wag po korekcie			
			
			Alphabet alphabet = new Alphabet();
			//wczytanie liter z pliku
			Letter[] letters = alphabet.inputAlphabet();
			int[] result = Letter.result;
			//tablica z wartoœciami oczekiwanymi
			int [][]letter = alphabet.letterSet;
			
			if (letters == null) {
	            System.out.println("Error reading file");	            
	        }
			myController.setText("--SIGMOIDAL--\n");
	        myController.setText("--LEARNING--\n");
	
	        do{			
	        	epoka++;
				MSE=0.0;					
				int p=0;
				for(int i=0; i<20; i++){									
					//obliczanie sumy po³¹czenia na wejœciu neuronu
					sum = letters[i].processCellNode(weights);
					//przes³anie sumy do funkcji aktywacji
					double output = activationFunction(sum);
					//sprawdzanie ró¿nicy miêdzy wartoœci¹ oczekiwan¹ a aktualn¹ otrzyman¹
					delta = result[i] - output;	
					//korekta wag
					p=0;				
	                for (int k = 0; k < 7; k++) {
	                    for (int j = 0; j < 5; j++) {
	                        weights[p] += LEARNING_RATE * delta * letters[i].getLetter(k, j);
	                        p++;
	                    }
	                }													 
					mseerror=0.5*(delta*delta);
					mseerror /=20;
	                MSE += (delta*delta);
	                MSE /= 20;
				}				
			}while(mseerror> 0.00001);
			
	        myController.setText("Epoka "+epoka+"\n");
	        myController.setText("MSError: "+mseerror+"\n");		
	}
	
	
	public void testing(double[]weights){
		Alphabet alphabet = new Alphabet();
		Letter[] testletters = alphabet.inputTestAlphabet();
		double[] suma = new double[20];
		double out=0;
		 this.myController.setText("\nTesting:\n");
		 this.myController.setText("--------\n");
		 for (int i = 0; i < 20; i++) {

	            myController.setText("Letter:  " + charLetter[i]+ "\n");
	            suma[i] = testletters[i].processCellNode(weights);
	            out = activationFunction(suma[i]);
	            if(out >= 0.80){
	                myController.setText("Result: UPPERCASE\n\n");
	            }else{

	                myController.setText("Result: LOWERCASE\n\n");
	            }
	        }
	}

	//funckja aktywacji
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
	
	//funkcja sumuj¹ca 
	public double calculateSum(int[] tab, double[] weights){
        double sum = 0;
        for(int i=0; i < tab.length; i++)
            sum += tab[i] * weights[i];
        return sum;
    }
	
	
}
