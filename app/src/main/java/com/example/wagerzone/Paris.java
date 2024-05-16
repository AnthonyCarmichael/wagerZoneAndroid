package com.example.wagerzone;

public class Paris {
    private int _id_paris;
    private double _montant;
    private String _date_heure;
    private String _receveur;
    private String _utilisateur;
    private String _partie;

    public int get_id_paris() {
        return _id_paris;
    }

    public void set_id_paris(int _id_paris) {
        this._id_paris = _id_paris;
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

    public String get_receveur() {
        return _receveur;
    }

    public void set_receveur(String _receveur) {
        this._receveur = _receveur;
    }

    public String get_utilisateur() {
        return _utilisateur;
    }

    public void set_utilisateur(String _utilisateur) {
        this._utilisateur = _utilisateur;
    }

    public String get_partie() {
        return _partie;
    }

    public void set_partie(String _partie) {
        this._partie = _partie;
    }
}
