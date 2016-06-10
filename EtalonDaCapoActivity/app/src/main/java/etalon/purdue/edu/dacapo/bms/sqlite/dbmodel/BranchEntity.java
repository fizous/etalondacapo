package etalon.purdue.edu.dacapo.bms.sqlite.dbmodel;

/**
 * Created by hussein on 2/10/16.
 */
public class BranchEntity {

  public void setmBid(long mBid) {
    this.mBid = mBid;
  }

  public void setFiller(String filler) {
    this.filler = filler;
  }

  public void setmBbalance(long mBbalance) {
    this.mBbalance = mBbalance;
  }

  private long mBid;

  public String getFiller() {
    return filler;
  }

  public long getmBbalance() {
    return mBbalance;
  }

  public long getmBid() {
    return mBid;
  }

  private long mBbalance;
  private String filler;


}
