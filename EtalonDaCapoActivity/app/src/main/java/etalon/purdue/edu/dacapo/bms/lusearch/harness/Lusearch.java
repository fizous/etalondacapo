/*
 * Copyright (c) 2005, 2009 The Australian National University.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0.
 * You may obtain the license at
 * 
 *    http://www.opensource.org/licenses/apache2.0.php
 */
package etalon.purdue.edu.dacapo.bms.lusearch.harness;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Constructor;

import etalon.purdue.edu.dacapo.harness.Benchmark;
import etalon.purdue.edu.dacapo.parser.Config;

/**
 * @date $Date: 2009-12-24 11:19:36 +1100 (Thu, 24 Dec 2009) $
 * @id $Id: Lusearch.java 738 2009-12-24 00:19:36Z steveb-oss $
 */
public class Lusearch extends Benchmark {
  private final Object benchmark;

  public Lusearch(Config config, File scratch, PrintStream saveOut,
                  PrintStream saveErr) throws Exception {
    super(config, scratch, false, saveOut,
        saveErr);
    Class<?> clazz = Class.forName("purdue.dacapo.bms.lusearch.Search", true, loader);
    this.method = clazz.getMethod("main", String[].class);
    Constructor<?> cons = clazz.getConstructor();
    useBenchmarkClassLoader();
    try {
      benchmark = cons.newInstance();
    } finally {
      revertClassLoader();
    }
  }

  @Override
  public void iterate(String size) throws Exception {
    method.invoke(benchmark, (Object) (config.preprocessArgs(size, scratch)));
  }
}
