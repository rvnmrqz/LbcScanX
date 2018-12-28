package c.weirdstuff.lbcscanx.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import c.weirdstuff.lbcscanx.Callbacks.AdapterCallback;
import c.weirdstuff.lbcscanx.Models.Record;
import c.weirdstuff.lbcscanx.R;

public class RecordsAdapter extends RecyclerView.Adapter<RecordsAdapter.MyViewHolder>{

    private List<Record> list;
    private AdapterCallback callback;
    private LayoutInflater inflater;

    public RecordsAdapter(Context context, List<Record> list, AdapterCallback callback) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
        this.callback = callback;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtValue, txtDate;

        public MyViewHolder(@NonNull View v) {
            super(v);
            txtValue = v.findViewById(R.id.txtValue);
            txtDate = v.findViewById(R.id.txtDate);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.row_record, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int index) {
        Record record = list.get(index);

        viewHolder.txtValue.setText(record.getValue());
        viewHolder.txtDate.setText(record.getDate());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClick(index);
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                callback.onLongClick(index);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
