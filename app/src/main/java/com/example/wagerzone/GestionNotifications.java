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

    public GestionNotifications(Context context){
        this.context = context;
    }

    public void notifBouton(int idNotification, String titre, String texte) {
        //Crée la channel (chaine) de notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Arguments pour le channel
            CharSequence name = "Notif Bouton";
            String description = "Envoyer une notification a partir d'un bouton";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //Déclaration du channel et manager
            NotificationChannel channel = new NotificationChannel("NotifBtnG", name, importance);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //Création de la norification Channel
            manager.createNotificationChannel(channel);
        }

        //Création du buider
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotifBtnG")
                .setContentTitle(titre)
                .setContentText(texte)
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
        managerCompat.notify(idNotification, builder.build());
    }
    //À la création d'un nouveau paris
    public void notifBoutonGrandTexte(int idNotification, String titre, String grandTexte) {
        //Crée la channel (chaine) de notification
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Arguments pour le channel
            CharSequence name = "Notif bouton grand";
            String description = "Envoyer une notification a partir d'un bouton";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            //Déclaration du channel et manager
            NotificationChannel channel = new NotificationChannel("NotifBtnG", name, importance);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            //Création de la norification Channel
            manager.createNotificationChannel(channel);
        }

        //Création du buider
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Nouveau paris")
                .setContentTitle(titre)
                .setContentText(grandTexte)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(grandTexte))
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
        managerCompat.notify(idNotification, builder.build());
    }
}
