/**
 * 
 */
package etalon.purdue.edu.base.wrapper;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;

/**
 * @author hussein
 *
 */
public class FileInputStream extends java.io.FileInputStream {

	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public FileInputStream(File file) throws FileNotFoundException {
		super(file);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param fd
	 */
	public FileInputStream(FileDescriptor fd) {
		super(fd);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param path
	 * @throws FileNotFoundException
	 */
	public FileInputStream(String path) throws FileNotFoundException {
		super(etalon.purdue.edu.base.wrapper.File.getRootPath() + File.separator + path);
		// TODO Auto-generated constructor stub
	}

}
