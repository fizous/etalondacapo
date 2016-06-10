package etalon.purdue.edu.dacapo.bms.sqlite.dbmodel;

/**
 * Created by hussein on 2/10/16.
 */
public class AccountEntity {

  public long getmAid() {
    return mAid;
  }

  public void setmAid(long mAid) {
    this.mAid = mAid;
  }

  public long getmBid() {
    return mBid;
  }

  public void setmBid(long mBid) {
    this.mBid = mBid;
  }

  public String getFiller() {
    return filler;
  }

  public void setFiller(String filler) {
    this.filler = filler;
  }

  long mAid;
  long mBid;
  String filler;

  public int getmAbalance() {
    return mAbalance;
  }

  public void setmAbalance(int mAbalance) {
    this.mAbalance = mAbalance;
  }

  private int  mAbalance;
}
