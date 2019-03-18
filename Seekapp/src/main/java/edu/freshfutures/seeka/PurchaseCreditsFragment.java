package edu.freshfutures.seeka;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.freshfutures.seeka.Models.PurchaseHistoryModel;
import edu.freshfutures.seeka.Models.TransactionHistoryModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class PurchaseCreditsFragment extends Fragment {

    public static TableLayout tablePurHistory;

    public PurchaseCreditsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_credits, container, false);

        tablePurHistory = (TableLayout) view.findViewById(R.id.purchase_credits_table);

        return view;

    }

    public void getResponse(String response, Activity context) {

        try {
            JSONObject result = new JSONObject(response);

            if (!result.isNull("data")) {

                JSONObject data = result.getJSONObject("data");
                JSONArray jsonPurHistory = data.getJSONArray("pointPurchaseHistoryList");

                for (int i = 0; i < jsonPurHistory.length(); i++) {

                    JSONObject purHis = jsonPurHistory.getJSONObject(i);

                    TableRow purchaseRow = (TableRow) LayoutInflater.from(context).inflate(R.layout.purchase_history_table_row, null);

                    ((TextView) purchaseRow.findViewById(R.id.txtCaseIDCode)).setText(purHis.getString("transRefId"));
                    ((TextView) purchaseRow.findViewById(R.id.txtUniName)).setText(purHis.getString("paymentMethod"));
                    ((TextView) purchaseRow.findViewById(R.id.txtCourse)).setText(String.valueOf(purHis.getInt("balancePoints")));
                    ((TextView) purchaseRow.findViewById(R.id.txtPrice)).setText(String.valueOf(purHis.getInt("purchasedPoints")));

                    tablePurHistory.addView(purchaseRow);

                }

                tablePurHistory.requestLayout();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
