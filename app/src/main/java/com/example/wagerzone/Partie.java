package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Partie {
    private int _id_partie;
    private String _date_heure;
    private String _prolongation;
    private String _statut;

    public Partie(JSONObject partie) throws JSONException {
        this._id_partie = partie.getInt("id_partie");
        this._date_heure = partie.getString("date_heure");
        this._prolongation = partie.getString("prolongation");
        this._statut = partie.getString("id_statut");
    }

    public Partie(){
        this._id_partie = 0;
        this._date_heure = "";
        this._prolongation = "";
        this._statut = "";
    }

    public int get_id_partie() {
        return _id_partie;
    }

    public void set_id_partie(int id_partie) {
        this._id_partie = id_partie;
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
