package mo.zain.storysaver.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;


import mo.zain.storysaver.R;
import mo.zain.storysaver.model.StoryModel;
import mo.zain.storysaver.ui.SavedFragment;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {
    private final List<StoryModel> modelsList;
    Context context;
    SavedFragment savedFragment;

    public SavedAdapter(List<StoryModel> modelsList, Context context, SavedFragment savedFragment) {
        this.modelsList = modelsList;
        this.context = context;
        this.savedFragment = savedFragment;
    }

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_saved,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position) {
        StoryModel storyModel=modelsList.get(position);
        Glide.with(context).asBitmap().load(storyModel.getFile()).into(holder.imageView);
        if (storyModel.isVideo())
        {
            holder.isVideoImage.setVisibility(View.VISIBLE);
            holder.textViewIsVideo.setText(R.string.videos_tap);
        }else {
            holder.isVideoImage.setVisibility(View.GONE);
            holder.textViewIsVideo.setText(R.string.images_tap);
        }
        holder.imageView.setOnClickListener(v -> {
            if (storyModel.isVideo())
            {

                LayoutInflater inflater = LayoutInflater.from(context);
                final View view1 = inflater.inflate(R.layout.view_video_full_screen, null);
                final AlertDialog.Builder alertDg = new AlertDialog.Builder(context);

                FrameLayout mediaControls = view1.findViewById(R.id.videoViewWrapper);

                if (view1.getParent() != null) {
                    ((ViewGroup) view1.getParent()).removeView(view1);
                }

                alertDg.setView(view1);

                final VideoView videoView = view1.findViewById(R.id.video_full);

                final MediaController mediaController = new MediaController(context, false);

                videoView.setOnPreparedListener(mp -> {

                    mp.start();
                    mediaController.show(0);
                    mp.setLooping(true);
                });

                videoView.setMediaController(mediaController);
                mediaController.setMediaPlayer(videoView);
                videoView.setVideoURI(Uri.fromFile(storyModel.getFile()));
                videoView.requestFocus();

                ((ViewGroup) mediaController.getParent()).removeView(mediaController);

                if (mediaControls.getParent() != null) {
                    mediaControls.removeView(mediaController);
                }

                mediaControls.addView(mediaController);

                final AlertDialog alert2 = alertDg.create();

                alert2.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;
                alert2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                alert2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                alert2.show();
            }else
            {
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageButton imageButton;
        ImageView imageView;
        ImageView isVideoImage;
        TextView textViewIsVideo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageButton=itemView.findViewById(R.id.imageButton);
            imageView=itemView.findViewById(R.id.image);
            isVideoImage=itemView.findViewById(R.id.isVideo);
            textViewIsVideo=itemView.findViewById(R.id.TVIsVideo);
            textViewIsVideo.getBackground().setAlpha(255);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StoryModel storyModel=modelsList.get(getAdapterPosition());
                    if (storyModel!=null)
                    {
                        try {
                            savedFragment.deleteItem(storyModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
