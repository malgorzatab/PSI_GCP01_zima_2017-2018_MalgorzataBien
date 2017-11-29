import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Network net = new Network(2,3,3,2);
		
		//double[] input = new double[]{0.1,0.2,0.5,0.9};
		//double[] target = new double[]{0,1,0,1};
		double[] input = new double[]{0.1,0.2};
		double[] target = new double[]{0,1};
		for(int i =0; i<1000; i++){
			net.train(input, target, 0.5);
		}
		//double[] output = net.calculate(0.2,0.9,0.3,0.4);
		double[] output = net.calculate(input);
		System.out.println(Arrays.toString(output));
	}

}
