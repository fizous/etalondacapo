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
