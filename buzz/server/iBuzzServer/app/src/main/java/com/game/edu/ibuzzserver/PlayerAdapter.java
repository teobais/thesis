package com.game.edu.ibuzzserver;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PlayerAdapter extends ArrayAdapter<Player> {
    Context context;
    int layoutResourceId;
    Player data[] = null;

    public PlayerAdapter(Context context, int layoutResourceId, Player[] data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ProfHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ProfHolder();

            holder.txtTitle = (TextView)row.findViewById(R.id.txtName);

            row.setTag(holder);
        }
        else
        {
            holder = (ProfHolder)row.getTag();
        }

        Player player = data[position];
        holder.txtTitle.setText(player.getNickName());


        return row;
    }

    static class ProfHolder
    {

        TextView txtTitle;
    }

}
