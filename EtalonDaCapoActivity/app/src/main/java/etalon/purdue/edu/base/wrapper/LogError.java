package etalon.purdue.edu.base.wrapper;

import android.util.Log;

/**
 * Created by hussein on 5/27/16.
 */
public class LogError extends LogTool {

  protected LogError(String tag, String name){
    super(tag,name);
  }

  @Override
  protected int sendLog(String s) {
    return Log.e(this.tag, s);
  }
}
