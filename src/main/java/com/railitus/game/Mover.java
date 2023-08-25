package com.railitus.game;

import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;

import java.awt.event.KeyEvent;

public class Mover extends PObject{
    public boolean isFloor() {
        return floor;
    }

    private boolean floor = false;

    public Mover(Vector pos, int color, String tag, Vector velocity, float mass){
        super(pos,color,tag,velocity,mass);
    }

    @Override
    public void update(GameContainer gc,GameManager gm, float dt) {
        //Movimiento natural
        velocity.add(acceleration);
        pos.add(velocity);
        //System.out.println(pos);
        //Fin Movimiento natural



        //Rebote con paredes
        if (pos.x >= gc.getWidth()) {
            velocity.x = velocity.x * -1;
            pos.x = gc.getWidth();
        }
        else if(pos.x <= 0 ){
            velocity.x = velocity.x * -1;
            pos.x = 0;
        }
        if (pos.y+20 >= gc.getHeight() ) {
            //velocity.y = 0;
            velocity.y *= -1;
            pos.y = gc.getHeight()-20;
            //floor = true;

        }
        else if(pos.y <= 0 ){
            velocity.y = velocity.y * -1;
            pos.y = 0;
        }
        //Fin rebote con paredes

        //Movimiento por usuario
        if(gc.getInput().isKeyDown(KeyEvent.VK_W)){
            velocity.y =-4;
        }
        if(gc.getInput().isKey(KeyEvent.VK_D)){
            velocity.x = 4;
        }

        if(gc.getInput().isKey(KeyEvent.VK_A)){
            velocity.x =-4;
        }

        //Fin movimiento por ususairo

        acceleration.mul(0);
    }

    public Vector attract(Mover m) {
        float G = 5f;

        //What’s the force’s direction?
        Vector force = Vector.sub(pos,m.pos);
        double distance = force.getLength();
        distance = Vector.constrain(distance,20,40);
        force.normalize();
        //What’s the force’s magnitude?
        double strength = (G * mass * m.mass) / (distance * distance);
        force.mul(strength);
        //Return the force so that it can be applied!
        return force;
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        renderer.drawFillCircle((int)pos.x,(int)pos.y,radius,color);
        renderer.drawCircle((int)pos.x,(int)pos.y,radius,-1);
        if(trackeable)
            renderer.drawPoint((int)pos.x,(int)pos.y,trackColor);
    }

}
