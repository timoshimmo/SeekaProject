package edu.freshfutures.seeka;


import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import edu.freshfutures.seeka.adapters.CurrencyConverterAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCurrencyConverter extends DialogFragment {

    ListView lisCurrencies;
    Context ctx;
    public static final String CURRENCY_NAME = "currency_name";
    public static final String CURRENCY_SYMBOL = "currency_symbol";
    public static final String CURRENCY_CODE = "currency_code";

    ImageButton btnBack;
    ImageButton btnSettings;

    static HashMap<String, ArrayList<String>> map;
    static ArrayList<HashMap<String, ArrayList<String>>> currencyList = null;

    Dialog d;
    private static String TAG = CountryDetailsActivity.class.getSimpleName();

    public FragmentCurrencyConverter() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MY_DIALOG);
    }

    @Override
    public void onStart() {
        super.onStart();
        d = getDialog();
        if (d!=null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_currency, container, false);

        ctx = getActivity();

        btnBack = (ImageButton) view.findViewById(R.id.btnBack);
        btnSettings = (ImageButton) view.findViewById(R.id.btnSettings);

        currencyList = new ArrayList<>();
        map = new HashMap<>();

        try {

            JSONObject obj = new JSONObject(loadJSONFromAsset());

            ArrayList<String> currDataName = new ArrayList<>();
            ArrayList<String> currDataSymbol = new ArrayList<>();
            ArrayList<String> currDataCode = new ArrayList<>();

            for (int i = 0; i < obj.names().length(); i++) {

                String str = (String) obj.names().get(i);
                JSONObject currObj = obj.getJSONObject(str);

                currDataName.add(currObj.getString("name"));
                currDataSymbol.add(currObj.getString("symbol_native"));
                currDataCode.add(currObj.getString("code"));

                map.put(CURRENCY_NAME, currDataName);
                map.put(CURRENCY_SYMBOL, currDataSymbol);
                map.put(CURRENCY_CODE, currDataCode);

                currencyList.add(map);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        lisCurrencies = (ListView) view.findViewById(R.id.listCurrencies);
        lisCurrencies.setAdapter(new CurrencyConverterAdapter(ctx, currencyList));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SettingsPopup newFragments = new SettingsPopup();

                FragmentTransaction transactions = fragmentManager.beginTransaction();

                newFragments.show(transactions, "settingsDialogs");
            }
        });

        return view;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("currency/common_currency.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("dialogsConverter");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }

}
