package mo.zain.storysaver.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.io.File;

import mo.zain.storysaver.ui.ImageFragment;
import mo.zain.storysaver.ui.SavedFragment;
import mo.zain.storysaver.ui.VideoFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private ImageFragment imageFragment;
    private VideoFragment videoFragment;
    private SavedFragment savedFragment;


    public PagerAdapter(@NonNull  FragmentManager fm) {
        super(fm);
        imageFragment=new ImageFragment();
        videoFragment=new VideoFragment();
        savedFragment=new SavedFragment();

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position==0)
        {
            return imageFragment;
        }else if (position==1)
        {
            return videoFragment;
        }else {
            return savedFragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Images";
        }else if (position==1)
        {
            return "Videos";
        }else
        {
            return "Saved";
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
