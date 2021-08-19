package mo.zain.storysaver.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.provider.MediaStore;
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
import mo.zain.storysaver.adapter.ImageAdapter;
import mo.zain.storysaver.adapter.VideoAdapter;
import mo.zain.storysaver.model.StoryModel;
import mo.zain.storysaver.utils.Constants;

import static android.content.Context.MODE_PRIVATE;

public class VideoFragment extends Fragment {

    @BindView(R.id.recycleViewImage)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;
    @BindView(R.id.swiperefresh) SwipeRefreshLayout swipeRefreshLayout;
    private VideoAdapter videoAdapter;
    private ArrayList<StoryModel> models=new ArrayList<>();
    private Handler handler=new Handler();
    private String lang;
    private SharedPreferences sharedPreferences;
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_video, container, false);

        ButterKnife.bind(this,view);

        sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        lang = sharedPreferences.getString(Constants.Dirctory_KEY,"W");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        getStatus(lang);

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
                getStatus(lang);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }
    private void getStatus(String lang)
    {
        progressBar.setVisibility(View.VISIBLE);
        if (lang.equals("W"))
        {
            if (Constants.Story_Directory.exists())
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File[] statusFiles=Constants.Story_Directory.listFiles();
                        models.clear();
                        if (statusFiles!=null &&  statusFiles.length>0)
                        {
                            Arrays.sort(statusFiles);
                            for (final File stutas:statusFiles)
                            {
                                StoryModel storyModel=new StoryModel(
                                        stutas,stutas.getName(),stutas.getAbsolutePath());
                                if (storyModel.isVideo())
                                {
                                    models.add(storyModel);
                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    progressBar.setVisibility(View.GONE);
                                    videoAdapter=new VideoAdapter(models,getContext(),VideoFragment.this);
                                    recyclerView.setAdapter(videoAdapter);
                                    videoAdapter.notifyDataSetChanged();
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

        }else if (lang.equals("WB"))
        {
            if (Constants.Story_DirectoryBusniess.exists())
            {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File[] statusFiles=Constants.Story_DirectoryBusniess.listFiles();
                        models.clear();
                        if (statusFiles!=null &&  statusFiles.length>0)
                        {
                            Arrays.sort(statusFiles);
                            for (final File stutas:statusFiles)
                            {
                                StoryModel storyModel=new StoryModel(
                                        stutas,stutas.getName(),stutas.getAbsolutePath());
                                if (storyModel.isVideo())
                                {
                                    models.add(storyModel);
                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {

                                    progressBar.setVisibility(View.GONE);
                                    videoAdapter=new VideoAdapter(models,getContext(),VideoFragment.this);
                                    recyclerView.setAdapter(videoAdapter);
                                    videoAdapter.notifyDataSetChanged();
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

    }
    public void downloadImage(StoryModel storyModel) throws Exception {

        File file=new File(Constants.App_Diectory);
        if (!file.exists())
        {
            file.mkdirs();
        }
        File destFile=new File(file+File.separator+storyModel.getTitle());

        if (destFile.exists())
        {
            destFile.delete();
        }
        copyVideo(storyModel.getFile(),destFile);



    }
    private void copyVideo(File file, File destFile) throws Exception {

        if (!destFile.getParentFile().exists())
        {
            destFile.getParentFile().mkdirs();
        }
        if (!destFile.exists())
        {
            destFile.createNewFile();
        }
        FileChannel source=null;
        FileChannel destination=null;

        source=new FileInputStream(file).getChannel();
        destination=new FileOutputStream(destFile).getChannel();

        destination.transferFrom(source,0, source.size());

        source.close();
        destination.close();

        FancyToast.makeText(getActivity(), "Download Complete !!", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
        Intent intent=new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(destFile));
        getActivity().sendBroadcast(intent);

    }


}