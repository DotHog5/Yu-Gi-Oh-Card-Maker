package com.yugioh.cardmaker.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yugioh.cardmaker.R;
import com.yugioh.cardmaker.server.BaseURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EditDeleteCardActivity extends AppCompatActivity {

    EditText edtCardCode, edtCardName, edtCardKind, edtCardType, edtCardAttr, edtCardAtt, edtCardDef, edtCardEff;
    Button btnEditData, deleteData;

    String strCardCode, strCardName, strCardKind, strCardType, strCardAttr, strCardAtt, strCardDef, strCardEff, _id;

    private RequestQueue mRequestQueue;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_card);
        getSupportActionBar().hide();

        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtCardCode = (EditText) findViewById(R.id.edtCardCode);
        edtCardName = (EditText) findViewById(R.id.edtCardName);
        edtCardKind = (EditText) findViewById(R.id.edtCardKind);
        edtCardType = (EditText) findViewById(R.id.edtCardType);
        edtCardAttr = (EditText) findViewById(R.id.edtCardAttr);
        edtCardAtt = (EditText) findViewById(R.id.edtCardAtt);
        edtCardDef = (EditText) findViewById(R.id.edtCardDef);
        edtCardEff = (EditText) findViewById(R.id.edtCardEff);

        btnEditData = (Button) findViewById(R.id.btnEditData);
        deleteData = (Button) findViewById(R.id.deleteData);

        Intent i = getIntent();
        strCardCode = i.getStringExtra("cardCode");
        strCardName = i.getStringExtra("cardName");
        strCardKind = i.getStringExtra("cardKind");
        strCardType = i.getStringExtra("cardType");
        strCardAttr = i.getStringExtra("cardAttr");
        strCardAtt = i.getStringExtra("cardAtt");
        strCardDef = i.getStringExtra("cardDef");
        strCardEff = i.getStringExtra("cardEff");
        _id = i.getStringExtra("_id");


        edtCardCode.setText(strCardCode);
        edtCardName.setText(strCardName);
        edtCardKind.setText(strCardKind);
        edtCardType.setText(strCardType);
        edtCardAttr.setText(strCardAttr);
        edtCardAtt.setText(strCardAtt);
        edtCardDef.setText(strCardDef);
        edtCardEff.setText(strCardEff);

        btnEditData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editData();
            }
        });

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDeleteCardActivity.this);

                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete the card " + strCardName + " ? ");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        deleteData();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }

    public void editData(){

        // Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("cardCode", edtCardCode.getText().toString());
        params.put("cardName", edtCardName.getText().toString());
        params.put("cardKind", edtCardKind.getText().toString());
        params.put("cardType", edtCardType.getText().toString());
        params.put("cardAttr", edtCardAttr.getText().toString());
        params.put("cardAtt", edtCardAtt.getText().toString());
        params.put("cardDef", edtCardDef.getText().toString());
        params.put("cardEff", edtCardEff.getText().toString());

        pDialog.setMessage("Please wait.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.PUT,BaseURL.editCard + _id, new JSONObject(params),
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    hideDialog();
                    try {
                        String strMsg = response.getString("msg");
                        boolean status= response.getBoolean("err");

                        Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                        if (status == false){
                            Intent i = new Intent(EditDeleteCardActivity.this, ActivityCard.class);
                            startActivity(i);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("Error: ", error.getMessage());
                    hideDialog();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        mRequestQueue.add(req);
    }

    public void deleteData(){

        pDialog.setMessage("Deleting card.....");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE,BaseURL.deleteCard + _id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String strMsg = response.getString("msg");
                            boolean status= response.getBoolean("err");

                            if (status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(EditDeleteCardActivity.this, ActivityCard.class);
                                startActivity(i);
                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
                hideDialog();
            }
        });
        mRequestQueue.add(req);
    }

    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}
