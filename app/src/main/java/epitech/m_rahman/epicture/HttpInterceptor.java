package epitech.m_rahman.epicture;


import android.util.Log;

import java.io.IOException;

import epitech.m_rahman.epicture.model.AccessToken;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;

public class HttpInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Request.Builder builder = request.newBuilder();
        builder.header("Accept", "application/json");

        String token = EpictureApp.getInstance().getAccessToken();
        setAuthHeader(builder, token);

        request = builder.build();
        Response response = chain.proceed(request);

        if (response.code() == 401) {
            synchronized (ServiceGenerator.getOkHttpClient()) {
                String currentToken = EpictureApp.getInstance().getAccessToken();

                if(currentToken != null && currentToken.equals(token)) {
                    int code = refreshToken() / 100;
                    if(code != 2) {
                        if(code == 4)
                            Log.d("Logout", "Logout user here but not needed in Epicture");
                        return response;
                    }
                }

                if(EpictureApp.getInstance().getAccessToken() != null) {
                    setAuthHeader(builder, EpictureApp.getInstance().getAccessToken());
                    request = builder.build();
                    return chain.proceed(request);
                }
            }
        }

        return response;
    }

    private void setAuthHeader(Request.Builder builder, String token) {
        if (token != null)
            builder.header("Authorization", String.format("Bearer %s", token));
    }

    private int refreshToken() throws IOException {
        Call<AccessToken> req = EpictureApp.getApi().refreshToken("https://api.imgur.com/oauth2/token",
                EpictureApp.getInstance().getRefreshToken(),
                ApiConstants.clientId,
                ApiConstants.clientSecret,
                "refresh_token");
        retrofit2.Response<AccessToken> response = req.execute();
        if (response.isSuccessful()) {
            EpictureApp.getInstance().setTokens(response.body().getAccessToken(),
                    response.body().getRefreshToken());
        }
        return response.code();
    }
}