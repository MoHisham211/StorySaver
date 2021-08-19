package mo.zain.storysaver.ui;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.zain.storysaver.R;
import mo.zain.storysaver.adapter.SavedAdapter;
import mo.zain.storysaver.adapter.VideoAdapter;
import mo.zain.storysaver.model.StoryModel;
import mo.zain.storysaver.utils.Constants;


public class SavedFragment extends Fragment {

    @BindView(R.id.recycleViewImage)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private SavedAdapter savedAdapter;
    private ArrayList<StoryModel> models=new ArrayList<>();
    private Handler handler=new Handler();
    private String lang;
    private SharedPreferences sharedPreferences;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_saved, container, false);
        ButterKnife.bind(this,view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        getStatus();
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStatus();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    private void getStatus()
    {
        progressBar.setVisibility(View.VISIBLE);

        File file=new File(Constants.App_Diectory);
        if (file.exists())
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File[] statusFiles=file.listFiles();
                        models.clear();
                        if (statusFiles!=null &&  statusFiles.length>0)
                        {
                            Arrays.sort(statusFiles);
                            for (final File stutas:statusFiles)
                            {
                                StoryModel storyModel=new StoryModel(
                                        stutas,stutas.getName(),stutas.getAbsolutePath());

                                    models.add(storyModel);
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    progressBar.setVisibility(View.GONE);
                                    savedAdapter=new SavedAdapter(models,getContext(),SavedFragment.this);
                                    recyclerView.setAdapter(savedAdapter);
                                    savedAdapter.notifyDataSetChanged();
                                }
                            });
                        }else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setVisibility(View.GONE);
                                    FancyToast.makeText(getActivity(), "Directory doesn't exist", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                                }
                            });
                        }
                    }
                }).start();
            }

    }
    public void deleteItem(StoryModel storyModel) throws Exception {

        File file=new File(Constants.App_Diectory);
        if (file.exists())
        {
            File destFile=new File(file+File.separator+storyModel.getTitle());
            destFile.delete();
            FancyToast.makeText(getActivity(), "Deleted Successfully !!", FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
        }
        getStatus();

    }


}