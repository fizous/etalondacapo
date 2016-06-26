package etalon.purdue.edu.dacapo.bms.sqlite.dbmodel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import etalon.purdue.edu.dacapoactivity.R;

/**
 * Created by hussein on 2/10/16.
 */
public class AccountDAO {
  interface Table {

    String COLUMN_ID = "Aid";
    String COLUMN_BID = "Bid";
    String COLUMN_ABALANCE = "Abalance";
    String COLUMN_FILLER = "filler";
  }

  private SQLiteDatabase mDatabase;
  private Context mContext;

  public AccountDAO(SQLiteDatabase database, Context context) {
    mDatabase = database;
    mContext = context;
  }

  public static String getCreateTable(Context context) {
    return context.getString(R.string.create_table_account);
  }

  public static String getDropTable(Context context) {
    return context.getString(R.string.drop_table_account);
  }

  public static String getDeleteAllTable(Context context) {
    return context.getString(R.string.delete_all_accounts);
  }

  public void deleteAll() {
    mDatabase.execSQL(mContext.getString(R.string.delete_all_accounts));
  }

  public void insert(List<AccountEntity> userList) {

    for (AccountEntity acc : userList) {
      String[] bindArgs = {
            String.valueOf(acc.getmAid()),
            String.valueOf(acc.getmBid())
      };
      mDatabase.execSQL(mContext.getString(R.string.insert_account), bindArgs);
    }
  }

  public void insert(AccountEntity acc) {
    String[] bindArgs = {
          String.valueOf(acc.getmAid()),
          String.valueOf(acc.getmBid())
    };
    mDatabase.execSQL(mContext.getString(R.string.insert_account), bindArgs);
  }

  public void updateAccountBalanceByID(long bid, int balance) {
    String[] bindArgs = {
          String.valueOf(balance),
          String.valueOf(bid)
    };
    mDatabase.execSQL(mContext.getString(R.string.update_branch_balance_by_bid),
                      bindArgs);
  }

  public List<AccountEntity> selectAccountByID(long aid) {
    String[] selectionArgs = {
          String.valueOf(aid)
    };
    String query  = mContext.getString(R.string.select_account_by_aid);
    Cursor cursor = mDatabase.rawQuery(query, selectionArgs);

    List<AccountEntity> dataList = manageCursor(cursor);

    closeCursor(cursor);

    return dataList;
  }

  public List<AccountEntity> selectByAge(int age) {
    String[] selectionArgs = {
          String.valueOf(age)
    };
    String query  = mContext.getString(R.string.select_users_by_age);
    Cursor cursor = mDatabase.rawQuery(query, selectionArgs);

    List<AccountEntity> dataList = manageCursor(cursor);

    closeCursor(cursor);

    return dataList;
  }

  public List<AccountEntity> selectAll() {
    Cursor cursor = mDatabase
          .rawQuery(mContext.getString(R.string.select_all_users), null);

    List<AccountEntity> dataList = manageCursor(cursor);

    closeCursor(cursor);

    return dataList;
  }

  protected AccountEntity cursorToData(Cursor cursor) {
    int idIndex      = cursor.getColumnIndex(Table.COLUMN_ID);
    int bidIndex     = cursor.getColumnIndex(Table.COLUMN_BID);
    int ablanceIndex = cursor.getColumnIndex(Table.COLUMN_ABALANCE);
    int fillerIndex  = cursor.getColumnIndex(Table.COLUMN_FILLER);

    AccountEntity user = new AccountEntity();
    user.setmAid(cursor.getLong(idIndex));
    user.setmBid(cursor.getLong(bidIndex));
    user.setmAbalance(cursor.getInt(ablanceIndex));
    user.setFiller(cursor.getString(fillerIndex));

    return user;
  }

  protected void closeCursor(Cursor cursor) {
    if (cursor != null) {
      cursor.close();
    }
  }

  protected List<AccountEntity> manageCursor(Cursor cursor) {
    List<AccountEntity> dataList = new ArrayList<AccountEntity>();

    if (cursor != null) {
      cursor.moveToFirst();
      while (!cursor.isAfterLast()) {
        AccountEntity user = cursorToData(cursor);
        dataList.add(user);
        cursor.moveToNext();
      }
    }
    return dataList;
  }
}
