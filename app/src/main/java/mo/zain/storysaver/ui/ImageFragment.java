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
import android.widget.Switch;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import mo.zain.storysaver.R;
import mo.zain.storysaver.adapter.ImageAdapter;
import mo.zain.storysaver.model.StoryModel;
import mo.zain.storysaver.utils.Constants;

import static android.content.Context.MODE_PRIVATE;


public class ImageFragment extends Fragment {

     RecyclerView recyclerView;
     ProgressBar progressBar;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageAdapter imageAdapter;
    ArrayList<StoryModel> models=new ArrayList<>();
    Handler handler=new Handler();
    SharedPreferences sharedPreferences;
    private String lang;
    //private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_image, container, false);

        recyclerView=view.findViewById(R.id.recycleViewImage);
        progressBar=view.findViewById(R.id.progress);
        swipeRefreshLayout=view.findViewById(R.id.swiperefresh);


        sharedPreferences = getActivity().getSharedPreferences("myKey", MODE_PRIVATE);
        lang = sharedPreferences.getString(Constants.Dirctory_KEY,"W");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        getStatus(lang);

        /*MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStatus(lang);
                swipeRefreshLayout.setRefreshing(false);

            }
        });

        return view;
    }
    private void getStatus(String lang) {
        progressBar.setVisibility(View.VISIBLE);
        if (lang.equals("WB")) {
            if (Constants.Story_DirectoryBusniess.exists()) {

                new Thread(() -> {
                    File[] statusFiles;
                    statusFiles = Constants.Story_DirectoryBusniess.listFiles();
                    models.clear();
                    if (statusFiles != null && statusFiles.length > 0) {
                        Arrays.sort(statusFiles, new Comparator<File>() {
                            @Override
                            public int compare(File o1, File o2) {
                                return Long.compare(o2.lastModified(), o1.lastModified());
                            }
                        });
                        for (File file : statusFiles) {
                            StoryModel storyModel = new StoryModel(
                                    file, file.getName(), file.getAbsolutePath());
                            if (!storyModel.isVideo() && storyModel.getTitle().endsWith(".jpg")) {
                                models.add(storyModel);
                            }
                        }
                        handler.post(() -> {
                            imageAdapter = new ImageAdapter(models, getContext(), ImageFragment.this);
                            recyclerView.setAdapter(imageAdapter);
                            imageAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        });

                    } else {
                        handler.post(() -> {
                            progressBar.setVisibility(View.GONE);
                            FancyToast.makeText(getActivity(), "Directory doesn't exist", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        });

                    }
                }).start();

            }else if (Constants.STATUS_DIRECTORY_NEW_WB.exists()){
                new Thread(() -> {
                    File[] statusFiles;
                    statusFiles = Constants.STATUS_DIRECTORY_NEW_WB.listFiles();
                    models.clear();
                    if (statusFiles != null && statusFiles.length > 0) {
                        Arrays.sort(statusFiles, new Comparator<File>() {
                            @Override
                            public int compare(File o1, File o2) {
                                return Long.compare(o2.lastModified(), o1.lastModified());
                            }
                        });
                        for (File file : statusFiles) {
                            StoryModel storyModel = new StoryModel(
                                    file, file.getName(), file.getAbsolutePath());
                            if (!storyModel.isVideo() && storyModel.getTitle().endsWith(".jpg")) {
                                models.add(storyModel);
                            }
                        }
                        handler.post(() -> {
                            imageAdapter = new ImageAdapter(models, getContext(), ImageFragment.this);
                            recyclerView.setAdapter(imageAdapter);
                            imageAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        });

                    } else {
                        handler.post(() -> {
                            progressBar.setVisibility(View.GONE);
                            FancyToast.makeText(getActivity(), "Directory doesn't exist", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        });

                    }
                }).start();

            }else if (Constants.BusinessDirectoryPath.exists()){
                new Thread(() -> {
                    File[] statusFiles;
                    statusFiles = Constants.BusinessDirectoryPath.listFiles();
                    models.clear();
                    if (statusFiles != null && statusFiles.length > 0) {
                        Arrays.sort(statusFiles, new Comparator<File>() {
                            @Override
                            public int compare(File o1, File o2) {
                                return Long.compare(o2.lastModified(), o1.lastModified());
                            }
                        });
                        for (File file : statusFiles) {
                            StoryModel storyModel = new StoryModel(
                                    file, file.getName(), file.getAbsolutePath());
                            if (!storyModel.isVideo() && storyModel.getTitle().endsWith(".jpg")) {
                                models.add(storyModel);
                            }
                        }
                        handler.post(() -> {
                            imageAdapter = new ImageAdapter(models, getContext(), ImageFragment.this);
                            recyclerView.setAdapter(imageAdapter);
                            imageAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        });

                    } else {
                        handler.post(() -> {
                            progressBar.setVisibility(View.GONE);
                            FancyToast.makeText(getActivity(), "Directory doesn't exist", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        });

                    }
                }).start();
            }
        }else if(lang.equals("W")){
            if (Constants.Story_Directory.exists()) {
                new Thread(() -> {
                    File[] statusFiles;
                    statusFiles = Constants.Story_Directory.listFiles();
                    models.clear();
                    if (statusFiles != null && statusFiles.length > 0) {
                        Arrays.sort(statusFiles, new Comparator<File>() {
                            @Override
                            public int compare(File o1, File o2) {
                                return Long.compare(o2.lastModified(), o1.lastModified());
                            }
                        });
                        for (File file : statusFiles) {
                            StoryModel storyModel = new StoryModel(
                                    file, file.getName(), file.getAbsolutePath());
                            if (!storyModel.isVideo() && storyModel.getTitle().endsWith(".jpg")) {
                                models.add(storyModel);
                            }
                        }
                        handler.post(() -> {
                            imageAdapter = new ImageAdapter(models, getContext(), ImageFragment.this);
                            recyclerView.setAdapter(imageAdapter);
                            imageAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        });

                    } else {
                        handler.post(() -> {
                            progressBar.setVisibility(View.GONE);
                            FancyToast.makeText(getActivity(), "Directory doesn't exist", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        });

                    }
                }).start();
            }else if (Constants.STATUS_DIRECTORY_NEW.exists()){
                new Thread(() -> {
                    File[] statusFiles;
                    statusFiles = Constants.STATUS_DIRECTORY_NEW.listFiles();
                    models.clear();
                    if (statusFiles != null && statusFiles.length > 0) {
                        Arrays.sort(statusFiles, new Comparator<File>() {
                            @Override
                            public int compare(File o1, File o2) {
                                return Long.compare(o2.lastModified(), o1.lastModified());
                            }
                        });
                        for (File file : statusFiles) {
                            StoryModel storyModel = new StoryModel(
                                    file, file.getName(), file.getAbsolutePath());
                            if (!storyModel.isVideo() && storyModel.getTitle().endsWith(".jpg")) {
                                models.add(storyModel);
                            }
                        }
                        handler.post(() -> {
                            imageAdapter = new ImageAdapter(models, getContext(), ImageFragment.this);
                            recyclerView.setAdapter(imageAdapter);
                            imageAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        });

                    } else {
                        handler.post(() -> {
                            progressBar.setVisibility(View.GONE);
                            FancyToast.makeText(getActivity(), "Directory doesn't exist", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        });

                    }
                }).start();
            }else if (Constants.WhatsAppDirectoryPath.exists())
            {
                new Thread(() -> {
                    File[] statusFiles;
                    statusFiles = Constants.WhatsAppDirectoryPath.listFiles();
                    models.clear();
                    if (statusFiles != null && statusFiles.length > 0) {
                        Arrays.sort(statusFiles, new Comparator<File>() {
                            @Override
                            public int compare(File o1, File o2) {
                                return Long.compare(o2.lastModified(), o1.lastModified());
                            }
                        });
                        for (File file : statusFiles) {
                            StoryModel storyModel = new StoryModel(
                                    file, file.getName(), file.getAbsolutePath());
                            if (!storyModel.isVideo() && storyModel.getTitle().endsWith(".jpg")) {
                                models.add(storyModel);
                            }
                        }
                        handler.post(() -> {
                            imageAdapter = new ImageAdapter(models, getContext(), ImageFragment.this);
                            recyclerView.setAdapter(imageAdapter);
                            imageAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        });

                    } else {
                        handler.post(() -> {
                            progressBar.setVisibility(View.GONE);
                            FancyToast.makeText(getActivity(), "Directory doesn't exist", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                        });

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
        copyImage(storyModel.getFile(),destFile);

    }

    private void copyImage(File file, File destFile) throws Exception {

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