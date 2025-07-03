package com.example.intensiv2;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.widget.ImageButton;
import android.media.MediaPlayer;

import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;

public class FinallyWindowQuest extends AppCompatActivity
{
    private static final int STORAGE_PERMISSION_CODE = 100;

    private static final String PREF_NAME = "CertificatePrefs";
    private static final String KEY_CERTIFICATE_RECEIVED = "is_certificate_received";
    private SharedPreferences sharedPrefs;

    private ImageButton soundButton;
    private MediaPlayer audioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finally_quest_window);
        enableFullscreen();
        sharedPrefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        checkStoragePermission();
        
        soundButton = findViewById(R.id.soundButton);
        if (soundButton != null) {
            soundButton.setOnClickListener(v -> {
                if (audioPlayer != null && audioPlayer.isPlaying()) {
                    audioPlayer.stop();
                    audioPlayer.release();
                    audioPlayer = null;
                    return;
                }
                String audioName = TestManager.getFinalQuestAudioName();
                if (audioName != null && !audioName.isEmpty()) {
                    int resId = getResources().getIdentifier(audioName, "raw", getPackageName());
                    if (resId != 0) {
                        if (audioPlayer != null) {
                            audioPlayer.stop();
                            audioPlayer.release();
                        }
                        audioPlayer = MediaPlayer.create(this, resId);
                        float volume = 0.7f;
                        audioPlayer.setVolume(volume, volume);
                        audioPlayer.setOnCompletionListener(mp -> {
                            mp.release();
                            audioPlayer = null;
                        });
                        audioPlayer.start();
                    } else {
                        Toast.makeText(this, "Аудио не найдено", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Нет аудио для этого экрана", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    ///  проверяем разрешение на сохранение в галерею
    private void checkStoragePermission()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение на запись необходимо для сохранения сертификата", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void enableFullscreen()
    {
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
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus)
        {
            enableFullscreen();
        }
    }
    ///  выход в меню
    public void goToMenuFromFinally (View v)
    {
        if (audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer.release();
            audioPlayer = null;
        }
        Intent intent = new Intent (this, main_menu_activity.class);
        startActivity(intent);
    }
    ///  получаем сертификат
    public void get_sertificate(View v)
    {

        if (audioPlayer != null) {
            audioPlayer.stop();
            audioPlayer.release();
            audioPlayer = null;
        }
      
        if (sharedPrefs.getBoolean(KEY_CERTIFICATE_RECEIVED, false))
        {
            Toast.makeText(this, "Вы уже получили сертификат!", Toast.LENGTH_LONG).show();
            return;
        }

        EditText nameInput = findViewById(R.id.text_input); // ввод имени
        String name = nameInput.getText().toString().trim();

        if (name.isEmpty())
        {
            nameInput.setError("Введите ваше имя!");
            return;
        }

        if (name.length() < 3)
        {
            nameInput.setError("Минимум 3 символа!");
            return;
        }

        Bitmap certificate = createCertificateBitmap(name);
        if (certificate != null && saveImageToGallery(certificate, name))
        {
            sharedPrefs.edit()
                    .putBoolean(KEY_CERTIFICATE_RECEIVED, true)
                    .apply();

            Toast.makeText(this,
                    "Сертификат для " + name + " сохранен в галерею!",
                    Toast.LENGTH_LONG).show();
            finish();
        }
        else
        {
            Toast.makeText(this,
                    "Ошибка при сохранении сертификата",
                    Toast.LENGTH_LONG).show();
        }
    }
    /// создаем сертификат, переносим на него записанное имя
    private Bitmap createCertificateBitmap(String name)
    {
        try
        {
            View certificateView = LayoutInflater.from(this).inflate(R.layout.certificate_template, null);

            TextView nameTextView = certificateView.findViewById(R.id.certificate_name);
            nameTextView.setText(name);

            int width = 800;
            int height = 1200;

            certificateView.measure(
                    View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));

            certificateView.layout(0, 0, width, height);

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            certificateView.draw(canvas);

            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    ///  сохраняем сертификат с именем в галерею пользователя
    private boolean saveImageToGallery(Bitmap bitmap, String name)
    {
        try
        {
            String fileName = "Киноквест_Сертификат_" + name + "_" +
                    System.currentTimeMillis() + ".jpg";

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            {
                values.put(MediaStore.Images.Media.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES + "/CinemaQuest");
                values.put(MediaStore.Images.Media.IS_PENDING, 1);
            }

            Uri uri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (uri != null)
            {
                try (OutputStream out = getContentResolver().openOutputStream(uri))
                {
                    if (bitmap.compress(Bitmap.CompressFormat.JPEG, 95, out))
                    {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            values.put(MediaStore.Images.Media.IS_PENDING, 0);
                            getContentResolver().update(uri, values, null, null);
                        }
                        sendBroadcast(new Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        return true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioPlayer != null) {
            audioPlayer.release();
            audioPlayer = null;
        }
    }
}
