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
