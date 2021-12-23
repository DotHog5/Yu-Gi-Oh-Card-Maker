package com.yugioh.cardmaker.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yugioh.cardmaker.R;
import com.yugioh.cardmaker.model.ModelCustomCard;

import java.util.List;

public class AdapterCustomCard extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelCustomCard> item;

    public AdapterCustomCard(Activity activity, List<ModelCustomCard> item) {
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
            convertView = inflater.inflate(R.layout.content_custom_card, null);


        TextView cardName = (TextView) convertView.findViewById(R.id.txtCardName);
        TextView cardKind     = (TextView) convertView.findViewById(R.id.txtCardKind);
        TextView cardType          = (TextView) convertView.findViewById(R.id.txtCardType);
        TextView cardAttr         = (TextView) convertView.findViewById(R.id.txtCardAttr);
        TextView cardAtt         = (TextView) convertView.findViewById(R.id.txtCardAtt);
        TextView cardDef         = (TextView) convertView.findViewById(R.id.txtCardDef);
        TextView cardEff         = (TextView) convertView.findViewById(R.id.txtCardNameEff);

        cardName.setText(item.get(position).getCardName());
        cardKind.setText(item.get(position).getCardKind());
        cardType.setText(item.get(position).getCardType());
        cardAttr.setText(item.get(position).getCardAttr());
        cardAtt.setText(item.get(position).getCardAtt());
        cardDef.setText(item.get(position).getCardDef());
        cardEff.setText(item.get(position).getCardEff());
        return convertView;
    }
}
