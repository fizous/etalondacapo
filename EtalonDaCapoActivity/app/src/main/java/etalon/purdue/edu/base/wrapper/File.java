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

import java.net.URI;

/**
 * @author hussein
 *
 */
public class File extends java.io.File {

	public static String rootPath = ".";
	
	public static void setRootPath(String currRootPath){
		synchronized ( rootPath ){
			rootPath = currRootPath;
		}
	}
	
	public static String getRootPath(){
		synchronized ( rootPath ){
			return rootPath;
		}
	}
	/**
	 * @param path
	 */
	public File(String path) {
		super(getRootPath()/*AndroidBenchmarkHelper.helperInstance.getBenchmarkRootPath()*/ + File.separator + path);
		
	}

	/**
	 * @param uri
	 */
	public File(URI uri) {
		super(uri);

	}

	/**
	 * @param dir
	 * @param name
	 */
	public File(java.io.File dir, String name) {
		super(dir, name);

	}

	/**
	 * @param dirPath
	 * @param name
	 */
	public File(String dirPath, String name) {
		super(dirPath, name);

	}

}
