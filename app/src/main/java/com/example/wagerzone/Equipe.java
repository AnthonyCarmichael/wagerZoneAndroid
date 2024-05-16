package com.example.wagerzone;

import java.util.HashMap;

public class Equipe {
    private String _nom_equipe;
    private String _entraineur;
    private String _stade;
    private int _match_joue;
    private int _victoire;
    private int _defaite;
    private int _match_nul;
    private int _defaite_prolongation;
    private String _sport;
    private String _ville;

    public Equipe(HashMap<String, String> equipe) {
        this._nom_equipe = equipe.get("nom_equipe");
        this._entraineur = equipe.get("entraineur");
        this._stade = equipe.get("stade");
        this._match_joue = Integer.parseInt(equipe.get("match_joue"));
        this._victoire = Integer.parseInt(equipe.get("victoire"));
        this._defaite = Integer.parseInt(equipe.get("defaite"));
        this._match_nul = Integer.parseInt(equipe.get("match_nul"));
        this._defaite_prolongation = Integer.parseInt(equipe.get("defaite_prolongation"));
        this._sport = equipe.get("sport");
        this._ville = equipe.get("ville");
    }

    public String get_nom_equipe() {
        return _nom_equipe;
    }

    public void set_nom_equipe(String _nom_equipe) {
        this._nom_equipe = _nom_equipe;
    }

    public String get_entraineur() {
        return _entraineur;
    }

    public void set_entraineur(String _entraineur) {
        this._entraineur = _entraineur;
    }

    public String get_stade() {
        return _stade;
    }

    public void set_stade(String _stade) {
        this._stade = _stade;
    }

    public int get_match_joue() {
        return _match_joue;
    }

    public void set_match_joue(int _match_joue) {
        this._match_joue = _match_joue;
    }

    public int get_victoire() {
        return _victoire;
    }

    public void set_victoire(int _victoire) {
        this._victoire = _victoire;
    }

    public int get_defaite() {
        return _defaite;
    }

    public void set_defaite(int _defaite) {
        this._defaite = _defaite;
    }

    public int get_match_nul() {
        return _match_nul;
    }

    public void set_match_nul(int _match_nul) {
        this._match_nul = _match_nul;
    }

    public int get_defaite_prolongation() {
        return _defaite_prolongation;
    }

    public void set_defaite_prolongation(int _defaite_prolongation) {
        this._defaite_prolongation = _defaite_prolongation;
    }

    public String get_sport() {
        return _sport;
    }

    public void set_sport(String _sport) {
        this._sport = _sport;
    }

    public String get_ville() {
        return _ville;
    }

    public void set_ville(String _ville) {
        this._ville = _ville;
    }
}
