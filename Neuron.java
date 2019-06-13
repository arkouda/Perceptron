import java.util.Random;
import java.awt.geom.*;


public class Neuron{
	float[] weights = new float[3];
	int bias = 1;
	float learningFactor = 0.001f;
	Neuron(){
		Random rand = new Random();
		for(int i=0;i<weights.length;i++){
			weights[i]=rand.nextFloat()*2-1;
		}
	}
	
	public int sign(float num){
		return num>=0?1:-1;
	}
	
	public int guess(Point2D.Float input){
		float sum=weights[0]*input.x+weights[1]*input.y+weights[2]*bias;//+weight[2];
		return sign(sum); 
	}
	
	public void train (Point2D.Float inputPoint, int answer){
		int guess=guess(inputPoint);
		int error=answer-guess;
		if(error!=0){
			this.weights[0]+=inputPoint.x*(float)error*learningFactor;
			this.weights[1]+=inputPoint.y*(float)error*learningFactor;
			this.weights[2]+=(float)error*learningFactor;
			System.out.println("Delta w :" +((float)inputPoint.x)*(float)error*0.1f+ " "+((float)inputPoint.y)*(float)error*0.1f);
		}
	}
}
