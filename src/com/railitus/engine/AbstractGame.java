package com.railitus.engine;

public abstract class AbstractGame {
    public abstract void update(GameContainer gc, float dt);
    public abstract void render(GameContainer gc, Renderer renderer);
    public abstract void finishGame(String player);
}
