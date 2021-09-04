package mo.zain.storysaver.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.zain.storysaver.R;
import mo.zain.storysaver.adapter.ApiClient;
import mo.zain.storysaver.adapter.TopAdapter;
import mo.zain.storysaver.model.TopModel;
import mo.zain.storysaver.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopActivity extends AppCompatActivity {


    private List<TopModel> list=new ArrayList<>();
    private ProgressBar progressBar;
    private TopAdapter topAdapter;
    private RecyclerView recyclerView;
    private ImageView imageView;
    private RewardedAd mRewardedAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        progressBar=findViewById(R.id.progress);
        recyclerView=findViewById(R.id.rv);
        imageView=findViewById(R.id.back);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new
                GridLayoutManager(TopActivity.this,2));
        //ca-app-pub-3940256099942544/5224354917
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-6018763248917274/3989027228",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                    }
                });
        //
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRewardedAd != null) {
                    Activity activityContext = TopActivity.this;
                    mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            int rewardAmount = rewardItem.getAmount();
                            String rewardType = rewardItem.getType();
                        }
                    });
                    mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdShowedFullScreenContent() {
                            mRewardedAd = null;
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            finish();
                        }
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            finish();
                        }
                    });

                } else {
                    finish();
                }
            }
        });

        getAllTopVideos();
    }
    public void getAllTopVideos()
    {
        progressBar.setVisibility(View.VISIBLE);
        Call<List<TopModel>> listCall= ApiClient.getApiInterface().getTop();
        listCall.enqueue(new Callback<List<TopModel>>() {
            @Override
            public void onResponse(Call<List<TopModel>> call, Response<List<TopModel>> response) {

                if (response.isSuccessful())
                {

                    list=response.body();
                    topAdapter=new TopAdapter(list,TopActivity.this,TopActivity.this);
                    recyclerView.setAdapter(topAdapter);
                    progressBar.setVisibility(View.GONE);

                }else
                {
                    progressBar.setVisibility(View.GONE);
                    FancyToast.makeText(TopActivity.this, "Error happen please try again..", Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
            }

            @Override
            public void onFailure(Call<List<TopModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                FancyToast.makeText(TopActivity.this, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT,FancyToast.ERROR,false).show();

            }
        });
    }

    public void downloadVideo(TopModel topModel) {



        File file=new File(Constants.App_Diectory);
        if (!file.exists())
        {
            file.mkdirs();
        }
        String url=topModel.getVedio();

        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |DownloadManager.Request.NETWORK_WIFI);

        request.setTitle("Download");
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS
                ,System.currentTimeMillis()+".mp4");
        request.setVisibleInDownloadsUi(false);

        DownloadManager manager=(DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        if (manager != null) {
            FancyToast.makeText(getApplication(), "Start Downloading !!", FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();
            manager.enqueue(request);
        }

    }
    @Override
    public void onBackPressed() {
        if (mRewardedAd != null) {
            Activity activityContext = TopActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    //Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();
                }
            });
            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    //Log.d(TAG, "Ad was shown.");
                    mRewardedAd = null;
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    //Log.d(TAG, "Ad failed to show.");
                    finish();
                }
                //-----------TMAM
                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Don't forget to set the ad reference to null so you
                    // don't show the ad a second time.
                    //Log.d(TAG, "Ad was dismissed.");
                    finish();
                }
            });

        } else {
            // Log.d(TAG, "The rewarded ad wasn't ready yet.");
            super.onBackPressed();
        }

    }


}