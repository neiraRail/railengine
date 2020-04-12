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
    public static final int pixelSize = 16;
    private int state=0;//0=Jugando, 1=Terminado
    private String winner=null;


    public GameManager(){
        objects.add(new Ball(4,4));

        objects.add(new Player(1,1,1));
        objects.add(new Player(2,18,1));

    }
    @Override
    public void update(GameContainer gc, float dt) {
        for(int i=0; i< objects.size(); i++){
            objects.get(i).update(gc,this,dt);
            if(objects.get(i).isDead){
                objects.remove(i);
                i--;
            }
        }
        if(state == 1){
            if(gc.getInput().isKey(KeyEvent.VK_ENTER)){
                state=0;
                reset();
            }
        }
    }

    private void reset() {
        getObject("ball").setPosX(40);
        getObject("ball").setPosY(40);
        getObject("ball").setVel(40,40);
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        if(state==0) {
            for (GameObject obj : objects) {
                obj.render(gc, renderer);
            }
        }else{
            renderer.clear();
            renderer.drawText(winner+" ha ganado",gc.getWidth()/2,gc.getHeight()/2,-1);
        }
    }

    @Override
    public void finishGame(String player) {
        state = 1;
        winner=player;

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
