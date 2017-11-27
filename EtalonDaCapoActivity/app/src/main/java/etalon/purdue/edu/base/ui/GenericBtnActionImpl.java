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

import android.os.AsyncTask;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by hussein on 5/25/16.
 */
public class GenericBtnActionImpl extends AsyncTask<Object, Object, Object>
    implements IUIAction {
  /**
   * The Data.
   */
  protected Object data;
  /**
   * The Btn.
   */
  protected ButtonContainer btn;


  /**
   * The Lock.
   */
  protected final ReentrantLock lock = new ReentrantLock();
  /**
   * The Try again.
   */
  protected final Condition tryAgain = lock.newCondition();
  /**
   * The Finished.
   */
  protected volatile boolean finished = false;

  /**
   * Gets btn container.
   *
   * @return the btn container
   */
  public ButtonContainer getBtnContainer() {
    return this.btn;
  }

  /**
   * Instantiates a new Generic btn action.
   *
   * @param data         the data
   * @param btnContainer the btn container
   */
  public GenericBtnActionImpl(Object data, ButtonContainer btnContainer) {
    this.btn = btnContainer;
    setAction(data);
  }


  public Object mainTaskExecution(Object... arguments) {
    try {
      throw new Exception("Execute Should be Implemented");
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    return null;
  }

  @Override
  protected void onPreExecute() {
    btn.setEnabledFlag(false);
  }

  @Override
  protected void onPostExecute(Object result) {
    btn.setEnabledFlag(true);
  }

  @Override
  protected Object doInBackground(Object... params) {
    Object outPut = null;
    try {
      lock.lockInterruptibly();
      finished = false;
      do {
        outPut = mainTaskExecution(params);
        finished = true;
      } while(!finished);

      lock.unlock();

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return outPut;
  }

  public void setAction(Object obj) {
    this.data = obj;
  }

  @Override
  public Object getActionData() {
    return data;
  }


//  @Override
//  protected void onProgressUpdate(Object... updateArgs) {
//
//  }
}
