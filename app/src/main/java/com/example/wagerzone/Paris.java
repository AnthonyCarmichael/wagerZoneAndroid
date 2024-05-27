package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 *  @author Jean-Loup Dandurand-Pominville
 *  @version 1.0
 * C'est une classe permet de gérer le transfert et le traitement des
 * données des paris.
 */
public class Paris {
    private float _montant;
    private String _date_heure;
    private int _receveur;
    private int _id_partie;
    private int _id_paris;

    public Paris(JSONObject paris) throws JSONException {
        this._montant =Float.parseFloat(paris.getString("montant"));
        this._date_heure = paris.getString("date_heure");
        this._receveur = Integer.parseInt(paris.getString("receveur"));
        this._id_partie = Integer.parseInt(paris.getString("id_partie"));
        this._id_paris = Integer.parseInt(paris.getString("id_paris"));
    }

    public Paris(){
        this._montant = 0;
        this._date_heure = "";
        this._receveur = 0;
        this._id_partie = 0;
        this._id_paris = 0;
    }

    public int get_id_partie() {
        return _id_partie;
    }

    public void set_id_partie(int _id_partie) {
        this._id_partie = _id_partie;
    }

    public int get_id_paris() {
        return _id_paris;
    }

    public void set_id_paris(int _id_paris) {
        this._id_paris = _id_paris;
    }

    public float get_montant() {
        return _montant;
    }
    public String get_string_montant() {
        return Float.toString(_montant);
    }

    public void set_montant(float _montant) {
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
}
