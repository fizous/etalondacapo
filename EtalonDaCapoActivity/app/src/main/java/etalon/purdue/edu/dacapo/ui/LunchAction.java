package etalon.purdue.edu.dacapo.ui;

import etalon.purdue.edu.base.BaseActivityHelper;
import etalon.purdue.edu.base.ConfigManager;
import etalon.purdue.edu.base.ui.ButtonContainer;
import etalon.purdue.edu.base.ui.LunchAppBtnAction;
import etalon.purdue.edu.dacapo.DaCapoBMRunner;
import etalon.purdue.edu.dacapo.config.BMHelper;

/**
 * Created by hussein on 5/26/16.
 */
public class LunchAction extends LunchAppBtnAction {


  /**
   * The type Console setter.
   */
  class ConsoleSetter implements Runnable {
    /**
     * The Bm helper.
     */
    BMHelper bmHelper;

    /**
     * Instantiates a new Console setter.
     *
     * @param bmHelper the bm helper
     */
    public ConsoleSetter(BMHelper bmHelper) {
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

  private String appName;


  /**
   * Instantiates a new Lunch action.
   *
   * @param data         the data
   * @param btnContainer the btn container
   */
  public LunchAction(Object data, ButtonContainer btnContainer) {
    super(data, btnContainer);
    setAppName();
  }

  private void setAppName() {
    this.appName = getBtnContainer().getLabel();
  }

  public Object mainTaskExecution(Object... arguments) {
    BMHelper bmHelper = new BMHelper(appName, (BaseActivityHelper) data);

    DaCapoBMRunner appRunner = new DaCapoBMRunner(bmHelper);
    bmHelper.setBmThread(appRunner);
    bmHelper.mActivityHelper.mActivity.runOnUiThread(new ConsoleSetter(bmHelper));

    synchronized (bmHelper) {
      while(!bmHelper.ready)
        try {
          bmHelper.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
    }

    appRunner.start();


    ConfigManager.DebugSOS("running action ... " +
        this.getBtnContainer().getActionName() +
        ", label = " + this.appName);

    try {
      appRunner.join();
      bmHelper.mActivityHelper.resetButnAction(this.getBtnContainer(), this.data);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return this.data;
  }

  @Override
  protected void onProgressUpdate(Object... updateArgs) {

  }
}
