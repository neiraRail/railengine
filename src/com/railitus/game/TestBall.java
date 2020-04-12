package com.railitus.game;

import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;

public class TestBall extends Ball {
    GameManager gm;
    public TestBall(int posX, int posY, GameManager gm) {
        super(posX, posY);
        this.gm=gm;

    }

    public void update(GameContainer gc, GameManager gm, float dt){
        GameObject paleta1 = gm.getObject("Player_1");

        posX = gc.getInput().getMouseX()-width/2f;
        posY = gc.getInput().getMouseY()-height/2f;

        boolean isColision = testColision(paleta1);
        if(isColision){
            //\/Valores para los bordes de la paleta
            float paleta_Abajo = paleta1.posY+paleta1.height;
            float paleta_Arriba = paleta1.posY;
            float paleta_Derecha = paleta1.posX+paleta1.width;
            float paleta_Izquierda = paleta1.posX;

            //\/ Distancia entre puntos centro
            float rectangleFactor = (float)((paleta1.height+(height/2))/(paleta1.width+ width/2));
            float colisionDistX = rectangleFactor*(this.getCenterX() - paleta1.getCenterX());
            float colisionDistY = 1.2f*(this.getCenterY() - paleta1.getCenterY());

            //\/ vector y mayor que vector x? vector es mas vertical
            if(colisionDistY*colisionDistY > colisionDistX*colisionDistX){

                //\/ El vector apunta hacia abajo?
                if(colisionDistY>0){
                   posY = paleta_Abajo;

                }
                //\/ El vector apunta hacia arriba?
                else{
                    posY = paleta_Arriba-height;

                }
            }
            //\/ vector x mayor que vector y, el vector es mas horizontal
            else{

                //\/ El vector apunta hacia la derecha?
                if(colisionDistX>0){
                    posX=paleta_Derecha;

                }
                //\/El vector apunta hacia  la izquierda
                else{
                    posX = paleta_Izquierda-width;

                }
            }
        }
        //Rebote con las paredes
        if(posY <= 0 ){
            posY = 0;
        }
        if(posY+height >= gc.getHeight()){
            posY=gc.getHeight()-height;
        }
        if(posX <= 0 ){
            posX=0;
        }
        if( posX+width >= gc.getWidth()){
            posX=gc.getWidth()-width;
        }
        //Fin rebote con las paredes


    }

    private boolean testColision(GameObject paleta1) {
        //\/Valores para los bordes de la pelota
        float bordeSup=posY;
        float bordeInf=posY+height;
        float bordeDer=posX+width;
        float bordeIzq=posX;

        //\/Valores para los bordes de la paleta
        float paleta_Abajo = paleta1.posY+paleta1.height;
        float paleta_Arriba = paleta1.posY;
        float paleta_Derecha = paleta1.posX+paleta1.width;
        float paleta_Izquierda = paleta1.posX;
        if(bordeSup > paleta_Abajo||bordeInf < paleta_Arriba||bordeDer < paleta_Izquierda||bordeIzq > paleta_Derecha){
            return false;
        }
        else{
            return true;
        }
    }

}
