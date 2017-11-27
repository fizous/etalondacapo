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
