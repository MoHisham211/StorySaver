package mo.zain.storysaver.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    }

    @Override
    public int getItemCount() {
        return modelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.imageButton)
        ImageButton imageButton;
        @BindView(R.id.image)
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
