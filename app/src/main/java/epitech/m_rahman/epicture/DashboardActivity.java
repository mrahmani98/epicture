package epitech.m_rahman.epicture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Button> buttons;
    private static final int[] BUTTON_IDS = {
            R.id.auth_btn,
            R.id.search_btn,
            R.id.upload_btn,
            R.id.image_btn,
            R.id.fav_btn,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        buttons = new ArrayList<Button>(BUTTON_IDS.length);
        for(int id : BUTTON_IDS) {
            Button button = (Button)findViewById(id);
            if (EpictureApp.getInstance().getAccessToken() != null && id == R.id.auth_btn)
                button.setEnabled(false);
            if (EpictureApp.getInstance().getAccessToken() == null && id != R.id.auth_btn)
                button.setEnabled(false);
            button.setOnClickListener(this);
            buttons.add(button);
        }
    }

    @Override
    public void onClick(View v) {

        Intent i;

        switch (v.getId()) {

            case R.id.auth_btn:
                i = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.search_btn:
                i = new Intent(getBaseContext(), SearchActivity.class);
                startActivity(i);
                break;

            case R.id.upload_btn:
                i = new Intent(getBaseContext(), UploadActivity.class);
                startActivity(i);
                break;

            case R.id.image_btn:
                i = new Intent(getBaseContext(), UserImagesActivity.class);
                startActivity(i);
                break;

            case R.id.fav_btn:
                i = new Intent(getBaseContext(), UserFavoritesActivity.class);
                startActivity(i);
                break;

            default:
                break;
        }

    }
}
