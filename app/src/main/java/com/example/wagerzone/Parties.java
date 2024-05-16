package com.example.wagerzone;

public class Parties {
    private int _id_partie;
    private String _date_heure;
    private int _prolongation;
    private String _statut;

    public int get_id_partie() {
        return _id_partie;
    }

    public void set_id_partie(int _id_partie) {
        this._id_partie = _id_partie;
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

