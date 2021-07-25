package mo.zain.storysaver.model;

import android.graphics.Bitmap;

import java.io.File;

public class StoryModel {

    private static final String MP4=".mp4";

    private final File file;
    private Bitmap bitmap;
    private final String title,path;
    private boolean isVideo;



    public StoryModel(File file, String title, String path) {
        this.file = file;
        this.title = title;
        this.path = path;
        this.isVideo=file.getName().endsWith(MP4);
    }

    public File getFile() {
        return file;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }
}
