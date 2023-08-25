package com.railitus.engine;


import com.railitus.engine.gfx.Font;
import com.railitus.engine.gfx.Image;
import com.railitus.engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;

public class Renderer {
    private final int UNCLEARABLE = 0xffff0000;

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
            if(pixels[i] != UNCLEARABLE && pixels[i] != 0xff00ff00)
                pixels[i] = 0;
            else if (pixels[i] == UNCLEARABLE)
                pixels[i]=UNCLEARABLE;
            else if (pixels[i] == 0xff00ff00)
                pixels[i]=0xff00ff00;
        }
    }

    public void setPixel(int x, int y, int value){
        if((x < 0 || x >= pW || y < 0 || y >= pH) || value == 0xffff00ff){
            return;
        }
        if(pixels[x+y*pW] == UNCLEARABLE ||pixels[x+y*pW] == 0xff00ff00 ){
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

    public void drawLine(int x1, int y1, int x2, int y2, int color) {
        if (x1 == x2) {
            if (y2 > y1) {
                for (int y = y1; y < y2; y++) {
                    setPixel(x1, y, color);
                }
            } else {
                for (int y = y2; y < y1; y++) {
                    setPixel(x1, y, color);
                }
            }
        } else {
            double slope = (double) (y2 - y1) / (x2 - x1);
            System.out.println("slope" + slope);
            double c = y2 - (slope * x2);
            int image;
            if (x1 < x2) {
                for (int x = x1; x < x2; x++) {
                    image = (int) (slope * x + c);
                    setPixel(x,image,color);
                }
            } else {
                for (int x = x2; x < x1; x++) {
                    image = (int) (slope * x + c);
                    setPixel(x,image,color);
                }
            }
        }
        /*double slope = (double)(y2-y1)/(x2-x1);
        System.out.println("slope"+ slope);
        double c;

        if(slope == 0){
            c = y2;
        }
        else{
            c = y2 - (slope * x2);
        }
        int y=0;
        for(int x = x1; x <= x2; x++){
            y = (int)(slope*x + c);
            setPixel(x,y,color);
        }*/

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
    public void drawCircle(int offX, int offY, int radius, int color){
       int xR;
        int yR;
        for (int x = -radius; x < radius; x++) {
            yR = (int) Math.sqrt(Math.pow(radius, 2) - Math.pow(x, 2));
            setPixel(x + offX, yR + offY, color);
            yR = (int) -Math.sqrt(Math.pow(radius, 2) - Math.pow(x, 2));
            setPixel(x + offX, yR + offY, color);
        }



        for (int y = -radius; y < radius; y++) {
            xR = (int) Math.sqrt(Math.pow(radius, 2) - Math.pow(y, 2));
            setPixel(xR + offX, y + offY, color);
            xR = (int) -Math.sqrt(Math.pow(radius, 2) - Math.pow(y, 2));
            setPixel(xR + offX, y + offY, color);
        }

    }

    public void drawFillCircle(int offX, int offY, int radius, int color){
        for(int x = 0; x < 2*radius; x++){
            for(int y = 0; y < 2*radius; y++){
                if(Math.pow(x-radius,2) + Math.pow(y-radius,2) < radius*radius){
                    setPixel(x+offX-radius,y+offY-radius,color);
                }
            }
        }
    }

    public void drawPoint(int x, int y,int color) {
        setPixel(x,y,color);
    }
}
