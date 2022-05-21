package com.raulp.weather.models.metarDecoded;

import java.util.ArrayList;

public class MetarDecodedData{
    public Barometer barometer;
    public Ceiling ceiling;
    public ArrayList<Cloud> clouds;
    public ArrayList<Condition> conditions;
    public Dewpoint dewpoint;
    public Elevation elevation;
    public String flight_category;
    public Humidity humidity;
    public String icao;
    public String observed;
    public String raw_text;
    public Station station;
    public Temperature temperature;
    public Visibility visibility;
    public Wind wind;
}

class Barometer{
    public double hg;
    public double hpa;
    public double kpa;
    public double mb;
}

class Ceiling{
    public int base_feet_agl;
    public int base_meters_agl;
    public String code;
    public int feet;
    public int meters;
    public String text;
}

class Cloud{
    public int base_feet_agl;
    public int base_meters_agl;
    public String code;
    public int feet;
    public int meters;
    public String text;
}

class Condition{
    public String code;
    public String prefix;
    public String text;
}

class Dewpoint{
    public int celsius;
    public int fahrenheit;
}

class Elevation{
    public int feet;
    public int meters;
}

class Geometry{
    public ArrayList<Double> coordinates;
    public String type;
}

class Humidity{
    public int percent;
}



class Station{
    public Geometry geometry;
    public String location;
    public String name;
    public String type;
}

class Temperature{
    public int celsius;
    public int fahrenheit;
}

 class Visibility{
    public String meters;
    public int meters_float;
    public String miles;
    public double miles_float;
}

 class Wind{
    public int degrees;
    public int speed_kph;
    public int speed_kts;
    public int speed_mph;
    public int speed_mps;
}