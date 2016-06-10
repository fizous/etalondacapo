package etalon.purdue.edu.base.ui;

/**
 * Created by hussein on 5/25/16.
 */
public interface IUIAction {
  /**
   * Main task execution object.
   *
   * @param arguments the arguments
   * @return the object
   */
  public Object mainTaskExecution(Object... arguments);

  /**
   * Sets action.
   *
   * @param obj the obj
   */
  public void setAction(Object obj);

  /**
   * Gets action data.
   *
   * @return the action data
   */
  public Object getActionData();
}
