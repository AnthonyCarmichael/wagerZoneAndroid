package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class Ville {
    private int _id_ville;
    private String _nom_ville;
    private String _pays;

    public Ville(JSONObject ville) throws JSONException {
        this._id_ville = Integer.parseInt(Objects.requireNonNull(ville.getString("id_ville")));
        this._nom_ville = ville.getString("nom_ville");
        this._pays = (Objects.requireNonNull(ville.getString("id_pays")));
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

    public String get_pays() {
        return _pays;
    }

    public void set_pays(String _pays) {
        this._pays = _pays;
    }
}
