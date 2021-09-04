package mo.zain.storysaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import mo.zain.storysaver.adapter.PagerAdapter;
import mo.zain.storysaver.ui.TopActivity;
import mo.zain.storysaver.utils.Constants;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.toolBarMain) Toolbar toolbar;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager2;
    @BindView(R.id.fileShare) ImageView imageView;
    @BindView(R.id.fileTop) ImageView imageTop;
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
        imageTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TopActivity.class));
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(
                        Intent.EXTRA_TEXT,
                        "Hey, Check out this Awesome Status Saver app.\nThis app Lets you save WhatsApp Status Images and Videos.\nDownload Now : https://play.google.com/store/apps/details?id=mo.zain.storysaver"
                );
                startActivity(Intent.createChooser(shareIntent, "Share!"));
            }
        });
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

}