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
package etalon.purdue.edu.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/**
 * Created by hussein on 5/27/16.
 */
public class BaseBMHelper {
  /**
   * The App name.
   */
  public String appName;
  /**
   * The Properties.
   */
  public Properties properties;
  /**
   * The M activity helper.
   */
  public BaseActivityHelper mActivityHelper;

  /**
   * The Bm thread.
   */
  public BMThread bmThread;

  /**
   * The Ready.
   */
  public volatile boolean ready;

  /**
   * Sets bm thread.
   *
   * @param bmTh the bm th
   */
  public void setBmThread(BMThread bmTh) {
    this.bmThread = bmTh;
  }

  /**
   * Instantiates a new Base bm helper.
   *
   * @param aName          the a name
   * @param activityHelper the activity helper
   */
  public BaseBMHelper(String aName,
                      BaseActivityHelper activityHelper) {
    this.mActivityHelper = activityHelper;
    this.appName = aName;
    this.ready = false;
    this.properties = new Properties();
    try {
      File            file      =
            new File(mActivityHelper.mConfigurations.getAppConfFile(appName));
      FileInputStream fileInput = new FileInputStream(file);
      this.properties.loadFromXML(fileInput);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (InvalidPropertiesFormatException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets bm worload.
   *
   * @return the bm worload
   */
  public String getBmWorload() {
    String workload = properties.getProperty("workload");
    if (workload != null) {
      return workload;
    }
    return this.mActivityHelper.mConfigurations.getBenchmarkLoad();
  }

  /**
   * Gets tag.
   *
   * @return the tag
   */
  public String getTag() {
    return properties.getProperty("tag");
  }

  /**
   * Gets prop value.
   *
   * @param propKey the prop key
   * @return the prop value
   */
  public String getPropValue(String propKey) {
    return properties.getProperty(propKey);
  }

  /**
   * Gets iterations.
   *
   * @return the iterations
   */
  public int getIterations() {
    String iters = properties.getProperty("iterations");
    if (iters != null) {
      return Integer.parseInt(iters.trim());
    }
    return this.mActivityHelper.mConfigurations.getIterationsCount();
  }

  /**
   * Get bm args string [ ].
   *
   * @return the string [ ]
   */
  public String[] getBmArgs() {
    return null;
  }

  private PrintStream outPrintStream;
  private PrintStream errPrintStream;

  /**
   * Sets out print stream.
   *
   * @param outStream the out stream
   */
  public void setOutPrintStream(PrintStream outStream) {
    this.outPrintStream = outStream;
  }

  /**
   * Sets err print stream.
   *
   * @param errStream the err stream
   */
  public void setErrPrintStream(PrintStream errStream) {
    this.errPrintStream = errStream;
  }

  /**
   * Gets out stream.
   *
   * @return the out stream
   */
  public PrintStream getOutStream() {
    return outPrintStream;
  }

  /**
   * Gets err stream.
   *
   * @return the err stream
   */
  public PrintStream getErrStream() {
    return errPrintStream;
  }

}
