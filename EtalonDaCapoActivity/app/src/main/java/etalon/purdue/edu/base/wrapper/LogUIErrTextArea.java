/*
 * Copyright (c) 2011, 2018 Purdue University.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
