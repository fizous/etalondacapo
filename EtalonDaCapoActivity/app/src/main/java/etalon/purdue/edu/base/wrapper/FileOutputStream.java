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
public class FileOutputStream extends java.io.FileOutputStream {

	/**
	 * @param file
	 * @throws FileNotFoundException
	 */
	public FileOutputStream(File file) throws FileNotFoundException {
		super(file);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param fd
	 */
	public FileOutputStream(FileDescriptor fd) {
		super(fd);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param path
	 * @throws FileNotFoundException
	 */
	public FileOutputStream(String path) throws FileNotFoundException {
		super(path);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param file
	 * @param append
	 * @throws FileNotFoundException
	 */
	public FileOutputStream(File file, boolean append)
	    throws FileNotFoundException {
		super(file, append);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param path
	 * @param append
	 * @throws FileNotFoundException
	 */
	public FileOutputStream(String path, boolean append)
	    throws FileNotFoundException {
		super(path, append);
		// TODO Auto-generated constructor stub
	}

}
