package etalon.purdue.edu.base;

import android.app.Activity;
import android.widget.TextView;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import etalon.purdue.edu.base.ui.BaseTextReport;
import etalon.purdue.edu.base.ui.ButtonContainer;
import etalon.purdue.edu.base.ui.GenericBtnActionImpl;
import etalon.purdue.edu.base.wrapper.LogTool;

/**
 * Created by hussein on 5/25/16.
 */
public class BaseActivityHelper {
  /**
   * The constant singleToneIns.
   */
  public static BaseActivityHelper singleToneIns;
  /**
   * The M activity.
   */
  public Activity mActivity;
  /**
   * The M configurations.
   */
  public ConfigManager mConfigurations;
  /**
   * The App buttons.
   */
  public List<ButtonContainer> appButtons;

  public List<ButtonContainer> sigButtons;

  public BaseTextReport txtReport;

  /**
   * Create base activity helper.
   *
   * @param mainActivity the main activity
   * @return the base activity helper
   */
  public static BaseActivityHelper Create(Activity mainActivity) {
    return new BaseActivityHelper(mainActivity);
  }

  /**
   * Sets enable lunch buttons.
   *
   * @param flag the flag
   */
  public static void setEnableLunchButtons(boolean flag) {
    for (ButtonContainer bc : singleToneIns.appButtons) {
      //if (BtnsPredicates.isLunchBtn((GenericBtnActionImpl) bc.getAction())) {
      bc.setEnabledFlag(flag);
      // }
    }
  }

  /**
   * Instantiates a new Base activity helper.
   *
   * @param mainActivity the main activity
   */
  protected BaseActivityHelper(Activity mainActivity) {
    this.mActivity = mainActivity;
    this.mConfigurations = ConfigManager.CreateConfigManager(mActivity);
    construcAppButtons();
    if (singleToneIns == null) {
      singleToneIns = this;
    }

    construcSignalButtons();
    //createTxtArea();
  }

  private void construcAppButtons() {
    this.appButtons = new ArrayList<ButtonContainer>();
    List<String> btnsList = this.mConfigurations.getButtons();
    if (btnsList == null)
      return;

    for (String btnName : btnsList) {
      int btnId = mActivity.getResources().getIdentifier(btnName, "id",
                                                         mActivity
                                                               .getPackageName());
      String actionName = mConfigurations.GetButtonAction(btnName);
      ButtonContainer btnHolder =
            ButtonContainer.Create(mActivity, btnId, actionName,
                                   mConfigurations.GetButtonLabel(btnName));
      SetButnAction(btnHolder, this);
      btnHolder.setClickListener();
      this.appButtons.add(btnHolder);
    }
  }

  private void construcSignalButtons() {
    this.sigButtons = new ArrayList<ButtonContainer>();
    List<String> btnsList = this.mConfigurations.getSignalButtons();
    if (btnsList == null)
      return;

    for (String btnName : btnsList) {
      int btnId = mActivity.getResources().getIdentifier(btnName, "id",
                                                         mActivity
                                                               .getPackageName());
      String actionName = mConfigurations.GetButtonAction(btnName);
      ButtonContainer btnHolder =
            ButtonContainer.Create(mActivity, btnId, actionName,
                                   mConfigurations.GetButtonLabel(btnName));
      SetButnAction(btnHolder, mConfigurations.GetButtonSignal(btnName));
      btnHolder.setClickListener();
      this.sigButtons.add(btnHolder);
    }
  }

  private void SetButnAction(ButtonContainer btnContainer, Object data) {
    try {
      Class<? extends GenericBtnActionImpl> clzz =
            ((Class<? extends GenericBtnActionImpl>) Class
                  .forName(btnContainer.getActionName()));
      Constructor<? extends GenericBtnActionImpl> constr =
            clzz.getConstructor(Object.class, ButtonContainer.class);
      btnContainer.setAction(constr.newInstance(data, btnContainer));
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void resetButnAction(ButtonContainer btnContainer, Object data) {
    try {
      Class<? extends GenericBtnActionImpl> clzz =
            ((Class<? extends GenericBtnActionImpl>) Class
                  .forName(btnContainer.getActionName()));
      Constructor<? extends GenericBtnActionImpl> constr =
            clzz.getConstructor(Object.class, ButtonContainer.class);
      btnContainer.setAction(constr.newInstance(data, btnContainer));
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets optimized dex path.
   *
   * @return the optimized dex path
   */
  public File getOptimizedDexPath() {
    File dexDir = mActivity.getApplicationContext().getDir("dex", 0);
    return dexDir;
  }

  /**
   * Gets class loader.
   *
   * @return the class loader
   */
  public ClassLoader getClassLoader() {
    return mActivity.getClassLoader();
  }

  /**
   * Gets all app names.
   *
   * @return the all app names
   */
  public List<String> getAllAppNames() {
    List<String> benchmarks = new ArrayList<String>();
    for (ButtonContainer bc : singleToneIns.appButtons) {
      //if (BtnsPredicates.isLunchBtn((GenericBtnActionImpl) bc.getAction())) {
      benchmarks.add(bc.getLabel());
      //}
    }
    return benchmarks;
  }

  /**
   * This method should be called to redirect the print statement in Java
   * applications to LogCat.
   *
   * @param benchTag is the tag used to mark the log messages coming from the
   *                 application
   */
  public static void setSystemOutputs(BaseBMHelper bmHelper, String benchTag) {
    singleToneIns.createTxtArea();

    LogTool errUI = LogTool.createSystemErrWithUI(benchTag, "System.err",
                                                  singleToneIns.mConfigurations
                                                        .isGuiEnabled(),
                                                  singleToneIns.txtReport
                                                 );
    LogTool outUI = LogTool.createSystemOutWithUI(benchTag, "System.out",
                                                  singleToneIns.mConfigurations
                                                        .isGuiEnabled(),
                                                  singleToneIns.txtReport
                                                 );

    bmHelper.setOutPrintStream(new PrintStream(outUI));

    bmHelper.setErrPrintStream(new PrintStream(errUI));

    System.setErr(bmHelper.getErrStream());
    System.setOut(bmHelper.getOutStream());

//    System.setErr(new PrintStream(
//        LogTool.createSystemWithUI(benchTag, "System.err",
//            singleToneIns.mConfigurations.isGuiEnabled(),
//            singleToneIns.txtReport
//        )));

//    System.setErr(new PrintStream(
//        LogTool.createSystemErr(benchTag)));

//    System.setOut(new PrintStream(
//        LogTool.createSystemWithUI(benchTag, "System.out",
//            singleToneIns.mConfigurations.isGuiEnabled(),
//            singleToneIns.txtReport
//        )));
//    System.setOut(new PrintStream(
//        LogTool.createSystemOut(benchTag)));
  }

  public TextView getReportingTextArea() {
    TextView tv = getTextViewByName("report_text_area");

    return tv;
  }

  public void createTxtArea() {
    if (mConfigurations.isGuiEnabled() && this.txtReport == null) {
      this.txtReport = new BaseTextReport(this);
      // Log.e("ess", "after AReat creation");
    }
  }

  public TextView getTextViewByName(String tvName) {
    int      tvId = getResourceIDbyName(tvName);
    TextView tv   = (TextView) mActivity.findViewById(tvId);

    return tv;
  }

  public String getPropValue(String propKey) {
    return mConfigurations.getConfigValue(propKey);
  }

  public int getResourceIDbyName(String resourceID) {
    int rId = mActivity.getResources().getIdentifier(resourceID, "id",
                                                     mActivity
                                                           .getPackageName());
    return rId;
  }
}