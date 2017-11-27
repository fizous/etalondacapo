/*
 * Copyright (c) 2005, 2009 The Australian National University.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0.
 * You may obtain the license at
 *
 *    http://www.opensource.org/licenses/apache2.0.php
 *
 * Modifications copyright (C) 2011, 2018 Purdue University
 * Refactor the code to use sqlite android library
 */
package etalon.purdue.edu.dacapo.bms.sqlite.harness;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;

import etalon.purdue.edu.dacapo.harness.Benchmark;
import etalon.purdue.edu.dacapo.parser.Config;
/**
 * Created by hussein on 5/27/16.
 */
public class SqliteEtalon extends Benchmark {

  private final Object benchmark;

  public SqliteEtalon(Config config, File scratch, PrintStream saveOut,
                      PrintStream saveErr) throws Exception {
    super(config, scratch, false, saveOut,
       saveErr);
    //AndroidBenchmarkHelper.getStorageDirectories();

    Class<?> clazz = Class.forName("etalon.purdue.edu.dacapo.bms.sqlite.SqliteEtalonBenchmark", true, loader);
    this.method = clazz.getMethod("main", String[].class);
    Constructor<?> cons = clazz.getConstructor();

    useBenchmarkClassLoader();
    Object benchmarktemp = null;
    try {

      benchmarktemp = cons.newInstance();
    } catch(Exception e) {

      e.printStackTrace();
    } finally{
      benchmark = benchmarktemp;
      revertClassLoader();
    }
  }

  @Override
  protected void prepare() throws Exception {
//    String dataBaseFileName =
//    DataBaseManager.initializeInstance(new DataBaseHelper(AndroidBenchmarkHelper.getActivity().getApplicationContext()));
    unpackZipFileResource("dat/" + config.name + ".zip", scratch);
  }

  @Override
  public void iterate(String size) throws Exception {
    method.invoke(benchmark, (Object) (config.preprocessArgs(size, scratch)));
  }

}
