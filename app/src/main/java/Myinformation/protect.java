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

public class protect extends AppCompatActivity {
    private WebView webView;
//이건 청소년 보호정책에 웹 하면
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect);
        webView = findViewById(R.id.webview);

        // 웹뷰 설정
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

        // 웹 페이지 로드
        webView.loadUrl("https://law.go.kr/%eb%b2%95%eb%a0%b9/%ec%b2%ad%ec%86%8c%eb%85%84%eb%b3%b4%ed%98%b8%eb%b2%95");

    }
}