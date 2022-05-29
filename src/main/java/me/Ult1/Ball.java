package me.Ult1;

import static org.lwjgl.opengl.GL11.*;

public class Ball {

    Vector2 pos, vel = new Vector2(12, 0), acc = new Vector2(0, 0);
    double size;
    Color color;
    public static final double GRAVITY = -1d;
    boolean dead = false;

    public Ball(Vector2 position, double size, Color color) {
        pos = position;
        this.size = size;
        this.color = color;



    }

    public Ball move (){
        acc.mlt(0.8, 0.8);
        vel.add(acc);
        vel.add(0, -GRAVITY);
        pos.add(vel);

        if(pos.y + size >= Main.height || pos.y - size <= 0) {
            pos.add(0, -vel.y);
            vel.mlt(0.95, -0.95);

//            acc.mlt(1,  -1 - GRAVITY);
        }

        if(pos.x - size <= 0 || pos.x + size >= Main.width){
            pos.add(-vel.x, 0);
            vel.mlt(-0.95, 1);

        }
        return this;
    }

    public void attract(double x, double y) {
        double xs = (x - pos.x) / 50;
        double ys = (y - pos.y) / 50;

        final double ndiv = Vector2.normalizationDivisor(xs, ys);
        double nxs = xs / ndiv,
               nys = ys / ndiv;

        if(Math.abs(xs) >= 4)
            xs = nxs * 4;
        if(Math.abs(ys) >= 4)
            ys = nys * 4;

        acc.set(xs, ys);
    }

    public Ball draw(){
        if(dead) return this;
        color.use();
        Shapes.circle(pos.x, pos.y, size, 40);


        return this;
    }

    public void die(){
        if(Math.abs(vel.x) < 1 && Math.abs(vel.y) < 1)
            dead = true;
    }




    public static class Color {
        byte r = 0, g = 0, b = 0, a = 127;
        public Color (int red, int green, int blue, int alpha){
            r = (byte) red; g = (byte) green; b = (byte) blue; a = (byte) alpha;
        }
        public Color (int red, int green, int blue){
            r = (byte) red; g = (byte) green; b = (byte) blue;
        }
        public Color (int brightness, int alpha){
            r = (byte) brightness; g = (byte) brightness; b = (byte) brightness; a = (byte) alpha;
        }
        public Color (int brightness){
            r = (byte) brightness; g = (byte) brightness; b = (byte) brightness;
        }
        public void use(){
            glColor4b(r, g, b, a);
        }
    }
}
