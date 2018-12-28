package c.weirdstuff.lbcscanx;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Queue;

import c.weirdstuff.lbcscanx.Adapters.RecordsAdapter;
import c.weirdstuff.lbcscanx.Callbacks.AdapterCallback;
import c.weirdstuff.lbcscanx.Models.Record;
import c.weirdstuff.lbcscanx.Sqlite.Tables.Records;

public class MainActivity extends AppCompatActivity {

    private TextView txtResult;
    private Records records;
    private RecyclerView.Adapter adapter;
    private List<Record> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        txtResult.setMovementMethod(new ScrollingMovementMethod());
        Button btnScan = findViewById(R.id.btnScan);
        ImageButton btnSave = findViewById(R.id.btnSave);
        ImageButton btnDelete = findViewById(R.id.btnDelete);

        records = new Records(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), ((LinearLayoutManager) layoutManager).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        list = new ArrayList<>();
        adapter = new RecordsAdapter(this, list, new AdapterCallback() {
            @Override
            public void onClick(int index) {
                Toast.makeText(MainActivity.this, list.get(index).getValue(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int index) {

            }
        });
        recyclerView.setAdapter(adapter);

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("All records will be deleted, continue?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                records.deleteAll();
                                list.clear();
                                adapter.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create().show();
            }
        });

        loadRecords();
    }

    private void loadRecords() {
        list.clear();
        list.addAll(records.getAll());
        adapter.notifyDataSetChanged();
    }

    private void save() {
        String value = txtResult.getText().toString();
        if (value.isEmpty()) {
            Toast.makeText(this, "Nothing to save", Toast.LENGTH_SHORT).show();
            return;
        }

        Record record = new Record(value, getDateTime());
        records.save(record);
        list.add(0,record);
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String result = intentResult != null ? intentResult.getContents() : "";

        if(!result.isEmpty()){
            txtResult.setText(result);
            save();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm:ss yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
