package com.yugioh.cardmaker.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yugioh.cardmaker.R;
import com.yugioh.cardmaker.adapter.AdapterCustomCard;
import com.yugioh.cardmaker.model.ModelCustomCard;
import com.yugioh.cardmaker.server.BaseURL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityCard extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterCustomCard adapter;
    ListView list;

    ArrayList<ModelCustomCard> newsList = new ArrayList<ModelCustomCard>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        getSupportActionBar().setTitle("Yu-Gi-Oh! Custom Cards");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterCustomCard(ActivityCard.this, newsList);
        list.setAdapter(adapter);
        getAllCards();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ActivityCard.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    private void getAllCards() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.card, null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            boolean status = response.getBoolean("error");

                            if (status == false) {
                                String data = response.getString("data");
                                JSONArray jsonArray = new JSONArray(data);

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                                    final ModelCustomCard card = new ModelCustomCard();
                                    final String _id = jsonObject.getString("_id");
                                    final String cardCode = jsonObject.getString("cardCode");
                                    final String cardName = jsonObject.getString("cardName");
                                    final String cardKind = jsonObject.getString("cardKind");
                                    final String cardType = jsonObject.getString("cardType");
                                    final String cardAttr = jsonObject.getString("cardAttr");
                                    final String cardAtt = jsonObject.getString("cardAtt");
                                    final String cardDef = jsonObject.getString("cardDef");
                                    final String cardEff = jsonObject.getString("cardEff");

                                    card.setCardCode(cardCode);
                                    card.setCardName(cardName);
                                    card.setCardKind(cardKind);
                                    card.setCardType(cardType);
                                    card.setCardAttr(cardAttr);
                                    card.setCardAtt(cardAtt);
                                    card.setCardDef(cardDef);
                                    card.setCardEff(cardEff);
                                    card.set_id(_id);

                                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // TODO Auto-generated method stub
                                            Intent a = new Intent(ActivityCard.this, EditDeleteCardActivity.class);
                                            a.putExtra("cardCode", newsList.get(position).getCardCode());
                                            a.putExtra("_id", newsList.get(position).get_id());
                                            a.putExtra("cardName", newsList.get(position).getCardName());
                                            a.putExtra("cardKind", newsList.get(position).getCardKind());
                                            a.putExtra("cardType", newsList.get(position).getCardType());
                                            a.putExtra("cardAttr", newsList.get(position).getCardAttr());
                                            a.putExtra("cardAtt", newsList.get(position).getCardAtt());
                                            a.putExtra("cardDef", newsList.get(position).getCardDef());
                                            a.putExtra("cardEff", newsList.get(position).getCardEff());
                                            startActivity(a);
                                        }
                                    });
                                    newsList.add(card);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
