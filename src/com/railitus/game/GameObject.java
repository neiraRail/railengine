package com.railitus.game;

import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;

public abstract class GameObject {

    protected String tag;
    protected float posX;
    protected float posY;
    protected int width, height;
    protected boolean isDead = false;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float positionX) {
        this.posX = positionX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posicionY) {
        this.posY = posicionY;
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

    public float getCenterX(){
        return posX+(width)/2f;
    }
    public float getCenterY(){
        return posY+(height)/2f;
    }

    public abstract void update(GameContainer gc,GameManager gm, float dt);
    public abstract void render(GameContainer gc,Renderer renderer);

    public abstract void setVel(int i, int i1);
}
