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

/**
 * Created by hussein on 5/26/16.
 */
public class BMThread extends Thread {

  /**
   * The Bm name.
   */
  String bmName;
  /**
   * The Bm helper.
   */
  protected BaseBMHelper bmHelper;
  /**
   * The Blinker.
   */
  protected volatile Thread blinker;


  /**
   * Instantiates a new Bm thread.
   *
   * @param bmName the bm name
   */
  public BMThread(String bmName) {
    super();
    this.bmName = bmName;
  }


  /**
   * Instantiates a new Bm thread.
   *
   * @param bmHelper the bm helper
   */
  public BMThread(BaseBMHelper bmHelper) {
    this(bmHelper.appName);
    this.bmHelper = bmHelper;
  }


  /**
   * check if the thread was stopped by another thread
   *
   * @throws InterruptedException the interrupted exception
   */
  protected void throwStoppedExc() throws InterruptedException {
    Thread.yield();
    if (blinker == null || Thread.currentThread().isInterrupted()) {
      throw new InterruptedException();
    }
  }

  /**
   * Pre run.
   *
   * @throws InterruptedException the interrupted exception
   */
  protected final void preRun() throws InterruptedException {
    blinker = Thread.currentThread();
    throwStoppedExc();
    hookingPreRun();
  }

  /**
   * Post run.
   *
   * @throws InterruptedException the interrupted exception
   */
  protected void postRun() throws InterruptedException {
    throwStoppedExc();
    blinker = null;
    hookingPostRun();
  }

  /**
   * Any child inheriting from this calss has to override this function which
   * represents the main execution of a thread
   */
  protected void executeTask() {
    try {
      throw new Exception("unimplemented code executeTask");
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Hooking pre run.
   */
  protected void hookingPreRun() {
    try {
      throw new Exception("unimplemented code hookingPreRun");
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Hooking post run.
   */
  protected void hookingPostRun() {
    try {
      throw new Exception("unimplemented code hookingPostRun");
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  public final void run() {
    try {
      preRun();
      executeTask();
      postRun();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
