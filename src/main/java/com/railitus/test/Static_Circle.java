package com.railitus.test;

import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;
import com.railitus.game.GameManager;
import com.railitus.game.GameObject;
import com.railitus.game.PObject;
import com.railitus.game.Vector;

public class Static_Circle extends PObject {

    public Static_Circle(Vector pos){
        this.pos = pos;
        this.color = -1;
    }

    public void update(GameContainer gc, TestGame tg, float dt) {
        if(testCollision(tg.mover1)){
            this.color=0xffff1100;
        }
        else{
            this.color = 0xffffffff;
        }
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {

    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        renderer.drawCircle((int)pos.x, (int) pos.y,radius,color);
    }
}
