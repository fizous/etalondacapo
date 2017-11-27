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
package etalon.purdue.edu.base.ui;

import java.lang.reflect.Field;

/**
 * Created by hussein on 5/27/16.
 */
public class SignalAppBtnAction extends GenericBtnActionImpl {

  private int mySignal;


  /**
   * Instantiates a new Signal app btn action.
   *
   * @param data         the data
   * @param btnContainer the btn container
   */
  public SignalAppBtnAction(Object data, ButtonContainer btnContainer) {
    super(data, btnContainer);

    try {
      Field f = android.os.Process.class.getField((String) this.data);
      Class<?> t = f.getType();
      if(t == int.class) {
        mySignal = f.getInt(null);
      }
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Object mainTaskExecution(Object... arguments) {
    android.os.Process.sendSignal(android.os.Process.myPid(), mySignal);
    return mySignal;
  }
}
