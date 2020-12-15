package br.unicamp.cotuca.agendaaluno19191;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlunoListAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<Aluno> data;
    LayoutInflater layoutInflater = null;

    public AlunoListAdapter(Activity activity, ArrayList data) {
        this.activity = activity;
        this.data = data;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder {
        TextView tvRa, tvNome, tvEmail;
    }
    ViewHolder viewHolder = null;

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v == null){
            viewHolder = new ViewHolder();
            v = layoutInflater.inflate(R.layout.list_item,null);

            viewHolder.tvRa = (TextView) v.findViewById(R.id.tvAlunoRa);
            viewHolder.tvNome = (TextView) v.findViewById(R.id.tvAlunoNome);
            viewHolder.tvEmail = (TextView) v.findViewById(R.id.tvAlunoEmail);

            v.setTag(viewHolder);
        } else {
            viewHolder= (ViewHolder) v.getTag();
        }

        viewHolder.tvRa.setText(data.get(i).getRa());
        viewHolder.tvNome.setText(data.get(i).getNome());
        viewHolder.tvEmail.setText(data.get(i).getEmail());

        return v;
    }
}
