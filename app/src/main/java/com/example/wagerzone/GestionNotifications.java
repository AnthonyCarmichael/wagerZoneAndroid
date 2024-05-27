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
/**
 * @author Jean-Loup Dandurand-Pominville
 * @version 1.0
 * Classe de gestion des notifications.
 */
public class GestionNotifications {
    private Context context;
    /**
     * @author Jean-Loup Dandurand-Pominville
     * @version 1.0
     * Constructeur de la classe GestionNotifications.
     * @param context Le contexte de l'application.
     */
    public GestionNotifications(Context context){
        this.context = context;
    }
    /**
     * @author Jean-Loup Dandurand-Pominville
     * @version 1.0
     * Envoie une notification lors de l'execution d'un bouton.
     * @param idNotification L'ID de la notification.
     * @param titre Le titre de la notification.
     * @param texte Le texte de la notification.
     */
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
    /**
     * @author Jean-Loup Dandurand-Pominville
     * @version 1.0
     * Envoie une notification avec un grand texte.
     * @param idNotification L'ID de la notification.
     * @param titre Le titre de la notification.
     * @param grandTexte Le texte détaillé de la notification.
     */
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
                .setSmallIcon(R.drawable.owl_icon)
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
