package com.railitus.game;

import com.railitus.engine.AbstractGame;
import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;
import com.railitus.engine.audio.SoundClip;
import com.railitus.engine.gfx.Image;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameManager extends AbstractGame {

    private ArrayList<GameObject> objects = new ArrayList<>();
    private Mover[] movers = new Mover[3];
    public static final int pixelSize = 16;
    public int modo = 1;



    public GameManager(){
        if(modo ==1) {
            for (int i = 0; i < movers.length; i++) {
                Vector pos = new Vector(50 * (i + 1), 200);
                Vector vel = Vector.zero();
                float mass = (i + 1);
                movers[i] = new Mover(pos, 0xff0033ff, "mover_" + i, vel, mass);
                movers[i].setRadius(20);
                movers[i].setTrackeable(true);
            }
        } else {


            for (int i = 0; i < movers.length; i++) {
                Vector pos = new Vector(50 * (i + 1), 200);
                Vector vel = Vector.zero();
                if (i == 0 || i == 2) {
                    vel = new Vector(2, 2);
                } else {
                    vel = new Vector(-2, -2);
                }


                movers[i] = new Mover(pos, 0xff5555bb, "mover_" + i, vel, 15);
                movers[i].setRadius(20);
                movers[i].setTrackeable(true);
                if(i == 0){
                    movers[i].setColor(0xff00ff00);
                }
            }


        }
    }

    @Override
    public void update(GameContainer gc, float dt) {
        if(modo==1) {
            for (int i = 0; i < movers.length; i++) {

                applyForces(movers[i]);
                movers[i].update(gc, this, dt);

            }
            for (Mover m : movers) {
                for (Mover n : movers) {
                    if (!m.equals(n)) {
                        if (m.testCollision(n))
                            m.resolveCollision(m, n);
                    }
                }
            }
        }else {



        for (int i = 0; i < movers.length; i++) {
            //For every Mover, check every Mover!
            for (int j = 0; j < movers.length; j++) {
                if(i!=j) {
                    Vector force = movers[j].attract(movers[i]);
                    movers[i].applyForce(force);
                }
            }
            movers[i].update(gc,this,dt);
            if(gc.getInput().isKey(KeyEvent.VK_SPACE)){
                movers[i].setRadius(0);
            }
            if(gc.getInput().isKey(KeyEvent.VK_ENTER)){
                movers[i].setRadius(20);
            }
        }

            for (Mover m : movers) {
                for (Mover n : movers) {
                    if (!m.equals(n)) {
                        if (m.testCollision(n))
                            m.resolveCollision(m, n);
                    }
                }
            }




        }
    }


    @Override
    public void render(GameContainer gc, Renderer renderer) {
        for (int i = 0; i < movers.length; i++) {
            movers[i].render(gc,renderer);
        }
    }

    private void applyForces(PObject object){
        Vector gravity = new Vector(0,0.2);
        //Vector friction = Vector.copy(object.velocity);
        //friction.mul(-1);
        //friction.normalize();
        //System.out.println("length = "+friction.getLength());
        //friction.mul(0.1);//mu
        object.applyForce(gravity);
        //object.applyForce(friction);


        //System.out.println(mover.isFloor()+" acc: "+mover.acceleration);
    }



    public GameObject getObject(String tag){
        GameObject object = null;
        for (GameObject g:objects){
            if(g.getTag().equals(tag)){
                object = g;
            }
        }
        return object;
    }



    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new GameManager());
        gc.start();
    }
}
