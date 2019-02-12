package epitech.m_rahman.epicture;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import epitech.m_rahman.epicture.adapter.ImagesAdapter;
import epitech.m_rahman.epicture.model.Image;
import epitech.m_rahman.epicture.model.ImageList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeContainer;

    private ImagesAdapter imagesAdapter;

    private List<Image> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_favorites);

        recyclerView = findViewById(R.id.recyclerview);
        swipeContainer = findViewById(R.id.swipeContainer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserFavorites();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UserFavoritesActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        imagesAdapter = new ImagesAdapter(UserFavoritesActivity.this, images);
        recyclerView.setAdapter(imagesAdapter);

        getUserFavorites();
    }

    private void getUserFavorites() {
        images.clear();
        Call<ImageList> call = EpictureApp.getApi().getUserFavorites();
        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                swipeContainer.setRefreshing(false);
                if(response.isSuccessful()) {
                    for(Image image: response.body().getImages()){
                        images.add(image);
                    }
                    imagesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                swipeContainer.setRefreshing(false);
            }
        });
    }
}
