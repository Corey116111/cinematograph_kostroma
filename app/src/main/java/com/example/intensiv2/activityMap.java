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
        MapKitFactory.setApiKey("AQVN3GNujXfRDTbrB5LiVkcH6UXwG_rH_EKSp3bE");
    }
}
