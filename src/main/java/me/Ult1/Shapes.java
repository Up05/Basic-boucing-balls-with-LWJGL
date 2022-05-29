package me.Ult1;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;

public class Shapes {
    public static void rect(float x, float y, float width, float height){
        glBegin(GL_QUAD_STRIP);
            glVertex2f(x, y);
            glVertex2f(x + width, y);
            glVertex2f(x, y + height);
            glVertex2f(x + width, y + height);
        glEnd();
    }

    public static void rect(double x, double y, double width, double height){
        glBegin(GL_QUAD_STRIP);
            glVertex2d(x, y);
            glVertex2d(x + width, y);
            glVertex2d(x, y + height);
            glVertex2d(x + width, y + height);
        glEnd();
    }

//    public static void circle(double x, double y, double r, double res){
//        final double TWO_PI = Math.PI * 2;
//
//        glPushMatrix();
//        glTranslated(x, y, 0);
//        glScaled(r, r, 1);
//
//        glBegin(GL_TRIANGLE_FAN);
//        glVertex2d(0, 0);
//            for(int i = 0; i <= res + 2; i++){
//                double angle = Math.toDegrees(TWO_PI * i / res * 2f);
//                System.out.println(String.format("angle(rad): %.2f", TWO_PI * i / 4f));
//                glVertex2d(Math.cos(angle), Math.sin(angle));
//            }
//
//        glEnd();
//
//        glPopMatrix();
//
//    }

    public static void circle(double x, double y, double r, int res) {
        final int vertN = res + 2;
        final double TWO_PI = Math.PI * 2d;

        ArrayList<Vector2> verts = new ArrayList<>(vertN);
        verts.add(new Vector2(x, y));

        for (int i = 1; i < vertN; i ++)
            verts.add(new Vector2 (
                    x + ( r * Math.cos( i * TWO_PI / res ) ),
                    y + ( r * Math.sin( i * TWO_PI / res ) )
            ));

        glBegin(GL_TRIANGLE_FAN);
        for(Vector2 vector2 : verts)
            glVertex2d(vector2.x, vector2.y);
        glEnd();
    }

}



















