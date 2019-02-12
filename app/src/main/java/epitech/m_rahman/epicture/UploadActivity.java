package epitech.m_rahman.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {

    private ImageView picked_image;
    private EditText title;
    private Button pick_btn;
    private Button upload_btn;

    private Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        picked_image = findViewById(R.id.picked_image);
        title = findViewById(R.id.title);
        pick_btn = findViewById(R.id.picker);
        upload_btn = findViewById(R.id.submit);

        pick_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(UploadActivity.this).start();
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image != null)
                    uploadImage();
                else
                    Toast.makeText(UploadActivity.this, "Pick an image first !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            image = ImagePicker.getFirstImageOrNull(data);
            Picasso.get().load(new File(image.getPath())).into(picked_image);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadImage() {
        File file = new File(image.getPath());

        Call<okhttp3.ResponseBody> req = EpictureApp.getApi().postImage( MultipartBody.Part.createFormData(
                "image",
                file.getName(),
                RequestBody.create(MediaType.parse("image/*"), file)
        ), title.getText().toString());
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UploadActivity.this, "Upload successful !", Toast.LENGTH_SHORT).show();
                    title.setText("");
                    image = null;
                    picked_image.setImageDrawable(null);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UploadActivity.this, "An unknown error has occured.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}