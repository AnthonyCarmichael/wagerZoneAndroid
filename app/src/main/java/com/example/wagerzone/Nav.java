package com.example.wagerzone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class Nav extends AppCompatActivity implements View.OnClickListener{
    private Button _home,_equipes,_matchs,_paris;
    private ImageButton _user;
    private boolean _selected = false;
    Context _context;
    private Activity _activity;

    public Nav(Context context, View rootView, Activity activity) {
        this._user = rootView.findViewById(R.id.userIcone);
        this._home = rootView.findViewById(R.id.home);
        this._equipes = rootView.findViewById(R.id.equipes);
        this._matchs = rootView.findViewById(R.id.matchs);
        this._paris = rootView.findViewById(R.id.paris);
        this._context = context;
        this._selected = false;
        this._activity = activity;

        _user.setOnClickListener(this);
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

    public ImageButton get_user() {
        return _user;
    }

    public void set_user(ImageButton _user) {
        this._user = _user;
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
            Intent equipesIntent = new Intent(_context,ConnexionActivity.class);
            v.setBackgroundResource(R.drawable.rounded_red);
            _home.setBackgroundResource(R.drawable.rounded_dark_red);
            _context.startActivity(equipesIntent);
            if (!_activity.getClass().equals(MainActivity.class))
                _activity.finish();
        }
        if (v.getId() == R.id.matchs) {
            Intent matchsIntent = new Intent(_context,ConnexionActivity.class);
            v.setBackgroundResource(R.drawable.rounded_red);
            _home.setBackgroundResource(R.drawable.rounded_dark_red);
            _context.startActivity(matchsIntent);
            if (!_activity.getClass().equals(MainActivity.class))
                _activity.finish();
        }
        if (v.getId() == R.id.paris) {
            Intent parisIntent = new Intent(_context,ConnexionActivity.class);
            v.setBackgroundResource(R.drawable.rounded_red);
            _home.setBackgroundResource(R.drawable.rounded_dark_red);
            _context.startActivity(parisIntent);
            if (!_activity.getClass().equals(MainActivity.class))
                _activity.finish();
        }
    }


    private void showUserMenu(View view) {
        PopupMenu menuUser = new PopupMenu(_context,view);
        menuUser.getMenuInflater().inflate(R.menu.user_menu,menuUser.getMenu());
        _user.setBackgroundResource(R.drawable.rounded_red);

        menuUser.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.connecter && !_activity.getClass().equals(ConnexionActivity.class)) {
                    Intent connexionIntent = new Intent(_context,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    _home.setBackgroundResource(R.drawable.rounded_dark_red);
                    _selected = true;
                    _context.startActivity(connexionIntent);
                    if (!_activity.getClass().equals(MainActivity.class))
                        _activity.finish();
                    return true;
                }
                if (item.getItemId() == R.id.deconnecter && !_activity.getClass().equals(ConnexionActivity.class)) {
                    Intent connexionIntent = new Intent(_context,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    _home.setBackgroundResource(R.drawable.rounded_dark_red);
                    _selected = true;
                    _context.startActivity(connexionIntent);
                    if (!_activity.getClass().equals(MainActivity.class))
                        _activity.finish();
                    return true;
                }
                if (item.getItemId() == R.id.inscription && !_activity.getClass().equals(ConnexionActivity.class)) {
                    Intent connexionIntent = new Intent(_context,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    _home.setBackgroundResource(R.drawable.rounded_dark_red);
                    _selected = true;
                    _context.startActivity(connexionIntent);
                    if (!_activity.getClass().equals(MainActivity.class))
                        _activity.finish();
                    return true;
                }
                if (item.getItemId() == R.id.compte && !_activity.getClass().equals(ConnexionActivity.class)) {
                    Intent connexionIntent = new Intent(_context,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    _home.setBackgroundResource(R.drawable.rounded_dark_red);
                    _selected = true;
                    _context.startActivity(connexionIntent);
                    if (!_activity.getClass().equals(MainActivity.class))
                        _activity.finish();
                    return true;
                }
                if (item.getItemId() == R.id.portefeuille && !_activity.getClass().equals(ConnexionActivity.class)) {
                    Intent connexionIntent = new Intent(_context,ConnexionActivity.class);
                    //Avant de lancer l'activité
                    _home.setBackgroundResource(R.drawable.rounded_dark_red);
                    _selected = true;
                    _context.startActivity(connexionIntent);
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
                if (!_selected && !_activity.getClass().equals(ConnexionActivity.class))
                    _user.setBackgroundResource(R.drawable.rounded_dark_red);
                else
                    _selected=false;
            }
        });

        menuUser.show();
    }
}
