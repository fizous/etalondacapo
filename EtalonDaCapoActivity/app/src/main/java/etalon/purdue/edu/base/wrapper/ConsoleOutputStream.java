package etalon.purdue.edu.base.wrapper;

import java.io.IOException;

/**
 * Created by hussein on 5/31/16.
 */
public class ConsoleOutputStream extends java.io.OutputStream {

  StringBuffer lineBuffer = new StringBuffer(256);

  @Override
  public void write(int oneByte) throws IOException {

		/*
		 * Just let old fashoned ASCII through
		 */
    if ((oneByte >= 0x20 && oneByte <= 0x7f) || oneByte == '\t' || oneByte == '\n') {
      lineBuffer.append((char)oneByte);
      if (oneByte == '\n') {
        flush();
      }
    }
  }

  /**
   * Writes an array of bytes.
   * @param b	the data to be written
   * @exception IOException If an I/O error has occurred.
   */
  public void write(byte b[]) throws java.io.IOException {
    write(b, 0, b.length);
  }

  /**
   * Writes a subarray of bytes.
   * @param b	the data to be written
   * @param off the start offset in the data
   * @param len the number of bytes that are written
   * @exception IOException If an I/O error has occurred.
   */
  public void write(byte b[], int off, int len) throws java.io.IOException {
    for (int i = 0 ; i < len ; i++) {
      write(b[off + i]);
    }
  }

  /**
   * Flushes the stream. This will write any buffered
   * output bytes.
   * @exception IOException If an I/O error has occurred.
   */
  public void flush() throws java.io.IOException {
   // Context.appendWindow(lineBuffer.toString());
    lineBuffer.delete(0, lineBuffer.length());
  }

}
