package sg.edu.rp.p14bounce;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class Bounce extends Activity {
	Ball b;
	ArrayList<Ball> position = new ArrayList<Ball>();
	DrawView myView;
	Handler ballHandler;
	Random r = new Random();
	
	int [] size = {10, 20, 35, 40};
	Random randomSize = new Random();
	
	int [] color = {Color.RED, Color.BLUE, Color.CYAN, Color.GREEN, Color.GRAY, Color.MAGENTA, Color.WHITE};
	Random randomColor = new Random();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        
        b = new Ball(60, 60, 30, 1, 1, Color.RED);
        position.add(b);
        
        Log.d("max height", "-> " + getWindowManager().getDefaultDisplay().getHeight());
        
        //Generate 10 random ball
        for(int i = 0; i < 10; i++) {
        	float randomHeight = (float) (Math.random() * getWindowManager().getDefaultDisplay().getHeight());
        	float randomWidth = (float) (Math.random() * getWindowManager().getDefaultDisplay().getWidth());
        	
        	position.add(new Ball(randomWidth, randomHeight, size[randomSize.nextInt(4)], r.nextInt(15), r.nextInt(15), color[randomColor.nextInt(7)]));
        	
        	Log.d(getPackageName(), "Random height -> " + randomHeight);
        	Log.d(getPackageName(), "Random width -> " + randomWidth);
        }
        
        myView = new DrawView(this);
        setContentView(myView);
        myView.invalidate();
        
        ballHandler = new Handler() {
        	public void handleMessage(Message msg) {
        		myView.invalidate();
        	}
        };
        
        TimerTask ballUpdate = new TimerTask() {
        	@Override
        	public void run() {
        		for(int i = 0; i < position.size(); i++) {
	        		position.get(i).update();
	        		position.get(i).setRegion(getWindowManager().getDefaultDisplay().getWidth(), getWindowManager().getDefaultDisplay().getHeight());
	        		ballHandler.sendEmptyMessage(0);
        		}
        	}
        };
       
        Timer t = new Timer();
        t.schedule(ballUpdate, 0, 42);
    }
    
    private class DrawView extends View {
    	public DrawView(Context context) {
    		super(context);
    	}
    	
    	protected void onDraw(Canvas canvas) {
    		super.onDraw(canvas);
    		
    		for (int i = 0; i < position.size(); i++) {
    			b = position.get(i);
    			
	    		Paint p = new Paint();
	    		p.setColor(position.get(i).getColor());
	    		p.setAlpha(155);
	    		
	    		canvas.drawCircle(b.getX(), b.getY(), b.getRadius(), p);
    		}
    	}
    	
    	@Override
    	public boolean onTouchEvent(MotionEvent event) {   		
    		Log.d(getPackageName(), "x: " + event.getX() + ", y: " + event.getY());
    		
    		int x = (int) event.getX();
    		int y = (int) event.getY();
    		for(int b = 0; b < position.size(); b++) {
        		if(x > position.get(b).getX() - position.get(b).getRadius() &&
        		   x < position.get(b).getX() + position.get(b).getRadius() &&
        		   y > position.get(b).getY() - position.get(b).getRadius() &&
        		   y < position.get(b).getY() + position.get(b).getRadius()) {
        			position.remove(b);
        		}
    		}
    		
    		return super.onTouchEvent(event);
    	}
    }
}