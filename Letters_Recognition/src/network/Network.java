package network;

public class Network {

	private Controller myController;
	private double[]weights = new double[35];
	
	public Network(Controller controller) {
		this.myController = controller;
	}
	
	
	
	public void letterRecognition() {
		
		for(int i=0; i<35; i++){
		weights[i] = Math.random();					//losowanie pocz¹tkowych wag
	}
		Adaline adaline = new Adaline(myController);
		adaline.startAdaline(weights);
		adaline.testing(weights);
		
		Sigmoidal sigmoidal = new Sigmoidal(myController);
		//sigmoidal.startSigmoidal(weights);
		//sigmoidal.testing(weights);
		
	}

}
