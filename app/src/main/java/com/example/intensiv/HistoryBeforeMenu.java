package com.example.intensiv;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

public class HistoryBeforeMenu extends AppCompatActivity {
    private int clickCount = 0;
    private ConstraintLayout history_before_menu_id_glav;

    private static final String CHANNEL_ID = "MyChannelID"; /// удалить потом, тест уведомлений был
    private static final int NOTIFICATION_ID = 1; /// удалить потом, тест уведомлений был

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableFullscreen();
        setContentView(R.layout.history_before_menu);
        history_before_menu_id_glav = findViewById(R.id.history_before_menu_id_glav);
        createNotificationChannel();

    }
    /// убираю шторы и кнопки телефона
    private void enableFullscreen() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            enableFullscreen();
        }
    }
    /// удалить потом, тест уведомлений был
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyChannel";
            String description = "Channel for my notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /// удалить потом, тест уведомлений был
    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Внимание!")
                .setContentText("Сбрасываю телефон до заводских настроек...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    // следующая реплика
    public void next(View view) {
        clickCount++;

        if (clickCount == 1)  // контролируем нажатия для реплик
        {
            history_before_menu_id_glav.setBackgroundResource(R.drawable.novella2);
        }
        else if (clickCount == 2)
        {
            history_before_menu_id_glav.setBackgroundResource(R.drawable.novella3);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_VISIBLE 
            );
            showNotification();
        }
        else
        {
            goToMainMenu();
        }
    }
    /// переход в меню
    public void goToMainMenu()
    {
        Intent intent = new Intent (this, main_menu_activity.class);
        startActivity(intent);
        finish();
    }


    // пропускаем вступление новеллы (сразу в меню)
    public void skip(View view)
    {
        goToMainMenu();
    }
}