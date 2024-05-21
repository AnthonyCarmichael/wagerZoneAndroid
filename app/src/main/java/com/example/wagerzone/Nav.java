package com.example.wagerzone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Nav extends AppCompatActivity implements View.OnClickListener{
    private Button _home,_equipes,_matchs,_paris;
    private ImageButton _userIcone;
    private boolean _selected = false;
    Context _context;
    private Activity _activity;
    private Utilisateur _user;
    private TextView _messageErreurSuccesMain;


    public Nav(Context context, View rootView, Activity activity) {
        this._userIcone = rootView.findViewById(R.id.userIcone);
        this._home = rootView.findViewById(R.id.home);
        this._equipes = rootView.findViewById(R.id.equipes);
        this._matchs = rootView.findViewById(R.id.matchs);
        this._paris = rootView.findViewById(R.id.paris);
        this._context = context;
        this._selected = false;
        this._activity = activity;

        if (_activity.getIntent() != null && _activity.getIntent().hasExtra("user")) {
            this._user = (Utilisateur) _activity.getIntent().getSerializableExtra("user");
        } else {
            this._user = null;
        }

        if (_activity.getClass().equals(MainActivity.class)){
            _messageErreurSuccesMain = rootView.findViewById(R.id.messageErreurSuccesMain);
            getUserWithToken();
        }
        _userIcone.setOnClickListener(this);
        _home.setOnClickListener(this);
        _equipes.setOnClickListener(this);
        _matchs.setOnClickListener(this);
        _paris.setOnClickListener(this);
    }

    public Button get_home() {
        return _home;
    }

    public void set_home(Button _home) {
        this._home = _home;
    }

    public Button get_equipes() {
        return _equipes;
    }

    public void set_user(Utilisateur _user) {
        this._user = _user;
    }
    public Utilisateur get_user(){return this._user;}

    public void set_equipes(Button _equipes) {
        this._equipes = _equipes;
    }

    public Button get_matchs() {
        return _matchs;
    }

    public void set_matchs(Button _matchs) {
        this._matchs = _matchs;
    }

    public Button get_paris() {
        return _paris;
    }

    public void set_paris(Button _paris) {
        this._paris = _paris;
    }

    public ImageButton get_userIcone() {
        return _userIcone;
    }

    public void set_userIcone(ImageButton _userIcone) {
        this._userIcone = _userIcone;
    }

    public boolean is_selected() {
        return _selected;
    }

    public void set_selected(boolean _selected) {
        this._selected = _selected;
    }

    public Context get_context() {
        return _context;
    }

    public void set_context(Context _context) {
        this._context = _context;
    }

    public TextView get_messageErreurSuccesMain() {
        return _messageErreurSuccesMain;
    }

    public void set_messageErreurSuccesMain(TextView _messageErreurSuccesMain) {
        this._messageErreurSuccesMain = _messageErreurSuccesMain;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.userIcone){
            showUserMenu(v);
        }
        if (v.getId() == R.id.home && !_activity.getClass().equals(MainActivity.class)){
            v.setBackgroundResource(R.drawable.rounded_red);
            _activity.finish();
        }
        if (v.getId() == R.id.equipes){
            Intent equipesIntent = new Intent(_context,EquipesActivity.class);
            equipesIntent.putExtra("user", _user);
            v.setBackgroundResource(R.drawable.rounded_red);
            _home.setBackgroundResource(R.drawable.rounded_dark_red);
            _context.startActivity(equipesIntent);
            if (!_activity.getClass().equals(MainActivity.class))
                _activity.finish();
        }
        if (v.getId() == R.id.matchs) {
            Intent matchsIntent = new Intent(_context,ConnexionActivity.class);
            matchsIntent.putExtra("user", _user);
            v.setBackgroundResource(R.drawable.rounded_red);
            _home.setBackgroundResource(R.drawable.rounded_dark_red);
            _context.startActivity(matchsIntent);
            if (!_activity.getClass().equals(MainActivity.class))
                _activity.finish();
        }
        if (v.getId() == R.id.paris) {
            Intent parisIntent = new Intent(_context,ParisActivity.class);
            parisIntent.putExtra("user", _user);
            v.setBackgroundResource(R.drawable.rounded_red);
            _home.setBackgroundResource(R.drawable.rounded_dark_red);
            _context.startActivity(parisIntent);
            if (!_activity.getClass().equals(MainActivity.class))
                _activity.finish();
        }
        if (_activity.getClass().equals(ParisActivity.class))
            _paris.setBackgroundResource(R.drawable.rounded_dark_red);
        else if (_activity.getClass().equals(EquipesActivity.class))
            _equipes.setBackgroundResource(R.drawable.rounded_dark_red);
    }


    private void showUserMenu(View view) {
        PopupMenu menuUser = new PopupMenu(_context,view);
        // Menu de l'icone user. Si un user est connecté, affiche un menu différent
        if (_user != null)
            menuUser.getMenuInflater().inflate(R.menu.connected_user_menu,menuUser.getMenu());
        else
            menuUser.getMenuInflater().inflate(R.menu.user_menu,menuUser.getMenu());

        _userIcone.setBackgroundResource(R.drawable.rounded_red);

        menuUser.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.connecter && !_activity.getClass().equals(ConnexionActivity.class)) {
                    Intent connexionIntent = new Intent(_context,ConnexionActivity.class);
                    connexionIntent.putExtra("user", _user);
                    //Avant de lancer l'activité
                    _home.setBackgroundResource(R.drawable.rounded_dark_red);
                    _selected = true;

                    _activity.startActivityForResult(connexionIntent, 1);
                    //_activity.startActivityForResult(connexionIntent,1);
                    if (!_activity.getClass().equals(MainActivity.class))
                        _activity.finish();
                    return true;
                }
                if (item.getItemId() == R.id.deconnecter) {
                    //Avant de lancer l'activité
                    _user = null;
                    if (!_activity.getClass().equals(MainActivity.class))
                        _activity.finish();
                    cleanFileToken();
                    _messageErreurSuccesMain.setVisibility(View.INVISIBLE);

                    return true;
                }
                if (item.getItemId() == R.id.inscription && !_activity.getClass().equals(InscriptionActivity.class)) {
                    Intent inscriptionIntent = new Intent(_context,InscriptionActivity.class);
                    //Avant de lancer l'activité
                    _home.setBackgroundResource(R.drawable.rounded_dark_red);
                    _selected = true;
                    _context.startActivity(inscriptionIntent);
                    if (!_activity.getClass().equals(MainActivity.class))
                        _activity.finish();
                    return true;
                }
                if (item.getItemId() == R.id.compte && !_activity.getClass().equals(ConnexionActivity.class)) {
                    Intent compteIntent = new Intent(_context,ConnexionActivity.class);
                    compteIntent.putExtra("user", _user);
                    //Avant de lancer l'activité
                    _home.setBackgroundResource(R.drawable.rounded_dark_red);
                    _selected = true;
                    _context.startActivity(compteIntent);
                    if (!_activity.getClass().equals(MainActivity.class))
                        _activity.finish();
                    return true;
                }
                if (item.getItemId() == R.id.portefeuille && !_activity.getClass().equals(Portefeuille.class)) {
                    Intent portefeuilleIntent = new Intent(_context,Portefeuille.class);
                    portefeuilleIntent.putExtra("user", _user);
                    //Avant de lancer l'activité
                    _home.setBackgroundResource(R.drawable.rounded_dark_red);
                    _selected = true;
                    _context.startActivity(portefeuilleIntent);
                    if (!_activity.getClass().equals(MainActivity.class))
                        _activity.finish();
                    return true;
                }
                return false;
            }
        });
        menuUser.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                // Lors de la fermeture du popmenu
                // RAJOUTER DANS LE IF LES AUTRES ACTIVITÉ DU POPMENU
                if (!_selected && !_activity.getClass().equals(ConnexionActivity.class) && !_activity.getClass().equals(InscriptionActivity.class) )
                    _userIcone.setBackgroundResource(R.drawable.rounded_dark_red);
                else
                    _selected=false;
            }
        });

        menuUser.show();
    }

    private Utilisateur getUserWithToken(){
        Utilisateur user = new Utilisateur();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            URL url = new URL("http://10.0.2.2:8000/api/connexionTokenApi");

            // Crée la connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type","application/json");
            connection.setConnectTimeout(1000);
            JSONObject data = new JSONObject();


            // Get token et username avec le fichier tokenWagerZone.txt
            HashMap<String,String> infoTokenMap = readToken();

            // Écriture de la requête
            if (infoTokenMap.get("username") != null && infoTokenMap.get("token") != null){
                data.put("username", infoTokenMap.get("username"));
                data.put("token", infoTokenMap.get("token"));
            }

            OutputStream os = connection.getOutputStream();
            os.write(data.toString().getBytes());
            os.flush();
            os.close();

            int codeReponse = connection.getResponseCode();
            String reponse = connection.getResponseMessage();
            if (codeReponse == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());

                System.out.println(jsonResponse);
                _messageErreurSuccesMain.setText(R.string.succesConnection);
                _messageErreurSuccesMain.setTextColor(_context.getResources().getColor(R.color.vertFonce));
                _messageErreurSuccesMain.setVisibility(View.VISIBLE);

                JSONObject successObject = jsonResponse.getJSONObject("SUCCESS");

                // Création du user:
                user = new Utilisateur();
                user.set_nom(successObject.getString("nom"));
                user.set_prenom(successObject.getString("prenom"));
                user.set_name(successObject.getString("name")); // USERNAME
                user.set_email(successObject.getString("name"));
                user.set_id(successObject.getInt("id"));
                user.set_date_naissance(successObject.getString("date_naissance"));
                user.set_telephone(successObject.getString("telephone"));
                user.set_adresse(successObject.getString("adresse"));
                user.set_fonds(successObject.getString("fonds"));
                //user.set_ville(successObject.getString("ville"));
                //user.set_pays(successObject.getString("ville"));
                this._user = user;

            } else if (codeReponse == 501) {
                // Compte inactif
                /*
                _messageErreurSuccesMain.setText(R.string.erreur_compte_inactif);
                _messageErreurSuccesMain.setTextColor(_context.getResources().getColor(R.color.rougeFonce));
                _messageErreurSuccesMain.setVisibility(View.VISIBLE);
                 */
                cleanFileToken();

            } else {
                // Compte information invalide
                /*
                _messageErreurSuccesMain.setText(R.string.erreur_information_invalide);
                _messageErreurSuccesMain.setTextColor(_context.getColor(R.color.rougeFonce));
                _messageErreurSuccesMain.setVisibility(View.VISIBLE);*/
                cleanFileToken();
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    private HashMap<String,String> readToken() {
        HashMap<String,String> infoTokenMap = new HashMap<>();
        String fileName="/data/data/com.example.wagerzone/files/tokenWagerZone.txt";
        try {
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);
            if (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] tokenUsername= data.split(";");
                infoTokenMap.put("token",tokenUsername[0]);
                infoTokenMap.put("username",tokenUsername[1]);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return infoTokenMap;
    }

    private void cleanFileToken() {
        String filePath = "/data/data/com.example.wagerzone/files/tokenWagerZone.txt";
        File file = new File(filePath);
        if (file.delete()) {
            System.out.println("Le fichier a été supprimé avec succès.");
        } else {
            System.out.println("Impossible de supprimer le fichier.");
        }
    }
}
