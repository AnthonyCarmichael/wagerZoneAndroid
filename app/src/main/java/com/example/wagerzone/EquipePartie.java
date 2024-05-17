package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

public class EquipePartie {
    private String _equipe;
    private String _partie;
    private int _but_marque;
    private int _cote;
    private int _receveur;

    public EquipePartie(JSONObject equipePartie) throws JSONException {
        this._equipe = equipePartie.getString("id_equipe");
        this._partie = equipePartie.getString("id_partie");
        this._but_marque = Integer.parseInt(equipePartie.getString("but_marque"));
        this._cote = Integer.parseInt(equipePartie.getString("cote"));
        this._receveur = Integer.parseInt(equipePartie.getString("receveur"));
    }

    public String get_equipe() {
        return _equipe;
    }

    public void set_equipe(String _equipe) {
        this._equipe = _equipe;
    }

    public String get_partie() {
        return _partie;
    }

    public void set_partie(String _partie) {
        this._partie = _partie;
    }

    public int get_but_marque() {
        return _but_marque;
    }

    public void set_but_marque(int _but_marque) {
        this._but_marque = _but_marque;
    }

    public int get_cote() {
        return _cote;
    }

    public void set_cote(int _cote) {
        this._cote = _cote;
    }

    public int get_receveur() {
        return _receveur;
    }

    public void set_receveur(int _receveur) {
        this._receveur = _receveur;
    }
}
