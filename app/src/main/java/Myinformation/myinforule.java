package Myinformation;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.minseok.loginanddatabase.R;

public class myinforule extends AppCompatActivity {
    private WebView webView;
    //뭐냐 묻는다면 웹페이지 띄워줍니다. 생명주기 확인하면 뭔지 나올겁니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_myinforule);

            webView = findViewById(R.id.webview);

            // 웹뷰 설정
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);

            // 웹 페이지 로드
            webView.loadUrl("https://www.mcst.go.kr/kor/s_etc/privacy/privacyGuide.jsp");
        }

        };
