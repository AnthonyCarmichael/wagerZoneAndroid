package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * La classe EquipePartie représente la relation entre une équipe et une partie,
 * incluant des détails spécifiques à cette relation.
 */
public class EquipePartie {
    private String _equipe;
    private String _partie;
    private int _but_marque;
    private int _cote;
    private int _receveur;

    /**
     * Constructeur qui initialise une instance de la classe EquipePartie à partir d'un objet JSON.
     *
     * @param equipePartie L'objet JSON contenant les informations de l'équipe et de la partie.
     * @throws JSONException Si une erreur se produit lors de la lecture des données JSON.
     */
    public EquipePartie(JSONObject equipePartie) throws JSONException {
        this._equipe = equipePartie.getString("id_equipe");
        this._partie = equipePartie.getString("id_partie");
        this._but_marque = Integer.parseInt(equipePartie.getString("but_marque"));
        this._cote = Integer.parseInt(equipePartie.getString("cote"));
        this._receveur = Integer.parseInt(equipePartie.getString("receveur"));
    }

    /**
     * Constructeur par défaut qui initialise une instance vide de la classe EquipePartie.
     */
    public EquipePartie() {
        this._equipe = "";
        this._partie = "";
        this._but_marque = 0;
        this._cote = 0;
        this._receveur = 0;
    }

    /**
     * Retourne l'identifiant de l'équipe.
     *
     * @return L'identifiant de l'équipe.
     */
    public String get_equipe() {
        return _equipe;
    }

    /**
     * Définit l'identifiant de l'équipe.
     *
     * @param _equipe L'identifiant de l'équipe.
     */
    public void set_equipe(String _equipe) {
        this._equipe = _equipe;
    }

    /**
     * Retourne l'identifiant de la partie.
     *
     * @return L'identifiant de la partie.
     */
    public String get_partie() {
        return _partie;
    }

    /**
     * Définit l'identifiant de la partie.
     *
     * @param _partie L'identifiant de la partie.
     */
    public void set_partie(String _partie) {
        this._partie = _partie;
    }

    /**
     * Retourne le nombre de buts marqués par l'équipe dans la partie.
     *
     * @return Le nombre de buts marqués par l'équipe.
     */
    public int get_but_marque() {
        return _but_marque;
    }

    /**
     * Définit le nombre de buts marqués par l'équipe dans la partie.
     *
     * @param _but_marque Le nombre de buts marqués par l'équipe.
     */
    public void set_but_marque(int _but_marque) {
        this._but_marque = _but_marque;
    }

    /**
     * Retourne la cote de l'équipe dans la partie.
     *
     * @return La cote de l'équipe.
     */
    public int get_cote() {
        return _cote;
    }

    /**
     * Définit la cote de l'équipe dans la partie.
     *
     * @param _cote La cote de l'équipe.
     */
    public void set_cote(int _cote) {
        this._cote = _cote;
    }

    /**
     * Retourne le statut de l'équipe en tant que receveur ou visiteur.
     *
     * @return Le statut de l'équipe (1 pour receveur, 0 pour visiteur).
     */
    public int get_receveur() {
        return _receveur;
    }

    /**
     * Définit le statut de l'équipe en tant que receveur ou visiteur.
     *
     * @param _receveur Le statut de l'équipe (1 pour receveur, 0 pour visiteur).
     */
    public void set_receveur(int _receveur) {
        this._receveur = _receveur;
    }
}
