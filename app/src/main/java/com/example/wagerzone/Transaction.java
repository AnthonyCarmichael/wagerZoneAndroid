package com.example.wagerzone;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

/**
 * La classe Transaction représente une transaction avec sa date et son montant.
 */
public class Transaction {

    private String _date; // Date de la transaction
    private BigDecimal _montant; // Montant de la transaction

    /**
     * Constructeur de la classe Transaction à partir d'une HashMap.
     *
     * @param transaction HashMap contenant les données de la transaction.
     */
    public Transaction(HashMap<String, String> transaction) {
        this._date = transaction.get("date");
        this._montant = BigDecimal.valueOf(Long.parseLong(Objects.requireNonNull(transaction.get("fonds"))));
    }

    /**
     * Méthode getter pour l'attribut _date.
     *
     * @return La date de la transaction.
     */
    public String get_date() {
        return _date;
    }

    /**
     * Méthode setter pour l'attribut _date.
     *
     * @param _date La nouvelle date de la transaction.
     */
    public void set_date(String _date) {
        this._date = _date;
    }

    /**
     * Méthode getter pour l'attribut _montant.
     *
     * @return Le montant de la transaction.
     */
    public BigDecimal get_montant() {
        return _montant;
    }

    /**
     * Méthode setter pour l'attribut _montant.
     *
     * @param _montant Le nouveau montant de la transaction.
     */
    public void set_montant(BigDecimal _montant) {
        this._montant = _montant;
    }
}
