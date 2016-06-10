package etalon.purdue.edu.base.ui;

import java.lang.reflect.Field;

/**
 * Created by hussein on 5/27/16.
 */
public class SignalAppBtnAction extends GenericBtnActionImpl {

  private int mySignal;


  /**
   * Instantiates a new Signal app btn action.
   *
   * @param data         the data
   * @param btnContainer the btn container
   */
  public SignalAppBtnAction(Object data, ButtonContainer btnContainer) {
    super(data, btnContainer);

    try {
      Field f = android.os.Process.class.getField((String) this.data);
      Class<?> t = f.getType();
      if(t == int.class) {
        mySignal = f.getInt(null);
      }
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Object mainTaskExecution(Object... arguments) {
    android.os.Process.sendSignal(android.os.Process.myPid(), mySignal);
    return mySignal;
  }
}
