package com.railitus.test;

import com.railitus.engine.AbstractGame;
import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;
import com.railitus.game.GameManager;
import com.railitus.game.Mover;
import com.railitus.game.PObject;
import com.railitus.game.Vector;

public class TestGame extends AbstractGame {

    public Mover mover1;
    public Mover mover2;
    private Static_Circle circle;
    private int t=0;
    private boolean r=true;

    private TestGame(){
        Vector pos = new Vector(280,440);
        Vector pos2 = new Vector(300,220);
        Vector vel = new Vector(0,-2);
        mover1 = new Mover(pos, 0xff0033ff, "mover1", vel, 1);
        mover1.setRadius(20);
        mover2 = new Mover(pos2, 0xff0033ff, "mover2", Vector.zero(), 2);
        mover2.setRadius(30);
        //circle = new Static_Circle(new Vector(320,240));
        //circle.setRadius(100);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        //Vector mousePos = new Vector(gc.getInput().getMouseX(),gc.getInput().getMouseY());
        //mover1.setPos(mousePos);
        mover1.update(gc,new GameManager(),dt);
        mover2.update(gc,new GameManager(),dt);


        if(mover1.testCollision(mover2)&&r){
            mover1.resolveCollision(mover1,mover2);
            r=true;
        }
        //circle.update(gc,this,dt);
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        mover1.render(gc,renderer);
        mover2.render(gc,renderer);
        //circle.render(gc,renderer);

        //renderer.drawLine((int)mover1.getPos().x,(int)mover1.getPos().y,(int)mover2.getPos().x,(int)mover2.getPos().y,-1);
        //renderer.drawLine(200,100,50,200,0xffff0000);
        double totalM = mover1.getMass()*mover1.getVelocity().getLength()+mover2.getMass()*mover2.getVelocity().getLength();
        double energia = mover1.getMass()*mover1.getVelocity().getLength()*mover1.getVelocity().getLength()+mover2.getMass()*mover2.getVelocity().getLength()*mover2.getVelocity().getLength();
        String text = "total momentum = "+totalM;
        String text2 = "total energia = "+energia;
        renderer.drawText(text,100,100,-1);
        renderer.drawText(text2,100,130,-1);
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new TestGame());
        gc.start();
    }
}
