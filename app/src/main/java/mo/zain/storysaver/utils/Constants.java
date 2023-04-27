package mo.zain.storysaver.utils;

import android.os.Environment;

import java.io.File;

public class Constants {
    public static final File Story_Directory=
            new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp/Media/.Statuses");

    public static final File Story_DirectoryBusniess=
            new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp Business/Media/.Statuses");

    public static final File STATUS_DIRECTORY_NEW = new File(Environment.getExternalStorageDirectory() +
            File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses");
    public static final File STATUS_DIRECTORY_NEW_WB = new File(Environment.getExternalStorageDirectory() +
            File.separator + "Android/media/com.WhatsApp Business/WhatsApp Business/Media/.Statuses");

    public static final String App_Diectory=Environment.getExternalStorageDirectory()+File.separator+"WhatsAppStorySaver";
    public static final int TBMBSIZE=1000;

    public static String Dirctory_KEY="Dirctory_KEY";

    public static final File WhatsAppDirectoryPath = new File("/storage/emulated/0/WhatsApp/Media/.Statuses/");
    //Next Update i will add GBWhatsApp
    public static final File GBDirectoryPath = new File("/storage/emulated/0/GBWhatsApp/Media/.Statuses/");
    public static final File BusinessDirectoryPath = new File("/storage/emulated/0/WhatsApp Business/Media/.Statuses/");

}
