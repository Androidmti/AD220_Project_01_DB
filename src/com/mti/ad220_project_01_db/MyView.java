/*
 * MyView.java
 * Daniel Borodenko
 */
package com.mti.ad220_project_01_db;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;

public class MyView extends View {

	Context context;
	
	private ArrayList<Circle> circles; // holds all circles

    private int screenW; // width of screen
    private int screenH; // height of screen
    
    private SharedPreferences sharedPref;
    
    private int numberOfCircles;
    private int radius;
    private String bg_color; // background color
    private Paint paint = new Paint();

	
	public MyView(Context context) {
		super(context);

		this.context = context;
		// initiate shared preferences variable
		sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
		
		// assign shared preferences to local variables
		numberOfCircles = Integer.valueOf(sharedPref.getString("pref_number", "10"));
		radius = Integer.valueOf(sharedPref.getString("pref_size", "100"));
		bg_color = sharedPref.getString("pref_bg_color", "White");
		
		if (bg_color.equals("Random")) {
			// if background color preference is "Random",
			// create random color and assign to bg_color
			Random rand = new Random();
			int r = rand.nextInt(255);
			int g = rand.nextInt(255);
			int b = rand.nextInt(255);
			bg_color = String.valueOf(Color.rgb(r,g,b));
		}
		
		// create Runnable to move circles in another thread
		final Handler handler = new Handler();
		
		final Runnable r = new Runnable()
		{
		    public void run() 
		    {
		    	// move circles every 30ms
		    	moveCircles();
		        handler.postDelayed(this, 30);
		    }
		};
		// delay initial move of circles by 500ms
		handler.postDelayed(r, 500);

	} // MyView(Context context)

	@Override
	public void onSizeChanged (int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
        screenW = w; // set screen width
        screenH = h; // set screen height
        
        // initialize ArrayList of Circles
        circles = new ArrayList<Circle>();
        
		for (int i = 0; i < numberOfCircles; i++) {
			
			circles.add(new Circle(screenW, screenH, radius));

		}
	} // onSizeChanged (int w, int h, int oldw, int oldh)
	
	@Override
	protected void onDraw (Canvas canvas) {
		
		// draw color of canvas based on preference
		if (bg_color.equals("White")) {
			canvas.drawColor(Color.WHITE);
		}
		
		else if (bg_color.equals("Black")) {
			canvas.drawColor(Color.BLACK);
		}
		
		else {
			canvas.drawColor(Integer.valueOf(bg_color));
		}
		
		// loop through ArrayList of circles and draw each circle
		for (int i = 0; i < numberOfCircles; i++) {

			paint.setColor(circles.get(i).getColor());
			canvas.drawCircle(circles.get(i).getCenterX(), circles.get(i).getCenterY(),
					circles.get(i).getRadius(), paint); 
		}
	
	} // onDraw (Canvas canvas)
	
	public void moveCircles() {
		
		if (circles == null)
			return; // exit out of method if there are no circles
		
		Circle c = new Circle(); // temp circle to hold current circle

		for (int i = 0; i < numberOfCircles; i++) {
			
			c = circles.get(i); // assign current circle to temp circle
	
			int rightLimit = screenW - c.getRadius(); 
			int bottomLimit = screenH - c.getRadius();
			
			if (c.getCenterX() >= rightLimit) {
				// if circle hits right side of the screen
				// negate speed, so that circle will move left
				c.setSpeedX(-c.getSpeedX());
			}
			
			if (c.getCenterX() <= c.getRadius()) {
				// else if circle hits left side of the screen
				// negate speed, so that circle will move right
				c.setSpeedX(-c.getSpeedX());
			}
			
			
			if (c.getCenterY() >= bottomLimit) {
				// if circle hits bottom side of the screen
				// negate speed, so that circle will move up
				c.setSpeedY(-c.getSpeedY());
			}

			if (c.getCenterY() <= c.getRadius()) {
				// else if circle hits upper side of the screen
				// negate speed, so that circle will move down
				c.setSpeedY(-c.getSpeedY());
			}
			
			// set new X and Y coordinates of center of the circle
			
			int newCenterX = c.getCenterX() + c.getSpeedX();
			int newCenterY = c.getCenterY() + c.getSpeedY();
			
			// check to see if new 
			if (newCenterX > rightLimit)
				c.setCenterX(rightLimit);
			else if (newCenterX < c.getRadius())
				c.setCenterX(c.getRadius());
			else
				c.setCenterX(newCenterX);
			
			if (newCenterY > bottomLimit)
				c.setCenterY(bottomLimit);
			else if (newCenterY < c.getRadius())
				c.setCenterY(c.getRadius());
			else
				c.setCenterY(c.getCenterY() + c.getSpeedY());
		}
		
		invalidate(); // draw circle
		
	} // moveCircles()

} // class MyView extends View
