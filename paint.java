import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class paint extends JPanel{
	
	static int width = 400;
	static int height = 400;
	private static final long serialVersionUID = 1L;
	float[] origX;
	float[] origY;
	float[] x;
	float[] y;
	int[] ans;
	int[] gs;
	float[] w;
	paint(Point2D.Float[] pointsArray,int[] calculatedAnswers, int[] guessArray, float[] weights){
		w=new float[weights.length];
		w=weights.clone();
		x=new float[pointsArray.length];
		y=new float[pointsArray.length];
		origX=new float[pointsArray.length];
		origY=new float[pointsArray.length];
		ans=new int[pointsArray.length];
		gs=new int[pointsArray.length];
		for(int i=0; i<pointsArray.length;i++){
			origX[i]=pointsArray[i].x;
			x[i]=toPixelPlane(pointsArray[i]).x;
			origY[i]=pointsArray[i].y;
			y[i]=toPixelPlane(pointsArray[i]).y;
			ans[i]=calculatedAnswers[i];
			gs[i]=guessArray[i];
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Ellipse2D.Float outerCircle = new Ellipse2D.Float();
		Ellipse2D.Float innerCircle = new Ellipse2D.Float();
		Point2D.Float pt1 = new Point2D.Float(width, f(width));
		Point2D.Float pt2 = new Point2D.Float(-width, f(-width));
		Line2D.Float line = new Line2D.Float(toPixelPlane(pt1), toPixelPlane(pt2));
		g2d.draw(line);
		Point2D.Float pt3 = new Point2D.Float(width, wf(width));
		Point2D.Float pt4 = new Point2D.Float(-width, wf(-width));
		Line2D.Float lineAbstract = new Line2D.Float(toPixelPlane(pt3), toPixelPlane(pt4));
		g2d.draw(lineAbstract);
		for(int i=0;i<x.length;i++){
			g2d.setPaint(Color.black);
			outerCircle.setFrame(x[i]-12, y[i]-12, 24f, 24f);
			if(f(origX[i])>=origY[i]){
				g2d.draw(outerCircle);
			}
			else{
				g2d.fill(outerCircle);
			}
			innerCircle.setFrame(x[i]-4, y[i]-4, 8f, 8f);
			if(ans[i]==gs[i]){
				g2d.setPaint(Color.green);
			}
			else{
				g2d.setPaint(Color.red);
			}
			g2d.fill(innerCircle);
		}
	}
//	
//	interface FunctionCaller{
//		public float fxn(float z);
//	}
//	private float operate(float z, FunctionCaller f){
//		return f.fxn(z);
//	}
//
	public Point2D.Float toPixelPlane(Point2D.Float p){
		return new Point2D.Float(p.x+width,mod(p.y-height));
	}
	public float mod(float n){
		return n<0?-n:n;
	}
	public static float f(float x){
		return 0.2f*x+0.364f;
	}
	public float wf(float x){
		return (-w[0]*x-w[2])/w[1];
	}
}
