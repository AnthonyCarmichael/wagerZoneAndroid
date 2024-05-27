package com.example.wagerzone;

import java.util.HashMap;

/**
 * La classe Sport représente un sport avec son nom.
 */
public class Sport {
    private String _nom_sport;

    /**
     * Constructeur de la classe Sport à partir d'une HashMap.
     *
     * @param sport HashMap contenant les données du sport.
     */
    public Sport(HashMap<String, String> sport) {
        this._nom_sport = sport.get("sport");
    }

    /**
     * Méthode getter pour l'attribut _nom_sport.
     *
     * @return Le nom du sport.
     */
    public String get_nom_sport() {
        return _nom_sport;
    }

    /**
     * Méthode setter pour l'attribut _nom_sport.
     *
     * @param _nom_sport Le nouveau nom du sport.
     */
    public void set_nom_sport(String _nom_sport) {
        this._nom_sport = _nom_sport;
    }
}
