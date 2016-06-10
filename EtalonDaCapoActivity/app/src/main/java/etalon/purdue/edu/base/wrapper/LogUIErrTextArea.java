package etalon.purdue.edu.base.wrapper;

import android.util.Log;

import etalon.purdue.edu.base.ui.BaseTextReport;

/**
 * Created by hussein on 6/1/16.
 */
public class LogUIErrTextArea extends LogUITextArea {

  protected LogUIErrTextArea(String tag, String name, BaseTextReport txtReport,
                          boolean allowUI) {
    super(tag, name, txtReport, allowUI);
  }

  @Override
  protected int sendLog(String s) {
    int res = Log.e(this.tag, s);
    if(this.allowUI && UIResult != null) {
      synchronized (UIResult){
        UIResult.appendErrln(s);
      }
    }
    return res;
  }
}
