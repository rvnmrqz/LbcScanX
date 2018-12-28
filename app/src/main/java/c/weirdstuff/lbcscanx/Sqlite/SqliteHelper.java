package c.weirdstuff.lbcscanx.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import c.weirdstuff.lbcscanx.Sqlite.Tables.Records;

public class SqliteHelper extends SQLiteOpenHelper
{
    private static final String DBNAME = "lbcscanx.db";
    private static final int DBVERSION = 1;

    public SqliteHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Records.CreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Records.Table);
    }
}
