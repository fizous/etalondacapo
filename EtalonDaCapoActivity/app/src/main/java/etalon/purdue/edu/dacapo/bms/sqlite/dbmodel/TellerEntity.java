package etalon.purdue.edu.dacapo.bms.sqlite.dbmodel;

/**
 * Created by hussein on 2/10/16.
 */
public class TellerEntity {

  public long getmTid() {
    return mTid;
  }

  public void setmTid(long mTid) {
    this.mTid = mTid;
  }

  public long getmBid() {
    return mBid;
  }

  public void setmBid(long mBid) {
    this.mBid = mBid;
  }

  public int getmTbalance() {
    return mTbalance;
  }

  public void setmTbalance(int mTbalance) {
    this.mTbalance = mTbalance;
  }

  public String getFiller() {
    return filler;
  }

  public void setFiller(String filler) {
    this.filler = filler;
  }

  private long mTid;
  private long mBid;
  private int mTbalance;
  private String filler;

}
