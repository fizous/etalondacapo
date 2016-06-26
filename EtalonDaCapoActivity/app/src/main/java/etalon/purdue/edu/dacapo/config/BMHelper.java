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
