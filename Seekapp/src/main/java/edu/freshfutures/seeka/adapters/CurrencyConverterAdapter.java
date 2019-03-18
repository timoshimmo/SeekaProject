package edu.freshfutures.seeka.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import edu.freshfutures.seeka.CountryDetailsActivity;
import edu.freshfutures.seeka.CustomWidgets.CustomTextViewLight;
import edu.freshfutures.seeka.FragmentCurrencyConverter;
import edu.freshfutures.seeka.R;
import edu.freshfutures.seeka.databases.EduQualificationDB;
import edu.freshfutures.seeka.volley.custom.application.AppController;

/**
 * Created by tokmang on 5/21/2016.
 */
public class CurrencyConverterAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    ArrayList<HashMap<String, ArrayList<String>>> currencyList;
    Context ctx;

    String baseCurrency;

    EduQualificationDB dbHandler;

    int countData;

    ProgressDialog progress;

    private static String TAG = CountryDetailsActivity.class.getSimpleName();

    protected static String TOKENID = "token_id";

    public CurrencyConverterAdapter(Context context, ArrayList<HashMap<String, ArrayList<String>>> cl) {
        this.ctx = context;
        this.currencyList = cl;

        dbHandler = new EduQualificationDB(context);
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.currency_converter_layout, parent, false);
        }

        holder.curNameSym = (CustomTextViewLight) convertView.findViewById(R.id.txtCurNameSym);
        holder.curCode = (CustomTextViewLight) convertView.findViewById(R.id.txtCurCode);

        holder.curNameSym.setText(currencyList.get(position).get(FragmentCurrencyConverter.CURRENCY_NAME).get(position) + " "
        + "("+currencyList.get(position).get(FragmentCurrencyConverter.CURRENCY_SYMBOL).get(position)+")");
        holder.curCode.setText(currencyList.get(position).get(FragmentCurrencyConverter.CURRENCY_CODE).get(position));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                baseCurrency = holder.curCode.getText().toString();
                countData = dbHandler.getCurrencyCount();

                if(countData > 0) {
                    dbHandler.deleteAllCurrencies();
                    getJsonObjectRequest();

                }
                else {
                    getJsonObjectRequest();
                }

                SharedPreferences baseCurr = ctx.getSharedPreferences("PREFBASECURRENCY", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = baseCurr.edit();
                editor.putString("BASECURRENCY", baseCurrency);
                editor.apply();

            }
        });

        return convertView;

    }

    public class Holder {
        CustomTextViewLight curNameSym;
        CustomTextViewLight curCode;
    }

    private void getJsonObjectRequest() {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/UtilityService/currencyFeed/";
        JSONObject obj = new JSONObject();

        SharedPreferences getSession = ctx.getSharedPreferences(ctx.getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);
        String session = getSession.getString(TOKENID, "");

        progress = new ProgressDialog(ctx);
        progress.setTitle("Updating Currency");
        progress.setMessage("Loading");

        progress.show();

        try {
            obj.put("sessionId", session);
            obj.put("baseCurrency", baseCurrency);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                JSONObject dataObj;
                JSONArray arrList;

                try {
                    String res = response.getString("status");

                    if(res.equals("1")) {

                        dataObj = response.getJSONObject("data");
                        arrList = dataObj.getJSONArray("listData");

                        double rates = 0;
                        String srcCode = null;
                        double srcRate = 0;
                        String upDates = null;
                        String destCode = null;

                        String getRates;

                        for(int i = 0; i < arrList.length(); i++) {

                            JSONObject objRow = arrList.getJSONObject(i);

                            if(!objRow.getString("Rate").equals("N/A")) {
                                getRates = objRow.getString("Rate");
                                srcRate = Double.valueOf(getRates);
                                rates = 1 / srcRate;
                            }
                            if(!objRow.getString("Name").equals("N/A")) {
                                srcCode = objRow.getString("Name");
                            }
                            if(!objRow.getString("Date").equals("N/A")) {
                                upDates = objRow.getString("Date");
                            }
                            if(!objRow.getString("id").equals("N/A")) {
                                destCode = objRow.getString("id");
                            }

                            dbHandler.insertCurrencies(destCode, rates, srcCode, srcRate, upDates);

                        }


                    }

                    progress.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(ctx.getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }
}
