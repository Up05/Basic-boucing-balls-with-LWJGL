package me.Ult1;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;
import java.util.ArrayDeque;
import java.util.Collection;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;


public class Main {

    private long window;

    public static int width = 1280, height = 720;
    public static double aspect = width / (double) height;

    public void run() {
//        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(width, height, "bbawlwjgl&ogl OR bouncing ball animation with light weight java game library and open graphics library", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_J && action == GLFW_RELEASE) {
//                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
                glClearColor(0.0f, (float) Math.random(), 0.0f, 0.0f);
            }
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.1f, 0.1f, 0.1f, 0.0f);

        aspect = (double) width / (double) height;

        glOrtho(0.0f, width, height, 0.0f, 0.0f, 1.0f);

        final int initSize = 8;
        Collection<Ball> balls = new ArrayDeque<>(initSize);

        int frameCount = 0;
        while (!glfwWindowShouldClose(window)) {
            {
                IntBuffer w = BufferUtils.createIntBuffer(1);
                IntBuffer h = BufferUtils.createIntBuffer(1);
                glfwGetWindowSize(window, w, h);
                width = w.get(0);
                height = h.get(0);
            }


            glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
                if(key == GLFW_KEY_SPACE && action == GLFW_RELEASE) {
                    balls.add(new Ball(new Vector2(width / 2f, height / 2f + (Math.random() * 40 - 20)), 20, new Ball.Color(30, 40, 80)));
                    //noinspection SuspiciousMethodCalls
                    balls.remove(balls.toArray()[0]);
                }
            });

            glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
                if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                    DoubleBuffer xpos = BufferUtils.createDoubleBuffer(1);
                    DoubleBuffer ypos = BufferUtils.createDoubleBuffer(1);
                    glfwGetCursorPos(window, xpos, ypos);
                    for (Ball ball : balls) {
                        ball.attract(xpos.get(0), ypos.get(0));
                    }
                }
            });

            if(frameCount < initSize * 32 && frameCount % 32 == 0)
                balls.add(new Ball(new Vector2(width / 2f, height / 2f + (Math.random() * 40 - 20)), 20, new Ball.Color(30, 40, 80)));

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            for(Ball ball : balls)
                ball.move().draw().die();

            frameCount ++;
            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }


}
