package epitech.m_rahman.epicture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import epitech.m_rahman.epicture.adapter.ImagesAdapter;
import epitech.m_rahman.epicture.model.Image;
import epitech.m_rahman.epicture.model.ImageList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText tagField;
    private Button validButton;

    private ImagesAdapter imagesAdapter;

    private List<Image> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recyclerview);
        tagField = findViewById(R.id.tagField);
        validButton = findViewById(R.id.validButton);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        imagesAdapter = new ImagesAdapter(SearchActivity.this, images);
        recyclerView.setAdapter(imagesAdapter);

        validButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages();
            }
        });
    }

    private void getImages() {
        images.clear();
        Call<ImageList> call = EpictureApp.getApi().getImages(tagField.getText().toString());
        call.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                if(response.isSuccessful()) {
                    for(Image image: response.body().getImages()){
                        images.add(image);
                    }
                    imagesAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
            }
        });
    }
}
