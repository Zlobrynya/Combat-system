package com.zlobrynya.game;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikita on 27.06.2017.
 */

public class AdapterBattleList extends ArrayAdapter<String> {

    public AdapterBattleList(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        TextView textView;
        if (v != null){
            textView = (TextView) v.getTag();
        }else {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.adapter_list_battle, null);
            textView = (TextView) v.findViewById(R.id.textBattle);
            v.setTag(textView);
        }
        String text = getItem(position);
        if (text != null){
            textView.setText(text);
        }
        return v;
    }
}
