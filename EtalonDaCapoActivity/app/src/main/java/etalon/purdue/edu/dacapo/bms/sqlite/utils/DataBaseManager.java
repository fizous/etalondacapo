/*
 * The MIT License (MIT)
 * Copyright (c) 2014 Danylyk Dmytro
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 */
package etalon.purdue.edu.dacapo.bms.sqlite.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.AtomicInteger;
//

/**
 * Created by hussein on 2/9/16.
 */
public class DataBaseManager {
  private AtomicInteger mOpenCounter = new AtomicInteger();

  private static DataBaseManager instance;
  private SQLiteOpenHelper mDatabaseHelper;
  private SQLiteDatabase mDatabase;

  private DataBaseManager(SQLiteOpenHelper helper) {
    mDatabaseHelper = helper;
  }

  public static synchronized void initializeInstance(Context context) {
    if (instance == null) {
      //System.err.println("INITIALIZING INSTANCE");
      SQLiteOpenHelper helper = DataBaseHelper.CreateDatabaseHelper(context);
      instance = new DataBaseManager(helper);
    }
  }

  public static synchronized DataBaseManager getInstance() {
    if (instance == null) {
      throw new IllegalStateException(DataBaseManager.class.getSimpleName() +
              " is not initialized, call initializeInstance(..) method first.");
    }

    return instance;
  }


  public static synchronized boolean shutdownInstance() {
    if (instance == null) {
      throw new IllegalStateException(DataBaseManager.class.getSimpleName() +
              " is not initialized, call initializeInstance(..) method first.");
    }
    if(instance.mOpenCounter.get() > 0) {
      return false;
    }

    boolean result = DataBaseHelper.ShutdownDatabaseHelper();
    instance = null;
    return result;
  }

  private synchronized SQLiteDatabase openDatabase() {
    if (mOpenCounter.incrementAndGet() == 1) {
      // Opening new database
      mDatabase = mDatabaseHelper.getWritableDatabase();
    }
    //System.err.println("Database open counter: " + mOpenCounter.get());
    return mDatabase;
  }

  private synchronized void closeDatabase() {
    if (mOpenCounter.decrementAndGet() == 0) {
      // Closing database
      mDatabase.close();

    }
    //System.err.println("Database open counter: " + mOpenCounter.get());
  }

  public void executeQuery(QueryExecutor executor) {
    SQLiteDatabase database = openDatabase();

    try {
      database.beginTransaction();
      executor.run(database);
      database.setTransactionSuccessful();
    } catch(Exception e) {
      e.printStackTrace();
    } finally {
      database.endTransaction();
    }
    closeDatabase();
  }

  public void executeQueryTask(final QueryExecutor executor) {
    new Thread(new Runnable() {
      public void run() {
        SQLiteDatabase database = openDatabase();
        executor.run(database);
        closeDatabase();
      }
    }).start();
  }

  public void dropTables() {
    ((DataBaseHelper) mDatabaseHelper).dropTables();
  }

  public void createTables() {
    ((DataBaseHelper) mDatabaseHelper).createTables();
  }


}
