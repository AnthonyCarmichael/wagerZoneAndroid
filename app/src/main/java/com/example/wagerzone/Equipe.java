package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
/**
 *  @author Jean-Loup Dandurand-Pominville
 *  @version 1.0
 * C'est une classe permet de gérer le transfert et le traitement des
 * données des equipes;
 */
public class Equipe {
    private int _id_equipe;
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

    public Equipe(JSONObject equipe) throws JSONException {
        this._id_equipe = equipe.getInt("id_equipe");
        this._nom_equipe = equipe.getString("nom_equipe");
        this._entraineur = equipe.getString("entraineur");
        this._stade = equipe.getString("stade");
        this._match_joue = Integer.parseInt(equipe.getString("match_joue"));
        this._victoire = Integer.parseInt(equipe.getString("victoire"));
        this._defaite = Integer.parseInt(equipe.getString("defaite"));
        this._match_nul = Integer.parseInt(equipe.getString("match_nul"));
        this._defaite_prolongation = Integer.parseInt(equipe.getString("defaite_prolongation"));
        this._sport = equipe.getString("id_sport");
        this._ville = equipe.getString("id_ville");
    }
    public Equipe(){
        this._id_equipe = 0;
        this._nom_equipe = "";
        this._entraineur = "";
        this._stade = "";
        this._match_joue = 0;
        this._victoire = 0;
        this._defaite = 0;
        this._match_nul = 0;
        this._defaite_prolongation = 0;
        this._sport = "";
        this._ville = "";
    }

    public int get_id_equipe() {
        return _id_equipe;
    }

    public void set_id_equipe(int id_equipe) {
        this._id_equipe = id_equipe;
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
