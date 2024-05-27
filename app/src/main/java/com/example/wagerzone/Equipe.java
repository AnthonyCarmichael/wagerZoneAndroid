package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
<<<<<<< HEAD

/**
 * La classe Equipe représente une équipe de sport avec ses différentes caractéristiques.
=======
/**
 *  @author Jean-Loup Dandurand-Pominville
 *  @version 1.0
 * C'est une classe permet de gérer le transfert et le traitement des
 * données des equipes;
>>>>>>> 455f5799fe080ba1cc3f02350976e24877a2d713
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

    /**
     * Constructeur qui initialise une instance de la classe Equipe à partir d'un objet JSON.
     *
     * @param equipe L'objet JSON contenant les informations de l'équipe.
     * @throws JSONException Si une erreur se produit lors de la lecture des données JSON.
     */
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

    /**
     * Constructeur par défaut qui initialise une instance vide de la classe Equipe.
     */
    public Equipe() {
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

    /**
     * Retourne l'identifiant de l'équipe.
     *
     * @return L'identifiant de l'équipe.
     */
    public int get_id_equipe() {
        return _id_equipe;
    }

    /**
     * Définit l'identifiant de l'équipe.
     *
     * @param id_equipe L'identifiant de l'équipe.
     */
    public void set_id_equipe(int id_equipe) {
        this._id_equipe = id_equipe;
    }

    /**
     * Retourne le nom de l'équipe.
     *
     * @return Le nom de l'équipe.
     */
    public String get_nom_equipe() {
        return _nom_equipe;
    }

    /**
     * Définit le nom de l'équipe.
     *
     * @param _nom_equipe Le nom de l'équipe.
     */
    public void set_nom_equipe(String _nom_equipe) {
        this._nom_equipe = _nom_equipe;
    }

    /**
     * Retourne le nom de l'entraîneur de l'équipe.
     *
     * @return Le nom de l'entraîneur de l'équipe.
     */
    public String get_entraineur() {
        return _entraineur;
    }

    /**
     * Définit le nom de l'entraîneur de l'équipe.
     *
     * @param _entraineur Le nom de l'entraîneur de l'équipe.
     */
    public void set_entraineur(String _entraineur) {
        this._entraineur = _entraineur;
    }

    /**
     * Retourne le nom du stade de l'équipe.
     *
     * @return Le nom du stade de l'équipe.
     */
    public String get_stade() {
        return _stade;
    }

    /**
     * Définit le nom du stade de l'équipe.
     *
     * @param _stade Le nom du stade de l'équipe.
     */
    public void set_stade(String _stade) {
        this._stade = _stade;
    }

    /**
     * Retourne le nombre de matchs joués par l'équipe.
     *
     * @return Le nombre de matchs joués par l'équipe.
     */
    public int get_match_joue() {
        return _match_joue;
    }

    /**
     * Définit le nombre de matchs joués par l'équipe.
     *
     * @param _match_joue Le nombre de matchs joués par l'équipe.
     */
    public void set_match_joue(int _match_joue) {
        this._match_joue = _match_joue;
    }

    /**
     * Retourne le nombre de victoires de l'équipe.
     *
     * @return Le nombre de victoires de l'équipe.
     */
    public int get_victoire() {
        return _victoire;
    }

    /**
     * Définit le nombre de victoires de l'équipe.
     *
     * @param _victoire Le nombre de victoires de l'équipe.
     */
    public void set_victoire(int _victoire) {
        this._victoire = _victoire;
    }

    /**
     * Retourne le nombre de défaites de l'équipe.
     *
     * @return Le nombre de défaites de l'équipe.
     */
    public int get_defaite() {
        return _defaite;
    }

    /**
     * Définit le nombre de défaites de l'équipe.
     *
     * @param _defaite Le nombre de défaites de l'équipe.
     */
    public void set_defaite(int _defaite) {
        this._defaite = _defaite;
    }

    /**
     * Retourne le nombre de matchs nuls de l'équipe.
     *
     * @return Le nombre de matchs nuls de l'équipe.
     */
    public int get_match_nul() {
        return _match_nul;
    }

    /**
     * Définit le nombre de matchs nuls de l'équipe.
     *
     * @param _match_nul Le nombre de matchs nuls de l'équipe.
     */
    public void set_match_nul(int _match_nul) {
        this._match_nul = _match_nul;
    }

    /**
     * Retourne le nombre de défaites en prolongation de l'équipe.
     *
     * @return Le nombre de défaites en prolongation de l'équipe.
     */
    public int get_defaite_prolongation() {
        return _defaite_prolongation;
    }

    /**
     * Définit le nombre de défaites en prolongation de l'équipe.
     *
     * @param _defaite_prolongation Le nombre de défaites en prolongation de l'équipe.
     */
    public void set_defaite_prolongation(int _defaite_prolongation) {
        this._defaite_prolongation = _defaite_prolongation;
    }

    /**
     * Retourne le sport de l'équipe.
     *
     * @return Le sport de l'équipe.
     */
    public String get_sport() {
        return _sport;
    }

    /**
     * Définit le sport de l'équipe.
     *
     * @param _sport Le sport de l'équipe.
     */
    public void set_sport(String _sport) {
        this._sport = _sport;
    }

    /**
     * Retourne la ville de l'équipe.
     *
     * @return La ville de l'équipe.
     */
    public String get_ville() {
        return _ville;
    }

    /**
     * Définit la ville de l'équipe.
     *
     * @param _ville La ville de l'équipe.
     */
    public void set_ville(String _ville) {
        this._ville = _ville;
    }
}
