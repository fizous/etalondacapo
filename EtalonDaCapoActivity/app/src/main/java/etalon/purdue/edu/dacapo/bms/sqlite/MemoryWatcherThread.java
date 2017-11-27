/*
 * Copyright (c) 2005, 2009 The Australian National University.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0.
 * You may obtain the license at
 *
 *    http://www.opensource.org/licenses/apache2.0.php
 *
 */
package etalon.purdue.edu.dacapo.bms.sqlite;

/**
 * Created by hussein on 2/9/16.
 */
class MemoryWatcherThread extends Thread {

  long    min          = 0;
  long    max          = 0;
  boolean keep_running = true;
  boolean verbose = true;

  public MemoryWatcherThread() {

    this.reset();

    keep_running = true;
  }

  public void reset() {

    System.gc();

    long currentFree  = Runtime.getRuntime().freeMemory();
    long currentAlloc = Runtime.getRuntime().totalMemory();

    min = max = (currentAlloc - currentFree);
  }

  public void end() {
    keep_running = false;
  }

  public void run() {

    while (keep_running) {
      long currentFree  = Runtime.getRuntime().freeMemory();
      long currentAlloc = Runtime.getRuntime().totalMemory();
      long used         = currentAlloc - currentFree;

      if (used < min) {
        min = used;
      }

      if (used > max) {
        max = used;
      }

      try {
        sleep(100);
      } catch (InterruptedException E) {}
    }
  }
}    /* end of class MemoryWatcherThread */
