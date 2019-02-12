package epitech.m_rahman.epicture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private final static String BASE_API_URL = "https://api.imgur.com/3/";
    private static Retrofit retrofit = null;
    private static Gson gson = new GsonBuilder().create();

    private static OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder().addInterceptor(new HttpInterceptor());

    private static OkHttpClient okHttpClient = okHttpClientBuilder.build();

    public static <T> T createService(Class<T> serviceClass){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(serviceClass);
    }

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
