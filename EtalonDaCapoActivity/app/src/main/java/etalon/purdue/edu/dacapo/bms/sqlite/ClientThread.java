package etalon.purdue.edu.dacapo.bms.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.AccountDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.AccountEntity;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.BrancheDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.HistoryDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.HistoryEntity;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.TellerDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.utils.DataBaseManager;
import etalon.purdue.edu.dacapo.bms.sqlite.utils.QueryExecutor;

/**
 * Created by hussein on 2/9/16.
 */
class ClientThread extends Thread {

  int               ntrans = 0;
  Context mContext;

  public void insertIntoHistory(final long tID, final long bid, final long aId, final int delta) {
    DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
      public void run(SQLiteDatabase database) {
        HistoryDAO dao = new HistoryDAO(database, mContext);

        HistoryEntity hE = new HistoryEntity();
        hE.setmTid(tID);
        hE.setmBid(bid);
        hE.setmAid(aId);
        hE.setmDelta(delta);
        dao.insert(hE);
      }
    });

  }


  public void updateTellersBalanceByTId(final long tID, final int bbalance) {
    DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
      public void run(SQLiteDatabase database) {
        TellerDAO dao = new TellerDAO(database, mContext);

        dao.updateTellerBalanceByID(tID, bbalance);
      }
    });

  }

  public void updatebrancheBlanaceById(final long brnchID, final int bbalance) {
    DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
      public void run(SQLiteDatabase database) {
        BrancheDAO dao = new BrancheDAO(database, mContext);

        dao.updateBranchBalanceByID(brnchID, bbalance);
      }
    });

  }

  public void updateAccountBalance(final long accountID, final int abalance) {
    DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
      public void run(SQLiteDatabase database) {
        AccountDAO dao = new AccountDAO(database, mContext);

        dao.updateAccountBalanceByID(accountID, abalance);
      }
    });

  }

  public int selectAccountBalanceById(final long accountID) {
    final List<AccountEntity> accountsList = new ArrayList<AccountEntity>();
    DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
      public void run(SQLiteDatabase database) {
        AccountDAO dao = new AccountDAO(database, mContext);

        List<AccountEntity> accounts = dao.selectAccountByID(accountID);
        accountsList.addAll(accounts);
      }
    });
    if(!accountsList.isEmpty()) {
      return accountsList.get(0).getmAbalance();
    }
    return 0;
  }

  public ClientThread(int number_of_txns, String url, String user,
                      String password, Context ctxt) {
    mContext = ctxt;
    ntrans = number_of_txns;
  }

  public void run() {

    SqlitePseudoBenchmark.runningClients++;
    while (ntrans-- > 0) {
      int account = SqlitePseudoBenchmark.getRandomID(SqlitePseudoBenchmark.ACCOUNT);
      int branch  = SqlitePseudoBenchmark.getRandomID(SqlitePseudoBenchmark.BRANCH);
      int teller  = SqlitePseudoBenchmark.getRandomID(SqlitePseudoBenchmark.TELLER);
      int delta   = SqlitePseudoBenchmark.getRandomInt(0, 1000);

      doOne(branch, teller, account, delta);
      SqlitePseudoBenchmark.instant.incrementTransactionCount();
    }

    SqlitePseudoBenchmark.runningClients--;
  }

  /*
   **  doOne() - Executes a single TPC BM B transaction.
   */
  int doOne(int bid, int tid, int aid, int delta) {

    int aBalance = 0;

    updateAccountBalance(aid, delta);

    aBalance = selectAccountBalanceById(aid);
    updateTellersBalanceByTId(tid, delta);
    updatebrancheBlanaceById(bid, delta);
    insertIntoHistory(tid, bid, aid, delta);

    return aBalance;
  }    /* end of DoOne         */
}    /* end of class ClientThread */
