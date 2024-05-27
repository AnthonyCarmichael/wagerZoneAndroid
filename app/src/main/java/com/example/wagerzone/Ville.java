package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * C'est une classe permet de gérer le transfert et le traitement des
 * données d'une ville.
 *  @author Anthony Carmichael
 *  @version 1.0
 */
public class Ville {
    private int _id_ville;
    private String _nom_ville;
    private int _id_pays;

    public Ville() {
        this._nom_ville=null;
    }

    public Ville(JSONObject ville) throws JSONException {
        this._id_ville = Integer.parseInt(Objects.requireNonNull(ville.getString("id_ville")));
        this._nom_ville = ville.getString("nom_ville");
        this._id_pays = (ville.getInt("id_pays"));
    }

    public int get_id_ville() {
        return _id_ville;
    }

    public void set_id_ville(int _id_ville) {
        this._id_ville = _id_ville;
    }

    public String get_nom_ville() {
        return _nom_ville;
    }

    public void set_nom_ville(String _nom_ville) {
        this._nom_ville = _nom_ville;
    }

    public int get_id_pays() {
        return _id_pays;
    }

    public void set_id_pays(int _id_pays) {
        this._id_pays = _id_pays;
    }
}
