package c.weirdstuff.lbcscanx.Sqlite.Tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import c.weirdstuff.lbcscanx.Models.Record;
import c.weirdstuff.lbcscanx.Sqlite.SqliteHelper;

public class Records {

    //region TABLE CREATION/STRUCTURE
    public static final String Table = "tblRecords";

    public static class Columns {
        public static final String id = "id";
        public static final String value = "value";
        public static final String date = "date";
    }

    public static final String CreateQuery = "CREATE TABLE " + Table
            + "( " +
            Columns.id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Columns.value + " TEXT, " +
            Columns.date + " TEXT)";
    //endregion

    private Context context;
    private SQLiteDatabase database;

    public Records(Context context) {
        this.context = context;
        database = new SqliteHelper(context).getWritableDatabase();
    }

    public List<Record> getAll(){
        Cursor c = database.rawQuery("SELECT * FROM "+Table+" ORDER BY "+Columns.id + " DESC",null);
        List<Record> records = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Record r = new Record();
                r.setId(c.getLong(c.getColumnIndex(Columns.id)));
                r.setValue(c.getString(c.getColumnIndex(Columns.value)));
                r.setDate(c.getString(c.getColumnIndex(Columns.date)));
                records.add(r);
            }while (c.moveToNext());
        }
        Log.i("getAll","Record Count: "+records.size());
        return records;
    }

    public void save(Record record) {
        long result;

        if (record.getId() == 0)
            result = database.insert(Table, null, record.toContentValues());
        else
            result = database.update(Table, record.toContentValues(), Columns.id + " = " + record.getId(), null);

        Log.i("save","Record id: "+ result);

    }

    public void deleteById(long id) {
        database.delete(Table, Columns.id + " = " + id, null);
    }

    public void deleteAll() {
        database.execSQL("DELETE FROM "+Table);
    }

}
