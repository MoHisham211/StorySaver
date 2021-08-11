package mo.zain.storysaver.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.zain.storysaver.R;
import mo.zain.storysaver.model.StoryModel;
import mo.zain.storysaver.ui.ImageFragment;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final List<StoryModel> imageList;
    Context context;
    ImageFragment imageFragment;

    public ImageAdapter(List<StoryModel> imageList, Context context, ImageFragment imageFragment) {
        this.imageList = imageList;
        this.context = context;
        this.imageFragment = imageFragment;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ImageAdapter.ImageViewHolder holder, int position) {

        StoryModel storyModel=imageList.get(position);
        //holder.imageView.setImageBitmap(storyModel.getBitmap());
        Picasso.get().load(storyModel.getFile()).into(holder.imageView);
        holder.imageView.setOnClickListener(v -> {

            final AlertDialog.Builder alertD = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.view_image_full_screen, null);
            alertD.setView(view);

            ImageView imageView = view.findViewById(R.id.img);
            Picasso.get().load(storyModel.getFile()).into(imageView);

            AlertDialog alert = alertD.create();
            alert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
            alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            alert.show();

        });


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends
            RecyclerView.ViewHolder{
        @BindView(R.id.imageButton) ImageButton imageButton;
        @BindView(R.id.image) ImageView imageView;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoryModel storyModel=imageList.get(getAdapterPosition());
                    if (storyModel!=null)
                    {
                        try {
                            imageFragment.downloadImage(storyModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

        }
    }
}
