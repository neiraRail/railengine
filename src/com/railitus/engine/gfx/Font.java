package com.railitus.engine.gfx;

public class Font {
    public static final Font STANDARD = new Font("/fonts/standard.png");

    private Image fontImage;
    private int[] offSets;
    private int[] widths;

    public Image getFontImage() {
        return fontImage;
    }

    public int[] getOffSets() {
        return offSets;
    }

    public int[] getWidths() {
        return widths;
    }

    public Font(String path){
        fontImage = new Image(path);

        offSets = new int[59];
        widths = new int[59];

        int unicode = 0;

        for(int i = 0; i < fontImage.getWidth(); i++){
            if(fontImage.getPixels()[i]==0xff0000ff){
                offSets[unicode]=i;
            }

            if(fontImage.getPixels()[i]==0xffffff00){
                widths[unicode]=i-offSets[unicode];
                unicode++;
            }
        }

    }

}
