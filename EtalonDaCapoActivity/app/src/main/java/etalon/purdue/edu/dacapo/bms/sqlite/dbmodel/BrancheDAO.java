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
package etalon.purdue.edu.dacapo.bms.sqlite.dbmodel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import etalon.purdue.edu.dacapoactivity.R;


/**
 * Created by hussein on 2/10/16.
 */
public class BrancheDAO {

  interface Table {

    String COLUMN_BID = "Bid";
    String COLUMN_BBALANCE = "Bbalance";
    String COLUMN_FILLER = "filler";
  }

  private SQLiteDatabase mDatabase;
  private Context mContext;

  public BrancheDAO(SQLiteDatabase database, Context context) {
    mDatabase = database;
    mContext = context;
  }

  public static String getCreateTable(Context context) {
    return context.getString(R.string.create_table_branche);
  }

  public static String getDropTable(Context context) {
    return context.getString(R.string.drop_table_branche);
  }


  public static String getDeleteAllTable(Context context) {
    return context.getString(R.string.delete_all_branche);
  }

  public void insert(List<BranchEntity> brnchList) {

    for (BranchEntity branch : brnchList) {
      String[] bindArgs = {
              String.valueOf(branch.getmBid())
      };
      mDatabase.execSQL(mContext.getString(R.string.insert_branche), bindArgs);
    }
  }

  public void insert(BranchEntity branch) {
    String[] bindArgs = {
            String.valueOf(branch.getmBid())
    };
    mDatabase.execSQL(mContext.getString(R.string.insert_branche), bindArgs);
  }

  public void updateBranchBalanceByID(long bid, int balance) {
    String[] bindArgs = {
            String.valueOf(balance),
            String.valueOf(bid)
    };
    mDatabase.execSQL(mContext.getString(R.string.update_account_by_aid), bindArgs);
  }
}
