package mo.zain.storysaver.utils;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final File Story_Directory=
            new File(Environment.getExternalStorageDirectory() +
                    File.separator + "WhatsApp/Media/.Statuses");

    public static final File Story_DirectoryBusniess=
            new File(Environment.getExternalStorageDirectory() +
                    File.separator + "WhatsApp Business/Media/.Statuses");

    public static final String App_Diectory=Environment.getExternalStorageDirectory()+File.separator+"WhatsAppStorySaver";
    public static final int TBMBSIZE=1000;

    public static String Dirctory_KEY="Dirctory_KEY";

}
