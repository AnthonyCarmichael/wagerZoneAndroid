package com.example.wagerzone;

import static android.icu.number.NumberRangeFormatter.with;
import static android.provider.Settings.System.getString;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class GestionNotifications {
    private Context context;
    static private int NOTIFICATION_PARIS_ID = 1;

    public GestionNotifications(Context context){
        this.context = context;
    }
    //À la création d'un nouveau paris
    public void creationNouveauParis(String dateEcheance) {
        //Crée la channel (chaine) de notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Arguments pour le channel
            CharSequence name = "Nouveau paris";
            String description = "Utilisateur a créer un nouveau paris";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //Déclaration du channel et manager
            NotificationChannel channel = new NotificationChannel("Nouveau paris", name, importance);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //Création de la norification Channel
            manager.createNotificationChannel(channel);
        }

        //Création du buider
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Nouveau paris")
                .setContentTitle("Nouveau paris")
                .setContentText("Vous avez effectuer un nouveau paris, il sera completé le " + dateEcheance)
                .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Vous avez effectuer un nouveau paris, il sera completé le " + dateEcheance))
                .setSmallIcon(R.drawable.logo_app)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //Création du manager (envoyeur)
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        //Vérifie si la permission est accepté (fait par android studio)
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            // public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                         int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //  Envois la notification
        managerCompat.notify(NOTIFICATION_PARIS_ID, builder.build());
    }
}
