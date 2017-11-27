/*
 * Copyright (c) 2005, 2009 The Australian National University.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License v2.0.
 * You may obtain the license at
 *
 *    http://www.opensource.org/licenses/apache2.0.php
 *
 * Modifications copyright (C) 2011, 2018 Purdue University
 * Refactor the code to use dbmodel
 */
package etalon.purdue.edu.dacapo.bms.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import etalon.purdue.edu.base.BaseActivityHelper;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.AccountDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.AccountEntity;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.BranchEntity;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.BrancheDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.HistoryDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.TellerDAO;
import etalon.purdue.edu.dacapo.bms.sqlite.dbmodel.TellerEntity;
import etalon.purdue.edu.dacapo.bms.sqlite.utils.DataBaseManager;
import etalon.purdue.edu.dacapo.bms.sqlite.utils.QueryExecutor;


/**
 * Created by hussein on 2/9/16.
 */
public class SqlitePseudoBenchmark {
  /* tpc bm b scaling rules */
  public static int tps = 1;         /* the tps scaling factor: here it is 1 */
  public static int nbranches = 1;         /* number of branches in 1 tps db       */
  public static int ntellers = 10;        /* number of tellers in  1 tps db       */
  public static int naccounts = 10000;    /* number of accounts in 1 tps db       */
  public static int nhistory = 864000;     /* number of history recs in 1 tps db   */
  public final static int TELLER = 0;
  public final static int BRANCH = 1;
  public final static int ACCOUNT = 2;
  int failed_transactions = 0;
  int transaction_count = 0;
  static int n_clients = 10;
  static int n_txn_per_client = 10;
  long start_time = 0;
  static boolean transactions = true;
  static boolean prepared_stmt = false;
  static String tableExtension = "";
  static String createExtension = "";
  static String ShutdownCommand = "";
  static PrintStream TabFile = null;
  static boolean verbose = false;
  MemoryWatcherThread MemoryWatcher;

  /* Debugging - number of clients currently active */
  volatile static int runningClients = 0;
  final static boolean debug = false;


  public static SqlitePseudoBenchmark instant;

  public Context mContext;
  //public static Connection dbConnection = null;

  /* main program,    creates a 1-tps database:  i.e. 1 branch, 10 tellers,...
   *                    runs one TPC BM B transaction
   * example command line:
   * -driver  org.hsqldb.jdbcDriver -url jdbc:hsqldb:/hsql/test33 -user sa -clients 20
   */

  public static int getRandomInt(int lo, int hi) {

    int ret = 0;

    ret = (int) (Math.random() * (hi - lo + 1));
    ret += lo;

    return ret;
  }

  public static int getRandomID(int type) {

    int min, max, num;

    max = min = 0;
    num = naccounts;

    switch (type) {

      case TELLER:
        min += nbranches;
        num = ntellers;

        /* FALLTHROUGH */
      case BRANCH:
        if (type == BRANCH) {
          num = nbranches;
        }

        min += naccounts;

        /* FALLTHROUGH */
      case ACCOUNT:
        max = min + num - 1;
    }

    return (getRandomInt(min, max));
  }



  public static Connection connect(String DBUrl, String DBUser,
                                   String DBPassword) {

    try {
//      synchronized(instant){
//        if(dbConnection == null) {
//          dbConnection = DriverManager.getConnection(DBUrl, DBUser,
//                  DBPassword);
//        }
//        return dbConnection;
//      }

      Connection conn = DriverManager.getConnection(DBUrl, DBUser,
          DBPassword);

      return conn;
    } catch (Exception E) {
      System.out.println(E.getMessage());
      E.printStackTrace();
    }

    return null;
  }

  public static void connectClose(Connection c) {

    if (c == null) {
      return;
    }

    try {
      c.close();
    } catch (Exception E) {
      System.out.println(E.getMessage());
      E.printStackTrace();
    }
  }


  void deleteFromTables() {
    try {
      //System.out.println("start deleteing from tables");
      DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
        public void run(SQLiteDatabase database) {
          StringBuffer queryString = new StringBuffer();
          queryString.append(HistoryDAO.getDeleteAllTable(mContext));
          queryString.append("\n");
          queryString.append(AccountDAO.getDeleteAllTable(mContext));
          queryString.append("\n");
          queryString.append(TellerDAO.getDeleteAllTable(mContext));
          queryString.append("\n");
          queryString.append(BrancheDAO.getDeleteAllTable(mContext));
          database.execSQL(queryString.toString());
        }
      });
      //System.out.println("done deleteing from tables");
    } catch (SQLiteException e){
      e.printStackTrace();
    }
  }

  void checkInitializations() {
    try {

      DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
        public void run(SQLiteDatabase database) {
          AccountDAO dao = new AccountDAO(database, mContext);
          List<AccountEntity> listFromDB = dao.selectAll();
          int accountsnb = listFromDB.size();
          if (accountsnb == (naccounts * tps)) {
            System.err.println("Already initialized for " + naccounts + "*" + tps);
            return;
          }
        }
      });
      //System.err.println("Done Check Initialization");
    } catch (SQLiteException e){
      e.printStackTrace();
    }

  }


  void createDatabase(String url, String user,
                      String password) throws Exception {

//    Connection Conn = connect(url, user, password);
//    ;
//    String s = Conn.getMetaData().getDatabaseProductName();
//
//    System.out.println("DBMS: " + s);
//
//    transactions = true;
//
//    if (transactions) {
//      try {
//        Conn.setAutoCommit(false);
//        System.out.println("In transaction mode");
//      } catch (SQLException Etrxn) {
//        transactions = false;
//      }
//    }

    //checkInitializations();
    DataBaseManager.getInstance().dropTables();
    DataBaseManager.getInstance().createTables();
    deleteFromTables();
//    try {
//      int accountsnb = 0;
//      Statement Stmt = Conn.createStatement();
//      String Query;
//
//      Query = "SELECT count(*) ";
//      Query += "FROM   accounts";
//
//      ResultSet RS = Stmt.executeQuery(Query);
//
//     // Stmt.clearWarnings();
//
//      while (RS.next()) {
//        accountsnb = RS.getInt(1);
//      }
//
//      if (transactions) {
//        Conn.commit();
//      }
//
//      Stmt.close();
//
//      if (accountsnb == (naccounts * tps)) {
//        System.out.println("Already initialized for " + naccounts + "*" + tps);
//        connectClose(Conn);
//
//        return;
//      }
//    } catch (Exception E) {
//    }




//    try {
//      Statement Stmt = Conn.createStatement();
//      String Query;
//
//      Query = "DROP TABLE history";
//
//      try {
//        Stmt.execute(Query);
//      } catch (Exception E) {
//      }
//     // Stmt.clearWarnings();
//
//      Query = "DROP TABLE accounts";
//
//      try {
//        Stmt.execute(Query);
//      } catch (Exception E) {
//      }
//     // Stmt.clearWarnings();
//
//      Query = "DROP TABLE tellers";
//
//      try {
//        Stmt.execute(Query);
//      } catch (Exception E) {
//      }
//     // Stmt.clearWarnings();
//
//      Query = "DROP TABLE branches";
//
//      try {
//        Stmt.execute(Query);
//      } catch (Exception E) {
//      }
//     // Stmt.clearWarnings();
//
//      if (transactions) {
//        Conn.commit();
//      }
//
//      Stmt.close();
//    } catch (Exception E) {
//    }


//    try {
//      Statement Stmt = Conn.createStatement();
//      String Query;
//
//      if (tableExtension.length() > 0) {
//        Query = tableExtension + " branches (";
//      } else {
//        Query = "CREATE TABLE branches (";
//      }
//
//      Query += "Bid         INTEGER NOT NULL PRIMARY KEY, ";
//      Query += "Bbalance    INTEGER,";
//      Query += "filler      CHAR(88))";    /* pad to 100 bytes */
//
//      if (createExtension.length() > 0) {
//        Query += createExtension;
//      }
//
//      Stmt.execute(Query);
//   //   Stmt.clearWarnings();
//
//      if (tableExtension.length() > 0) {
//        Query = tableExtension + " tellers (";
//      } else {
//        Query = "CREATE TABLE tellers (";
//      }
//
//      Query += "Tid         INTEGER NOT NULL PRIMARY KEY,";
//      Query += "Bid         INTEGER,";
//      Query += "Tbalance    INTEGER,";
//      Query += "filler      CHAR(84))";    /* pad to 100 bytes */
//
//      if (createExtension.length() > 0) {
//        Query += createExtension;
//      }
//
//      Stmt.execute(Query);
//   //   Stmt.clearWarnings();
//
//      if (tableExtension.length() > 0) {
//        Query = tableExtension + " accounts (";
//      } else {
//        Query = "CREATE TABLE accounts (";
//      }
//
//      Query += "Aid         INTEGER NOT NULL PRIMARY KEY, ";
//      Query += "Bid         INTEGER, ";
//      Query += "Abalance    INTEGER, ";
//      Query += "filler      CHAR(84))";    /* pad to 100 bytes */
//
//      if (createExtension.length() > 0) {
//        Query += createExtension;
//      }
//
//      Stmt.execute(Query);
//    //  Stmt.clearWarnings();
//
//      if (tableExtension.length() > 0) {
//        Query = tableExtension + " history (";
//      } else {
//        Query = "CREATE TABLE history (";
//      }
//
//      Query += "Tid         INTEGER, ";
//      Query += "Bid         INTEGER, ";
//      Query += "Aid         INTEGER, ";
//      Query += "delta       INTEGER, ";
//      Query += "tstime        TIMESTAMP, ";
//      Query += "filler      CHAR(22))";    /* pad to 50 bytes  */
//
//      if (createExtension.length() > 0) {
//        Query += createExtension;
//      }
//
//      Stmt.execute(Query);
//   //   Stmt.clearWarnings();
//
//      if (transactions) {
//        Conn.commit();
//      }
//
//      Stmt.close();
//    } catch (Exception E) {
//      E.printStackTrace();
//    }




          /* prime database using TPC BM B scaling rules.
       **  Note that for each branch and teller:
       **      branch_id = teller_id  / ntellers
       **      branch_id = account_id / naccounts
       */

    DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
      public void run(SQLiteDatabase database) {
        BrancheDAO dao = new BrancheDAO(database, mContext);
        List<BranchEntity> branchesList = new ArrayList<BranchEntity>();
        for (int i = 0; i < nbranches * tps; i++) {
          BranchEntity bE = new BranchEntity();
          bE.setmBid((long) i);
          bE.setmBbalance(0);
          branchesList.add(bE);
        }

        dao.insert(branchesList);
      }
    });


    DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
      public void run(SQLiteDatabase database) {
        TellerDAO dao = new TellerDAO(database, mContext);
        List<TellerEntity> tellersList = new ArrayList<TellerEntity>();
        for (int i = 0; i < ntellers * tps; i++) {
          TellerEntity bE = new TellerEntity();
          bE.setmTid((long) i);
          bE.setmBid((long) i / ntellers);
          tellersList.add(bE);
        }

        dao.insert(tellersList);
      }
    });

    DataBaseManager.getInstance().executeQuery(new QueryExecutor() {
      public void run(SQLiteDatabase database) {
        AccountDAO dao = new AccountDAO(database, mContext);
        List<AccountEntity> accountsList = new ArrayList<AccountEntity>();
        for (int i = 0; i < naccounts * tps; i++) {
          AccountEntity bE = new AccountEntity();
          bE.setmAid((long) i);
          bE.setmBid((long) i / naccounts);
          accountsList.add(bE);

          if ((i > 0) && ((i % 80000) == 0)) {
            System.out.println("\t" + i + "\t records inserted");
          }
        }

        dao.insert(accountsList);
      }
    });





    System.out.println("\t" + (naccounts * tps)
              + "\t records inserted");

    //      for (int i = 0; i < naccounts * tps; i++) {
//        if (prepared_stmt) {
//          pstmt.setInt(1, i);
//          pstmt.setInt(2, i / naccounts);
//          pstmt.executeUpdate();
//   //       pstmt.clearWarnings();
//        } else {
//          Query = "INSERT INTO accounts(Aid,Bid,Abalance) VALUES ("
//                  + i + "," + i / naccounts + ",0)";
//
//          Stmt.executeUpdate(Query);
//        }




    try {
//      Statement Stmt = Conn.createStatement();
//      String Query;
//
//      Query = "DELETE FROM history";
//
//      Stmt.execute(Query);
//   //   Stmt.clearWarnings();
//
//      Query = "DELETE FROM accounts";
//
//      Stmt.execute(Query);
//   //   Stmt.clearWarnings();
//
//      Query = "DELETE FROM tellers";
//
//      Stmt.execute(Query);
//   //   Stmt.clearWarnings();
//
//      Query = "DELETE FROM branches";
//
//      Stmt.execute(Query);
//   //   Stmt.clearWarnings();
//
//      if (transactions) {
//        Conn.commit();
//      }










//      PreparedStatement pstmt = null;
//
//      prepared_stmt = true;
//
//
//
//
//
//      if (prepared_stmt) {
//        try {
//          Query = "INSERT INTO branches(Bid,Bbalance) VALUES (?,0)";
//          pstmt = Conn.prepareStatement(Query);
//        } catch (SQLException Epstmt) {
//          pstmt = null;
//          prepared_stmt = false;
//        }
//      }
//
//
//      for (int i = 0; i < nbranches * tps; i++) {
//        if (prepared_stmt) {
//          pstmt.setInt(1, i);
//          pstmt.executeUpdate();
//   //       pstmt.clearWarnings();
//        } else {
//          Query = "INSERT INTO branches(Bid,Bbalance) VALUES (" + i
//                  + ",0)";
//
//          Stmt.executeUpdate(Query);
//        }
//
//        if ((i % 100 == 0) && (transactions)) {
//          Conn.commit();
//        }
//      }
//
//      if (prepared_stmt) {
//        pstmt.close();
//      }
//
//      if (transactions) {
//        Conn.commit();
//      }
//
//      if (prepared_stmt) {
//        Query =
//                "INSERT INTO tellers(Tid,Bid,Tbalance) VALUES (?,?,0)";
//        pstmt = Conn.prepareStatement(Query);
//      }
//
//      for (int i = 0; i < ntellers * tps; i++) {
//        if (prepared_stmt) {
//          pstmt.setInt(1, i);
//          pstmt.setInt(2, i / ntellers);
//          pstmt.executeUpdate();
//    //      pstmt.clearWarnings();
//        } else {
//          Query = "INSERT INTO tellers(Tid,Bid,Tbalance) VALUES ("
//                  + i + "," + i / ntellers + ",0)";
//
//          Stmt.executeUpdate(Query);
//        }
//
//        if ((i % 100 == 0) && (transactions)) {
//          Conn.commit();
//        }
//      }
//
//      if (prepared_stmt) {
//        pstmt.close();
//      }
//
//      if (transactions) {
//        Conn.commit();
//      }
//
//      if (prepared_stmt) {
//        Query =
//                "INSERT INTO accounts(Aid,Bid,Abalance) VALUES (?,?,0)";
//        pstmt = Conn.prepareStatement(Query);
//      }
//
//      for (int i = 0; i < naccounts * tps; i++) {
//        if (prepared_stmt) {
//          pstmt.setInt(1, i);
//          pstmt.setInt(2, i / naccounts);
//          pstmt.executeUpdate();
//   //       pstmt.clearWarnings();
//        } else {
//          Query = "INSERT INTO accounts(Aid,Bid,Abalance) VALUES ("
//                  + i + "," + i / naccounts + ",0)";
//
//          Stmt.executeUpdate(Query);
//        }
//
//        if ((i % 10000 == 0) && (transactions)) {
//          Conn.commit();
//        }
//
//        if ((i > 0) && ((i % 80000) == 0)) {
//          System.out.println("\t" + i + "\t records inserted");
//        }
//      }
//
//      if (prepared_stmt) {
//        pstmt.close();
//      }
//
//      if (transactions) {
//        Conn.commit();
//      }
//
//      System.out.println("\t" + (naccounts * tps)
//              + "\t records inserted");
//      Stmt.close();
    } catch (Exception E) {
      System.out.println(E.getMessage());
      E.printStackTrace();
    }
//
//    connectClose(Conn);
  }    /* end of CreateDatabase    */


  public SqlitePseudoBenchmark(String url, String user, String password,
                               boolean init) {
    //DS. Sept. 2004:
    instant = this;
    mContext = BaseActivityHelper.singleToneIns.mActivity.getApplicationContext();
//
//    if(mContext == null) {
//      System.err.println(" context is null");
//    } else {
//      System.err.println(" context is not nul");
//    }

    DataBaseManager.initializeInstance(mContext);


    Vector vClient = new Vector();
    Thread Client  = null;
    Enumeration e       = null;


    try {
      if (init) {
        //System.out.println("Start: "
        //        + (new java.util.Date()).toString());
        System.out.println("Initializing dataset...");
        createDatabase(url, user, password);
        System.out.println("done.\n");
//        System.out.println("Complete: "
//                + (new java.util.Date()).toString());
      }


      System.out.println("* Starting Benchmark Run *");

      MemoryWatcher = new MemoryWatcherThread();

      MemoryWatcher.start();




      transactions  = false;
      prepared_stmt = false;
      start_time    = System.currentTimeMillis();

      for (int i = 0; i < n_clients; i++) {
        Client = new ClientThread(n_txn_per_client, url, user, password, mContext);

        Client.start();
        vClient.addElement(Client);
      }

      /*
       ** Barrier to complete this test session
       */
      e = vClient.elements();

      while (e.hasMoreElements()) {
        Client = (Thread) e.nextElement();

        if (debug) System.out.println("Active client threads: " + runningClients);
        Client.join();
      }
      if (debug) System.out.println("All clients exited");

      vClient.removeAllElements();
      reportDone();

      transactions  = true;
      prepared_stmt = false;
      start_time    = System.currentTimeMillis();

      for (int i = 0; i < n_clients; i++) {
        Client = new ClientThread(n_txn_per_client, url, user, password, mContext);

        Client.start();
        vClient.addElement(Client);
      }

       /*
        ** Barrier to complete this test session
        */
      e = vClient.elements();

      while (e.hasMoreElements()) {
        Client = (Thread) e.nextElement();

        if (debug) System.out.println("Active client threads: " + runningClients);
        Client.join();
      }
      if (debug) System.out.println("All clients exited");

      vClient.removeAllElements();
      reportDone();

      transactions  = false;
      prepared_stmt = true;
      start_time    = System.currentTimeMillis();

      for (int i = 0; i < n_clients; i++) {
        Client = new ClientThread(n_txn_per_client, url, user,
                password, mContext);

        Client.start();
        vClient.addElement(Client);
      }

        /*
         ** Barrier to complete this test session
         */
      e = vClient.elements();

      while (e.hasMoreElements()) {
        Client = (Thread) e.nextElement();

        if (debug) System.out.println("Active client threads: "+runningClients);
        Client.join();
      }
      if (debug) System.out.println("All clients exited");

      vClient.removeAllElements();
      reportDone();

      transactions  = true;
      prepared_stmt = true;
      start_time    = System.currentTimeMillis();

      for (int i = 0; i < n_clients; i++) {
        Client = new ClientThread(n_txn_per_client, url, user,
                password, mContext);

        Client.start();
        vClient.addElement(Client);
      }

        /*
         ** Barrier to complete this test session
         */
      e = vClient.elements();

      while (e.hasMoreElements()) {
        Client = (Thread) e.nextElement();

        if (debug) System.out.println("Active client threads: "+runningClients);
        Client.join();
      }
      if (debug) System.out.println("All clients exited");

      vClient.removeAllElements();

      boolean result = DataBaseManager.shutdownInstance();
      if(!result) {
        System.err.println("Could not shutdown database");
      }

      reportDone();
    } catch (Exception E) {
      System.out.println(E.getMessage());
      E.printStackTrace();
    } finally {
      MemoryWatcher.end();

      try {
        MemoryWatcher.join();

//        if (ShutdownCommand.length() > 0) {
//          Connection C    = connect(url, user, password);
//          ;
//          Statement  Stmt = C.createStatement();
//
//          Stmt.execute(ShutdownCommand);
//          Stmt.close();
//          connectClose(C);
//        }

        if (TabFile != null) {
          TabFile.close();
        }
      } catch (Exception E1) {
        E1.printStackTrace();
      }
      //DS. Disable System.exit(0) so that benchmark can be invoked twice from harness.
      //System.out.println ("-" + " PseudoJDBCBench.PseudoJDBCBench " + "calling System.exit(0)");
      //System.exit(0);
    }



  }

  public void reportDone() {

    long end_time = System.currentTimeMillis();
    double completion_time = ((double) end_time - (double) start_time)
            / 1000;

    if (TabFile != null) {
      TabFile.print(tps + ";" + n_clients + ";" + n_txn_per_client
              + ";");
    }

    System.out.println("\n* Benchmark Report *");
    System.out.print("* Featuring ");

    if(false) {
      if (prepared_stmt) {
        System.out.print("<prepared statements> ");

        if (TabFile != null) {
          TabFile.print("<prepared statements>;");
        }
      } else {
        System.out.print("<direct queries> ");

        if (TabFile != null) {
          TabFile.print("<direct queries>;");
        }
      }

      if (transactions) {
        System.out.print("<transactions> ");

        if (TabFile != null) {
          TabFile.print("<transactions>;");
        }
      } else {
        System.out.print("<auto-commit> ");

        if (TabFile != null) {
          TabFile.print("<auto-commit>;");
        }
      }
    }

    System.out.println("\n--------------------");
    //System.out.println("Time to execute " + transaction_count
    //        + " transactions: " + completion_time
    //        + " seconds.");
    //System.out.println("Max/Min memory usage: " + MemoryWatcher.max
    //        + " / " + MemoryWatcher.min + " kb");
    System.out.println(failed_transactions + " / " + transaction_count
            + " failed to complete.");

    double rate = (transaction_count - failed_transactions)
            / completion_time;

    //System.out.println("Transaction rate: " + rate + " txn/sec.");

    if (TabFile != null) {
      TabFile.print(MemoryWatcher.max + ";" + MemoryWatcher.min + ";"
              + failed_transactions + ";" + rate + "\n");
    }

    transaction_count   = 0;
    failed_transactions = 0;

    MemoryWatcher.reset();
  }

  public synchronized void incrementTransactionCount() {
    transaction_count++;
  }

  public synchronized void incrementFailedTransactionCount() {
    failed_transactions++;
  }

}
