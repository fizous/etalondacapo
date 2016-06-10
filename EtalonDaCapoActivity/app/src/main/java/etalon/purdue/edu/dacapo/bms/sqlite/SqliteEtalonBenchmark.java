package etalon.purdue.edu.dacapo.bms.sqlite;

import java.io.PrintStream;

import etalon.purdue.edu.base.wrapper.FileOutputStream;

/**
 * Created by hussein on 2/9/16.
 */
public class SqliteEtalonBenchmark {




  /* main program,    creates a 1-tps database:  i.e. 1 branch, 10 tellers,...
 *                    runs one TPC BM B transaction
 * example command line:
 * -driver  org.hsqldb.jdbcDriver -url jdbc:hsqldb:/hsql/test33 -user sa -clients 20
 */
  public static void main(String[] Args) {

//    for(int i = 0; i < Args.length; i++) {
//      System.err.println("arg " + i + " : " + Args[i]);
//    }

    //DS. Sept. 2004:
    String DriverName         = "";
    String DBUrl              = "";
    String DBUser             = "";
    String DBPassword         = "";
    String outputDBFile        = "";
    boolean initialize_dataset = false;

    for (int i = 0; i < Args.length; i++) {
      if (Args[i].equals("-clients")) {
        if (i + 1 < Args.length) {
          i++;

          SqlitePseudoBenchmark.n_clients = Integer.parseInt(Args[i]);
        }
      } else if (Args[i].equals("-output")) {
        outputDBFile = Args[++i];
        //System.out.println("output file is : " +  outputDBFile);
      } else if (Args[i].equals("-driver")) {
        if (i + 1 < Args.length) {
          i++;

          DriverName = Args[i];

          if (DriverName.equals(
                  "org.enhydra.instantdb.jdbc.idbDriver")) {
            SqlitePseudoBenchmark.ShutdownCommand = "SHUTDOWN";
          }

          if (DriverName.equals(
                  "com.borland.datastore.jdbc.DataStoreDriver")) {}

          if (DriverName.equals("com.mckoi.JDBCDriver")) {
            SqlitePseudoBenchmark.ShutdownCommand = "SHUTDOWN";
          }

          if (DriverName.equals("org.hsqldb.jdbcDriver")) {
            //DS, 16 December 2003: commented out this line to
            //    disable CACHED
            //tableExtension  = "CREATE CACHED TABLE ";
            SqlitePseudoBenchmark.ShutdownCommand = "SHUTDOWN COMPACT";
          }
        }
      } else if (Args[i].equals("-url")) {
        if (i + 1 < Args.length) {
          i++;

          DBUrl = Args[i];
        }
      } else if (Args[i].equals("-user")) {
        if (i + 1 < Args.length) {
          i++;

          DBUser = Args[i];
        }
      } else if (Args[i].equals("-tabfile")) {
        if (i + 1 < Args.length) {
          i++;

          try {
            FileOutputStream File = new FileOutputStream(Args[i]);

            SqlitePseudoBenchmark.TabFile = new PrintStream(File);
          } catch (Exception e) {
            SqlitePseudoBenchmark.TabFile = null;
          }
        }
      } else if (Args[i].equals("-password")) {
        if (i + 1 < Args.length) {
          i++;

          DBPassword = Args[i];
        }
      } else if (Args[i].equals("-tpc")) {
        if (i + 1 < Args.length) {
          i++;

          SqlitePseudoBenchmark.n_txn_per_client = Integer.parseInt(Args[i]);
        }
      } else if (Args[i].equals("-init")) {
        initialize_dataset = true;
      } else if (Args[i].equals("-tps")) {
        if (i + 1 < Args.length) {
          i++;

          SqlitePseudoBenchmark.tps = Integer.parseInt(Args[i]);
        }
      } else if (Args[i].equals("-v")) {
        SqlitePseudoBenchmark.verbose = true;
      }
    }

    if (DriverName.length() == 0 || DBUrl.length() == 0) {
      System.out.println(
              "usage: java JDBCBench -driver [driver_class_name] -url [url_to_db] -user [username] -password [password] [-v] [-init] [-tpc n] [-clients]");
      System.out.println();
      System.out.println("-v          verbose error messages");
      System.out.println("-init       initialize the tables");
      System.out.println("-tpc        transactions per client");
      System.out.println("-clients    number of simultaneous clients");
      System.exit(-1);
    }

    System.out.println(
            "*********************************************************");
    System.out.println(
            "* SqliteAndroidBenchmark                                *");
    System.out.println(
            "*********************************************************");
    System.out.println();
    System.out.println("Scale factor value: " + SqlitePseudoBenchmark.tps);
    System.out.println("Number of clients: " + SqlitePseudoBenchmark.n_clients);
    System.out.println("Number of transactions per client: "
            + SqlitePseudoBenchmark.n_txn_per_client);
    System.out.println();

    try {
      //Class.forName(DriverName);

      new SqlitePseudoBenchmark(DBUrl, DBUser, DBPassword,
              initialize_dataset);
    } catch (Exception E) {
      System.out.println(E.getMessage());
      E.printStackTrace();
    }
  }
}
