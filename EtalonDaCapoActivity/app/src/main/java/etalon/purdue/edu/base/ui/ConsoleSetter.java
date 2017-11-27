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

/**
 * Created by hussein on 6/10/16.
 */

import etalon.purdue.edu.base.BaseActivityHelper;
import etalon.purdue.edu.base.BaseBMHelper;


/**
 * The type Console setter.
 */
public class ConsoleSetter implements Runnable {
  /**
   * The Bm helper.
   */
  BaseBMHelper bmHelper;

  /**
   * Instantiates a new Console setter.
   *
   * @param bmHelper the bm helper
   */
  public ConsoleSetter(BaseBMHelper bmHelper) {
    this.bmHelper = bmHelper;
  }
  @Override
  public void run() {
    BaseActivityHelper.setSystemOutputs(bmHelper, bmHelper.getTag());
    synchronized (bmHelper) {
      bmHelper.ready = true;
      bmHelper.notifyAll();
      //Log.e("testErr", "Done bmHelper");
    }
  }

}
