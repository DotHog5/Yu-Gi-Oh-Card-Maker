package com.yugioh.cardmaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yugioh.cardmaker.R;
import com.yugioh.cardmaker.model.ModelYGOCard;

import java.util.List;

public class AdapterYGOCard extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelYGOCard> item;

    public AdapterYGOCard(Activity activity, List<ModelYGOCard> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_ygo_card, null);


        TextView cardName = (TextView) convertView.findViewById(R.id.txtCardName);
        TextView cardKind     = (TextView) convertView.findViewById(R.id.txtCardKind);
        TextView cardType          = (TextView) convertView.findViewById(R.id.txtCardType);
        TextView cardAttr         = (TextView) convertView.findViewById(R.id.txtCardAttr);
        TextView cardAtk         = (TextView) convertView.findViewById(R.id.txtCardAtt);
        TextView cardDef         = (TextView) convertView.findViewById(R.id.txtCardDef);
        TextView cardEff         = (TextView) convertView.findViewById(R.id.txtCardNameEff);

        cardName.setText(item.get(position).getName());
        cardKind.setText(item.get(position).getType());
        cardType.setText(item.get(position).getRace());
        cardAttr.setText(item.get(position).getAttribute());
        cardAtk.setText(item.get(position).getAtk());
        cardDef.setText(item.get(position).getDef());
        cardEff.setText(item.get(position).getDesc());
        return convertView;
    }
}
