package etalon.purdue.edu.base.ui;

import etalon.purdue.edu.base.BaseActivityHelper;

/**
 * Created by hussein on 5/26/16.
 */
public class LunchAppBtnAction extends GenericBtnActionImpl {


  /**
   * Instantiates a new Lunch app btn action.
   *
   * @param data         the data
   * @param btnContainer the btn container
   */
  public LunchAppBtnAction(Object data, ButtonContainer btnContainer) {
    super(data, btnContainer);
  }


  @Override
  protected void onPreExecute() {
    BaseActivityHelper.setEnableLunchButtons(false);

  }



  @Override
  protected void onPostExecute(Object result) {
    BaseActivityHelper.setEnableLunchButtons(true);
    this.finished = true;
  }






}
