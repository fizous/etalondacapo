package etalon.purdue.edu.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by hussein on 5/25/16.
 */
public class ConfigManager {

  private static final int DEBUG_MODE = 1;

  /**
   * The constant isDebug.
   */
  public final static boolean isDebug =
      (DEBUG_MODE | ApplicationInfo.FLAG_DEBUGGABLE) != 0;

  /**
   * Debug sos.
   *
   * @param msg the msg
   */
  public static void DebugSOS(String msg) {
    if(isDebug) {
      Log.e("debu_msg --> ", msg);
    }
  }

  /**
   * The constant PREFS_NAME.
   */
  public static final String PREFS_NAME = "app.conf";
  /**
   * The constant READABLE_MEM.
   */
  public static final int READABLE_MEM = 1;
  /**
   * The constant WRITABLE_MEM.
   */
  public static final int WRITABLE_MEM = 2;


  /**
   * The Properties.
   */
  public Properties properties;

  private SharedPreferences configs;
  private String confFilePath;
  private String appRootPath;
  private String dataRootPath;

  private String defaultButtonClassName;

  private int storageAccess;

  private Activity creatorActivity;


  /**
   * Gets app data path.
   *
   * @param foldername the foldername
   * @return the app data path
   */
  public String getAppDataPath(String foldername) {
    return dataRootPath + File.separator + foldername;
  }

  /**
   * Gets app conf file.
   *
   * @param foldername the foldername
   * @return the app conf file
   */
  public String getAppConfFile(String foldername) {
    return dataRootPath + File.separator + foldername + File.separator +
        this.properties.getProperty("app_conf_file");
  }

  /**
   * Create config manager config manager.
   *
   * @param runningActivity the running activity
   * @return the config manager
   */
  public static ConfigManager CreateConfigManager(Activity runningActivity) {
    return new ConfigManager(runningActivity);
  }

  private void loadFromPrivate() {
    int fileNameId =
        creatorActivity.getResources().getIdentifier("conf_file_name", "string",
            creatorActivity.getPackageName());

    confFilePath = creatorActivity.getResources().getString(fileNameId);

    configs =
        creatorActivity.getApplicationContext().getSharedPreferences(confFilePath,
            Context.MODE_PRIVATE);
  }

  private void generateAppPath() {
    String pkgName = creatorActivity.getPackageName();
    int confDirNameId =
        creatorActivity.getResources().getIdentifier("conf_dir_name",
            "string", pkgName);

    int extRootPathId =
        creatorActivity.getResources().getIdentifier("external_root_path",
            "string", pkgName);

//    int appNameId =
//        creatorActivity.getResources().getIdentifier("app_name", "string",
//            pkgName);

    String extDirPath =
        creatorActivity.getResources().getString(extRootPathId);// +
            //File.separator + creatorActivity.getResources().getString(appNameId);

    appRootPath = Environment.getExternalStorageDirectory() + File.separator +
        extDirPath;

    dataRootPath = appRootPath + File.separator +
        creatorActivity.getResources().getString(confDirNameId);

    confFilePath = dataRootPath + File.separator + confFilePath;

  }


  /**
   * Instantiates a new Config manager.
   *
   * @param runningActivity the running activity
   */
  private ConfigManager(Activity runningActivity) {

    this.creatorActivity = runningActivity;
    storageAccess = 0;
    storageAccess |= (isExternalStorageReadable() ? READABLE_MEM : 0);
    storageAccess |= (isExternalStorageWritable() ? WRITABLE_MEM : 0);

    loadFromPrivate();

    properties = new Properties();
    for (Map.Entry<String, ?> entry : configs.getAll().entrySet()) {
      String key = entry.getKey();
      String value = entry.getValue().toString();
      properties.setProperty(key, value);
    }

    if (isReadableCard()) {
      generateAppPath();

      try {
        File file = new File(confFilePath);
        FileInputStream fileInput = new FileInputStream(file);
        properties.loadFromXML(fileInput);
        defaultButtonClassName = properties.getProperty("clzz_default_button", null);
        fileInput.close();


        Enumeration enuKeys = properties.keys();
        SharedPreferences.Editor editor = configs.edit();

        while (enuKeys.hasMoreElements()) {
          String key = (String) enuKeys.nextElement();
          String value = properties.getProperty(key);
          editor.putString(key, value);
          editor.commit();
        }

      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      confFilePath = PREFS_NAME;
    }

    for (Map.Entry<String, ?> entry : configs.getAll().entrySet()) {
      DebugSOS("config values.. ["+ entry.getKey() + "] : [" +
          entry.getValue().toString() + "]");
    }
  }


  /**
   * Gets app tag.
   *
   * @return the app tag
   */
  public String getAppTag() {
    return this.properties.getProperty("tag");
  }


  /**
   * Gets iterations count.
   *
   * @return the iterations count
   */
  public int getIterationsCount() {
    String iterationsCount = this.properties.getProperty("iterations", "1");
    return Integer.parseInt(iterationsCount);
  }

  /**
   * Gets buttons.
   *
   * @return the buttons
   */
  public List<String> getButtons() {
    String definedBtns = this.properties.getProperty("app_buttons", null);
    if (definedBtns == null)
      return null;
    ArrayList<String> decalredBTNs = new ArrayList<String>();
    decalredBTNs.addAll(Arrays.asList(definedBtns.split(",")));
    return decalredBTNs;
  }


  /**
   * Gets signal buttons.
   *
   * @return the signal buttons
   */
  public List<String> getSignalButtons() {
    String definedSigBtns = this.properties.getProperty("signal_buttons", null);
    if (definedSigBtns == null)
      return null;
    ArrayList<String> decalredSigBtnsBTNs = new ArrayList<String>();
    decalredSigBtnsBTNs.addAll(Arrays.asList(definedSigBtns.split(",")));
    return decalredSigBtnsBTNs;
  }

  /**
   * Get benchmark speed string.
   *
   * @return the string
   */
  public String getBenchmarkLoad() {
    try {
      throw new Exception("Add the implementation here");
    } catch (Exception e) {

      e.printStackTrace();
    }
    return null;
  }

  /**
   * Get button action string.
   *
   * @param butnName the butn name
   * @return the string
   */
  public String GetButtonAction(String butnName) {
    return this.properties.getProperty("action_" + butnName);
  }

  /**
   * Get button label string.
   *
   * @param butnName the butn name
   * @return the string
   */
  public String GetButtonLabel(String butnName) {
    String btnLabel = this.properties.getProperty("label_" + butnName);
    return (btnLabel == null ? butnName : btnLabel);
  }


  /**
   * Get button signal string.
   *
   * @param butnName the butn name
   * @return the string
   */
  public String GetButtonSignal(String butnName) {
    String btnSignal = this.properties.getProperty("sig_" + butnName);
    return (btnSignal == null ? butnName : btnSignal);
  }

  /**
   * Gets benchmark iterations.
   *
   * @return the benchmark iterations
   */
  public String getBenchmarkIterations() {
    try {
      throw new Exception("Add the implementation here");
    } catch (Exception e) {

      e.printStackTrace();
    }
    return null;
  }

  /**
   * Is gui enabled boolean.
   *
   * @return the boolean
   */
  public boolean isGuiEnabled() {
    String isGUIstr =  this.configs.getString("use_gui", "false");
    boolean isGuiB = Boolean.valueOf(isGUIstr);

    return isGuiB;
  }

  /**
   * Gets config value.
   *
   * @param key the key
   * @return the config value
   */
  public String getConfigValue(String key) {
    return this.configs.getString(key, null);
  }


  /* Checks if external storage is available to at least read */
  private boolean isExternalStorageReadable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state) ||
        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
      return true;
    }
    return false;
  }

  /**
   * Is external storage writable boolean.
   *
   * @return the boolean
   */
/* Checks if external storage is available for read and write */
  public boolean isExternalStorageWritable() {
    String state = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals(state)) {
      return true;
    }
    return false;
  }

  /**
   * @return
   */
  private boolean isReadableCard() {
    return ((storageAccess & READABLE_MEM) > 0);
  }

  /**
   * @return
   */
  private boolean isWritableCard() {
    return ((storageAccess & WRITABLE_MEM) > 0);
  }
}
