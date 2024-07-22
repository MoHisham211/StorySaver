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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;


import mo.zain.storysaver.R;
import mo.zain.storysaver.model.StoryModel;
import mo.zain.storysaver.ui.VideoFragment;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private final List<StoryModel> videoList;
    Context context;
    VideoFragment videoFragment;

    public VideoAdapter(List<StoryModel> videoList, Context context, VideoFragment videoFragment) {
        this.videoList = videoList;
        this.context = context;
        this.videoFragment = videoFragment;
    }
    public VideoAdapter(List<StoryModel> videoList, Context context){
        this.videoList = videoList;
        this.context = context;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  VideoAdapter.VideoViewHolder holder, int position) {

        StoryModel storyModel=videoList.get(position);
//        holder.imageView.setImageBitmap(storyModel.getBitmap());
        //Picasso.get().load(storyModel.getFile()).into(holder.imageView);
        Glide.with(context).asBitmap().load(storyModel.getFile()).into(holder.imageView);
        holder.imageButtonShar.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("video/");
            shareIntent.putExtra(
                    Intent.EXTRA_STREAM,
                    Uri.parse(storyModel.getPath())
            );
            context.startActivity(Intent.createChooser(shareIntent, "Share!"));
        });
        holder.isVideo.setVisibility(View.VISIBLE);



        LayoutInflater inflater = LayoutInflater.from(context);
        final View view1 = inflater.inflate(R.layout.view_video_full_screen, null);


        holder.imageView.setOnClickListener(v -> {

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

        });


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public class VideoViewHolder extends RecyclerView.ViewHolder{

        ImageButton imageButton;

        ImageView imageView;

        ImageView isVideo;
      ImageButton imageButtonShar;
        public VideoViewHolder(@NonNull View itemView) {
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
                    StoryModel storyModel=videoList.get(getAdapterPosition());
                    if (storyModel!=null)
                    {
                        try {
                            videoFragment.downloadImage(storyModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }
}
