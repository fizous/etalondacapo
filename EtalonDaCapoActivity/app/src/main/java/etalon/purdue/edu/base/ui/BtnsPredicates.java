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
