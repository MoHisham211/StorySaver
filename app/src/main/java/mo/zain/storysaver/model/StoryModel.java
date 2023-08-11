package mo.zain.storysaver.model;

import android.graphics.Bitmap;

import androidx.documentfile.provider.DocumentFile;

import java.io.File;
import java.util.Objects;

public class StoryModel {

    private static final String MP4=".mp4";

    private  File file;
    private Bitmap bitmap;
    private  String title,path;
    private boolean isVideo,isApi30;
    DocumentFile documentFile;

    public StoryModel(File file, String title, String path) {
        this.file = file;
        this.title = title;
        this.path = path;
        this.isApi30=false;
        this.isVideo=file.getName().endsWith(MP4);
    }

    public StoryModel(DocumentFile documentFile) {
        this.isApi30=true;
        this.documentFile = documentFile;
        String MP4 = ".mp4";
        this.isVideo = Objects.requireNonNull(documentFile.getName()).endsWith(MP4);
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
    public DocumentFile getDocumentFile() {
        return documentFile;
    }
    public void setDocumentFile(DocumentFile documentFile) {
        this.documentFile = documentFile;
    }

    public boolean isApi30() {
        return isApi30;
    }

    public void setApi30(boolean api30) {
        isApi30 = api30;
    }
}
