package com.railitus.game;

import com.railitus.engine.GameContainer;
import com.railitus.engine.Renderer;

public class Ball extends GameObject{
    private int speedX=-60;
    private int speedY=60;

    public Ball(int posX, int posY){
        this.tag = "ball";
        this.posX = posX * GameManager.pixelSize;
        this.posY = posY * GameManager.pixelSize;
        this.width = GameManager.pixelSize;
        this.height = GameManager.pixelSize;
    }

    @Override
    public void update(GameContainer gc,GameManager gm, float dt) {
        //Movimiento natural
        posX += dt * speedX;
        posY -= dt * speedY;
        //Fin movimiento natural

        //Rebote con paletas
        GameObject paleta1 = gm.getObject("Player_1");
        GameObject paleta2 = gm.getObject("Player_2");

        if(testColision(paleta1))
            resolveColission(paleta1);
        if(testColision(paleta2))
            resolveColission(paleta2);
        //Fin rebote con paletas

        //Rebote con las paredes
        if(posY <= 0 ){
            speedY=-speedY;
        }
        if(posY+height >= gc.getHeight()){
            speedY=-speedY;
        }
        if(posX <= 0 ){
            speedX=0;
            gm.finishGame("Player 1");
        }
        if( posX+width >= gc.getWidth()){
            speedX=0;
            gm.finishGame("Player 2");
        }
        //Fin rebote con las paredes
    }

    @Override
    public void render(GameContainer gc, Renderer renderer) {
        renderer.drawFillRect((int)posX,(int)posY,width,height,0xff000dff);

    }
    public void setVel(int vx, int vy){
        this.speedY=vy;
        this.speedX=vx;
    }

    private void resolveColission(GameObject paleta1) {

        //\/Valores para los bordes de la paleta
        //float paleta_Abajo = paleta1.posY+paleta1.height;
        //float paleta_Arriba = paleta1.posY;
        float paleta_Derecha = paleta1.posX + paleta1.width;
        float paleta_Izquierda = paleta1.posX;

        //\/ Distancia entre puntos centro
        float rectangleFactor = (float) ((paleta1.height + (height / 2)) / (paleta1.width + width / 2));

        float colisionDistX = rectangleFactor * (this.getCenterX() - paleta1.getCenterX());
        float colisionDistY = 1.2f * (this.getCenterY() - paleta1.getCenterY());

        //\/ vector y mayor que vector x? vector es mas vertical
        if (colisionDistY * colisionDistY > colisionDistX * colisionDistX) {

            //\/ El vector apunta hacia abajo?
            if (colisionDistY > 0) {
                //speedY=-speedY;

            }
            //\/ El vector apunta hacia arriba?
            else {
                //speedY=-speedY;

            }
        }
        //\/ vector x mayor que vector y, el vector es mas horizontal
        else {

            //\/ El vector apunta hacia la derecha?
            if (colisionDistX > 0) {
                speedX = -speedX;

            }
            //\/El vector apunta hacia  la izquierda
            else {
                speedX = -speedX;

            }
        }

        //Rebote con paletas
    }



    private boolean testColision(GameObject paleta1) {
        //\/Valores para los bordes de la pelota
        //float bordeSup=posY;
        //float bordeInf=posY+height;
        float bordeDer=posX+width;
        float bordeIzq=posX;

        //\/Valores para los bordes de la paleta
        //float paleta_Abajo = paleta1.posY+paleta1.height;
        //float paleta_Arriba = paleta1.posY;
        float paleta_Derecha = paleta1.posX+paleta1.width;
        float paleta_Izquierda = paleta1.posX;
        if(/*bordeSup > paleta_Abajo||bordeInf < paleta_Arriba||*/bordeDer < paleta_Izquierda||bordeIzq > paleta_Derecha){
            return false;
        }
        else{
            return true;
        }
    }
}
