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
