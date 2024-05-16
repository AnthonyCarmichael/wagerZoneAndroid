package com.example.wagerzone;

import java.util.HashMap;

public class Sport {
    private String _nom_sport;

    public Sport(HashMap<String, String> sport) {
        this._nom_sport = sport.get("sport");
    }

    public String get_nom_sport() {
        return _nom_sport;
    }

    public void set_nom_sport(String _nom_sport) {
        this._nom_sport = _nom_sport;
    }
}
