package com.raulp.weather.models.tafDecoded;

import java.util.ArrayList;

public class TafDecodedData {
    public ArrayList<Forecast> forecast;
    public String icao;
    public String raw_text;
    public Station station;
    public Timestamp timestamp;
}

class Change {
    public Indicator indicator;
    public String probability;
}

class Cloud {
    public int base_feet_agl;
    public int base_meters_agl;
    public String code;
    public int feet;
    public int meters;
    public String text;
}


class Forecast {
    public ArrayList<Cloud> clouds;
    public Timestamp timestamp;
    public Visibility visibility;
    public Wind wind;
    public Change change;
}

class Geometry {
    public ArrayList<Double> coordinates;
    public String type;
}

class Indicator {
    public String code;
    public String desc;
    public String text;
}

class Station {
    public Geometry geometry;
    public String location;
    public String name;
    public String type;
}

class Timestamp {
    public String from;
    public String to;
    public String bulletin;
    public String issued;
}

class Visibility {
    public String meters;
    public double meters_float;
    public String miles;
    public double miles_float;
}

class Wind {
    public int degrees;
    public int speed_kph;
    public int speed_kts;
    public int speed_mph;
    public int speed_mps;
}

