package org.example;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Microsoft;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LifeExpectancy extends PApplet {
    UnfoldingMap map;
    Map<String, Float> lifeExpByCountry;
    List<Feature> countries;
    List<Marker> countryMarkers;

    public void setup() {
        size(800, 600);
        map = new UnfoldingMap(this, 50, 50, 700, 500, new Microsoft.RoadProvider());
        MapUtils.createDefaultEventDispatcher(this, map);
        String lifeExpPath = "src/main/resources/LifeExpectancyWorldBankModule.csv";
        String countriesPath = "src/main/resources/countries.geo.json";
        lifeExpByCountry = loadLifeExpectancyFromCSV(lifeExpPath);
        countries = GeoJSONReader.loadData(this, countriesPath);
        countryMarkers = MapUtils.createSimpleMarkers(countries);
        map.addMarkers(countryMarkers);
        shadeCountries();
    }

    private void shadeCountries() {
        for (Marker marker:countryMarkers) {
            String countryId = marker.getId();

            if (lifeExpByCountry.containsKey(countryId)) {
                float lifeExp = lifeExpByCountry.get(countryId);
                int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
                marker.setColor(color(colorLevel, 0, 255-colorLevel));
            }
            else marker.setColor(color(100, 100, 100));
        }
    }

    private Map<String, Float> loadLifeExpectancyFromCSV(String path) {
        Map<String, Float> lifeExpByCountry = new HashMap<>();
        String[] rows = loadStrings(path);
        boolean head = true;
        for (String row : rows) {
            String[] cols = row.split(",");
            if (cols.length > 5 && !head && !cols[cols.length - 1].equals("..")) {
                lifeExpByCountry.put(cols[cols.length - 2], Float.valueOf(cols[cols.length - 1]));
            }
            head = false;
        }

        return lifeExpByCountry;
    }

    public void draw() {
        map.draw();
    }

    public static void main(String... args) {
        LifeExpectancy le = new LifeExpectancy();
        PApplet.runSketch(new String[]{"ProcessingTest"}, le);
    }
}
