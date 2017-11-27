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

package etalon.purdue.edu.dacapo;

import etalon.purdue.edu.base.BMThread;
import etalon.purdue.edu.base.BaseBMHelper;
import etalon.purdue.edu.dacapo.harness.TestHarness;

/**
 * Created by hussein on 5/26/16.
 */

public class DaCapoBMRunner extends BMThread {

  public DaCapoBMRunner(String bmName) {
    super(bmName);
  }

  public DaCapoBMRunner(BaseBMHelper bmHelper) {
    super(bmHelper);
  }

  /**
   * Any child inheriting from this calss has to override this function which
   * represents the main execution of a thread
   */
  protected void executeTask() {
    TestHarness tHarnesss = new TestHarness();
    String[]    args      = this.bmHelper.getBmArgs();
    tHarnesss.runHarness(args, this.bmHelper.getOutStream(),
                         this.bmHelper.getErrStream());
  }

  @Override
  protected void hookingPreRun() {

  }

  @Override
  protected void hookingPostRun() {

  }

}
