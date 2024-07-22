package mo.zain.storysaver.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

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
        holder.imageButtonShar.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("image/");
            shareIntent.putExtra(
                    Intent.EXTRA_STREAM,
                    Uri.parse(storyModel.getPath())
            );
            context.startActivity(Intent.createChooser(shareIntent, "Share!"));
        });

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

        holder.isVideo.setVisibility(View.GONE);


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageViewHolder extends
            RecyclerView.ViewHolder{
        ImageButton imageButton;
        ImageView imageView;
        ImageView isVideo;
        ImageButton imageButtonShar;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            imageButton=itemView.findViewById(R.id.imageButton);
            imageView=itemView.findViewById(R.id.image);
            isVideo=itemView.findViewById(R.id.isVideo);
            imageButtonShar=itemView.findViewById(R.id.imageButtonShar);


            imageButton.getBackground().setAlpha(175);
            imageButtonShar.getBackground().setAlpha(175);
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
