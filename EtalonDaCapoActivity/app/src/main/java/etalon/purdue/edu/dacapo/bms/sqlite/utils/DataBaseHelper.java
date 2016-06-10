package etalon.purdue.edu.dacapo.bms.sqlite.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.AccountDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.BrancheDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.HistoryDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.TellerDAO;

/**
 * Created by hussein on 2/9/16.
 */
public class DataBaseHelper extends SQLiteOpenHelper {


  public static final String DATABASE_NAME = "sqlitedacapo";
  public static final int DATABASE_VERSION = 1;
  private Context mContext;


  private static DataBaseHelper helperInstant = null;


  public static synchronized DataBaseHelper CreateDatabaseHelper(Context context) {
    if(helperInstant == null) {
      helperInstant = new DataBaseHelper(context);
    }
    return helperInstant;
  }


  public static synchronized boolean ShutdownDatabaseHelper() {
    if(helperInstant == null) {
      return true;
    }
    helperInstant = null;
    return true;
  }

  private DataBaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    mContext = context;
  }


  private void createDBTables(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(BrancheDAO.getCreateTable(mContext));
    sqLiteDatabase.execSQL(TellerDAO.getCreateTable(mContext));
    sqLiteDatabase.execSQL(AccountDAO.getCreateTable(mContext));
    sqLiteDatabase.execSQL(HistoryDAO.getCreateTable(mContext));
  }



  private void dropDBTables(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(HistoryDAO.getDropTable(mContext));
    sqLiteDatabase.execSQL(AccountDAO.getDropTable(mContext));
    sqLiteDatabase.execSQL(TellerDAO.getDropTable(mContext));
    sqLiteDatabase.execSQL(BrancheDAO.getDropTable(mContext));
  }

  public void createTables() {
    try {
    //  System.err.println("start creating from tables");
      DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
        public void run(SQLiteDatabase database) {
////          StringBuffer queryString = new StringBuffer();
////          queryString.append(BrancheDAO.getCreateTable(mContext));
////          queryString.append("\n");
////          queryString.append(TellerDAO.getCreateTable(mContext));
////          queryString.append("\n");
////          queryString.append(AccountDAO.getCreateTable(mContext));
////          queryString.append("\n");
////          queryString.append(HistoryDAO.getCreateTable(mContext));
////          queryString.append("\n");
////          database.execSQL(queryString.toString());
//
//         // System.out.println("creating query---------\n" + queryString.toString());

          createDBTables(database);
        }
      });
     // System.err.println("done creating tables");
    } catch (SQLiteException e) {
      e.printStackTrace();
    }

  }

  public void dropTables() {
    try {
     // System.err.println("start dropping tables");
      DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
        public void run(SQLiteDatabase database) {
//          StringBuffer queryString = new StringBuffer();
//          queryString.append(HistoryDAO.getDropTable(mContext));
//          queryString.append("\n");
//          queryString.append(AccountDAO.getDropTable(mContext));
//          queryString.append("\n");
//          queryString.append(TellerDAO.getDropTable(mContext));
//          queryString.append("\n");
//          queryString.append(BrancheDAO.getDropTable(mContext));
//          queryString.append("\n");
//          database.execSQL(queryString.toString());
//          System.out.println("dropping query=====\n" + queryString.toString());

          dropDBTables(database);
        }
      });
      //System.err.println("done dropping tables");
    } catch (SQLiteException e){
      e.printStackTrace();
    }
  }
  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    // create all tables
   // System.err.println("onCreate SqlHelper");
    createDBTables(sqLiteDatabase);
    //sqLiteDatabase.execSQL(UserDAO.getCreateTable(mContext));
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    if (newVersion > oldVersion) {
      // drop all tables
     // sqLiteDatabase.execSQL(UserDAO.getDropTable(mContext));
      //re-create all tables
      dropDBTables(sqLiteDatabase);
      onCreate(sqLiteDatabase);
    }
  }
}
