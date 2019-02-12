package epitech.m_rahman.epicture;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

public class EpictureApp extends Application {

    private static EpictureApp sInstance = null;

    private static ApiInterface apiInterface = null;

    private static String accessToken = null;
    private static String refreshToken = null;

    @Override
    public void onCreate(){
        super.onCreate();

        sInstance = this;
        apiInterface = ServiceGenerator.createService(ApiInterface.class);
        retrieveTokensFromSharedPrefs();
    }

    public static EpictureApp getInstance() {
        return sInstance;
    }

    public static ApiInterface getApi() {
        return apiInterface;
    }

    private void retrieveTokensFromSharedPrefs() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("EpicturePrefs", 0);
        SharedPreferences.Editor editor = pref.edit();

        accessToken = pref.getString("access_token", null);
        refreshToken = pref.getString("refresh_token", null);
    }

    public void setTokens(String access_token, String refresh_token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("EpicturePrefs", 0);
        SharedPreferences.Editor editor = pref.edit();

        accessToken = access_token;
        refreshToken = refresh_token;
        editor.putString("access_token", access_token);
        editor.putString("refresh_token", refresh_token);
        editor.commit();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
