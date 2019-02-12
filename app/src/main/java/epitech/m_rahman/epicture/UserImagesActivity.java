package epitech.m_rahman.epicture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import epitech.m_rahman.epicture.adapter.UserImagesAdapter;
import epitech.m_rahman.epicture.model.Image;
import epitech.m_rahman.epicture.model.ImageList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserImagesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private UserImagesAdapter userImagesAdapter;

    private List<Image> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_images);

        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserImagesActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        userImagesAdapter = new UserImagesAdapter(UserImagesActivity.this, images);
        recyclerView.setAdapter(userImagesAdapter);

        getUserImages();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserImages();
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private void getUserImages() {
        images.clear();
        Call<ImageList> call = EpictureApp.getApi().getUserImages();
        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                if(response.isSuccessful()) {
                    for(Image image: response.body().getImages()){
                        images.add(image);
                    }
                    userImagesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
            }
        });
    }
}
