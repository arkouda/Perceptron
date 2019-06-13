import javax.swing.*;
import java.util.Random;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.concurrent.CountDownLatch;



public class Main extends JFrame{
	private static final long serialVersionUID = 1L;
	static int width = 400;
	static int height = 400;
	
	public static void main(String[] args){
		
		JFrame window = new JFrame();
		window.setSize(width*2, height*2);
		window.setTitle("Feed Forward Supervised Learning");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		
		Neuron neo= new Neuron();
		Random r= new Random();
		Point2D.Float[] pointsArray= new Point2D.Float[100];
		int[] guessArray = new int[pointsArray.length];
		int[] calculatedAnswers = new int[pointsArray.length];
		
		for(int i=0;i<pointsArray.length;i++){
			//Initialize points in Cartesian Plane
			pointsArray[i]= new Point2D.Float((r.nextFloat()*2-1)*width,(r.nextFloat()*2-1)*height);
			guessArray[i]=neo.guess(pointsArray[i]);
			calculatedAnswers[i]=f(pointsArray[i].x)>=pointsArray[i].y?1:-1;
		}
		paint p = new paint(pointsArray,calculatedAnswers,guessArray,neo.weights);
		window.add(p);
		while(!(p.ans.equals(p.gs))){
			System.out.println(neo.weights[0]+" "+neo.weights[1]);
			waitForSpace();
			for(int i=0;i<pointsArray.length;i++){
				neo.train(pointsArray[i],calculatedAnswers[i]);
				guessArray[i]=neo.guess(pointsArray[i]);
				p.gs[i]=guessArray[i];
				p.w=neo.weights.clone();
				window.update(window.getGraphics());
			}
		}
		System.out.println("Weights Tweaked!!!");
		return;
	}	
	
	public static Point2D.Float toPixelPlane(Point2D.Float p){
		return new Point2D.Float(p.x+width,mod(p.y-height));
	}
	
	public static float mod(float x){
		return x<0?-x:x;
	}
	
	public static float f(float x){
		return 0.2f*x-0.364f;
	}
	
	public static void waitForSpace() {
	    final CountDownLatch latch = new CountDownLatch(1);
	    KeyEventDispatcher dispatcher = new KeyEventDispatcher() {
	        // Anonymous class invoked from EDT
	        public boolean dispatchKeyEvent(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_SPACE)
	                latch.countDown();
	            return false;
	        }
	    };
	    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
	    try {
			latch.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}  // current thread waits here until countDown() is called
	    KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dispatcher);
	}
}
