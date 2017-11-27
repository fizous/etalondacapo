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
package etalon.purdue.edu.base.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import etalon.purdue.edu.base.ui.BaseTextReport;

/**
 * Created by hussein on 5/27/16.
 */
public class LogTool extends OutputStream {
  /**
   * The Tag.
   */
  protected String tag;
  /**
   * The Name.
   */
  protected String name;

  /**
   * The Bos.
   */
  protected ByteArrayOutputStream bos = new ByteArrayOutputStream();

  /**
   * Instantiates a new Log tool.
   *
   * @param tag  the tag
   * @param name the name
   */
  protected LogTool(String tag, String name)   {
    this.tag = tag;
    this.name = name;
  }

  /**
   * Create system out log tool.
   *
   * @param tag the tag
   * @return the log tool
   */
  public static LogTool createSystemOut(String tag) {
    return new LogOut(tag, "System.out");
  }

  /**
   * Create system err log tool.
   *
   * @param tag the tag
   * @return the log tool
   */
  public static LogTool createSystemErr(String tag) {
    return new LogError(tag, "System.err");
  }

  /**
   * Create system out with ui log tool.
   *
   * @param tag      the tag
   * @param name     the name
   * @param allowUI  the allow ui
   * @param uiReport the ui report
   * @return the log tool
   */
  public static LogTool createSystemOutWithUI(String tag, String name,
                                              boolean allowUI,
                                              BaseTextReport uiReport) {
    return new LogUITextArea(tag, name, uiReport, allowUI);
  }


  /**
   * Create system err with ui log tool.
   *
   * @param tag      the tag
   * @param name     the name
   * @param allowUI  the allow ui
   * @param uiReport the ui report
   * @return the log tool
   */
  public static LogTool createSystemErrWithUI(String tag, String name,
                                           boolean allowUI,
                                           BaseTextReport uiReport) {
    return new LogUIErrTextArea(tag, name, uiReport, allowUI);
  }

  /**
   * Send log int.
   *
   * @param s the s
   * @return the int
   */
  protected int sendLog(String s){return 0;}

  /**
   * Send ui report int.
   *
   * @param s the s
   * @return the int
   */
  protected int sendUIReport(String s){return 0;}

  @Override
  public void write(int b) throws IOException {
    if (b == (int)'\n') {
      String s = new String(this.bos.toByteArray());
      sendLog(s);
      this.bos = new ByteArrayOutputStream();
    } else {
      this.bos.write(b);
    }
  }

  /**
   * Gets ex trace as string.
   *
   * @param exception the exception
   * @return the ex trace as string
   */
  public String getExTraceAsString(Exception exception) {
    StringWriter sw = new StringWriter();
    exception.printStackTrace(new PrintWriter(sw));
    return sw.toString();
  }

}
