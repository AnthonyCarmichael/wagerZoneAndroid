package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

<<<<<<< HEAD
/**
 * La classe Paris représente un pari avec des détails tels que le montant, la date et l'heure,
 * l'équipe receveur, l'identifiant de la partie, et l'identifiant du pari.
=======
import java.util.HashMap;

/**
 *  @author Jean-Loup Dandurand-Pominville
 *  @version 1.0
 * C'est une classe permet de gérer le transfert et le traitement des
 * données des paris.
>>>>>>> 455f5799fe080ba1cc3f02350976e24877a2d713
 */
public class Paris {
    private float _montant;
    private String _date_heure;
    private int _receveur;
    private int _id_partie;
    private int _id_paris;

    /**
     * Constructeur de la classe Paris qui initialise les attributs à partir d'un objet JSON.
     *
     * @param paris Un objet JSONObject contenant les détails du pari.
     * @throws JSONException Si une erreur se produit lors de la lecture des données JSON.
     */
    public Paris(JSONObject paris) throws JSONException {
        this._montant = Float.parseFloat(paris.getString("montant"));
        this._date_heure = paris.getString("date_heure");
        this._receveur = Integer.parseInt(paris.getString("receveur"));
        this._id_partie = Integer.parseInt(paris.getString("id_partie"));
        this._id_paris = Integer.parseInt(paris.getString("id_paris"));
    }

    /**
     * Constructeur par défaut de la classe Paris. Initialise les attributs avec des valeurs par défaut.
     */
    public Paris() {
        this._montant = 0;
        this._date_heure = "";
        this._receveur = 0;
        this._id_partie = 0;
        this._id_paris = 0;
    }

    /**
     * Obtient l'identifiant de la partie.
     *
     * @return L'identifiant de la partie.
     */
    public int get_id_partie() {
        return _id_partie;
    }

    /**
     * Définit l'identifiant de la partie.
     *
     * @param _id_partie L'identifiant de la partie à définir.
     */
    public void set_id_partie(int _id_partie) {
        this._id_partie = _id_partie;
    }

    /**
     * Obtient l'identifiant du pari.
     *
     * @return L'identifiant du pari.
     */
    public int get_id_paris() {
        return _id_paris;
    }

    /**
     * Définit l'identifiant du pari.
     *
     * @param _id_paris L'identifiant du pari à définir.
     */
    public void set_id_paris(int _id_paris) {
        this._id_paris = _id_paris;
    }

    /**
     * Obtient le montant du pari.
     *
     * @return Le montant du pari.
     */
    public float get_montant() {
        return _montant;
    }

    /**
     * Obtient le montant du pari sous forme de chaîne de caractères.
     *
     * @return Le montant du pari en tant que chaîne de caractères.
     */
    public String get_string_montant() {
        return Float.toString(_montant);
    }

    /**
     * Définit le montant du pari.
     *
     * @param _montant Le montant du pari à définir.
     */
    public void set_montant(float _montant) {
        this._montant = _montant;
    }

    /**
     * Obtient la date et l'heure du pari.
     *
     * @return La date et l'heure du pari.
     */
    public String get_date_heure() {
        return _date_heure;
    }

    /**
     * Définit la date et l'heure du pari.
     *
     * @param _date_heure La date et l'heure du pari à définir.
     */
    public void set_date_heure(String _date_heure) {
        this._date_heure = _date_heure;
    }

    /**
     * Obtient l'équipe receveur du pari.
     *
     * @return L'équipe receveur.
     */
    public int get_receveur() {
        return _receveur;
    }

    /**
     * Définit l'équipe receveur du pari.
     *
     * @param _receveur L'équipe receveur à définir.
     */
    public void set_receveur(int _receveur) {
        this._receveur = _receveur;
    }
}
