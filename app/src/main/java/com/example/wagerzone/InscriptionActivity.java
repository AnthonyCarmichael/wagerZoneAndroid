package com.example.wagerzone;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InscriptionActivity extends AppCompatActivity {

    private Nav _nav;
    private List<Pays> _pays;
    private List<Ville> _villes;
    private ArrayList<String> _nomVilles;
    private ArrayList<String> _nomPays;
    private Spinner _spinnerVille;
    private Spinner _spinnerPays;
    private String _gpsVille;
    private String _gpsPays;

    private Boolean _permissionGPS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inscription);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Set le nav et récupere le titre
        this._nav = new Nav(this,findViewById(android.R.id.content),InscriptionActivity.this);
        TextView titre = findViewById(R.id.titre);

        // Mise a jour du titre, et surlignement de l'iconeUser
        titre.setText(R.string.inscription);
        ImageButton userIcone = findViewById(R.id.userIcone);
        userIcone.setBackgroundResource(R.drawable.rounded_red);

        // Set les spinner
        set_spinnerPays();
        set_spinnerVille();
        // Permission geolocalisation.
        // Le callback permet d'attendre que l'utilisateur permet ou non le gps pour loader ensuite la page
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);


    }

    private void fetchVille(int idPays) {
        SQLiteManager sqLiteManager = new SQLiteManager(InscriptionActivity.this);
        _villes = sqLiteManager.fetchAllVillesByPaysId(idPays);
    }

    private void fetchPays() {
        SQLiteManager sqLiteManager = new SQLiteManager(InscriptionActivity.this);
        _pays = sqLiteManager.fetchAllPays();
    }

    private void set_spinnerVille(){
        // Set les spinner:
        fetchVille(1);
        _nomVilles = new ArrayList<>();
        for (Ville ville: _villes) {
            _nomVilles.add(ville.get_nom_ville());
        }
        ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                android.R.layout.simple_spinner_dropdown_item, _nomVilles);

        Spinner spinnerVille= findViewById(R.id.villes);

        spinnerVille.setAdapter(arrayAdapterVilles);
        this._spinnerVille = spinnerVille;
        _spinnerPays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                _villes.clear();
                _nomVilles.clear();
                fetchVille(_pays.get(position).get_id_pays());
                for (Ville ville: _villes) {
                    _nomVilles.add(ville.get_nom_ville());
                }
                ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, _nomVilles);
                _spinnerVille.setAdapter(arrayAdapterVilles);
                if (_gpsVille!= null && _gpsPays.equals(_pays.get(position).get_nom_pays())){
                    _nomVilles.add(_gpsVille);
                    arrayAdapterVilles.notifyDataSetChanged();
                    setSelectedItem(_spinnerVille,_gpsVille);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });
    }

    private void set_spinnerPays(){
        // Set les spinner:
        fetchPays();
        _nomPays= new ArrayList<>();
        for (Pays pays: _pays) {
            _nomPays.add(pays.get_nom_pays());
        }
        ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                android.R.layout.simple_spinner_dropdown_item, _nomPays);

        Spinner spinnerPays= findViewById(R.id.pays);

        spinnerPays.setAdapter(arrayAdapterVilles);
        this._spinnerPays = spinnerPays;
    }

    private void setVillePaysWithGPS() {
        GPStracker gpsTracker = new GPStracker(this);
        Location location = gpsTracker.getLocation();
        Geocoder geocoder = new Geocoder(this, Locale.FRENCH);
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address address = addresses.get(0);
                String cityName = address.getLocality(); // Ville
                String countryName = address.getCountryName(); // Pays

                // Log the city and country
                Log.d("AddressInfo", "Ville: " + cityName);
                Log.d("AddressInfo", "Pays: " + countryName);

                // Set les spinners!
                int cpt =0;
                boolean villeTrouve = false;
                boolean paysTrouve = false;
                _gpsPays=countryName;
                int idPaysTrouve = 0;
                for (Pays pays : _pays) {
                    if (pays.get_nom_pays().equals(countryName)) {
                        setSelectedItem(_spinnerPays, pays.get_nom_pays());
                        paysTrouve =true;
                        idPaysTrouve = pays.get_id_pays();
                        cpt =0;
                        for (Ville ville : _villes) {
                            if (ville.get_nom_ville().equals(cityName)) {
                                setSelectedItem(_spinnerVille, ville.get_nom_ville());
                                villeTrouve=true;
                                break;
                            }
                            else
                                cpt++;
                        }
                        break;
                    }
                    else
                        cpt++;
                }
                if (!paysTrouve){
                    _nomPays.add(countryName);
                    Pays paysGPS = new Pays();
                    paysGPS.set_nom_pays(countryName);
                    _pays.add(paysGPS);
                    ArrayAdapter<String> arrayAdapterPays=new ArrayAdapter<String>(InscriptionActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, _nomPays);
                    _spinnerPays.setAdapter(arrayAdapterPays);
                    setSelectedItem(_spinnerPays,countryName);
                }
                if (!villeTrouve){
                    _gpsVille=cityName;
                    Ville villeGPS =new Ville();
                    villeGPS.set_nom_ville(cityName);
                    if (paysTrouve && idPaysTrouve != 0)
                        villeGPS.set_id_pays(idPaysTrouve);
                    _villes.add(villeGPS);

                    _nomVilles.add(cityName);
                    ArrayAdapter<String> arrayAdapterVilles=new ArrayAdapter<String>(InscriptionActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, _nomVilles);
                    _spinnerVille.setAdapter(arrayAdapterVilles);
                    setSelectedItem(_spinnerVille,cityName);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("GeocoderError", "Unable to get address from location", e);
        }
    }

    private void setSelectedItem(Spinner spinner, String value) {
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
        int position = adapter.getPosition(value);
        if (position >= 0) {
            spinner.setSelection(position);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                _permissionGPS = true;
                setVillePaysWithGPS();
            } else {
                _permissionGPS = false;
            }
        }
    }

}