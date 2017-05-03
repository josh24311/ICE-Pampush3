package games.math;

import main.MsPacInterface;

public class Vector2d {
    // of course, also require the methods for adding
    // to these vectors
    public double x, y;
    
    public Vector2d() {
        this(0, 0);
    }
    
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void add(Vector2d v) {
        this.x += v.x;
        this.y += v.y;
    }

    public void add(Vector2d v, double w) {
        // weighted addition
        this.x += w * v.x;
        this.y += w * v.y;
    }

    public void subtract(Vector2d v) {
        this.x -= v.x;
        this.y -= v.y;
    }

    public void mul(double fac) {
        x *= fac;
        y *= fac;
    }

    public void div(double den) {
        x /= den;
        y /= den;
    }

    public void limit(double maxMag) {
        double mag = this.mag();
        if (mag > maxMag) {
            this.mul(maxMag / mag);
        }
    }

    public void setMag(double m) {
        if (mag() != 0) { // can still blow up!
            this.mul( m / mag() );
        }
    }

    public void rotate(double theta) {
        // rotate this vector by the angle made to the horizontal by this line
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);

        double nx = x * cosTheta - y * sinTheta;
        double ny = x * sinTheta + y * cosTheta;

        x = nx;
        y = ny;
    }

    public void rotate(Vector2d start, Vector2d end) {
        // rotate this vector by the angle made to the horizontal by this line
        double r = start.dist(end);
        double cosTheta = (end.x - start.x) / r;
        double sinTheta = (end.y - start.y) / r;

        double nx = x * cosTheta - y * sinTheta;
        double ny = x * sinTheta + y * cosTheta;

        x = nx;
        y = ny;
    }

    public double scalarProduct(Vector2d v) {
        return x * v.x + y * v.y;
    }

    public void contraRotate(Vector2d start, Vector2d end) {
        // rotate this vector by the opposite angle made to the horizontal by this line
        double r = start.dist(end);
        double cosTheta = (end.x - start.x) / r;
        double sinTheta = (end.y - start.y) / r;

        double nx = x * cosTheta + y * sinTheta;
        double ny = -x * sinTheta + y * cosTheta;

        x = nx;
        y = ny;
    }

    public void set(Vector2d v) {
        this.x = v.x;
        this.y = v.y;
    }

    public void set(int x,int y){
    	this.x=(double)x;
    	this.y=(double)y;
    }
    
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void zero() {
        x = 0.0;
        y = 0.0;
    }

    public String toString() {
        return x + " : " + y;
    }

    public static double sqr(double x) {
        return x * x;
    }

    public double sqDist(Vector2d v) {
        return sqr(x - v.x) + sqr(y - v.y);
    }

    public double mag() {
        return Math.sqrt(sqr(x) + sqr(y));
    }

    public double sqMag() {
        return sqr( x ) + sqr( y );
    }

    public double dist(Vector2d v) {
        //return Math.sqrt(sqDist(v));
    	return (double)MsPacInterface.dij.dij[(int)this.x][(int)this.y][(int)v.x][(int)v.y];
    }
    public double dist(int x,int y){
    	//return (double)(((double)x-this.x)*((double)x-this.x)
    	//		+((double)y-this.y)*((double)y-this.y));
    	return (double)MsPacInterface.dij.dij[(int)this.x][(int)this.y][x][y];
    }
    
    public int dist2(int x,int y){
    	//return Math.abs((int)this.x-x)+Math.abs((int)this.y-y);
    	return MsPacInterface.dij.dij[(int)this.x][(int)this.y][(int)x][(int)y];
    }
   
    public int dist2(Vector2d point){
    	//return dist2((int)point.x,(int)point.y);
    	return MsPacInterface.dij.dij[(int)this.x][(int)this.y][(int)point.x][(int)point.y];
    }
    
    public int dist3(int x1,int x2,int y1,int y2){
    	return Math.abs(x1-x2)+Math.abs(y1-y2);
    	//return MsPacInterface.dij.dij[(int)x1][(int)y1][(int)x2][(int)y2];
    }
    public int dist3(Vector2d v,Vector2d w){
    	return dist3((int)v.x,(int)w.x,(int)v.y,(int)w.y);
    	//return MsPacInterface.dij.dij[(int)v.x][(int)v.y][(int)w.x][(int)w.y];
    }
    
    public int dist4(Vector2d point){
		/*座標調整（芦田）*/
    	int setX1, setX2;
		if(point.x <= 12){
			setX1 = (int)point.x + 14;
		}
		else if(point.x >= 41){
			setX1 = (int)point.x - 40;
		}
		else 
			setX1 = (int)point.x - 13;
		
		if(this.x <= 12){
			setX2 = (int)this.x + 14;
		}
		else if(this.x >= 41){
			setX2 = (int)this.x - 40;
		}
		else 
			setX2 = (int)this.x - 13;;
    	return MsPacInterface.dij.dij[(int)setX2][(int)this.y][(int)setX1][(int)point.y];
    }
    
    public int dist4(int x, int y){
		/*座標調整（芦田）*/
    	int setX1, setX2;
		if(x <= 12){
			setX1 = (int)x + 14;
		}
		else if(x >= 41){
			setX1 = (int)x - 40;
		}
		else 
			setX1 = (int)x - 13;
		
		if(this.x <= 12){
			setX2 = (int)this.x + 14;
		}
		else if(this.x >= 41){
			setX2 = (int)this.x - 40;
		}
		else 
			setX2 = (int)this.x - 13;
		
    	return MsPacInterface.dij.dij[(int)setX2][(int)this.y][(int)setX1][(int)y];
    }
    
    public int dist4(Vector2d v, Vector2d w){
		/*座標調整（芦田）*/
    	int setX1, setX2;
		if(v.x <= 12){
			setX1 = (int)v.x + 14;
		}
		else if(v.x >= 41){
			setX1 = (int)v.x - 40;
		}
		else 
			setX1 = (int)v.x - 13;
		
		if(w.x <= 12){
			setX2 = (int)w.x + 14;
		}
		else if(w.x >= 41){
			setX2 = (int)w.x - 40;
		}
		else 
			setX2 = (int)w.x - 13;
		
    	return MsPacInterface.dij.dij[(int)setX2][(int)w.y][(int)setX1][(int)v.y];
    }
}
