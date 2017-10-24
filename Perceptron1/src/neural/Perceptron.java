package neural;

import java.util.Random;

public class Perceptron {

	NeuralFXMLController controller = new NeuralFXMLController();
	
	public static final int[][] ANDdata = {
			{0,0,0},
			{0,1,0},
			{1,0,0},
			{1,1,1}};
	
	public static final double LEARNING_RATE=0.05;
	public static final double[] WEIGHTS = {Math.random(),Math.random()};
	
	public Perceptron(NeuralFXMLController neuralFXMLController) {
		this.controller=neuralFXMLController;
	}


	public double processCellNode(int[]dendrites,double[]synapses){
		double sum=0;
		for(int i=0; i<dendrites.length; i++){
			sum += dendrites[i]*synapses[i];
		}
		return sum;
	}
	
	public int activationFunction(double cellNode){
		int result;
		if(cellNode > 1) {
			result = 1;
		}else result=0;
		return result;
	}
	
	public double[] calculateWeight(int[]data, double[]weights, double error){
		double[] inputWeights =  new double[weights.length];
		for(int i=0; i<weights.length; i++){
			inputWeights[i] = LEARNING_RATE *error * data[i] + weights[i]; 
		}
		return inputWeights;
	}
	
	double[]weights = Perceptron.WEIGHTS;
	int iter = 0;
	boolean errorFlag = true;
	double error = 0;
	double[] adjustedWeights = null;
	
	public void logicalFunction(){
		while(errorFlag){
			controller.setText("\n");
			controller.setText("Iteration :" + iter+"\n");
			controller.setText("________________________________________________________________________________________________________________\n");
			controller.setText("  w1  |  w2  |  x1  |  x2  | Target result | Result |  error   | WeightedSum | adjusted w1 | adjusted w2\n");
			controller.setText("___________________________________________________________________________________________________________\n");
			errorFlag=false;
			error = 0;
			for(int i=0; i<ANDdata.length; i++){
				int[]input={ANDdata[i][0],ANDdata[i][1]};
				double calculateInput =  processCellNode(input,weights);
				int result = activationFunction(calculateInput);
				error = ANDdata[i][2] - result;
				if(error != 0)
					errorFlag = true;
				
				adjustedWeights = calculateWeight(input, weights, error);
				controller.setText(ANDdata, weights, result, error, calculateInput, adjustedWeights);
				controller.setText("\n");
				weights=adjustedWeights;
				
			}iter++;
		}		
	}
	
	
	public void Testing(){
		
		int testIter=0;
		int result=0;
		Random r = new Random();
		int x=0,y=0;
		
		for(int i=0; i<1000; i++){
		
			controller.setText("\n");
			controller.setText("Iteration :" + testIter+"\n");
			controller.setText("________________________________________________________________________________________________________________\n");
			
			x = r.nextInt(2);
		    y = r.nextInt(2);
			
		    int[]testData = { x,y };								
			double weightedSum =  processCellNode(testData,weights);
			int doTest = activationFunction(weightedSum);
			if(doTest==1 && x==1 && y==1){				
				result++; 			//iloœæ poprawnych odp
			}
			controller.setTestText(x,y,doTest);
			controller.setText("\n");
					
			testIter++;
			}
			controller.setText("Ilosc poprawnych odp: "+result+"\n");
		
		}	
		
	}
	
	

