package com.example.intensiv;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class LocationNotifier {
    private static final String CHANNEL_ID = "location_channel";
    private static final int NOTIFICATION_ID = 1;
    private final Context context;
    private final LocationManager locationManager;
    private final double targetLat;
    private final double targetLng;
    private final float radiusMeters;
    private boolean notified = false;

    public LocationNotifier(Context context, double targetLat, double targetLng, float radiusMeters) {
        this.context = context;
        this.targetLat = targetLat;
        this.targetLng = targetLng;
        this.radiusMeters = radiusMeters;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        createNotificationChannel();
    }

    public void start() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //permission not granted
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 1, locationListener, Looper.getMainLooper());
    }

    public void stop() {
        locationManager.removeUpdates(locationListener);
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            float[] results = new float[1];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(), targetLat, targetLng, results);
            if (results[0] <= radiusMeters && !notified) {
                showNotification();
                notified = true;
            }
        }
    };

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_map)
                .setContentTitle("Вы у цели!")
                .setContentText("Нажмите 'Я на месте'")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Location Channel";
            String description = "Channel for location notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
} 