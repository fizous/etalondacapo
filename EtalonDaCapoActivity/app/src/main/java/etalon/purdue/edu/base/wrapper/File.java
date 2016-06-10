/**
 * 
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
