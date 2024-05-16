package com.example.wagerzone;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;


public class Utilisateur {

    private String _name; // username
    private String _email;
    private String _nom;
    private String _prenom;
    private String _password;
    private String _date_naissance;
    private String _telephonne;
    private String _adresse;
    private String _tel;
    private String _ville;
    private String _pays;
    private BigDecimal _fonds;

    public Utilisateur(HashMap<String, String> user) {
        this._name = user.get("name");
        this._email = user.get("email");
        this._nom = user.get("nom");
        this._prenom = user.get("prenom");
        this._password = user.get("password");
        this._date_naissance = user.get("date_naissance");
        this._telephonne = user.get("telephone");
        this._adresse = user.get("adresse");
        this._ville = user.get("ville");
        this._pays = user.get("pays");
        this._fonds = BigDecimal.valueOf(Long.parseLong(Objects.requireNonNull(user.get("fonds"))));
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_nom() {
        return _nom;
    }

    public void set_nom(String _nom) {
        this._nom = _nom;
    }

    public String get_prenom() {
        return _prenom;
    }

    public void set_prenom(String _prenom) {
        this._prenom = _prenom;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_date_naissance() {
        return _date_naissance;
    }

    public void set_date_naissance(String _date_naissance) {
        this._date_naissance = _date_naissance;
    }

    public String get_telephonne() {
        return _telephonne;
    }

    public void set_telephonne(String _telephonne) {
        this._telephonne = _telephonne;
    }

    public String get_adresse() {
        return _adresse;
    }

    public void set_adresse(String _adresse) {
        this._adresse = _adresse;
    }

    public String get_tel() {
        return _tel;
    }

    public void set_tel(String _tel) {
        this._tel = _tel;
    }

    public String get_ville() {
        return _ville;
    }

    public void set_ville(String _ville) {
        this._ville = _ville;
    }

    public String get_pays() {
        return _pays;
    }

    public void set_pays(String _pays) {
        this._pays = _pays;
    }

    public BigDecimal get_fonds() {
        return _fonds;
    }

    public void set_fonds(String _fonds) {
        this._fonds = BigDecimal.valueOf(Long.parseLong(_fonds));
    }
}
