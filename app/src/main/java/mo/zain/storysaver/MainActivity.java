package mo.zain.storysaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.zain.storysaver.adapter.PagerAdapter;
import mo.zain.storysaver.ui.FilesDownloaderActivity;
import mo.zain.storysaver.utils.Constants;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolBarMain) Toolbar toolbar;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager2;
    //@BindView(R.id.fileDownload) ImageView imageView;
    Switch simpleSwitch ;
    Boolean Directory=false;
    PagerAdapter pagerAdapter;
    private SharedPreferences sharedPref;
    String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        simpleSwitch=  findViewById(R.id.simpleSwitch);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, FilesDownloaderActivity.class));
//            }
//        });
        pagerAdapter=new PagerAdapter(getSupportFragmentManager());
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        lang = sharedPreferences.getString(Constants.Dirctory_KEY,"W");
        if (lang.equals("W"))
        {
            simpleSwitch.setChecked(false);
        }else
        {
            simpleSwitch.setChecked(true);
        }
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked)
               {

                   sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedPref.edit();
                   editor.putString(Constants.Dirctory_KEY, "WB");
                   editor.apply();
                   recreate();
                   //onRestart();
               }else
               {

                   sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                   SharedPreferences.Editor editor = sharedPref.edit();
                   editor.putString(Constants.Dirctory_KEY, "W");
                   editor.apply();
                   recreate();
                   //onRestart();
               }
            }
        });

        viewPager2.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager2);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        //System.exit(0);
        finish();
    }
}