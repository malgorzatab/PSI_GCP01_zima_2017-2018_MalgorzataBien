package network;

public class Letter {

	public int letter[][];					//wejœcia czyli tablica zawieraj¹ca ci¹g 0 i 1 przedstawiaj¹cych dan¹ litere																								//wspó³czynnik uczenia
	public char[] charLetter = {'A','B','C','D','E','F','G','H','I','J','a','b','c','d','e','f','g','h','i','j'};		//litera w formacie ASCII
	public static int[] result = {1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0}; //result=1 du¿a litera, result=0 ma³a litera
	
	public Letter(){
		letter = new int[7][5];
	}

	public int[][] getLetter() {
		return letter;
	}
	
	public double getLetter(int i, int j) {			
			return letter[i][j];
		}

	public void setLetter(int[][] letter) {
		this.letter = letter;
	}

	//public int[] getResult(int i) {
	//	return result[i];
	//}

	public void setResult(int[] result) {
		this.result = result;
	}

	public void setLetter(int i, int j, int value) {
		this.letter[i][j] = value;
		
	}
	
	//funckja zwracaj¹ca sume waga*wartoœæ na poszczególnym wejœciu
		public double processCellNode(double[]weights){
			double sum=0;
			int l=0;
			for(int i=0; i<7; i++){
				for(int j=0; j<5; j++){
					if(l<35){
						sum += letter[i][j]*weights[l];
					}
					l++;
				}			
			}
			return sum;
		}

		
	
	
}
