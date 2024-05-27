package com.example.wagerzone;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * La classe Statut représente un statut avec son nom.
 */
public class Statut {
    private String _nom_statut;

    /**
     * Constructeur de la classe Statut à partir d'un objet JSONObject.
     *
     * @param statut Objet JSONObject contenant les données du statut.
     * @throws JSONException Si une erreur se produit lors de l'extraction des données JSON.
     */
    public Statut(JSONObject statut) throws JSONException {
        this._nom_statut = statut.getString("nom_statut");
    }

    /**
     * Méthode setter pour l'attribut _nom_statut.
     *
     * @param _nom_statut Le nouveau nom du statut.
     */
    public void set_nom_statut(String _nom_statut) {
        this._nom_statut = _nom_statut;
    }

    /**
     * Méthode getter pour l'attribut _nom_statut.
     *
     * @return Le nom du statut.
     */
    public String get_nom_statut() {
        return _nom_statut;
    }
}
