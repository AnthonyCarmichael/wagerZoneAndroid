package com.example.wagerzone;

import java.util.HashMap;

public class Paris {
    private double _montant;
    private String _date_heure;
    private int _receveur;
    private String _utilisateur;
    private int _id_partie;

    public Paris(HashMap<String, String> paris) {
        this._montant =Double.parseDouble(paris.get("montant"));
        this._date_heure = paris.get("date_heure");
        this._receveur = Integer.parseInt(paris.get("receveur"));
        this._utilisateur = paris.get("utilisateur");
        this._id_partie = Integer.parseInt(paris.get("id_partie"));
    }


    public double get_montant() {
        return _montant;
    }

    public void set_montant(double _montant) {
        this._montant = _montant;
    }

    public String get_date_heure() {
        return _date_heure;
    }

    public void set_date_heure(String _date_heure) {
        this._date_heure = _date_heure;
    }

    public int get_receveur() {
        return _receveur;
    }

    public void set_receveur(int _receveur) {
        this._receveur = _receveur;
    }

    public String get_utilisateur() {
        return _utilisateur;
    }

    public void set_utilisateur(String _utilisateur) {
        this._utilisateur = _utilisateur;
    }

    public int get_partie() {
        return _id_partie;
    }

    public void set_partie(int _partie) {
        this._id_partie = _partie;
    }
}
