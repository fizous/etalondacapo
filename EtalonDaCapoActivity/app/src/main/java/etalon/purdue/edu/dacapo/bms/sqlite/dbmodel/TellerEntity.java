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
