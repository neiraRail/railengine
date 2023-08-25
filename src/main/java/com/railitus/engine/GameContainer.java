package com.railitus.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameContainer implements Runnable {

    public Window getWindow() {
        return window;
    }

    private Input input;
    private Window window;
    private Thread thread;
    private Renderer renderer;
    private AbstractGame game;

    private boolean running = false;
    private final double UPDATE_CAP = 1.0/60.0;

    private int width = 640,height = 480;
    private float scale = 1.2f;
    private String tittle = "RailitusEngine";

    public GameContainer(AbstractGame game){
        this.game = game;
    }

    public void start(){
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);
        thread = new Thread(this);
        thread.run();
    }
    public void stop(){

    }
    public void run(){

        running = true;

        boolean render = false;
        double firstTime = 0;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime = 0;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames=0;
        int fps=0;

        while(running){
            render = false;

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime-lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while (unprocessedTime>=UPDATE_CAP){
                unprocessedTime-= UPDATE_CAP;
                render = true;

                game.update(this,(float)UPDATE_CAP) ;
                input.update();

                if(frameTime>=1.0){

                    frameTime=0;
                    fps = frames;
                    frames=0;

                }
            }

            if(render){
                renderer.clear();
                game.render(this,renderer);
                renderer.drawText("FPS = "+fps,0,0,0xff00ffff);
                window.update();
                frames++;
            }
            else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        dispose();
    }
    private void dispose(){

    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public Input getInput() {
        return input;
    }
}
