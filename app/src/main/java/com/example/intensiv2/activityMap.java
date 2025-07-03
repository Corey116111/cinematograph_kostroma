package com.example.intensiv2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.MapKitFactory;

public class activityMap extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("${YANDEX_MAPS_API_KEY}");
    }
}
