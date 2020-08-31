package com.example.gorevyoneticisi.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.gorevyoneticisi.MainActivity;
import com.example.gorevyoneticisi.R;
import com.example.gorevyoneticisi.add;
import com.example.gorevyoneticisi.database.DatabaseConnection;
import com.example.gorevyoneticisi.entity.tasks;

import java.util.List;


public class listAdapter extends BaseAdapter {
    private LayoutInflater userInflater;
    private List<tasks> tasksList;
    private Context context;
    DatabaseConnection con;
    AlertDialog.Builder builder;

    public listAdapter(Activity activity, List<tasks> tasksList) {
        userInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.tasksList = tasksList;
        context = activity;
    }

    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int i) {
        return tasksList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final View lineView;
        lineView = userInflater.inflate(R.layout.single_task, null);
        con = new DatabaseConnection(lineView.getContext());
        builder = new AlertDialog.Builder(lineView.getContext());

        final TextView txt_task_id = lineView.findViewById(R.id.txt_task_id);
        TextView txt_task_header = lineView.findViewById(R.id.txt_task_header);
        TextView txt_task_content = lineView.findViewById(R.id.txt_task_content);
        TextView txt_spinner_type = lineView.findViewById(R.id.txt_task_type);
        final ImageView task_image = lineView.findViewById(R.id.task_image);
        Button btn_OK = lineView.findViewById(R.id.btn_OK);
        Button btn_DELETE = lineView.findViewById(R.id.btn_DELETE);
        Button btn_EDIT = lineView.findViewById(R.id.btn_EDIT);

        final tasks temp = tasksList.get(i);
        txt_task_id.setText(temp.getTask_id() + "");
        txt_task_header.setText(temp.getTask_header());
        txt_task_content.setText(temp.getTask_content());
        txt_spinner_type.setText(temp.getTask_type());

        if (temp.getTask_type().equals("Günlük")) {
            txt_spinner_type.setTextColor(Color.parseColor("#6BFF00"));
        } else if (temp.getTask_type().equals("Haftalık")) {
            txt_spinner_type.setTextColor(Color.parseColor("#FEC180"));
        } else if (temp.getTask_type().equals("Aylık")) {
            txt_spinner_type.setTextColor(Color.parseColor("#EFBAD3"));
        }


        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp.setTask_status(1);
                con.editPlan(temp);
                tasksList.remove(i);
                MainActivity.statistics();
                Toast.makeText(lineView.getContext(), "Tebrikler, planınız TAMAMLANDI!", Toast.LENGTH_LONG).show();
                notifyDataSetChanged();
            }
        });

        btn_DELETE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Görev Yöneticisi");
                builder.setMessage("Başlığı: " + temp.getTask_header() + "\n\n" +
                        "olan planlamanızı silmek istediğinize emin misiniz?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i2) {
                        con.deletePlan(Integer.parseInt(txt_task_id.getText().toString()));
                        tasksList.remove(i);
                        MainActivity.statistics();
                        Toast.makeText(lineView.getContext(), "Başarıyla silindi.", Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Hayır", null);
                builder.show();
            }
        });

        btn_EDIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), add.class);
                intent.putExtra("id", temp.getTask_id());
                intent.putExtra("header", temp.getTask_header());
                intent.putExtra("content", temp.getTask_content());
                intent.putExtra("type", temp.getTask_type());
                intent.putExtra("date", temp.getTask_date());
                intent.putExtra("status", temp.getTask_status());
                lineView.getContext().startActivity(intent);
                ((Activity)context).finish();
            }
        });

        return lineView;
    }
}
