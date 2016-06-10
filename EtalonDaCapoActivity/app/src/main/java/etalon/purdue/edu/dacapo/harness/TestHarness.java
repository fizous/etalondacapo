/*
 * Copyright (c) 2006, 2009 The Australian National University.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0.
 * You may obtain the license at
 * 
 *    http://www.opensource.org/licenses/apache2.0.php
 */
package etalon.purdue.edu.dacapo.harness;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import etalon.purdue.edu.dacapo.parser.Config;


/**
 * Main class for the Dacapo benchmark suite. Locates the configuration file for
 * the specified benchmark, interprets command line arguments, and invokes the
 * benchmark-specific harness class.
 * 
 * @date $Date: 2009-12-24 11:19:36 +1100 (Thu, 24 Dec 2009) $
 * @id $Id: TestHarness.java 738 2009-12-24 00:19:36Z steveb-oss $
 */
public class TestHarness {

  public static final String BUILD_NICKNAME = "Specification-Version";
  public static final String BUILD_VERSION = "Implementation-Version";

  // these hold the build nick name and version strings respectively
  private String BuildNickName;
  private String BuildVersion;

  private Config config;
//  private commandLineArgs;

  public String getBuildNickName() {
    return BuildNickName;
  }

  public String getBuildVersion() {
    return BuildVersion;
  }

  /**
   * Calculates coefficient of variation of a set of longs (standard deviation
   * divided by mean).
   * 
   * @param times Array of input values
   * @return Coefficient of variation
   */
  public double coeff_of_var(long[] times) {
    double n = times.length;
    double sum = 0.0;
    double sum2 = 0.0;

    for (int i = 0; i < times.length; i++) {
      double x = times[i];
      sum += x;
      sum2 += x * x;
    }

    double mean = sum / n;
    double sigma = Math.sqrt(1.0 / n * sum2 - mean * mean);

    return sigma / mean;
  }
  
  private CommandLineArgs createCmdArgs(String[] args){
  	try {
	    return new CommandLineArgs(args);
    } catch (Exception e) {
	    
	    e.printStackTrace();
    }
  	return null;
  }

  public void runHarness(String[] args, PrintStream outSave, PrintStream errSave) {
    // force the locale so that we don't have any character set issues
    // when generating output for the digests.
    Locale.setDefault(new Locale("en", "AU"));

    /* All benchmarks run headless */
    System.setProperty("java.awt.headless", "true");

    try {
    	CommandLineArgs commandLineArgs = createCmdArgs(args);

      File scratch = new etalon.purdue.edu.base.wrapper.File(commandLineArgs.getScratchDir());
      makeCleanScratch(scratch);

      // this is not right
      Benchmark.setCommandLineOptions(commandLineArgs);
      try {
        Config.setThreadCountOverride(Integer.parseInt(commandLineArgs.getThreadCount()));
      } catch (RuntimeException re) {
      }

      // now get the benchmark names and run them
      for (String bm : commandLineArgs.benchmarks()) {
        // check if it is a benchmark name
        // name of file containing configurations
        InputStream ins = null;
        if (commandLineArgs.getCnfOverride() == null) {
          String cnf = "cnf/" + bm + ".cnf";
          ins = new etalon.purdue.edu.base.wrapper.FileInputStream(cnf);
          //ins = TestHarness.class.getClassLoader().getResourceAsStream(cnf);
//          if (ins == null) {
//            System.err.println("Unknown benchmark: " + bm);
//            System.exit(20);
//          }
        } else {
          String cnf = commandLineArgs.getCnfOverride();
          try {
            ins = new FileInputStream(cnf);
          } catch (FileNotFoundException e) {
            System.err.println("Count not find cnf file: '" + cnf + "'");
            System.exit(20);
          }
        }

        setConfig(ins);

        String size = commandLineArgs.getSize();

        int factor = 0;
        int limit = config.getThreadLimit(size);

        try {
          factor = Integer.parseInt(commandLineArgs.getThreadFactor());
          if (0 < factor && config.getThreadModel() == Config.ThreadModel.PER_CPU)
            config.setThreadFactor(size, factor);
        } catch (RuntimeException re) {
        }

        if (!isValidSize(size)) {
          System.err.println("No configuration size, " + size + ", for benchmark " + bm + ".");
        } else if (factor != 0 && config.getThreadModel() != Config.ThreadModel.PER_CPU) {
          System.err.println("Can only set the thread factor for per_cpu configurable benchmarks");
        } else if (!isValidThreadCount(size) && (config.getThreadCountOverride() > 0 || factor > 0)) {
          System.err.println("The specified number of threads (" + config.getThreadCount(size) + ") is outside the range [1,"
              + (limit == 0 ? "unlimited" : "" + limit) + "]");
        } else if (commandLineArgs.getInformation()) {
           bmInfo(size);
        } else if (commandLineArgs.getSizes()) {
          bmSizes();
        } else {
          if (!isValidThreadCount(size)) {
            System.err.println("The derived number of threads (" + config.getThreadCount(size) + ") is outside the range [1,"
                + (limit == 0 ? "unlimited" : "" + limit) + "]; rescaling to match thread limit.");
            config.setThreadCountOverride(config.getThreadLimit(size));
          }

          dump(commandLineArgs, commandLineArgs.getVerbose());
          runBenchmark(scratch, bm, this, commandLineArgs, outSave, errSave);
       
        }
        commandLineArgs = null; 
      }
    } catch (Exception e) {
      System.err.println(e);
      e.printStackTrace();
      System.exit(-1);
    }
  }

  public void makeCleanScratch(File scratch) {
    rmdir(scratch);
    scratch.mkdir();
  }

  private boolean isValidSize(String size) {
    return size != null && config.getSizes().contains(size);
  }

  private boolean isValidThreadCount(String size) {
    return config.getThreadLimit(size) == 0 || config.getThreadCount(size) <= config.getThreadLimit(size);
  }

  /**
   * @param scratch
   * @param bm
   * @param harness
   * @throws NoSuchMethodException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws Exception
   */
  private  void runBenchmark(File scratch, String bm, TestHarness harness,
                             CommandLineArgs commandLineArgs, PrintStream saveOut,
                             PrintStream saveErr)
                    throws NoSuchMethodException, InstantiationException, IllegalAccessException,
      InvocationTargetException, Exception {
    harness.config.printThreadModel(saveOut, commandLineArgs.getSize(), commandLineArgs.getVerbose());

    Constructor<?> cons = harness.findClass().getConstructor(new Class[] { Config.class, File.class, PrintStream.class, PrintStream.class });

    Benchmark b = (Benchmark) cons.newInstance(new Object[] { harness.config, scratch, saveOut, saveErr });

    boolean valid = true;
    Callback callback = commandLineArgs.getCallback();
    callback.init(harness.config);

    do {
      valid = b.run(callback, commandLineArgs.getSize(), this) && valid;
    } while (callback.runAgain(this));
    b.cleanup();

    if (!valid) {
      System.err.println("Validation FAILED for " + bm + " " + commandLineArgs.getSize());
      if (!commandLineArgs.getIgnoreValidation())
        System.exit(-2);
    }
  }

  private  void rmdir(File dir) {
    String[] files = dir.list();
    if (files != null) {
      for (int f = 0; f < files.length; f++) {
        File file = new File(dir, files[f]);
        if (file.isDirectory()) {
          rmdir(file);
          file.delete();
        }
        else {
          Benchmark.deleteFile(file);
        }
       // if (!file.delete())
      //    System.err.println("Could not delete " + files[f]);
      }
    }
  }

  private void bmInfo(String size) {
    config.describe(System.err, size);
  }

  private void bmSizes() {
    config.describeSizes(System.err);
  }

  private void dump(CommandLineArgs commandLineArgs, boolean verbose) {
    if (verbose) {
      System.err.println("Class name: " + config.className);

      System.err.println("Configurations:");
      config.describe(System.err, commandLineArgs.getSize());
    }
  }
  
  private void setConfig(InputStream stream){
    config = Config.parse(stream);
    if (config == null)
      System.exit(-1);
  }

  public TestHarness() {

  }

  private Class<?> findClass() {
    try {
      return Class.forName(config.className);
    } catch (ClassNotFoundException e) {
      System.err.println(e);
      e.printStackTrace();
      System.exit(-1);
      return null; // not reached
    }
  }

  {
    try {
      JarFile jarFile = new JarFile(new File(TestHarness.class.getProtectionDomain().getCodeSource().getLocation().getFile()));

      Manifest manifest = jarFile.getManifest();
      Attributes attributes = manifest.getMainAttributes();

      String nickname = attributes.get(new Attributes.Name(BUILD_NICKNAME)).toString();
      String version = attributes.get(new Attributes.Name(BUILD_VERSION)).toString();

      BuildNickName = nickname;
      BuildVersion = version;

      jarFile.close();
    } catch (Exception e) {
      BuildNickName = "Unknown";
      BuildVersion = "unknown";
    }
  }
}