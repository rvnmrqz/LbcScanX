package c.weirdstuff.lbcscanx.Models;

import android.content.ContentValues;

import c.weirdstuff.lbcscanx.Sqlite.Tables.Records;

public class Record {
    long id;
    String value, date;

    public Record() {
    }

    public Record(String value, String date) {
        this.value = value;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ContentValues toContentValues(){
        if(this==null)return null;

        ContentValues cv = new ContentValues();
        if(this.getId()!=0) cv.put(Records.Columns.id,getId());

        cv.put(Records.Columns.value, getValue());
        cv.put(Records.Columns.date, getDate());
        return cv;
    }
}
