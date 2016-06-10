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
  //Connection Conn;
//  PreparedStatement pstmt1 = null;
//  PreparedStatement pstmt2 = null;
//  PreparedStatement pstmt3 = null;
//  PreparedStatement pstmt4 = null;
//  PreparedStatement pstmt5 = null;


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
////    Conn   = SqlitePseudoBenchmark.connect(url, user, password);
//
//    if (Conn == null) {
//      System.err.println("Cannot connect to database");
//      return;
//    }
//
//    try {
//      if (SqlitePseudoBenchmark.transactions) {
//        Conn.setAutoCommit(false);
//      }
//
//      if (SqlitePseudoBenchmark.prepared_stmt) {
//        String Query;
//
//        Query  = "UPDATE accounts ";
//        Query  += "SET     Abalance = Abalance + ? ";
//        Query  += "WHERE   Aid = ?";
//        pstmt1 = Conn.prepareStatement(Query);
//        Query  = "SELECT Abalance ";
//        Query  += "FROM   accounts ";
//        Query  += "WHERE  Aid = ?";
//        pstmt2 = Conn.prepareStatement(Query);
//        Query  = "UPDATE tellers ";
//        Query  += "SET    Tbalance = Tbalance + ? ";
//        Query  += "WHERE  Tid = ?";
//        pstmt3 = Conn.prepareStatement(Query);
//        Query  = "UPDATE branches ";
//        Query  += "SET    Bbalance = Bbalance + ? ";
//        Query  += "WHERE  Bid = ?";
//        pstmt4 = Conn.prepareStatement(Query);
//        Query  = "INSERT INTO history(Tid, Bid, Aid, delta) ";
//        Query  += "VALUES (?,?,?,?)";
//        pstmt5 = Conn.prepareStatement(Query);
//      }
//    } catch (Exception E) {
//      System.out.println(E.getMessage());
//      E.printStackTrace();
//    }
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

//    if (SqlitePseudoBenchmark.prepared_stmt) {
//      try {
//        if (pstmt1 != null) {
//          pstmt1.close();
//        }
//
//        if (pstmt2 != null) {
//          pstmt2.close();
//        }
//
//        if (pstmt3 != null) {
//          pstmt3.close();
//        }
//
//        if (pstmt4 != null) {
//          pstmt4.close();
//        }
//
//        if (pstmt5 != null) {
//          pstmt5.close();
//        }
//      } catch (Exception E) {
//        System.out.println(E.getMessage());
//        E.printStackTrace();
//      }
//    }
//
//    SqlitePseudoBenchmark.instant.connectClose(Conn);
//
//    Conn = null;
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

//    if (Conn == null) {
//      SqlitePseudoBenchmark.instant.incrementFailedTransactionCount();
//
//      return 0;
//    }
//
//    try {
//
//
//
//
//
//      if (SqlitePseudoBenchmark.prepared_stmt) {
//        pstmt1.setInt(1, delta);
//        pstmt1.setInt(2, aid);
//        pstmt1.executeUpdate();
//      //  pstmt1.clearWarnings();
//        pstmt2.setInt(1, aid);
//
//        ResultSet RS = pstmt2.executeQuery();
//
//      //  pstmt2.clearWarnings();
//
//        while (RS.next()) {
//          aBalance = RS.getInt(1);
//        }
//
//        pstmt3.setInt(1, delta);
//        pstmt3.setInt(2, tid);
//        pstmt3.executeUpdate();
//     //   pstmt3.clearWarnings();
//        pstmt4.setInt(1, delta);
//        pstmt4.setInt(2, bid);
//        pstmt4.executeUpdate();
//     //   pstmt4.clearWarnings();
//        pstmt5.setInt(1, tid);
//        pstmt5.setInt(2, bid);
//        pstmt5.setInt(3, aid);
//        pstmt5.setInt(4, delta);
//        pstmt5.executeUpdate();
//     //   pstmt5.clearWarnings();
//      } else {
//        Statement Stmt  = Conn.createStatement();
//        String    Query = "UPDATE accounts ";
//
//        Query += "SET     Abalance = Abalance + " + delta + " ";
//        Query += "WHERE   Aid = " + aid;
//
//        Stmt.executeUpdate(Query);
//
//     //   Stmt.clearWarnings();
//
//        Query = "SELECT Abalance ";
//        Query += "FROM   accounts ";
//        Query += "WHERE  Aid = " + aid;
//
//        ResultSet RS = Stmt.executeQuery(Query);
//
//     //   Stmt.clearWarnings();
//
//        while (RS.next()) {
//          aBalance = RS.getInt(1);
//        }
//
//        Query = "UPDATE tellers ";
//        Query += "SET    Tbalance = Tbalance + " + delta + " ";
//        Query += "WHERE  Tid = " + tid;
//
//        Stmt.executeUpdate(Query);
//  //      Stmt.clearWarnings();
//
//        Query = "UPDATE branches ";
//        Query += "SET    Bbalance = Bbalance + " + delta + " ";
//        Query += "WHERE  Bid = " + bid;
//
//        Stmt.executeUpdate(Query);
//   //     Stmt.clearWarnings();
//
//        Query = "INSERT INTO history(Tid, Bid, Aid, delta) ";
//        Query += "VALUES (";
//        Query += tid + ",";
//        Query += bid + ",";
//        Query += aid + ",";
//        Query += delta + ")";
//
//        Stmt.executeUpdate(Query);
//    //    Stmt.clearWarnings();
//        Stmt.close();
//      }
//
//      if (SqlitePseudoBenchmark.transactions) {
//        Conn.commit();
//      }
//
//      return aBalance;
//    } catch (Exception E) {
//      // Always print exceptions for failed transactions
//      System.out.println("Transaction failed: " + E.getMessage());
//      E.printStackTrace();
//
//      SqlitePseudoBenchmark.instant.incrementFailedTransactionCount();
//
//      if (SqlitePseudoBenchmark.transactions) {
//        try {
//           Conn.rollback();
//        } catch (SQLException E1) {
//
//        }
//        catch (java.sql.SQLException e) {
//          e.printStackTrace();
//        }
//      }
//    }
//
//    return 0;
  }    /* end of DoOne         */
}    /* end of class ClientThread */
