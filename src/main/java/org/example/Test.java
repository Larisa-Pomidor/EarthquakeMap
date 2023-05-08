package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class Test extends PApplet
{
    private String bgURL = "src/main/resources/cat.jpg";
    private PImage bgImage;

    public void setup() {
        size(800, 450);
        bgImage = loadImage(bgURL,"jpg");
    }

    public void draw() {
        bgImage.resize(0, height);
        int[] color = changeColor(second());
        fill(color[0], color[1], color[2]);
        image(bgImage, 0, 0);

        ellipse(width - width / 4, height / 5, height / 5, height / 5);
    }

    public int[] changeColor(float s) {
        float disperse = Math.abs(30 - s);
        float ratio = disperse / 30;
        return new int[] {(int) (255*ratio), (int) (255*ratio), 0};
    }

    public static void main (String... args) {
        Test test = new Test();
        PApplet.runSketch(new String[]{"ProcessingTest"}, test);
    }
}

