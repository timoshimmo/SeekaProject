package edu.freshfutures.seeka;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsedCreditFragment extends Fragment {

    String[] reportAreaList;
    String[] caseIDList;
    String[] uniNameList;
    String[] courseList;
    String[] priceList;

    public static TableLayout tableTransHistory;

    public UsedCreditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_used_credit, container, false);

        tableTransHistory = (TableLayout) view.findViewById(R.id.used_credits_table);

        reportAreaList = new String[] {"Unlocked Course", "Apply for offer", "Unlocked Course", "Unlocked Course", "Unlocked Course"};
        caseIDList = new String[] {"2245 22458", "2245 22459", "2245 22460", "2255 24551", "2301 12421"};
        uniNameList = new String[] {"Royal Roads University", "Royal Roads University", "Royal Roads University",
                "Royal Roads University", "Royal Roads University"};
        courseList = new String[] {"Electrical Engineering",
                "Electronic And Electrical Engineering", "Mechanical Engineering",
                "Chemical Engineering",
                "Management Accounting"};
        priceList = new String[] {"1", "1", "1", "1", "1"};

        for(int i=0; i < caseIDList.length; i++) {

            TableRow transactionRow = (TableRow)LayoutInflater.from(getActivity()).inflate(R.layout.purchase_history_table_row, null);

            ((TextView)transactionRow.findViewById(R.id.txtReportArea)).setText(reportAreaList[i]);
            ((TextView)transactionRow.findViewById(R.id.txtCaseIDCode)).setText(caseIDList[i]);
            ((TextView)transactionRow.findViewById(R.id.txtUniName)).setText(uniNameList[i]);
            ((TextView)transactionRow.findViewById(R.id.txtCourse)).setText(courseList[i]);
            ((TextView)transactionRow.findViewById(R.id.txtPrice)).setText(priceList[i]);

           tableTransHistory.addView(transactionRow);

        }

        tableTransHistory.requestLayout();

        return view;
    }


}
