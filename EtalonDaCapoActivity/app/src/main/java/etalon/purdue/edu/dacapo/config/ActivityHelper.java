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

package etalon.purdue.edu.dacapo.config;

import android.app.Activity;

import com.google.common.base.Joiner;

import java.util.List;

import etalon.purdue.edu.base.BaseActivityHelper;

/**
 * Created by hussein on 5/26/16.
 */
public class ActivityHelper extends BaseActivityHelper {

  /**
   * Instantiates a new Activity helper.
   *
   * @param mainActivity the main activity
   */
  protected ActivityHelper(Activity mainActivity) {
    super(mainActivity);
  }

  /**
   * Generate run arguments string [ ].
   *
   * @param extraArgs the extra args
   * @return the string [ ]
   */
  public String[] generateRunArguments(List<String>extraArgs) {
    return new String[] {
        "-s", mConfigurations.getBenchmarkLoad(),
        "-n", mConfigurations.getBenchmarkIterations(),
        Joiner.on(" ").join(extraArgs)
    };
  }
}
