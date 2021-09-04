package mo.zain.storysaver.model;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopModel {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("vedio")
    @Expose
    private String vedio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVedio() {
        return vedio;
    }

    public void setVedio(String vedio) {
        this.vedio = vedio;
    }
}
