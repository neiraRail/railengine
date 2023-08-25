package com.railitus.game;

import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;

public abstract class GameObject {

    protected String tag;
    protected Vector pos;
    protected int width, height;
    protected boolean isDead = false;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Vector getPos() {
        return pos;
    }

    public void setPos(Vector position) { this.pos = position;}


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

    public Vector getCenter() { return Vector.getMid(pos);}

    public abstract void update(GameContainer gc,GameManager gm, float dt);
    public abstract void render(GameContainer gc,Renderer renderer);
}
