package etalon.purdue.edu.base.ui;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

/**
 * Created by hussein on 5/25/16.
 */
public class ButtonContainer {

  private int id;
  private String actionName;
  private String label;
  private GenericBtnActionImpl action;
  //private Activity mActivity;

  private View andrBtn;


  /**
   * Gets action.
   *
   * @return the action
   */
  public IUIAction getAction() {
  return this.action;
  }

  /**
   * Create button container.
   *
   * @param activity   the activity
   * @param id         the id
   * @param actionName the action name
   * @return the button container
   */
  public static ButtonContainer Create(Activity activity, int id,
                                       String actionName) {
    return new ButtonContainer(activity, id, actionName);
  }

  /**
   * Create button container.
   *
   * @param activity   the activity
   * @param id         the id
   * @param actionName the action name
   * @param label      the label
   * @return the button container
   */
  public static ButtonContainer Create(Activity activity, int id,
                                       String actionName,
                                       String label) {
    return new ButtonContainer(activity, id, actionName, label);

  }


  /**
   * Sets click listener.
   */
  public void setClickListener() {
    andrBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        action.execute(action.getActionData());
      }
    });
  }

  /**
   * Set enabled flag.
   *
   * @param flag the flag
   */
  public void setEnabledFlag(boolean flag){
    ((Button)andrBtn).setEnabled(flag);
  }


  /**
   * Gets label.
   *
   * @return the label
   */
  public String getLabel() {
    return this.label;
  }

  /**
   * Gets action name.
   *
   * @return the action name
   */
  public String getActionName() {
    return actionName;
  }

  /**
   * Sets action.
   *
   * @param acshion the acshion
   */
  public void setAction(GenericBtnActionImpl acshion) {
    this.action =  acshion;
  }

  private ButtonContainer(Activity activity, int id, String actionName) {
    this.id = id;
    //this.mActivity = activity;
    this.actionName = actionName;
    this.andrBtn = activity.findViewById(id);
  }

  private ButtonContainer(Activity activity, int id, String actionName,
                          String label) {
    this(activity, id, actionName);
    this.label = label;

  }


}
