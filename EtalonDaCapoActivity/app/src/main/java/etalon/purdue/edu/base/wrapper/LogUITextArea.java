package etalon.purdue.edu.base.wrapper;

import android.util.Log;

import etalon.purdue.edu.base.ui.BaseTextReport;

/**
 * Created by hussein on 5/31/16.
 */
public class LogUITextArea extends LogOut {

  protected boolean allowUI;
  protected BaseTextReport UIResult;

  protected LogUITextArea(String tag, String name) {
    super(tag, name);
  }


  protected LogUITextArea(String tag, String name, BaseTextReport txtReport,
                          boolean allowUI) {
    super(tag, name);
    this.allowUI = allowUI;
    this.UIResult = txtReport;
  }

  @Override
  protected int sendLog(String s) {
    int res = Log.i(this.tag, s);
    if(this.allowUI && UIResult != null) {
      synchronized (UIResult){
        UIResult.appendln(s);
      }
    }
    return res;
  }

  protected int sendUIReport(String s){
    if(this.allowUI && UIResult != null) {
      //UIResult.append(s);
    }
    return 0;
  }
}
