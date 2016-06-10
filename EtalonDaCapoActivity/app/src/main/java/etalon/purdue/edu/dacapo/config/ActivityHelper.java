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
