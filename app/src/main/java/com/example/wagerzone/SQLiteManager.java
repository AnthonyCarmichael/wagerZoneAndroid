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
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

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
    private static final String ID_PARTIE = "id_pays";
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
                .append(PROLONGATION)
                .append(" BOOL, ")
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
            insertSports(db);
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



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

