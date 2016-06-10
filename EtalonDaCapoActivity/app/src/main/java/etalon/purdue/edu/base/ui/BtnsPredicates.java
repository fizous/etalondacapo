package etalon.purdue.edu.base.ui;

import com.android.internal.util.Predicate;

/**
 * Created by hussein on 5/26/16.
 */
public class BtnsPredicates {


  static class LunchBtnsPredicate implements Predicate<GenericBtnActionImpl>  {
    @Override
    public boolean apply(GenericBtnActionImpl input) {
      return input.getClass().isAssignableFrom(LunchAppBtnAction.class);
    }
  }

  static LunchBtnsPredicate pre;

  static {
    pre = new LunchBtnsPredicate();
  }

  public static boolean isLunchBtn(GenericBtnActionImpl act) {
    return pre.apply(act);
  }
}
