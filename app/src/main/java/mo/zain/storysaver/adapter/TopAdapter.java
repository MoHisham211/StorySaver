package mo.zain.storysaver.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.zain.storysaver.R;
import mo.zain.storysaver.model.StoryModel;
import mo.zain.storysaver.model.TopModel;
import mo.zain.storysaver.ui.TopActivity;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> {

    private List<TopModel> topModels;
    private Context context;
    private TopActivity topActivity;

    public TopAdapter(List<TopModel> topModels, Context context, TopActivity topActivity) {
        this.topModels = topModels;
        this.context = context;
        this.topActivity = topActivity;
    }

    @NonNull
    @Override
    public TopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_top,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopAdapter.ViewHolder holder, int position) {
        TopModel topModel=topModels.get(position);
        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(context).asBitmap().load(topModel.getVedio())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.imageView);
        holder.textView.setText(topModel.getName());

        LayoutInflater inflater = LayoutInflater.from(context);
        final View view1 = inflater.inflate(R.layout.view_video_top_screen, null);

        holder.imageView.setOnClickListener(v -> {

            final AlertDialog.Builder alertDg = new AlertDialog.Builder(context);

            FrameLayout mediaControls = view1.findViewById(R.id.videoViewWrapper);

            if (view1.getParent() != null) {
                ((ViewGroup) view1.getParent()).removeView(view1);
            }

            alertDg.setView(view1);

            final VideoView videoView = view1.findViewById(R.id.video_full);
            final ProgressBar progressBar=view1.findViewById(R.id.proBBar);

            final MediaController mediaController = new MediaController(context, false);

            videoView.setOnPreparedListener(mp -> {

                mp.start();
                mediaController.show(0);
                progressBar.setVisibility(View.GONE);
                mp.setLooping(true);
            });

            Uri video = Uri.parse(topModel.getVedio().toString().trim());

            videoView.setVideoURI(video);
            mediaController.setMediaPlayer(videoView);
            videoView.setMediaController(mediaController);
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
        return topModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.image)
        ImageView imageView;
        @BindView(R.id.title)
        TextView textView;
        @BindView(R.id.imageButton)
        ImageButton imageButton;
        @BindView(R.id.proBar)
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TopModel topModel=topModels.get(getAdapterPosition());
                    if (topModel!=null)
                    {
                        try {
                            topActivity.downloadVideo(topModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


        }
    }
}
