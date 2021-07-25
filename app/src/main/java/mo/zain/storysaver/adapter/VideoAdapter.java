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
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_status,parent,false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  VideoAdapter.VideoViewHolder holder, int position) {

        StoryModel storyModel=videoList.get(position);
        holder.imageView.setImageBitmap(storyModel.getBitmap());

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
        @BindView(R.id.imageButton)
        ImageButton imageButton;
        @BindView(R.id.image)
        ImageView imageView;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

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
