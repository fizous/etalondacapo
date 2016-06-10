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
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;
import etalon.purdue.edu.dacapo.config.ActivityHelper;
import etalon.purdue.edu.dacapo.parser.Config;

/**
 * Custom class loader for the dacapo benchmarks. Instances of this classloader
 * are created by passing a list of jar files. This allows us to package a
 * benchmark as a set of jar files, rather than having to mix the classes for
 * all the benchmarks into the dacapo jar file.
 * 
 * @date $Date: 2009-12-24 11:19:36 +1100 (Thu, 24 Dec 2009) $
 * @id $Id: DacapoClassLoader.java 738 2009-12-24 00:19:36Z steveb-oss $
 */
public class DacapoClassLoader extends ClassLoader {

	 List<DexClassLoader> allDexLoaders;
  File[] definedJars;
	 
	 public DacapoClassLoader(File[] allJars) {
		 super(ClassLoader.getSystemClassLoader());
     definedJars = allJars;
	   File optDexOutputPath = ActivityHelper.singleToneIns.getOptimizedDexPath();
	   allDexLoaders = new ArrayList<DexClassLoader>();
	   int ind = 0;
	   ClassLoader parent = ActivityHelper.singleToneIns.getClassLoader();
	   for (File file : definedJars) {
	  	 //if(ind == 0)
	  		 
	  	 DexClassLoader dl = new DexClassLoader(file.getPath(), optDexOutputPath.getAbsolutePath(), null, parent);

	  	// parent = dl;
	  	 ind++;
	  	 allDexLoaders.add(dl);
	  	 parent = dl;

	   }
	}


  /**
   * Factory method to create the class loader to be used for each invocation of
   * this benchmark
   * 
   * @param config The config file, which contains information about the jars
   * this benchmark depends on
   * @param scratch The scratch directory (in which the jars will be located)
   * @return The class loader in which this benchmark's iterations should
   * execute.
   * @throws Exception
   */
  public static DacapoClassLoader create(Config config, File scratch) {
  	DacapoClassLoader rtn = null;
  	if(Benchmark.IGNORE_JARS){
      System.err.println("Benchmark ignore class path:");
  		rtn =  new DacapoClassLoader(ClassLoader.getSystemClassLoader()/*Thread.currentThread().getContextClassLoader()*/);
  		//rtn = new DacapoClassLoader(ClassLoader.getSystemClassLoader());
  	} else {
      File[] jars = getJars(config, scratch);
      if (Benchmark.getVerbose()) {
      	System.out.println("Benchmark classpath:");
      	for (File file : jars) {
      		System.out.println("  " + file.getAbsolutePath());
      	}
      }
      rtn = new DacapoClassLoader(jars);
  	}
    

    
//    try {
//      URL[] urls = getJars(config, scratch);
//      if (Benchmark.getVerbose()) {
//        System.out.println("Benchmark classpath:");
//        for (URL url : urls) {
//          System.out.println("  " + url.toString());
//        }
//      }
//      rtn = new DacapoClassLoader(urls, ClassLoader.getSystemClassLoader());
//    } catch (Exception e) {
//      System.err.println("Unable to create loader for " + config.name + ":");
//      e.printStackTrace();
//      System.exit(-1);
//    }
    return rtn;
  }
	
	/* the following method was working given that we do not do any dynamic loading */
//	public static DacapoClassLoader create(Config config, File scratch) {
//		DacapoClassLoader rtn = null;
//		rtn =  new DacapoClassLoader(Thread.currentThread().getContextClassLoader());
//		return rtn;
//	}
	
	public DacapoClassLoader(ClassLoader parent){
		super(parent);
	}


  /**
   * Get a list of jars (if any) which should be in the classpath for this
   * benchmark
   * 
   * @param config The config file for this benchmark, which lists the jars
   * @param scratch The scratch directory, in which the jars will be located
   * @return An array of URLs, one URL for each jar
   * @throws MalformedURLException
   */
  private static File[] getJars(Config config, File scratch)  {
    List<File> jars = new ArrayList<File>();
    File jardir = new File(scratch, "jar");
    if (config.jars != null) {
      for (int i = 0; i < config.jars.length; i++) {
//        System.err.println("jars....: " + config.jars[i]);
        File jar = new File(jardir, config.jars[i]);
        jars.add(jar);
      }
    }
    return jars.toArray(new File[jars.size()]);
  }

  /**
   * Reverse the logic of the default classloader, by trying the top-level
   * classes first. This way, libraries packaged with the benchmarks override
   * those provided by the runtime environment.
   */
  @Override
  protected synchronized Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
  	
  	
  	if(Benchmark.IGNORE_JARS){
      // First, check if the class has already been loaded
      Class<?> c = findLoadedClass(name);
      if (c == null) {
        try {

          // Next, try to resolve it from the dacapo JAR files
          c = super.findClass(name);
        } catch (ClassNotFoundException e) {
          // And if all else fails delegate to the parent.
          c = super.loadClass(name, resolve);
        }
      }
      if (resolve) {
        resolveClass(c);
      }
      return c;
  	} else {
      // First, check if the class has already been loaded
      Class<?> c = findLoadedClass(name);
      
      if (c == null) {
      	boolean found = false;
        try {

          // Next, try to resolve it from the dacapo JAR files
         // c = super.findClass(name);
        	int ind = allDexLoaders.size();
        	while(--ind >= 0 ){
        		DexClassLoader dexLoader = allDexLoaders.get(ind);
        		try {
        			c = dexLoader.loadClass(name);
        		} catch(ClassNotFoundException e) {
        			continue;
        		}
        		//if we reach here we know that the class was found
        		found = true;
        		break;	
        	}
        	if(!found) {
        		c = super.loadClass(name, resolve);
        	}
        } catch (ClassNotFoundException e) {
          // And if all else fails delegate to the parent.
          e.printStackTrace();
        }
      }
      if (resolve) {
        resolveClass(c);
      }
      return c;
  	}
  }
}




