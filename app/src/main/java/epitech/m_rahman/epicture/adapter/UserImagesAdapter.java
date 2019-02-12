package epitech.m_rahman.epicture.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import epitech.m_rahman.epicture.EditImageActivity;
import epitech.m_rahman.epicture.EpictureApp;
import epitech.m_rahman.epicture.FullScreenImageActivity;
import epitech.m_rahman.epicture.R;
import epitech.m_rahman.epicture.SearchActivity;
import epitech.m_rahman.epicture.model.Image;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserImagesAdapter extends RecyclerView.Adapter<UserImagesAdapter.ViewHolder> {

    private List<Image> images;
    private Context context;

    public UserImagesAdapter(Context context, List<Image> images){
        this.images = images;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, views;
        private ImageView image;
        private Button edit_btn;
        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            views = (TextView) itemView.findViewById(R.id.views);
            image = (ImageView) itemView.findViewById(R.id.image);
            edit_btn = (Button) itemView.findViewById(R.id.edit_btn);
        }
    }


    @Override
    public void onBindViewHolder(final UserImagesAdapter.ViewHolder holder, int position) {
        Image image = images.get(position);
        if (image.getTitle() != null)
            holder.title.setText(image.getTitle());
        else
            holder.title.setText("Aucun titre");
        holder.views.setText(Integer.toString(image.getViews()) + " vues");
        Picasso.get().load(image.getLink()).into(holder.image);

        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EditImageActivity.class);
                i.putExtra("image_id", images.get(holder.getAdapterPosition()).getId());
                v.getContext().startActivity(i);
            }
        });

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), FullScreenImageActivity.class);
                i.putExtra("image_link", images.get(holder.getAdapterPosition()).getLink());
                i.putExtra("image_title", images.get(holder.getAdapterPosition()).getTitle());
                i.putExtra("image_views", images.get(holder.getAdapterPosition()).getViews());
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public UserImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_image, parent, false);
        return new UserImagesAdapter.ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    private void updateImage(String id, String title) {
        Call<okhttp3.ResponseBody> req = EpictureApp.getApi().updateImage(id, title);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
