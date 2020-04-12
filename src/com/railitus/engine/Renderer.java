package com.railitus.engine;


import com.railitus.engine.gfx.Font;
import com.railitus.engine.gfx.Image;
import com.railitus.engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;

public class Renderer {

    private int pW, pH;
    private int[] pixels;
    private Font font = Font.STANDARD;

    public Renderer(GameContainer gc){
        pW = gc.getWidth();
        pH = gc.getHeight();
        pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear(){
        for(int i=0;i< pixels.length;i++){
            pixels[i] = 0;
        }
    }

    public void setPixel(int x, int y, int value){
        if((x < 0 || x >= pW || y < 0 || y >= pH) || value == 0xffff00ff){
            return;
        }
        pixels[x+y*pW] = value;
    }

    public void drawImage(Image image, int offX, int offY){

        //Codigo para no renderizar
        if(offX<-image.getWidth()) return;
        if(offY<-image.getHeight()) return;
        if(offX>=pW) return;
        if(offY>=pH) return;

        int newX=0,newY=0;
        int newWidth = image.getWidth();
        int newHeight = image.getHeight();

        //Codigo para no intentar renderizar
        if(offX < 0){ newX -= offX;}
        if(offY < 0){ newY -= offY;}
        if(newWidth + offX >= pW){ newWidth -= newWidth + offX - pW;}
        if(newHeight + offY >= pH){ newHeight -= newHeight + offY - pH; }

        for(int y = newY;y<newHeight;y++){
            for(int x = newX; x< newWidth;x++){
                setPixel(x+offX,y+offY, image.getPixels()[x+y*image.getWidth()]);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
        //Codigo para no renderizar
        if (offX < -image.getTileW()) return;
        if (offY < -image.getTileH()) return;
        if (offX >= pW) return;
        if (offY >= pH) return;

        int newX = 0, newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();

        //Codigo para no intentar renderizar
        if (offX < 0) {
            newX -= offX;
        }
        if (offY < 0) {
            newY -= offY;
        }
        if (newWidth + offX >= pW) {
            newWidth -= newWidth + offX - pW;
        }
        if (newHeight + offY >= pH) {
            newHeight -= newHeight + offY - pH;
        }

        for (int y = newY; y < newHeight; y++) {
            for (int x = newX; x < newWidth; x++) {
                setPixel(x + offX, y + offY, image.getPixels()[(x + tileX * image.getTileW()) + (y +tileY * image.getTileH() ) * image.getWidth()]);
            }
        }
    }

    public void drawText(String text, int offX, int offY, int color){
        ;
        text = text.toUpperCase();
        int offset = 0;
        for(int i = 0;i<text.length();i++){
            int unicode = text.codePointAt(i) - 32;
            for(int y = 0;y < font.getFontImage().getHeight();y++){
                for(int x = 0;x < font.getWidths()[unicode];x++){
                    if(font.getFontImage().getPixels()[(x+font.getOffSets()[unicode]) + y*font.getFontImage().getWidth()]==0xffffffff){
                        setPixel(x+offX+offset,y+offY,color);
                    }
                }
            }

            offset += font.getWidths()[unicode];
        }
    }

    public void drawRect(int offX, int offY, int width, int height, int color){
        for(int x = 0; x < width ;x++){
            setPixel(offX+x,offY,color);
            setPixel(offX+x,offY+height,color);
        }
        for(int y = 0; y < height ;y++){
            setPixel(offX,offY+y,color);
            setPixel(offX+width,offY+y,color);
        }
    }
    public void drawFillRect(int offX, int offY, int width, int height, int color){
        for(int x = 0; x < width ;x++){
            for(int y = 0; y < height ;y++){
                setPixel(x+offX,y+offY,color);
            }
        }
    }

}
