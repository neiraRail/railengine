package com.railitus.game;

import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;

public class Atractor extends PObject{
    public Atractor(Vector pos, int color, String tag) {
        super(pos, color, tag);
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {

    }

    public Vector attract(Mover m) {
        int G =1;

        //What’s the force’s direction?
        Vector force = Vector.sub(pos,m.pos);
        double distance = force.getLength();
        distance = Vector.constrain(distance,5,25);
        force.normalize();
        //What’s the force’s magnitude?
        double strength = (G * mass * m.mass) / (distance * distance);
        force.mul(strength);
        //Return the force so that it can be applied!
        return force;
    }



    @Override
    public void render(GameContainer gc, Renderer renderer) {
        renderer.drawFillCircle((int)pos.x,(int)pos.y,30,color);
        renderer.drawCircle((int)pos.x,(int)pos.y,30,0xff333333);
    }
}
