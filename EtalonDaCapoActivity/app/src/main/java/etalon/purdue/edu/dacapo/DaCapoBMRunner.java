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
