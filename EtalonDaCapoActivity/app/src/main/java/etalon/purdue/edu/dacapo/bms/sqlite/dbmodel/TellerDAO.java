package etalon.purdue.edu.dacapo.bms.sqlite.dbmodel;

/**
 * Created by hussein on 2/10/16.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import etalon.purdue.edu.dacapoactivity.R;


/**
 * Created by hussein on 2/10/16.
 */
public class TellerDAO {
  interface Table {

    String COLUMN_ID = "Tid";
    String COLUMN_BID = "Bid";
    String COLUMN_TBALANCE = "Tbalance";
    String COLUMN_FILLER = "filler";
  }



  private SQLiteDatabase mDatabase;
  private Context mContext;

  public TellerDAO(SQLiteDatabase database, Context context) {
    mDatabase = database;
    mContext = context;
  }

  public static String getCreateTable(Context context) {
    return context.getString(R.string.create_table_teller);
  }

  public static String getDropTable(Context context) {
    return context.getString(R.string.drop_table_teller);
  }

  public static String getDeleteAllTable(Context context) {
    return context.getString(R.string.delete_all_tellers);
  }

  public void deleteAll() {
    mDatabase.execSQL(mContext.getString(R.string.delete_all_tellers));
  }

  public void insert(List<TellerEntity> tellerList) {

    for (TellerEntity teller : tellerList) {
      String[] bindArgs = {
              String.valueOf(teller.getmTid()),
              String.valueOf(teller.getmBid())
      };
      mDatabase.execSQL(mContext.getString(R.string.insert_teller), bindArgs);
    }
  }






  public List<TellerEntity> selectAll() {
    Cursor cursor = mDatabase.rawQuery(mContext.getString(R.string.select_all_tellers), null);

    List<TellerEntity> dataList = manageCursor(cursor);

    closeCursor(cursor);

    return dataList;
  }

  protected TellerEntity cursorToData(Cursor cursor) {
    int idIndex = cursor.getColumnIndex(Table.COLUMN_ID);
    int bidIndex = cursor.getColumnIndex(Table.COLUMN_BID);
    int tbalanceIndex = cursor.getColumnIndex(Table.COLUMN_TBALANCE);
    int fillerIndex = cursor.getColumnIndex(Table.COLUMN_FILLER);

    TellerEntity user = new TellerEntity();
    user.setmTid(cursor.getLong(idIndex));
    user.setmBid(cursor.getLong(bidIndex));
    user.setmTbalance(cursor.getInt(tbalanceIndex));
    user.setFiller(cursor.getString(fillerIndex));
    return user;
  }

  protected void closeCursor(Cursor cursor) {
    if (cursor != null) {
      cursor.close();
    }
  }

  protected List<TellerEntity> manageCursor(Cursor cursor) {
    List<TellerEntity> dataList = new ArrayList<TellerEntity>();

    if (cursor != null) {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        TellerEntity user = cursorToData(cursor);
        dataList.add(user);
        cursor.moveToNext();
      }
    }
    return dataList;
  }

  public void updateTellerBalanceByID(long tid, int balance) {
    String[] bindArgs = {
            String.valueOf(balance),
            String.valueOf(tid)
    };
    mDatabase.execSQL(mContext.getString(R.string.update_teller_balance_by_tid), bindArgs);
  }
}

