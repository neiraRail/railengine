package com.railitus.game;

public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Vector zero() {
        return new Vector(0,0);
    }

    public static Vector copy(Vector vector) {
        return new Vector(vector.x,vector.y);
    }

    public static Vector polar(double magnitude, double angle) {
        return new Vector(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
    }

    public void set(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void set(Vector v){
        this.x = v.x;
        this.y = v.y;
    }

    public static Vector add(Vector v1, Vector v2){
        return new Vector(v1.x+v2.x,v1.y+v2.y);
    }

    public void add(Vector vector) {
        this.x += vector.x;
        this.y += vector.y;
    }

    public static Vector sub(Vector v1, Vector v2){
        return new Vector(v1.x-v2.x,v1.y-v2.y);
    }

    public void sub(Vector vector) {
        this.x -= vector.x;
        this.y -= vector.y;
    }

    public void mul(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public double dot(Vector vector){
        return vector.x*this.x + vector.y*this.y;
    }

    public double getLength() {
        return Math.sqrt(x * x + y * y);
    }

    public double distance(Vector v) {
        double vx = v.x - this.x;
        double vy = v.y - this.y;
        return Math.sqrt(vx * vx + vy * vy);
    }

    public double angle(){
        return Math.asin(getLength());
    }

    public void normalize() {
        double magnitude = getLength();
        if(magnitude!=0) {
            x /= magnitude;
            y /= magnitude;
        }
        else{
            x=0;
            y=0;
        }
    }

    public void reverse() {
        x = -x;
        y = -y;
    }

    public static double constrain(double scalar, float min, float max){
        if(scalar>max){
            return max;
        }
        else if(scalar<min){
            return min;
        }
        else{
            return scalar;
        }
    }

    public static Vector constrainVector(Vector vector,float min, float max){
        double angle = vector.angle();
        if(vector.getLength()>max){
            return new Vector(min*Math.cos(angle),min*Math.sin(angle));
        }
        else if(vector.getLength()<min){
            return new Vector(max*Math.cos(angle),max*Math.sin(angle));
        }
        else{
            return vector;
        }
    }

    public static Vector getReversed(Vector v) {
        return new Vector(-v.x, -v.y);
    }

    public static Vector getMid(Vector v){
        return new Vector(v.x/2,v.y/2);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vector) {
            Vector v = (Vector) obj;
            return (x == v.x) && (y == v.y);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vector2d[" + x + ", " + y + "]";
    }


}
