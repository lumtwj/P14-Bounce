package sg.edu.rp.p14bounce;

public class Ball {
	float x;
	float y;
	int radius;
	int xspeed;
	int yspeed;
	int canvasHeight;
	int canvasWidth;
	int color;
	
	public Ball(float x, float y, int radius, int xSpeed, int ySpeed, int color) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.xspeed = xSpeed;
		this.yspeed = ySpeed;
		this.radius = radius;
		this.color = color;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}
	
	public int getColor() {
		return color;
	}
	
	public void setRegion(int width, int height) {
		this.canvasWidth = width;
		this.canvasHeight = height;
	}
	
	public void update() {
		this.x = (float)(this.x + this.xspeed);
		this.y = (float)(this.y + this.yspeed);
		
		if(this.x > this.canvasWidth){
			xspeed = -xspeed;
		}
		if(this.x < 0){
			xspeed = -xspeed;
		}
		if(this.y > this.canvasHeight){
			yspeed = -yspeed;
		}
		if(this.y < 0){
			yspeed = -yspeed;
		}
		
	}
}
