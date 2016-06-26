/*
The MIT License (MIT)

Copyright (c) 2014 Danylyk Dmytro

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package etalon.purdue.edu.dacapo.bms.sqlite.utils;

/**
 * Created by hussein on 2/9/16.
 */

import android.content.ContentValues;

public class DatabaseParams {

  public static class Insert {

    public String table;
    public String nullColumnHack;
    public ContentValues values;
  }

  public static class Delete {
    public String table;
    public String whereClause;
    public String[] whereArgs;
  }

  public static class Select {
    public boolean distinct;
    public String table;
    public String[] columns;
    public String selection;
    public String[] selectionArgs;
    public String groupBy;
    public String having;
    public String orderBy;
    public String limit;
  }

  public static class Update {

    public String table;
    public ContentValues values;
    public String whereClause;
    public String[] whereArgs;
  }
}