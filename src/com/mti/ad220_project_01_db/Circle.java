/*
 * Circle.java
 * Daniel Borodenko
 */
package com.mti.ad220_project_01_db;

import java.util.Random;

import android.graphics.Color;

public class Circle {
	private int centerX;
	private int centerY;
	private int radius;
	private int speedX;
	private int speedY;
	private int color;
	
	public Circle (int width, int height, int radius) {

		this.radius = radius;

		Random rand = new Random();
		
		// set random X and Y coordinates of the center of circle
		// based on screen width and height

		this.centerX = rand.nextInt(width - (radius * 2)) + radius;
		this.centerY = rand.nextInt(height - (radius * 2)) + radius;
		
		// set random color
		
		int r = rand.nextInt(255);
		int g = rand.nextInt(255);
		int b = rand.nextInt(255);
		
		this.color = Color.rgb(r,g,b);
		
		// set random speed on X coordinate
		speedX = rand.nextInt(30) + 10;
		// randomly choose direction
		if (rand.nextInt(2) == 1)
			speedX = -speedX;
		
		// set random speed on Y coordinate
		speedY = rand.nextInt(30) + 10;
		// randomly choose direction
		if (rand.nextInt(2) == 1)
			speedY = -speedY;

	} // Circle (int width, int height, int radius)
	
	public Circle() {}


	public int getCenterX() {
		return centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
	
	public int getColor() {
		return color;
	}
	
} // class Circle
