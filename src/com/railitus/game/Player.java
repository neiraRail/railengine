package com.railitus.game;

import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;

import java.awt.event.KeyEvent;

public class Player extends GameObject{
    private int speed = 80;

    public Player(int playernum, int posX, int posY){
        this.tag = "Player_"+playernum;
        this.posX = posX * GameManager.pixelSize;
        this.posY = posY * GameManager.pixelSize;
        this.width = GameManager.pixelSize;
        this.height = 4*GameManager.pixelSize;
    }
    @Override
    public void update(GameContainer gc,GameManager gm, float dt) {
        //Chocar con las paredes
        if(tag.equals("Player_1")) {
            if (posY <= 0) {
                if (gc.getInput().isKey(KeyEvent.VK_DOWN)) {
                    posY += dt * speed;
                }
            } else if (posY + height >= gc.getHeight()) {
                if (gc.getInput().isKey(KeyEvent.VK_UP)) {
                    posY -= dt * speed;
                }
            } else {
                if (gc.getInput().isKey(KeyEvent.VK_DOWN)) {
                    posY += dt * speed;
                }
                if (gc.getInput().isKey(KeyEvent.VK_UP)) {
                    posY -= dt * speed;
                }
            }
        }
        else{
            if (posY <= 0) {
                if (gc.getInput().isKey(KeyEvent.VK_S)) {
                    posY += dt * speed;
                }
            } else if (posY + height >= gc.getHeight()) {
                if (gc.getInput().isKey(KeyEvent.VK_W)) {
                    posY -= dt * speed;
                }
            } else {
                if (gc.getInput().isKey(KeyEvent.VK_S)) {
                    posY += dt * speed;
                }
                if (gc.getInput().isKey(KeyEvent.VK_W)) {
                    posY -= dt * speed;
                }
            }
        }
        //Fin chocar con las paredes
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        renderer.drawFillRect((int)posX,(int)posY,width,height,0xff008dff);
    }

    @Override
    public void setVel(int i, int i1) {

    }
}
