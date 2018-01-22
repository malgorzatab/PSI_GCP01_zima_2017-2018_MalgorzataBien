import java.util.Random;

public class KohonenWTA {
	private int noNeurons = 20;
	private int noInputs = 75;		//liczba danych wejœciowych ucz¹cych i testuj¹cych
	private String[] flowerType;
	public static double LEARNING_RATE=0.2; //wspó³czynnik uczenia
	private static int maxIter = 1000;	
	//private String[] testflowerType;
	private double[][]weights;		//tablica wag
	
	public double[][] learnFlowerSet = {{5.1,3.5,1.4,0.2},		//dane ucz¹ce
			{4.9,3.0,1.4,0.2},
			{4.7,3.2,1.3,0.2},
			{4.6,3.1,1.5,0.2},
			{5.0,3.6,1.4,0.3},
			{5.4,3.9,1.7,0.4},
			{4.6,3.4,1.4,0.3},
			{5.0,3.4,1.5,0.2},
			{4.4,2.9,1.4,0.2},
			{4.9,3.1,1.5,0.1},
			{5.4,3.7,1.5,0.2},
			{4.8,3.4,1.6,0.2},
			{4.8,3.0,1.4,0.1},
			{4.3,3.0,1.1,0.1},
			{5.8,4.0,1.2,0.2},
			{5.7,4.4,1.5,0.4},
			{5.4,3.9,1.3,0.4},
			{5.1,3.5,1.4,0.3},
			{5.7,3.8,1.7,0.3},
			{5.1,3.8,1.5,0.3},
			{5.4,3.4,1.7,0.2},
			{5.1,3.7,1.5,0.4},
			{4.6,3.6,1.0,0.2},
			{5.1,3.3,1.7,0.5},
			{4.8,3.4,1.9,0.2},
			{7.0,3.2,4.7,1.4},
			{6.4,3.2,4.5,1.5},
			{6.9,3.1,4.9,1.5},
			{5.5,2.3,4.0,1.3},
			{6.5,2.8,4.6,1.5},
			{5.7,2.8,4.5,1.3},
			{6.3,3.3,4.7,1.6},
			{4.9,2.4,3.3,1.0},
			{6.6,2.9,4.6,1.3},
			{5.2,2.7,3.9,1.4},
			{5.0,2.0,3.5,1.0},
			{5.9,3.0,4.2,1.5},
			{6.0,2.2,4.0,1.0},
			{6.1,2.9,4.7,1.4},
			{5.6,2.9,3.6,1.3},
			{6.7,3.1,4.4,1.4},
			{5.6,3.0,4.5,1.5},
			{5.8,2.7,4.1,1.0},
			{6.2,2.2,4.5,1.5},
			{5.6,2.5,3.9,1.1},
			{5.9,3.2,4.8,1.8},
			{6.1,2.8,4.0,1.3},
			{6.3,2.5,4.9,1.5},
			{6.1,2.8,4.7,1.2},
			{6.4,2.9,4.3,1.3},
			{6.3,3.3,6.0,2.5},
			{5.8,2.7,5.1,1.9},
			{7.1,3.0,5.9,2.1},
			{6.3,2.9,5.6,1.8},
			{6.5,3.0,5.8,2.2},
			{7.6,3.0,6.6,2.1},
			{4.9,2.5,4.5,1.7},
			{7.3,2.9,6.3,1.8},
			{6.7,2.5,5.8,1.8},
			{7.2,3.6,6.1,2.5},
			{6.5,3.2,5.1,2.0},
			{6.4,2.7,5.3,1.9},
			{6.8,3.0,5.5,2.1},
			{5.7,2.5,5.0,2.0},
			{5.8,2.8,5.1,2.4},
			{6.4,3.2,5.3,2.3},
			{6.5,3.0,5.5,1.8},
			{7.7,3.8,6.7,2.2},
			{7.7,2.6,6.9,2.3},
			{6.0,2.2,5.0,1.5},
			{6.9,3.2,5.7,2.3},
			{5.6,2.8,4.9,2.0},
			{7.7,2.8,6.7,2.0},
			{6.3,2.7,4.9,1.8},
			{6.7,3.3,5.7,2.1}};
	
	public double[][] testFlowerSet = {{5.0,3.0,1.6,0.2},		//dane testuj¹ce
			{5.0,3.4,1.6,0.4},
			{5.2,3.5,1.5,0.2},
			{5.2,3.4,1.4,0.2},
			{4.7,3.2,1.6,0.2},
			{4.8,3.1,1.6,0.2},
			{5.4,3.4,1.5,0.4},
			{5.2,4.1,1.5,0.1},
			{5.5,4.2,1.4,0.2},
			{4.9,3.1,1.5,0.2},
			{5.0,3.2,1.2,0.2},
			{5.5,3.5,1.3,0.2},
			{4.9,3.6,1.4,0.1},
			{4.4,3.0,1.3,0.2},
			{5.1,3.4,1.5,0.2},
			{5.0,3.5,1.3,0.3},
			{4.5,2.3,1.3,0.3},
			{4.4,3.2,1.3,0.2},
			{5.0,3.5,1.6,0.6},
			{5.1,3.8,1.9,0.4},
			{4.8,3.0,1.4,0.3},
			{5.1,3.8,1.6,0.2},
			{4.6,3.2,1.4,0.2},
			{5.3,3.7,1.5,0.2},
			{5.0,3.3,1.4,0.2},
			{6.6,3.0,4.4,1.4},
			{6.8,2.8,4.8,1.4},
			{6.7,3.0,5.0,1.7},
			{6.0,2.9,4.5,1.5},
			{5.7,2.6,3.5,1.0},
			{5.5,2.4,3.8,1.1},
			{5.5,2.4,3.7,1.0},
			{5.8,2.7,3.9,1.2},
			{6.0,2.7,5.1,1.6},
			{5.4,3.0,4.5,1.5},
			{6.0,3.4,4.5,1.6},
			{6.7,3.1,4.7,1.5},
			{6.3,2.3,4.4,1.3},
			{5.6,3.0,4.1,1.3},
			{5.5,2.5,4.0,1.3},
			{5.5,2.6,4.4,1.2},
			{6.1,3.0,4.6,1.4},
			{5.8,2.6,4.0,1.2},
			{5.0,2.3,3.3,1.0},
			{5.6,2.7,4.2,1.3},
			{5.7,3.0,4.2,1.2},
			{5.7,2.9,4.2,1.3},
			{6.2,2.9,4.3,1.3},
			{5.1,2.5,3.0,1.1},
			{5.7,2.8,4.1,1.3},
			{7.2,3.2,6.0,1.8},
			{6.2,2.8,4.8,1.8},
			{6.1,3.0,4.9,1.8},
			{6.4,2.8,5.6,2.1},
			{7.2,3.0,5.8,1.6},
			{7.4,2.8,6.1,1.9},
			{7.9,3.8,6.4,2.0},
			{6.4,2.8,5.6,2.2},
			{6.3,2.8,5.1,1.5},
			{6.1,2.6,5.6,1.4},
			{7.7,3.0,6.1,2.3},
			{6.3,3.4,5.6,2.4},
			{6.4,3.1,5.5,1.8},
			{6.0,3.0,4.8,1.8},
			{6.9,3.1,5.4,2.1},
			{6.7,3.1,5.6,2.4},
			{6.9,3.1,5.1,2.3},
			{5.8,2.7,5.1,1.9},
			{6.8,3.2,5.9,2.3},
			{6.7,3.3,5.7,2.5},
			{6.7,3.0,5.2,2.3},
			{6.3,2.5,5.0,1.9},
			{6.5,3.0,5.2,2.0},
			{6.2,3.4,5.4,2.3},
			{5.9,3.0,5.1,1.8}};

	//funkcja w której nastêpuje uczenie	
	public void startKohonen(){		
		
	//tablica z nazwami kwiatów z zestawu ucz¹cego i testuj¹cego		
		flowerType = new String[noInputs];	
		for(int i=0; i<noInputs; i++){
			if(i<25){
				flowerType[i] = "I.setosa";
			}
			if(i>=25 && i<51){
				flowerType[i] = "I.versicolor";
			}
			if(i>=51){
				flowerType[i] = "I.virginica";
			}
		}
	
		weights = new double[noNeurons][4];
		randWeight();
		normalizeLearningSet();	//normalizacja danych
		int iter = 0;
		double[] result = new double[noNeurons];
		System.out.println("Uczenie: ");
		do{
			for(int i=0; i<noInputs; i++){
				for(int j=0; j<noNeurons; j++){
					//obliczanie sumy po³¹czenia na wejœciu
					result[j] = calculateInput(j,i);
				}
				//korekta wag
				updateWeights(winner(result),i);
			}
			iter++;
			
		}while(iter<maxIter);
		System.out.println("Liczba iteracji: " + iter);
	}
	
	//funkcja w której nastêpuje testowanie
    public void testKohonen() {
    	
        System.out.println("Testowanie");        
        normalizeTestSet();
        double[] result = new double[noNeurons];
        int winner;     
       
        for(int i=0; i< noInputs; i++) {
            for(int j=0;j<noNeurons;j++)
            {
                result[j]=calculateTestInput(j,i);
            }
            winner = winner(result); //zwyciêzca, otrzyma³ najwiêkszy wynik przy aktywacji
                       
            System.out.println(i+1 + ". Flower: " + flowerType[i] + "  result: " + winner );
        }

    }

	
	//ustawienie poczatkowych wartoœci wag
    public void randWeight(){
        Random random = new Random();
        for(int i =0;i<noNeurons;i++) {
            for (int j = 0; j < 4; j++)
                weights[i][j] = random.nextDouble();
        }
    }
    
    //funkcja normalizuj¹ca
    public void normalizeFlowerSet(){   
    	double min_elem = 0.0;
    	double max_elem = 0.0;       
        min_elem = min(learnFlowerSet);
        max_elem = max(learnFlowerSet);
            
        for(int i =0 ; i<noInputs ; i++){              	
            for(int j=0;j<4;j++)            {
                learnFlowerSet[i][j] = (learnFlowerSet[i][j] - min_elem)/(max_elem - min_elem);
            }        
        }
    }
    
    public void normalizeTestFlowerSet(){
    	double min_elem = 0.0;
    	double max_elem = 0.0;
        min_elem = min(testFlowerSet);
        max_elem = max(testFlowerSet);
        for(int i =0 ; i<noInputs ; i++){       
        	                     
            for(int j=0;j<4;j++)            {
                testFlowerSet[i][j] = (testFlowerSet[i][j] - min_elem)/(max_elem - min_elem);
            }        
        }
    }
    
  //normalizuja danych ucz¹cych
    public void normalizeLearningSet() {
        double suma = 0.0;
        double Sqrt = 0.0;
        for(int i =0 ; i<noInputs ; i++){
            for(int j=0;j<4;j++){
                suma +=learnFlowerSet[i][j]*learnFlowerSet[i][j];
            }
           Sqrt=Math.sqrt(suma);
           for(int j=0;j<4;j++){
                learnFlowerSet[i][j]/=Sqrt;
            }
            suma =0.0;
        }
    }    
    //normalizuja danych testuj¹cych
    public void normalizeTestSet() {
        double suma = 0.0;
        double Sqrt = 0.0;
        for(int i =0 ; i<noInputs ; i++){
            for(int j=0;j<4;j++){
                suma +=testFlowerSet[i][j]*testFlowerSet[i][j];
            }
           Sqrt=Math.sqrt(suma);
           for(int j=0;j<4;j++){
                testFlowerSet[i][j]/=Sqrt;
            }
            suma =0.0;
        }
    }
    
    
    public double calculateInput(int j, int k){
        double activate = 0;
        for(int i = 0; i < 4; i++) {
            activate += learnFlowerSet[k][i] * weights[j][i];
        }
        return activate;
    }
    
    public double calculateTestInput(int j, int k){
        double activate = 0;
        for(int i = 0; i < 4; i++) {
            activate += testFlowerSet[k][i] * weights[j][i];
        }
        return activate;
    }
    
    //zwraca id elementu tablicy -> ten neuron ma najwiêksz¹ wartoœæ aktywacji
    public int winner(double[] result){
    	double input = result[0];
    	int id = 0;
    	for(int i=1; i<result.length; i++){
    		if(input<result[i]){
    			input = result[i];
    			id = i;
    		}
    	}
    	return id;
    }
    
    public double max(double[][] result){
    	double input = result[0][0];   	
    	for(int i=1; i<noInputs; i++){
    		for(int j=1; j<4;j++)
    		if(input<result[i][j]){
    			input = result[i][j];
    			
    		}
    	}
    	return input;
    }
    
    public double min(double[][] result){
    	double input = result[0][0];    
    	for(int i=1; i<noInputs; i++){
    		for(int j=1; j<4;j++)
    		if(input>result[i][j]){
    			input = result[i][j];    			
    		}
    	}
    	return input;
    }
    
    public void updateWeights(int j,int l){
    	for (int i = 0; i < 4; i++) {
            weights[j][i] = weights[j][i] + LEARNING_RATE * (learnFlowerSet[l][i] - weights[j][i]);
    }    
    }
    
}
