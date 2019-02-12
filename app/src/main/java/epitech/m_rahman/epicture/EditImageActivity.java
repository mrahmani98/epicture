package epitech.m_rahman.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditImageActivity extends AppCompatActivity {

    private EditText title;
    private Button edit_btn, delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);

        title = findViewById(R.id.title);
        edit_btn = findViewById(R.id.edit_btn);
        delete_btn = findViewById(R.id.delete_btn);

        Intent myIntent = getIntent(); // gets the previously created intent
        final String image_id = myIntent.getStringExtra("image_id");

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateImage(image_id, title.getText().toString());
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImage(image_id);
            }
        });
    }

    private void updateImage(String id, String title) {
        Call<ResponseBody> req = EpictureApp.getApi().updateImage(id, title);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditImageActivity.this, "Image title edited with success !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditImageActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void deleteImage(String id) {
        Call<ResponseBody> req = EpictureApp.getApi().deleteImage(id);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditImageActivity.this, "Image deleted with success !", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditImageActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
