package com.example.wagerzone;

import java.util.HashMap;

public class Parties {
    private String _date_heure;
    private int _prolongation;
    private String _statut;

    public Parties(HashMap<String, String> partie) {
        this._date_heure = partie.get("date_heure");
        this._prolongation = Integer.parseInt(partie.get("prolongation"));
        this._statut = partie.get("statut");
    }

    public String get_date_heure() {
        return _date_heure;
    }

    public void set_date_heure(String _date_heure) {
        this._date_heure = _date_heure;
    }

    public int get_prolongation() {
        return _prolongation;
    }

    public void set_prolongation(int _prolongation) {
        this._prolongation = _prolongation;
    }

    public String get_statut() {
        return _statut;
    }

    public void set_statut(String _statut) {
        this._statut = _statut;
    }
}

