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

import etalon.purdue.edu.base.BaseActivityHelper;
import etalon.purdue.edu.base.BaseBMHelper;

/**
 * Created by hussein on 5/27/16.
 */
public class BMHelper extends BaseBMHelper {

  /**
   * The Data path.
   */
  String dataPath;

  /**
   * Instantiates a new Bm helper.
   *
   * @param aName          the a name
   * @param activityHelper the activity helper
   */
  public BMHelper(String aName, BaseActivityHelper activityHelper) {
    super(aName, activityHelper);

    dataPath = mActivityHelper.mConfigurations.getAppDataPath(appName);
  }

  @Override
  public String[] getBmArgs() {
    System.out.println(getBmWorload() + ", " +
        String.valueOf(getIterations()) + ", " +
        this.getPropValue("interior_id"));
    return new String[] {
        "-s", getBmWorload(),
        "-n", String.valueOf(getIterations()),
        "-R", dataPath,
        this.getPropValue("interior_id")
    };
  }
}
