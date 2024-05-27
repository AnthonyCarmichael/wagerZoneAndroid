package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * La classe Partie représente une partie avec ses attributs principaux.
 */
import java.util.HashMap;


public class Partie {
    private int _id_partie;
    private String _date_heure;
    private String _prolongation;
    private String _statut;

    /**
     * Constructeur de la classe Partie à partir d'un objet JSONObject.
     *
     * @param partie Objet JSONObject contenant les données de la partie.
     * @throws JSONException Si une erreur se produit lors de l'extraction des données JSON.
     */
    public Partie(JSONObject partie) throws JSONException {
        this._id_partie = partie.getInt("id_partie");
        this._date_heure = partie.getString("date_heure");
        this._prolongation = partie.getString("prolongation");
        this._statut = partie.getString("id_statut");
    }

    /**
     * Constructeur par défaut de la classe Partie.
     * Initialise les attributs à des valeurs par défaut.
     */
    public Partie(){
        this._id_partie = 0;
        this._date_heure = "";
        this._prolongation = "";
        this._statut = "";
    }

    /**
     * Méthode getter pour l'attribut _id_partie.
     *
     * @return L'identifiant de la partie.
     */
    public int get_id_partie() {
        return _id_partie;
    }

    /**
     * Méthode setter pour l'attribut _id_partie.
     *
     * @param id_partie Le nouvel identifiant de la partie.
     */
    public void set_id_partie(int id_partie) {
        this._id_partie = id_partie;
    }

    /**
     * Méthode getter pour l'attribut _date_heure.
     *
     * @return La date et l'heure de la partie.
     */
    public String get_date_heure() {
        return _date_heure;
    }

    /**
     * Méthode setter pour l'attribut _date_heure.
     *
     * @param _date_heure La nouvelle date et heure de la partie.
     */
    public void set_date_heure(String _date_heure) {
        this._date_heure = _date_heure;
    }

    /**
     * Méthode getter pour l'attribut _prolongation.
     *
     * @return Le statut de prolongation de la partie.
     */
    public String get_prolongation() {
        return _prolongation;
    }

    /**
     * Méthode setter pour l'attribut _prolongation.
     *
     * @param _prolongation Le nouveau statut de prolongation de la partie.
     */
    public void set_prolongation(String _prolongation) {
        this._prolongation = _prolongation;
    }

    /**
     * Méthode getter pour l'attribut _statut.
     *
     * @return Le statut de la partie.
     */
    public String get_statut() {
        return _statut;
    }

    /**
     * Méthode setter pour l'attribut _statut.
     *
     * @param _statut Le nouveau statut de la partie.
     */
    public void set_statut(String _statut) {
        this._statut = _statut;
    }
}
