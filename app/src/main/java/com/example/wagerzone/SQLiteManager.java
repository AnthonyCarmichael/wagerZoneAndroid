package com.example.wagerzone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLiteManager  extends SQLiteOpenHelper
{
    private static SQLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "wagerzone";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PAYS = "pays";
    private static final String ID_PAYS = "id_pays";
    private static final String NOM_PAYS = "nom_pays";
    private static final String TABLE_VILLE = "villes";
    private static final String ID_VILLE = "id_ville";
    private static final String NOM_VILLE = "nom_ville";
    private static final String TABLE_EQUIPE = "equipes";
    private static final String ID_EQUIPE = "id_equipe";
    private static final String NOM_EQUIPE = "nom_equipe";
    private static final String STADE = "stade";
    private static final String MATCH_JOUE = "match_joue";
    private static final String VICTOIRE = "victoire";
    private static final String DEFAITE = "defaite";
    private static final String MATCH_NUL = "match_nul";
    private static final String DEFAITE_PROLONGATION = "defaite_prolongation";
    private static final String ENTRAINEUR = "entraineur";
    private static final String TABLE_SPORT = "sports";
    private static final String ID_SPORT = "id_sport";
    private static final String NOM_SPORT = "nom_sport";
    private static final String TABLE_EQUIPE_PARTIE = "equipe_partie";
    private static final String BUT_MARQUE = "but_marque";
    private static final String PROLONGATION = "prolongation";
    private static final String COTE = "cote";
    private static final String RECEVEUR = "receveur";
    private static final String TABLE_PARTIE = "parties";
    private static final String ID_PARTIE = "id_partie";
    private static final String DATE_HEURE = "date_heure";
    private static final String TABLE_STATUT = "statuts";
    private static final String ID_STATUT = "id_statut";
    private static final String NOM_STATUT = "nom_statut";
    private static final String TABLE_PARIS = "paris";
    private static final String ID_PARIS = "id_paris";
    private static final String MONTANT = "montant";

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sqlPays;
        StringBuilder sqlVille;
        StringBuilder sqlEquipe;
        StringBuilder sqlPartie;
        StringBuilder sqlEquipePartie;
        StringBuilder sqlStatut;
        StringBuilder sqlParis;
        StringBuilder sqlSport;

        sqlPays = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_PAYS)
                .append(" (")
                .append(ID_PAYS)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(NOM_PAYS)
                .append(" TEXT)");

        sqlVille = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_VILLE)
                .append(" (")
                .append(ID_VILLE)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(NOM_VILLE)
                .append(" TEXT, ")
                .append(ID_PAYS)
                .append(" INTEGER REFERENCES ")
                .append(TABLE_PAYS)
                .append(" (")
                .append(ID_PAYS)
                .append(")")
                .append(")");

        sqlSport = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_SPORT)
                .append(" (")
                .append(ID_SPORT)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(NOM_SPORT)
                .append(" TEXT)");

        sqlStatut = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_STATUT)
                .append(" (")
                .append(ID_STATUT)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(NOM_STATUT)
                .append(" TEXT)");

        sqlPartie = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_PARTIE)
                .append(" (")
                .append(ID_PARTIE)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(PROLONGATION)
                .append(" BOOL, ")
                .append(DATE_HEURE)
                .append(" DATETIME, ")
                .append(ID_STATUT)
                .append(" INTEGER REFERENCES ")
                .append(TABLE_STATUT)
                .append(" (")
                .append(ID_STATUT)
                .append(")")
                .append(")");

        sqlParis = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_PARIS)
                .append(" (")
                .append(ID_PARIS)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(MONTANT)
                .append(" REAL, ")
                .append(DATE_HEURE)
                .append(" DATETIME, ")
                .append(RECEVEUR)
                .append(" BOOL, ")
                .append(ID_PARTIE)
                .append(" INTEGER REFERENCES ")
                .append(TABLE_PARTIE)
                .append(" (")
                .append(ID_PARTIE)
                .append(")")
                .append(")");


        sqlEquipe = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_EQUIPE)
                .append(" (")
                .append(ID_EQUIPE)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(NOM_EQUIPE)
                .append(" TEXT, ")
                .append(ENTRAINEUR)
                .append(" TEXT, ")
                .append(STADE)
                .append(" TEXT, ")
                .append(MATCH_JOUE)
                .append(" INTEGER, ")
                .append(VICTOIRE)
                .append(" INTEGER, ")
                .append(DEFAITE)
                .append(" INTEGER, ")
                .append(MATCH_NUL)
                .append(" INTEGER, ")
                .append(DEFAITE_PROLONGATION)
                .append(" INTEGER, ")
                .append(ID_VILLE)
                .append(" INTEGER REFERENCES ")
                .append(TABLE_VILLE)
                .append(" (")
                .append(ID_VILLE)
                .append("), ")
                .append(ID_SPORT)
                .append(" INTEGER REFERENCES ")
                .append(TABLE_SPORT)
                .append(" (")
                .append(ID_SPORT)
                .append(")")
                .append(")");

        sqlEquipePartie = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_EQUIPE_PARTIE)
                .append(" (")
                .append(BUT_MARQUE)
                .append(" INTEGER, ")
                .append(COTE)
                .append(" INTEGER, ")
                .append(RECEVEUR)
                .append(" BOOL, ")
                .append(ID_PARTIE)
                .append(" INTEGER REFERENCES ")
                .append(TABLE_PARTIE)
                .append(" (")
                .append(ID_PARTIE)
                .append("), ")
                .append(ID_EQUIPE)
                .append(" INTEGER REFERENCES ")
                .append(TABLE_EQUIPE)
                .append(" (")
                .append(ID_EQUIPE)
                .append("), PRIMARY KEY (")
                .append(ID_EQUIPE)
                .append(", ")
                .append(ID_PARTIE)
                .append(")")
                .append(")");

        db.execSQL(sqlPays.toString());
        db.execSQL(sqlVille.toString());
        db.execSQL(sqlSport.toString());
        db.execSQL(sqlEquipe.toString());
        db.execSQL(sqlStatut.toString());
        db.execSQL(sqlPartie.toString());
        db.execSQL(sqlParis.toString());
        db.execSQL(sqlEquipePartie.toString());
        try {
            insertPays(db);
            insertVilles(db);
            insertSports(db);
            insertEquipes(db);
            insertStatuts(db);
            insertParties(db);
            insertParis(db);
            insertEquipePartie(db);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertSports(SQLiteDatabase sqLiteDatabase) throws SQLException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String url = new String("http://10.0.2.2:8000/api/sports");

        try(InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            StringBuilder builder = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line + "\n");
            }
            JSONObject data = new JSONObject(builder.toString());
            JSONArray sportsArr = data.getJSONArray("data");
            for (int i=0; i < sportsArr.length(); i++) {
                JSONObject sport = sportsArr.getJSONObject(i);
                ContentValues contentValues = new ContentValues();
                contentValues.put(NOM_SPORT, sport.getString("nom_sport"));
                sqLiteDatabase.insert(TABLE_SPORT, null, contentValues);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getNomSport() throws IOException {
        ArrayList<String> sports = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + NOM_SPORT + " FROM " + TABLE_SPORT, null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    sports.add(result.getString(0));
                }
            }
        }
        return sports;
    }

    public void insertPays(SQLiteDatabase sqLiteDatabase) throws SQLException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String url = new String("http://10.0.2.2:8000/api/pays");

        try(InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            StringBuilder builder = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line + "\n");
            }
            JSONObject data = new JSONObject(builder.toString());
            JSONArray paysArr = data.getJSONArray("data");
            for (int i=0; i < paysArr.length(); i++) {
                JSONObject pays = paysArr.getJSONObject(i);
                Pays pays2 = new Pays(pays);
                ContentValues contentValues = new ContentValues();
                contentValues.put(NOM_PAYS, pays2.get_nom_pays());
                sqLiteDatabase.insert(TABLE_PAYS, null, contentValues);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getNomPays() throws IOException {
        ArrayList<String> pays = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + NOM_PAYS + " FROM " + TABLE_PAYS, null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    pays.add(result.getString(0));
                }
            }
        }
        return pays;
    }

    public void insertVilles(SQLiteDatabase sqLiteDatabase) throws SQLException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String url = new String("http://10.0.2.2:8000/api/villes");

        try(InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            StringBuilder builder = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line + "\n");
            }
            JSONObject data = new JSONObject(builder.toString());
            JSONArray villesArr = data.getJSONArray("data");
            for (int i=0; i < villesArr.length(); i++) {
                JSONObject ville = villesArr.getJSONObject(i);
                Ville ville2 = new Ville(ville);
                ContentValues contentValues = new ContentValues();
                contentValues.put(NOM_VILLE, ville2.get_nom_ville());
                contentValues.put(ID_PAYS, ville2.get_pays());
                sqLiteDatabase.insert(TABLE_VILLE, null, contentValues);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getNomVille() throws IOException {
        ArrayList<String> ville = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + NOM_VILLE + " FROM " + TABLE_VILLE, null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    ville.add(result.getString(0));
                }
            }
        }
        return ville;
    }

    public void insertEquipes(SQLiteDatabase sqLiteDatabase) throws SQLException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String url = new String("http://10.0.2.2:8000/api/equipes");

        try(InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            StringBuilder builder = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line + "\n");
            }
            JSONObject data = new JSONObject(builder.toString());
            JSONArray equipesArr = data.getJSONArray("data");
            for (int i=0; i < equipesArr.length(); i++) {
                JSONObject equipe = equipesArr.getJSONObject(i);
                Equipe equipe2 = new Equipe(equipe);
                ContentValues contentValues = new ContentValues();
                contentValues.put(NOM_EQUIPE, equipe2.get_nom_equipe());
                contentValues.put(ENTRAINEUR, equipe2.get_entraineur());
                contentValues.put(STADE, equipe2.get_stade());
                contentValues.put(MATCH_NUL, equipe2.get_match_nul());
                contentValues.put(MATCH_JOUE, equipe2.get_match_joue());
                contentValues.put(VICTOIRE, equipe2.get_victoire());
                contentValues.put(DEFAITE, equipe2.get_defaite());
                contentValues.put(DEFAITE_PROLONGATION, equipe2.get_defaite_prolongation());
                contentValues.put(ID_VILLE, equipe2.get_ville());
                contentValues.put(ID_SPORT, equipe2.get_sport());
                sqLiteDatabase.insert(TABLE_EQUIPE, null, contentValues);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<String> getNomEquipe() throws IOException {
        ArrayList<String> equipe = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT " + NOM_EQUIPE + " FROM " + TABLE_EQUIPE, null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    equipe.add(result.getString(0));
                }
            }
        }
        return equipe;
    }

    public void insertEquipePartie(SQLiteDatabase sqLiteDatabase) throws SQLException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String url = new String("http://10.0.2.2:8000/api/equipePartie");

        try(InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            StringBuilder builder = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line + "\n");
            }
            JSONObject data = new JSONObject(builder.toString());
            JSONArray equipePartieArr = data.getJSONArray("data");
            for (int i=0; i < equipePartieArr.length(); i++) {
                JSONObject equipePartie = equipePartieArr.getJSONObject(i);
                EquipePartie equipePartie2 = new EquipePartie(equipePartie);
                ContentValues contentValues = new ContentValues();
                contentValues.put(ID_EQUIPE, equipePartie2.get_equipe());
                contentValues.put(ID_PARTIE, equipePartie2.get_partie());
                contentValues.put(BUT_MARQUE, equipePartie2.get_but_marque());
                contentValues.put(COTE, equipePartie2.get_cote());
                contentValues.put(RECEVEUR, equipePartie2.get_receveur());
                sqLiteDatabase.insert(TABLE_EQUIPE_PARTIE, null, contentValues);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertParties(SQLiteDatabase sqLiteDatabase) throws SQLException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String url = new String("http://10.0.2.2:8000/api/parties");

        try(InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            StringBuilder builder = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line + "\n");
            }
            JSONObject data = new JSONObject(builder.toString());
            JSONArray partiesArr = data.getJSONArray("data");
            for (int i=0; i < partiesArr.length(); i++) {
                JSONObject partie = partiesArr.getJSONObject(i);
                Partie partie2 = new Partie(partie);
                ContentValues contentValues = new ContentValues();
                contentValues.put(ID_STATUT, partie2.get_statut());
                contentValues.put(DATE_HEURE, partie2.get_date_heure());
                sqLiteDatabase.insert(TABLE_PARTIE, null, contentValues);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    //à modifier pour prendre seulement les paris de l'utilisateur connecté
    public void insertParis(SQLiteDatabase sqLiteDatabase) throws SQLException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String url = new String("http://10.0.2.2:8000/api/paris");

        try(InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            StringBuilder builder = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line + "\n");
            }
            JSONObject data = new JSONObject(builder.toString());
            JSONArray parisArr = data.getJSONArray("data");
            for (int i=0; i < parisArr.length(); i++) {
                JSONObject paris = parisArr.getJSONObject(i);
                Paris paris2 = new Paris(paris);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MONTANT, paris2.get_montant());
                contentValues.put(DATE_HEURE, paris2.get_date_heure());
                contentValues.put(RECEVEUR, paris2.get_receveur());
                contentValues.put(ID_PARTIE, paris2.get_partie());
                sqLiteDatabase.insert(TABLE_PARIS, null, contentValues);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Paris> getParis() throws IOException {
        ArrayList<Paris> paris = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_PARIS, null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    Paris pari = new Paris();
                    pari.set_montant(result.getFloat(1));
                    pari.set_date_heure(result.getString(2));
                    pari.set_receveur(result.getInt(3));
                    pari.set_partie(result.getInt(4));
                    paris.add(pari);
                }
            }
        }
        return paris;
    }

    public Partie getPartie(int id_partie) throws IOException {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Partie partie = new Partie();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_PARTIE + " WHERE " + ID_PARTIE + " = " + id_partie, null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    partie.set_id_partie(result.getInt(0));
                    partie.set_prolongation(result.getString(1));
                    partie.set_date_heure(result.getString(2));
                    partie.set_statut(result.getString(3));
                }
            }
        }
        return partie;
    }

    public ArrayList<Equipe> getEquipes() throws IOException {
        ArrayList<Equipe> equipes = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_EQUIPE, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    Equipe equipe = new Equipe();

                    equipe.set_id_equipe(result.getInt(1));
                    equipe.set_nom_equipe(result.getString(2));
                    equipe.set_entraineur(result.getString(3));
                    equipe.set_stade(result.getString(4));
                    equipe.set_match_joue(result.getInt(5));
                    equipe.set_victoire(result.getInt(6));
                    equipe.set_defaite(result.getInt(7));
                    equipe.set_match_nul(result.getInt(8));
                    equipe.set_defaite_prolongation(result.getInt(9));
                    equipe.set_sport(result.getString(10));
                    equipe.set_ville(result.getString(11));

                    equipes.add(equipe);
                }
            }
        }
        return equipes;
    }


    public Equipe getEquipeReceveur(int id_partie) throws IOException {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int id_equipe = 0;
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_EQUIPE_PARTIE + " WHERE " + ID_PARTIE + " = " + id_partie + " AND " + RECEVEUR + " =  1", null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    id_equipe = result.getInt(4);
                }
            }
        }
        Equipe equipe = new Equipe();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_EQUIPE + " WHERE " + ID_EQUIPE + " = " + id_equipe, null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    equipe.set_id_equipe(result.getInt(0));
                    equipe.set_nom_equipe(result.getString(1));
                    equipe.set_entraineur(result.getString(2));
                    equipe.set_stade(result.getString(3));
                    equipe.set_match_joue(result.getInt(4));
                    equipe.set_victoire(result.getInt(5));
                    equipe.set_defaite(result.getInt(6));
                    equipe.set_match_nul(result.getInt(7));
                    equipe.set_defaite_prolongation(result.getInt(8));
                    equipe.set_ville(result.getString(9));
                    equipe.set_sport(result.getString(10));
                }
            }
        }
        return equipe;
    }

    public void MAJParis(int id_user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_PARIS,null, null );
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String url = new String("http://10.0.2.2:8000/api/parisUser?id_user="+id_user);

        try(InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            StringBuilder builder = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line + "\n");
            }
            JSONObject data = new JSONObject(builder.toString());
            JSONArray parisArr = data.getJSONArray("data");
            for (int i=0; i < parisArr.length(); i++) {
                JSONObject paris = parisArr.getJSONObject(i);
                Paris paris2 = new Paris(paris);
                ContentValues contentValues = new ContentValues();
                contentValues.put(MONTANT, paris2.get_montant());
                contentValues.put(DATE_HEURE, paris2.get_date_heure());
                contentValues.put(RECEVEUR, paris2.get_receveur());
                contentValues.put(ID_PARTIE, paris2.get_partie());
                sqLiteDatabase.insert(TABLE_PARIS, null, contentValues);
            }
        } catch (JSONException | MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Equipe getEquipeVisiteur(int id_partie) throws IOException {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int id_equipe = 0;
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_EQUIPE_PARTIE + " WHERE " + ID_PARTIE + " = " + id_partie + " AND " + RECEVEUR + " = 0", null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    id_equipe = result.getInt(4);
                }
            }
        }
        Equipe equipe =new Equipe();
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_EQUIPE + " WHERE " + ID_EQUIPE + " = " + id_equipe, null)){
            if(result.getCount() != 0){
                while (result.moveToNext()){
                    equipe.set_id_equipe(result.getInt(0));
                    equipe.set_nom_equipe(result.getString(1));
                    equipe.set_entraineur(result.getString(2));
                    equipe.set_stade(result.getString(3));
                    equipe.set_match_joue(result.getInt(4));
                    equipe.set_victoire(result.getInt(5));
                    equipe.set_defaite(result.getInt(6));
                    equipe.set_match_nul(result.getInt(7));
                    equipe.set_defaite_prolongation(result.getInt(8));
                    equipe.set_ville(result.getString(9));
                    equipe.set_sport(result.getString(10));
                }
            }
        }
        return equipe;
    }

    public void insertStatuts(SQLiteDatabase sqLiteDatabase) throws SQLException, IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        String url = new String("http://10.0.2.2:8000/api/statuts");

        try(InputStream is = new URL(url).openConnection().getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));

            StringBuilder builder = new StringBuilder();
            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                builder.append(line + "\n");
            }
            JSONObject data = new JSONObject(builder.toString());
            JSONArray statutsArr = data.getJSONArray("data");
            for (int i=0; i < statutsArr.length(); i++) {
                JSONObject statut = statutsArr.getJSONObject(i);
                Statut statut2 = new Statut(statut);
                ContentValues contentValues = new ContentValues();
                contentValues.put(NOM_STATUT, statut2.get_nom_statut());
                sqLiteDatabase.insert(TABLE_STATUT, null, contentValues);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

