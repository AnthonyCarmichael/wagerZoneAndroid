package com.example.wagerzone;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;


public class Utilisateur implements Serializable {
    private int _id;
    private String _name; // username
    private String _email;
    private String _nom;
    private String _prenom;
    private String _password;
    private String _date_naissance;
    private String _telephone;
    private String _adresse;
    private String _ville;
    private String _pays;
    private BigDecimal _fonds;
    private byte[] _image;

    public Utilisateur() {
        this._id = 0;
        this._name = null;
        this._email = null;
        this._nom = null;
        this._prenom =null;
        this._password = null;
        this._date_naissance = null;
        this._telephone =null;
        this._adresse =null;
        this._ville = null;
        this._pays = null;
        this._fonds = null;
        this._image = null;
    }


    public Utilisateur(HashMap<String, String> user) {
        this._id = Integer.parseInt(Objects.requireNonNull(user.get("id")));
        this._name = user.get("name");
        this._email = user.get("email");
        this._nom = user.get("nom");
        this._prenom = user.get("prenom");
        this._password = user.get("password");
        this._date_naissance = user.get("date_naissance");
        this._telephone = user.get("telephone");
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

    public String get_telephone() {
        return _telephone;
    }

    public void set_telephone(String _telephone) {
        this._telephone = _telephone;
    }

    public String get_adresse() {
        return _adresse;
    }

    public void set_adresse(String _adresse) {
        this._adresse = _adresse;
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

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }
}
