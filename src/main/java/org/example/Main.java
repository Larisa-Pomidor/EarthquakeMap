package org.example;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class Main extends PApplet {

    UnfoldingMap map;

    public void setup() {
        size(800, 600);

        map = new UnfoldingMap(this, new Microsoft.RoadProvider());
        map.zoomAndPanTo(14, new Location(32.881, -117.238)); // UCSD

        MapUtils.createDefaultEventDispatcher(this, map);
    }

    public void draw() {
        background(0);
        map.draw();
    }

    public static void main(String... args) {
        Main main = new Main();
        PApplet.runSketch(new String[]{"ProcessingTest"}, main);
    }
}