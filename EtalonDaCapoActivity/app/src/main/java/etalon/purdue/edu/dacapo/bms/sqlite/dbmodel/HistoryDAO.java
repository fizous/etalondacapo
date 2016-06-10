package etalon.purdue.edu.dacapo.bms.sqlite.dbmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import etalon.purdue.edu.dacapoactivity.R;


/**
 * Created by hussein on 2/10/16.
 */
public class HistoryDAO {
  interface Table {

    String COLUMN_ID = "Tid";
    String COLUMN_BID = "Bid";
    String COLUMN_AID = "Aid";
    String COLUMN_DELTA = "delta";
    String COLUMN_TSTIME = "tstime";
    String COLUMN_FILLER = "filler";
  }
  private SQLiteDatabase mDatabase;
  private Context mContext;

  public HistoryDAO(SQLiteDatabase database, Context context) {
    mDatabase = database;
    mContext = context;
  }

  public static String getCreateTable(Context context) {
    return context.getString(R.string.create_table_history);
  }

  public static String getDropTable(Context context) {
    return context.getString(R.string.drop_table_history);
  }

  public static String getDeleteAllTable(Context context) {
    return context.getString(R.string.delete_all_history);
  }


  public void insert(HistoryEntity hE) {
    String[] bindArgs = {
            String.valueOf(hE.getmTid()),
            String.valueOf(hE.getmBid()),
            String.valueOf(hE.getmAid()),
            String.valueOf(hE.getmDelta())
    };
    mDatabase.execSQL(mContext.getString(R.string.insert_history), bindArgs);
  }
}
