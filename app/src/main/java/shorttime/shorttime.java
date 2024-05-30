package shorttime;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.minseok.loginanddatabase.R;

import activity.MainActivity;

public class shorttime extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorttime);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // 다른 액티비티로 전환
                Intent intent = new Intent(shorttime.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000); // 2000ms = 2초

       }
    }
