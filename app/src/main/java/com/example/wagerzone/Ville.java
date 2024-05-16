package com.example.wagerzone;

import java.util.HashMap;
import java.util.Objects;

public class Ville {
    private int _id_ville;
    private String _nom_ville;
    private String _pays;

    public Ville(HashMap<String, String> ville) {
        this._id_ville = Integer.parseInt(Objects.requireNonNull(ville.get("id_ville")));
        this._nom_ville = ville.get("nom_ville");
        this._pays = Objects.requireNonNull(ville.get("id_pays"));
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

    public String get_id_pays() {
        return _pays;
    }

    public void set_id_pays(String _id_pays) {
        this._pays = _id_pays;
    }
}
