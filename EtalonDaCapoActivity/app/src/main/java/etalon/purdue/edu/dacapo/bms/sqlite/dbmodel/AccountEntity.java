package etalon.purdue.edu.dacapo.bms.sqlite.dbmodel;

/**
 * Created by hussein on 2/10/16.
 */
public class AccountEntity {
  long mAid;
  long mBid;
  String filler;

  /**
   * Gets aid.
   *
   * @return the aid
   */
  public long getmAid() {
    return mAid;
  }

  /**
   * Sets aid.
   *
   * @param mAid the m aid
   */
  public void setmAid(long mAid) {
    this.mAid = mAid;
  }

  /**
   * Gets bid.
   *
   * @return the bid
   */
  public long getmBid() {
    return mBid;
  }

  /**
   * Sets bid.
   *
   * @param mBid the m bid
   */
  public void setmBid(long mBid) {
    this.mBid = mBid;
  }

  /**
   * Gets filler.
   *
   * @return the filler
   */
  public String getFiller() {
    return filler;
  }

  /**
   * Sets filler.
   *
   * @param filler the filler
   */
  public void setFiller(String filler) {
    this.filler = filler;
  }

  /**
   * Gets abalance.
   *
   * @return the abalance
   */
  public int getmAbalance() {
    return mAbalance;
  }

  public void setmAbalance(int mAbalance) {
    this.mAbalance = mAbalance;
  }

  private int  mAbalance;
}
