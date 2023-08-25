package com.railitus.test;

import com.railitus.engine.AbstractGame;
import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;
import com.railitus.game.GameManager;
import com.railitus.game.Mover;
import com.railitus.game.PObject;
import com.railitus.game.Vector;

import java.util.ArrayList;

public class RocketTest extends AbstractGame {
    private ArrayList<PObject> elementos = new ArrayList<>();

    public RocketTest(){
        elementos.add(new Mover(new Vector(100,100),0,"rocket",new Vector(0,0),50));
    }

    @Override
    public void update(GameContainer gc, float dt) {
        for(PObject o:elementos) {o.update(gc,new GameManager(),dt);}
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        for (PObject o:elementos
             ) {o.render(gc,renderer);
        }
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new RocketTest());
        gc.start();
    }
}
