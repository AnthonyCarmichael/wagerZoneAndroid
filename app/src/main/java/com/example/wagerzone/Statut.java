package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Statut {
    private String _nom_statut;

    public Statut(JSONObject statut) throws JSONException {
        this._nom_statut = statut.getString("nom_statut");
    }

    public void set_nom_statut(String _nom_statut) {
        this._nom_statut = _nom_statut;
    }

    public String get_nom_statut() {
        return _nom_statut;
    }
}
