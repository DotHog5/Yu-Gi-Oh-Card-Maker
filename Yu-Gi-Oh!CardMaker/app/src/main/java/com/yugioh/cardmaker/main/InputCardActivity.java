package com.yugioh.cardmaker.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.yugioh.cardmaker.R;
import com.yugioh.cardmaker.server.BaseURL;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class InputCardActivity extends AppCompatActivity {

    EditText inputCardCode, inputCardName, inputCardKind, inputCardType, inputCardAttr, inputCardAtt, inputCardDef, inputCardEff;

    Button saveData;
    private RequestQueue mRequestQueue;

    Bitmap bitmap;

    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_card);

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

        inputCardCode = (EditText) findViewById(R.id.inputCardCode);
        inputCardName = (EditText) findViewById(R.id.inputCardName);
        inputCardKind = (EditText) findViewById(R.id.inputCardKind);
        inputCardType = (EditText) findViewById(R.id.inputCardType);
        inputCardAttr = (EditText) findViewById(R.id.inputCardAttr);
        inputCardAtt = (EditText) findViewById(R.id.inputCardAtt);
        inputCardDef = (EditText) findViewById(R.id.inputCardDef);
        inputCardEff = (EditText) findViewById(R.id.inputCardEff);

        saveData = (Button) findViewById(R.id.saveData);

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCard(bitmap);
            }
        });
    }

    private void inputCard(final Bitmap bitmap) {
        pDialog.setMessage("Loading .........");
        showDialog();
        Map<String, String> params = new HashMap<>();
        params.put("cardCode", inputCardCode.getText().toString());
        params.put("cardName", inputCardName.getText().toString());
        params.put("cardKind", inputCardKind.getText().toString());
        params.put("cardType", inputCardType.getText().toString());
        params.put("cardAttr", inputCardAttr.getText().toString());
        params.put("cardAtt", inputCardAtt.getText().toString());
        params.put("cardDef", inputCardDef.getText().toString());
        params.put("cardEff", inputCardEff.getText().toString());

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.inputCard, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mRequestQueue.getCache().clear();
                hideDialog();
                try {
                    String strMsg = response.getString("msg");
                    boolean status = response.getBoolean("err");

                    if (status == false) {
                        Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(InputCardActivity.this, ActivityCard.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        Volley.newRequestQueue(InputCardActivity.this).add(req);
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

    @Override
    public void onBackPressed() {
        Intent i = new Intent(InputCardActivity.this, HomeActivity.class);
        startActivity(i);
        finish();
    }
}
