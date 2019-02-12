package epitech.m_rahman.epicture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Uri uri = getIntent().getData();

        if (uri != null && uri.toString().startsWith(ApiConstants.redirectUri)) {
            String[] paramList = uri.toString().split("&");
            String access_token = paramList[0].substring(paramList[0].indexOf("=") + 1);
            String refresh_token = paramList[3].substring(paramList[3].indexOf("=") + 1);
            EpictureApp.getInstance().setTokens(access_token, refresh_token);
            Intent i = new Intent(getBaseContext(), DashboardActivity.class);
            startActivity(i);
            finish();
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.imgur.com/oauth2/authorize" + "?client_id=" + ApiConstants.clientId + "&response_type=token"));
            startActivity(intent);
        }
    }
}
