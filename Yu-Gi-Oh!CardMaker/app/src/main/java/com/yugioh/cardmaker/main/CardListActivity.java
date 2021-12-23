package com.yugioh.cardmaker.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yugioh.cardmaker.R;
import com.yugioh.cardmaker.adapter.AdapterYGOCard;
import com.yugioh.cardmaker.model.ModelYGOCard;

import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CardListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner cardKindSpinner, cardTypeSpinner, cardAttrSpinner;

    ProgressDialog pDialog;

    AdapterYGOCard adapter;
    ListView list;

    Button filterCards;

    ArrayList<ModelYGOCard> newsList = new ArrayList<ModelYGOCard>();
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);

        cardKindSpinner = (Spinner) findViewById(R.id.cardKindSpinner);
        cardTypeSpinner = (Spinner) findViewById(R.id.cardTypeSpinner);
        cardAttrSpinner = (Spinner) findViewById(R.id.cardAttrSpinner);
        filterCards = (Button) findViewById(R.id.filterCards);

        // card kind
        ArrayAdapter<CharSequence> cardKindAdapter = ArrayAdapter.createFromResource(this, R.array.cardKind, android.R.layout.simple_spinner_item);
        cardKindAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        cardKindSpinner.setAdapter(cardKindAdapter);

        cardKindSpinner.setOnItemSelectedListener(this);
        cardTypeSpinner.setEnabled(false);
        cardAttrSpinner.setEnabled(false);


        // set cards
        getSupportActionBar().setTitle("Yu-Gi-Oh! Cards");
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        list = (ListView) findViewById(R.id.array_list_card);
        newsList.clear();
        adapter = new AdapterYGOCard(CardListActivity.this, newsList);
        list.setAdapter(adapter);
        getAllCards();

        filterCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterAllCards();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Monster")) {
            //type
            ArrayAdapter<CharSequence> cardTypeAdapter = ArrayAdapter.createFromResource(this, R.array.monsterType, android.R.layout.simple_spinner_item);
            cardTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            cardTypeSpinner.setAdapter(cardTypeAdapter);
            cardTypeSpinner.setEnabled(true);

            //attr
            ArrayAdapter<CharSequence> cardAttrAdapter = ArrayAdapter.createFromResource(this, R.array.monsterAttr, android.R.layout.simple_spinner_item);
            cardAttrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            cardAttrSpinner.setAdapter(cardAttrAdapter);
            cardAttrSpinner.setEnabled(true);
        } else if (parent.getItemAtPosition(position).equals("Spell")) {
            //type
            ArrayAdapter<CharSequence> cardTypeAdapter = ArrayAdapter.createFromResource(this, R.array.spellType, android.R.layout.simple_spinner_item);
            cardTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            cardTypeSpinner.setAdapter(cardTypeAdapter);
            cardTypeSpinner.setEnabled(true);

            //attr
            ArrayAdapter<CharSequence> cardAttrAdapter = ArrayAdapter.createFromResource(this, R.array.spellTrapAttr, android.R.layout.simple_spinner_item);
            cardAttrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            cardAttrSpinner.setAdapter(cardAttrAdapter);
            cardAttrSpinner.setEnabled(false);
        } else if (parent.getItemAtPosition(position).equals("Trap")) {
            //type
            ArrayAdapter<CharSequence> cardTypeAdapter = ArrayAdapter.createFromResource(this, R.array.trapType, android.R.layout.simple_spinner_item);
            cardTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            cardTypeSpinner.setAdapter(cardTypeAdapter);
            cardTypeSpinner.setEnabled(true);

            //attr
            ArrayAdapter<CharSequence> cardAttrAdapter = ArrayAdapter.createFromResource(this, R.array.spellTrapAttr, android.R.layout.simple_spinner_item);
            cardAttrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            cardAttrSpinner.setAdapter(cardAttrAdapter);
            cardAttrSpinner.setEnabled(false);
        } else if (parent.getItemAtPosition(position).equals("")) {
            //type
            ArrayAdapter<CharSequence> cardTypeAdapter = ArrayAdapter.createFromResource(this, R.array.emptyType, android.R.layout.simple_spinner_item);
            cardTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            cardTypeSpinner.setAdapter(cardTypeAdapter);
            cardTypeSpinner.setEnabled(false);

            //attr
            ArrayAdapter<CharSequence> cardAttrAdapter = ArrayAdapter.createFromResource(this, R.array.emptyAttr, android.R.layout.simple_spinner_item);
            cardAttrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            cardAttrSpinner.setAdapter(cardAttrAdapter);
            cardAttrSpinner.setEnabled(false);
        } else {
            Toast.makeText(getApplicationContext(), "Please choose a card kind", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

    public void onBackPressed() {
        Intent i = new Intent(CardListActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }

    // get cards
    private void getAllCards() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://db.ygoprodeck.com/api/v7/cardinfo.php", null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {

                            String data = response.getString("data");
                            JSONArray jsonArray = new JSONArray(data);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);


                                final ModelYGOCard card = new ModelYGOCard();
                                final String id = jsonObject.getString("id");
                                final String cardName = jsonObject.getString("name");
                                final String cardKind = jsonObject.getString("type");
                                final String cardType = jsonObject.getString("race");
                                final String cardEff = jsonObject.getString("desc");
                                card.setName(cardName);
                                card.setType(cardKind);
                                card.setRace(cardType);
                                card.setDesc(cardEff);
                                card.setId(id);

                                if (jsonObject.has("atk")) {
                                    final String cardAtk = jsonObject.getString("atk");
                                    card.setAtk(cardAtk);
                                } else {
                                    card.setAtk("/");
                                }
                                if (jsonObject.has("def")) {
                                    final String cardDef = jsonObject.getString("def");
                                    card.setDef(cardDef);
                                } else {
                                    card.setDef("/");
                                }
                                if (jsonObject.has("attribute")) {
                                    final String cardAttr = jsonObject.getString("attribute");
                                    card.setAttribute(cardAttr);
                                } else {
                                    card.setAttribute("/");
                                }
                                newsList.add(card);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("status: ", String.valueOf(error));

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

    private void filterAllCards() {
        pDialog.setMessage("Loading");
        showDialog();

        newsList.clear();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, "https://db.ygoprodeck.com/api/v7/cardinfo.php", null,

                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {

                            String data = response.getString("data");
                            JSONArray jsonArray = new JSONArray(data);

                            String cardKindSpinnerData = cardKindSpinner.getSelectedItem().toString();
                            String cardTypeSpinnerData = cardTypeSpinner.getSelectedItem().toString();
                            String cardAttrSpinnerData = cardAttrSpinner.getSelectedItem().toString();

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);


                                final ModelYGOCard card = new ModelYGOCard();


                                if (jsonObject.getString("type").contains(cardKindSpinnerData) && jsonObject.getString("race").contains(cardTypeSpinnerData)) {
                                    if (jsonObject.has("attribute")) {
                                        if (jsonObject.getString("attribute").contains(cardAttrSpinnerData) ) {
                                            final String id = jsonObject.getString("id");
                                            final String cardName = jsonObject.getString("name");
                                            final String cardKind = jsonObject.getString("type");
                                            final String cardType = jsonObject.getString("race");
                                            final String cardEff = jsonObject.getString("desc");
                                            card.setName(cardName);
                                            card.setType(cardKind);
                                            card.setRace(cardType);
                                            card.setDesc(cardEff);
                                            card.setId(id);

                                            if (jsonObject.has("atk")) {
                                                final String cardAtk = jsonObject.getString("atk");
                                                card.setAtk(cardAtk);
                                            } else {
                                                card.setAtk("/");
                                            }
                                            if (jsonObject.has("def")) {
                                                final String cardDef = jsonObject.getString("def");
                                                card.setDef(cardDef);
                                            } else {
                                                card.setDef("/");
                                            }
                                            if (jsonObject.has("attribute")) {
                                                final String cardAttr = jsonObject.getString("attribute");
                                                card.setAttribute(cardAttr);
                                            } else {
                                                card.setAttribute("/");
                                            }
                                            newsList.add(card);
                                        }
                                    } else {
                                        final String id = jsonObject.getString("id");
                                        final String cardName = jsonObject.getString("name");
                                        final String cardKind = jsonObject.getString("type");
                                        final String cardType = jsonObject.getString("race");
                                        final String cardEff = jsonObject.getString("desc");
                                        card.setName(cardName);
                                        card.setType(cardKind);
                                        card.setRace(cardType);
                                        card.setDesc(cardEff);
                                        card.setId(id);

                                        if (jsonObject.has("atk")) {
                                            final String cardAtk = jsonObject.getString("atk");
                                            card.setAtk(cardAtk);
                                        } else {
                                            card.setAtk("/");
                                        }
                                        if (jsonObject.has("def")) {
                                            final String cardDef = jsonObject.getString("def");
                                            card.setDef(cardDef);
                                        } else {
                                            card.setDef("/");
                                        }
                                        if (jsonObject.has("attribute")) {
                                            final String cardAttr = jsonObject.getString("attribute");
                                            card.setAttribute(cardAttr);
                                        } else {
                                            card.setAttribute("/");
                                        }
                                        newsList.add(card);
                                    }
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
                Log.v("status: ", String.valueOf(error));

                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });
        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }

}