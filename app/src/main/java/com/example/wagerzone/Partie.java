package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Partie {
    private String _date_heure;
    private String _prolongation;
    private String _statut;

    public Partie(JSONObject partie) throws JSONException {
        this._date_heure = partie.getString("date_heure");
        this._prolongation = partie.getString("prolongation");
        this._statut = partie.getString("id_statut");
    }

    public String get_date_heure() {
        return _date_heure;
    }

    public void set_date_heure(String _date_heure) {
        this._date_heure = _date_heure;
    }

    public String get_prolongation() {
        return _prolongation;
    }

    public void set_prolongation(String _prolongation) {
        this._prolongation = _prolongation;
    }

    public String get_statut() {
        return _statut;
    }

    public void set_statut(String _statut) {
        this._statut = _statut;
    }
}
