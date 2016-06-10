package etalon.purdue.edu.base.wrapper;

import android.util.Log;

/**
 * Created by hussein on 5/27/16.
 */
public class LogOut extends LogTool {

  protected LogOut(String tag, String name) {
    super(tag, name);
  }

  @Override
  protected int sendLog(String s) {
    return Log.i(this.tag, s);
  }

  @Override
  public String getExTraceAsString(Exception exception) {
    try {
      throw new Exception ("the method exception should not be used on stdout");
    } catch (Exception e) {

      e.printStackTrace();
    }
    return null;
  }
}
