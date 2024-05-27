package com.example.wagerzone;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;
/**
 *  @author Jean-Loup Dandurand-Pominville
 *  @version 1.0
 * C'est une classe permet de gérer le transfert et le traitement des
 * données des Transactions.
 */
public class Transaction {

    private String _date; // username
    private BigDecimal _montant;

    public Transaction(HashMap<String, String> transaction) {
        this._date = transaction.get("date");
        this._montant = BigDecimal.valueOf(Long.parseLong(Objects.requireNonNull(transaction.get("fonds"))));
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    public BigDecimal get_montant() {
        return _montant;
    }

    public void set_montant(BigDecimal _montant) {
        this._montant = _montant;
    }
}
