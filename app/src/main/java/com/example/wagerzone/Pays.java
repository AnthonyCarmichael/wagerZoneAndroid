package com.example.wagerzone;

import java.util.HashMap;
import java.util.Objects;

public class Pays {
    private String _nom_pays;

    public Pays(JSONObject pays) throws JSONException {
        this._nom_pays = pays.getString("nom_pays");
    }

    public String get_nom_pays() {
        return _nom_pays;
    }

    public void set_nom_pays(String _nom_pays) {
        this._nom_pays = _nom_pays;
    }
}
