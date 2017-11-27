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
