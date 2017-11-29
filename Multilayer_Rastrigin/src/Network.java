import java.util.Random;

public class Network {
 
	private double[][] output;
	private double[][][] weights;
	private double[][] bias;
	private int BETA = 1;
	private double[][] error_signal;
	private double[][] output_derivative;
	
	public final int[] NETWORK_LAYER_SIZES;// =  new int[5];
	//public int[] noLayers;
	public int inputSize;
	public int outputSize;
	public int networkSize;
	double[] random = new Random().doubles(100, 0, 100).toArray();
	

	
	
	public Network(int... NETWORK_LAYER_SIZES){
		this.NETWORK_LAYER_SIZES = NETWORK_LAYER_SIZES;
		//this.noLayers = NETWORK_LAYER_SIZES;
		this.inputSize = NETWORK_LAYER_SIZES[0];
		this.networkSize = NETWORK_LAYER_SIZES.length;
		this.outputSize = NETWORK_LAYER_SIZES[networkSize - 1];
		
		this.output = new double[networkSize][];
		this.weights = new double[networkSize][][];
		this.bias = new double[networkSize][];
		this.error_signal = new double[networkSize][];
		this.output_derivative = new double[networkSize][];
		
		
		for( int i=0 ; i<networkSize; i++){		
			this.output[i] = new double[NETWORK_LAYER_SIZES[i]];
			this.bias[i] = new double[NETWORK_LAYER_SIZES[i]];
		//	this.bias[i] = new Random().doubles(1, 0, 1).toArray();
			this.bias[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i],0.3,0.7);
			this.error_signal[i] = new double[NETWORK_LAYER_SIZES[i]];
			this.output_derivative[i] = new double[NETWORK_LAYER_SIZES[i]];
			if(i>0){
			this.weights[i] = new double[NETWORK_LAYER_SIZES[i]][NETWORK_LAYER_SIZES[i-1]];
			this.weights[i] = NetworkTools.createRandomArray(NETWORK_LAYER_SIZES[i],NETWORK_LAYER_SIZES[i-1],0.3,0.7);
			}
			
		
		}
		
		
	}
	
	public double[] calculate(double...input){
		if(input.length != this.inputSize) return null;
		this.output[0] = input;
		for( int layer=1 ; layer<networkSize; layer++){
			for(int neuron=0; neuron<NETWORK_LAYER_SIZES[layer]; neuron++){
				double sum = bias[layer][neuron];
				for( int prevNeuron=0; prevNeuron<NETWORK_LAYER_SIZES[layer-1]; prevNeuron++){
					sum += output[layer-1][prevNeuron]*weights[layer][neuron][prevNeuron];
				}
				output[layer][neuron]=sigmoid(sum);
				output_derivative[layer][neuron] = output[layer][neuron]*(1-output[layer][neuron]);
			}
		}
		return output[networkSize-1];
	}
	
	private double sigmoid(double sum){

        return ( 1 / ( 1 + Math.exp(-BETA * sum)));
    }
	
	public void train(double[] input, double[]target, double eta){
		if(input.length != inputSize || target.length != outputSize)return;
			
		calculate(input);
		backPropagation(target);
		updateWeights(eta);
	}
	
	public void backPropagation(double[] target){
		for( int neuron=0; neuron<NETWORK_LAYER_SIZES[networkSize-1]; neuron++){
			error_signal[networkSize-1][neuron]=(output[networkSize-1][neuron]-target[neuron])*output_derivative[networkSize-1][neuron];			
		}
		for( int layer=networkSize-2;layer>0; layer--){
			for(int neuron=0;neuron<NETWORK_LAYER_SIZES[layer]; neuron++ ){
				double sum=0;
				for(int nextNeuron=0; nextNeuron<NETWORK_LAYER_SIZES[layer+1]; nextNeuron++){
					sum += weights[layer+1][nextNeuron][neuron]*error_signal[layer+1][nextNeuron];
				}
				this.error_signal[layer][neuron]= sum * output_derivative[layer][neuron];
			}
		}
	}
	
	public void updateWeights(double eta){
		for(int layer=1; layer<networkSize; layer++){
			for(int neuron=0; neuron<NETWORK_LAYER_SIZES[layer]; neuron++){
				for(int prevNeuron=0; prevNeuron<NETWORK_LAYER_SIZES[layer-1]; prevNeuron++){
					//weights[layer][neuron][prevneuron]
					double delta = -eta * output[layer-1][prevNeuron] * error_signal[layer][neuron];
					weights[layer][neuron][prevNeuron] +=delta;
				}
				double delta = -eta * error_signal[layer][neuron];
				bias[layer][neuron] +=delta;
			}
		}
	}
	
	
	
	
	
	
	
	
}
