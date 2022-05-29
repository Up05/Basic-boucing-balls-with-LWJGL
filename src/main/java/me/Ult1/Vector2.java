package me.Ult1;

public class Vector2 {
    public double x = 0d, y = 0d;

    public Vector2(double x, double y){
        this.x = x;
        this.y = y;

    }

    public Vector2(){}

    public Vector2 add(Vector2 vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vector2 sub(Vector2 vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vector2 mlt(Vector2 vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vector2 set(Vector2 vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vector2 mlt(double x, double y){
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2 add(double x, double y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2 set(double x, double y){
        this.x = x;
        this.y = y;
        return this;
    }

    public static double normalizationDivisor(double x, double y){
        return Math.sqrt(x*x + y*y);
    }
}
