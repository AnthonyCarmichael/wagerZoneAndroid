package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class Pays {
    private String _nom_pays;
    private int _id_pays;

    public Pays(JSONObject pays) throws JSONException {
        this._id_pays = Integer.parseInt(Objects.requireNonNull(pays.getString("id_pays")));
        this._nom_pays = pays.getString("nom_pays");
    }

    public String get_nom_pays() {
        return _nom_pays;
    }

    public void set_nom_pays(String _nom_pays) {
        this._nom_pays = _nom_pays;
    }

    public int get_id_pays() {
        return _id_pays;
    }

    public void set_id_pays(int _id_pays) {
        this._id_pays = _id_pays;
    }
}
