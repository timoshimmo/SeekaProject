package edu.freshfutures.seeka;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import edu.freshfutures.seeka.adapters.EduSysAdapter;
import edu.freshfutures.seeka.adapters.NationalityAdapter;
import edu.freshfutures.seeka.databases.EduQualificationDB;
import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProfileEdu extends Fragment implements View.OnClickListener {

    public static TextView txtLevel;
    public static TextView txtEduLvlOrigin;
    public static TextView txtEduSystem;
    public static TextView txtInstitution;

    public static TextView txtEditLevel;
    public static TextView txtEditEduLvlOrigin;
    public static TextView txtEditEduSystem;

    public static EditText txtEditInstitution;
    public static EditText txtsingleScore;
    public static EditText txtgpaScore;

    public static EditText txtgradeTitle1;
    public static EditText txtgradeTitle2;
    public static EditText txtgradeTitle3;
    public static EditText txtgradeTitle4;
    public static EditText txtgradeTitle5;

    public static EditText txtgrade1;
    public static EditText txtgrade2;
    public static EditText txtgrade3;
    public static EditText txtgrade4;
    public static EditText txtgrade5;

    public static EditText txtOverall;
    public static EditText txtRead;
    public static EditText txtWrite;
    public static EditText txtListen;
    public static EditText txtSpeak;

    public static Button btnUpdate;

    public static LinearLayout btnLevel;
    public static LinearLayout btnEduLvlOrigin;
    public static LinearLayout btnEduSystem;

    public static RadioGroup eduLevelGroup, engReqGroup;
    public static RadioButton rdHSchool, rdFYear, rdSYear, rdDiploma, rdDegree, rdGpa;
    public static RadioButton rdIelts, rdToefl, rdNo;
    public static Button btnlevelCancel, btnLevelOk;
    public static Button btnNltyCancel, btnNltyOk;

    String highschoolValue;
    String fYearValue;
    String sYearValue;
    String diplomaValue;
    String degreeValue;
    String gpaValue;

    String ctryName;
    String ctryCode;
    public static String eduSystems;

    public static AlertDialog levelAlertDialog;
    public static AlertDialog originAlertDialog;
    public static AlertDialog edysysAlertDialog;

    public static NationalityAdapter adptsss;
    public static EduSysAdapter adptsys;


    public static final String CTRY_NAME = "country_name";
    public static final String CTRY_CODE = "country_code";

    static HashMap<String, ArrayList<String>> map;
    static ArrayList<HashMap<String, ArrayList<String>>> countryList = null;

    static HashMap<String, ArrayList<String>> mapss;
    static ArrayList<HashMap<String, ArrayList<String>>> eduSysList = null;

    String getEditLevel;
    String getEditEduOrigin;
    public static String getEditEduSystem;
    String getEditInstitution;


    public static LinearLayout singleGradesBody;
    public static LinearLayout listGradesBody;

    public static TextView txtGradesTitle;

    public static String GradesTitleString;

    private EduQualificationDB databaseHelper;
    TextView txtExamTitle;
    LinearLayout engExamBody;

    protected static String TOKENID = "token_id";

    public FragmentProfileEdu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment...
        View view = inflater.inflate(R.layout.fragment_profile_edu, container, false);

        txtLevel = (TextView) view.findViewById(R.id.txtUserEduLevel);
        txtEduLvlOrigin = (TextView) view.findViewById(R.id.txtUserOrigin);
        txtEduSystem = (TextView) view.findViewById(R.id.txtUserEduSystem);
        txtInstitution = (TextView) view.findViewById(R.id.txtUserInstitution);

        txtEditLevel = (TextView) view.findViewById(R.id.txtEditedUserEduLevel);
        txtEditEduLvlOrigin = (TextView) view.findViewById(R.id.txtEditedUserEduOrigin);
        txtEditEduSystem = (TextView) view.findViewById(R.id.txtEditedUserEduSystem);

        txtEditInstitution = (EditText) view.findViewById(R.id.txtUserInstitutionName);

        btnUpdate = (Button) view.findViewById(R.id.btnUpdateEdu);

        btnLevel = (LinearLayout) view.findViewById(R.id.btnEduLevel);
        btnEduLvlOrigin = (LinearLayout) view.findViewById(R.id.btnEduOrigin);
        btnEduSystem = (LinearLayout) view.findViewById(R.id.btnEduSystem);

        engReqGroup = (RadioGroup) view.findViewById(R.id.engReqGroup);

        rdIelts = (RadioButton) view.findViewById(R.id.rdBtnIELTS);
        rdToefl = (RadioButton) view.findViewById(R.id.rdBtnTOEFL);
        rdNo = (RadioButton) view.findViewById(R.id.rdBtnNo);

        singleGradesBody = (LinearLayout) view.findViewById(R.id.SingleGradesBody);
        listGradesBody = (LinearLayout) view.findViewById(R.id.CourseGradesList);
        txtGradesTitle = (TextView) view.findViewById(R.id.SPMGradesTitle);

        txtgpaScore = (EditText) view.findViewById(R.id.convertedGPA);
        txtsingleScore = (EditText) view.findViewById(R.id.txtSingleScore);

        txtgrade1 = (EditText) view.findViewById(R.id.txtGrade1);
        txtgrade2 = (EditText) view.findViewById(R.id.txtGrade2);
        txtgrade3 = (EditText) view.findViewById(R.id.txtGrade3);
        txtgrade4 = (EditText) view.findViewById(R.id.txtGrade4);
        txtgrade5 = (EditText) view.findViewById(R.id.txtGrade5);

        txtgradeTitle1 = (EditText) view.findViewById(R.id.txtCourseGradesValue1);
        txtgradeTitle2 = (EditText) view.findViewById(R.id.txtCourseGradesValue2);
        txtgradeTitle3 = (EditText) view.findViewById(R.id.txtCourseGradesValue3);
        txtgradeTitle4 = (EditText) view.findViewById(R.id.txtCourseGradesValue4);
        txtgradeTitle5 = (EditText) view.findViewById(R.id.txtCourseGradesValue5);

        txtOverall = (EditText) view.findViewById(R.id.editTextOverall);
        txtRead = (EditText) view.findViewById(R.id.editTextRead);
        txtWrite = (EditText) view.findViewById(R.id.editTextListen);
        txtListen = (EditText) view.findViewById(R.id.editTextWrite);
        txtSpeak = (EditText) view.findViewById(R.id.editTextSpeak);

        txtExamTitle = (TextView) view.findViewById(R.id.txtExamNameTitle);
        engExamBody = (LinearLayout) view.findViewById(R.id.engExamResultsBody);

        databaseHelper=new EduQualificationDB(getActivity());

        btnLevel.setEnabled(false);
        btnEduLvlOrigin.setEnabled(false);
        btnEduSystem.setEnabled(false);

        btnEduSystem.setOnClickListener(this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEditLevel = txtEditLevel.getText().toString();
                getEditEduOrigin = txtEditEduLvlOrigin.getText().toString();
                getEditEduSystem = txtEditEduSystem.getText().toString();
                getEditInstitution = txtEditInstitution.getText().toString();

                if (!getEditLevel.equals("") || !getEditLevel.equals("-")) {
                    txtLevel.setText(getEditLevel);
                } else {
                    Toast.makeText(getActivity(), "You did not select a level", Toast.LENGTH_LONG).show();
                }

                if (!getEditEduOrigin.equals("") || !getEditEduOrigin.equals("-")) {
                    txtEduLvlOrigin.setText(getEditEduOrigin);
                } else {
                    Toast.makeText(getActivity(), "You did not select a country", Toast.LENGTH_LONG).show();
                }

                if (!getEditEduSystem.equals("") || !getEditEduSystem.equals("-")) {
                    txtEduSystem.setText(getEditEduSystem);
                } else {
                    Toast.makeText(getActivity(), "You did not select an education system", Toast.LENGTH_LONG).show();
                }

                if (!getEditInstitution.equals("")) {
                    txtInstitution.setText(getEditInstitution);
                } else {
                    Toast.makeText(getActivity(), "You did not enter an email", Toast.LENGTH_LONG).show();
                }

                txtLevel.setVisibility(View.VISIBLE);
                txtEduLvlOrigin.setVisibility(View.VISIBLE);
                txtEduSystem.setVisibility(View.VISIBLE);
                txtInstitution.setVisibility(View.VISIBLE);

                txtEditLevel.setVisibility(View.GONE);
                txtEditEduLvlOrigin.setVisibility(View.GONE);
                txtEditEduSystem.setVisibility(View.GONE);
                txtEditInstitution.setVisibility(View.GONE);

                makeJsonObjectRequest();

            }
        });


        engReqGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdBtnIELTS) {
                    txtExamTitle.setText("IELTS");
                    if (engExamBody.getVisibility() == View.GONE) {
                        engExamBody.setVisibility(View.VISIBLE);
                    }
                } else if (checkedId == R.id.rdBtnTOEFL) {
                    txtExamTitle.setText("TOEFL");
                    if (engExamBody.getVisibility() == View.GONE) {
                        engExamBody.setVisibility(View.VISIBLE);
                    }
                } else {
                    txtExamTitle.setText("");
                    if (engExamBody.getVisibility() == View.VISIBLE) {
                        engExamBody.setVisibility(View.GONE);

                    }
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        getCodeOnLoad();
        // initEduSystemData();
        eduLevelDialog();
        getOriginDialog();

    }

    private void makeJsonObjectRequest() {

        String requestURL = "http://ec2-52-74-92-131.ap-southeast-1.compute.amazonaws.com:8080/FF_WS/services/rest/UserService/userEducation/";

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getResources().getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);

        String sId = sharedPref.getString(TOKENID, "");

        //String requestURL = Url.URL_UPDATE_PROFILE_INFO;
        JSONObject obj = new JSONObject();
        JSONObject subjectsObj = new JSONObject();
        JSONObject subjectsScores = new JSONObject();

        JSONObject ieltsToefflScores = new JSONObject();
        JSONObject ieltsToefflSubjects = new JSONObject();


        try {


            if(getEditEduOrigin.equals("")) {
                obj.put("eduCountry", txtEduLvlOrigin.getText().toString());
            }
            else {
                obj.put("eduCountry", getEditEduOrigin);
            }

            if(getEditEduSystem.equals("")) {
                obj.put("eduSystem", txtEduSystem.getText().toString());
            }
            else {
                obj.put("eduSystem", getEditEduSystem);
            }

            if(getEditInstitution.equals("")) {
                obj.put("eduInst", txtInstitution.getText().toString());
            }
            else {
                obj.put("eduInst", getEditInstitution);
            }

            if(getEditInstitution.equals("")) {
                obj.put("eduLevel", txtLevel.getText().toString());
            }
            else {
                obj.put("eduLevel", getEditLevel);
            }

            if(!txtgpaScore.getText().toString().equals("")) {
                obj.put("gpaScore", txtgpaScore.getText().toString());
            }

            if(listGradesBody.getVisibility() == View.VISIBLE) {

                if(txtgradeTitle1.getText().toString().equals("") || txtgradeTitle2.getText().toString().equals("") ||
                        txtgradeTitle3.getText().toString().equals("") || txtgradeTitle4.getText().toString().equals("") ||
                        txtgradeTitle5.getText().toString().equals("") || txtgrade1.getText().toString().equals("") ||
                        txtgrade2.getText().toString().equals("") || txtgrade3.getText().toString().equals("") ||
                        txtgrade4.getText().toString().equals("") || txtgrade5.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Empty grade field found. All fields are required. Make sure all fields are filled before updating", Toast.LENGTH_LONG).show();

                }

                else {

                    subjectsScores.put(txtgradeTitle1.getText().toString(), txtgrade1.getText().toString());
                    subjectsScores.put(txtgradeTitle2.getText().toString(), txtgrade2.getText().toString());
                    subjectsScores.put(txtgradeTitle3.getText().toString(), txtgrade3.getText().toString());
                    subjectsScores.put(txtgradeTitle4.getText().toString(), txtgrade4.getText().toString());
                    subjectsScores.put(txtgradeTitle5.getText().toString(), txtgrade5.getText().toString());

                    subjectsObj.put("subjects", subjectsScores);
                    obj.put("cambrigeSubGrds", subjectsObj);
                }

            }

            else if(singleGradesBody.getVisibility() == View.VISIBLE) {
                obj.put("eduSystemScore", Float.valueOf(txtsingleScore.getText().toString()));
            }

            obj.put("isEngMedium", "Y");

            if(rdIelts.isChecked()) {

                if(txtOverall.equals("") || txtRead.equals("") || txtWrite.equals("") || txtSpeak.equals("") || txtListen.equals("")) {

                    Toast.makeText(getActivity(), "Your IELTS results are not filled. All fields are required.", Toast.LENGTH_LONG).show();

                }
                else {

                    ieltsToefflScores.put("overall", txtOverall.getText().toString());
                    ieltsToefflScores.put("read", txtRead.getText().toString());
                    ieltsToefflScores.put("write", txtWrite.getText().toString());
                    ieltsToefflScores.put("speak", txtSpeak.getText().toString());
                    ieltsToefflScores.put("listen", txtListen.getText().toString());

                    ieltsToefflSubjects.put("subjects", ieltsToefflScores);
                    obj.put("ieltsToffelScore", ieltsToefflSubjects);
                }

                obj.put("ieltsToffel", "IELTS");

            }

            if(rdToefl.isChecked()) {

                if(txtOverall.equals("") || txtRead.equals("") || txtWrite.equals("") || txtSpeak.equals("") || txtListen.equals("")) {

                    Toast.makeText(getActivity(), "Your TOFFEL results are not filled. All fields are required.", Toast.LENGTH_LONG).show();

                }
                else {

                    ieltsToefflScores.put("overall", txtOverall.getText().toString());
                    ieltsToefflScores.put("read", txtRead.getText().toString());
                    ieltsToefflScores.put("write", txtWrite.getText().toString());
                    ieltsToefflScores.put("speak", txtSpeak.getText().toString());
                    ieltsToefflScores.put("listen", txtListen.getText().toString());

                    ieltsToefflSubjects.put("subjects", ieltsToefflScores);
                    obj.put("ieltsToffelScore", ieltsToefflSubjects);
                }
                obj.put("ieltsToffel", "TOEFL");

            }

            if(rdNo.isChecked()) {
                obj.put("ieltsToffel", "N");
            }

            obj.put("sessionId", sId);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                requestURL, obj, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {

                    String result = response.getString("message");
                    Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);

    }

    public void eduLevelDialog() {

        btnLevel.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaters = (getActivity()).getLayoutInflater();

        View dialogView = inflaters.inflate(R.layout.edu_level_dialog_layout, null);
        builder.setView(dialogView);

        btnlevelCancel = (Button) dialogView.findViewById(R.id.btnCancelLevel);
        btnLevelOk = (Button) dialogView.findViewById(R.id.btnAcceptLevel);
        eduLevelGroup = (RadioGroup) dialogView.findViewById(R.id.rdEduLevelGroup);
        rdHSchool = (RadioButton) dialogView.findViewById(R.id.radioHighSchool);
        rdFYear = (RadioButton) dialogView.findViewById(R.id.radioFirstYear);
        rdSYear = (RadioButton) dialogView.findViewById(R.id.radioSecondYear);
        rdDiploma = (RadioButton) dialogView.findViewById(R.id.radioDiploma);
        rdDegree = (RadioButton) dialogView.findViewById(R.id.radioDegree);
        rdGpa = (RadioButton) dialogView.findViewById(R.id.radioGPA);

        levelAlertDialog = builder.create();

        eduLevelGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioHighSchool) {
                    highschoolValue = "High School";
                } else if (checkedId == R.id.radioFirstYear) {
                    fYearValue = "First Year (University)";
                } else if (checkedId == R.id.radioSecondYear) {
                    sYearValue = "Second Year (University)";
                } else if (checkedId == R.id.radioDiploma) {
                    diplomaValue = "Diploma";
                } else if (checkedId == R.id.radioDegree) {
                    degreeValue = "Degree";
                } else {
                    gpaValue = "GPA";
                }

            }
        });

        btnlevelCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelAlertDialog.dismiss();
            }
        });

        btnLevelOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = eduLevelGroup.getCheckedRadioButtonId();

                txtEditLevel.setGravity(Gravity.LEFT);

                // find which radioButton is checked by id
                if (selectedId == rdHSchool.getId()) {
                    txtEditLevel.setText(highschoolValue);
                } else if (selectedId == rdFYear.getId()) {
                    txtEditLevel.setText(fYearValue);
                } else if (selectedId == rdSYear.getId()) {
                    txtEditLevel.setText(sYearValue);
                } else if (selectedId == rdDiploma.getId()) {
                    txtEditLevel.setText(diplomaValue);
                } else if (selectedId == rdDegree.getId()) {
                    txtEditLevel.setText(degreeValue);
                } else {
                    txtEditLevel.setText(gpaValue);
                }

                levelAlertDialog.dismiss();

            }
        });

    }

    public void getCodeOnLoad() {


        try {
            JSONArray obj = new JSONArray(loadJSONFromAsset());

            JSONObject codeObj = new JSONObject();

            for (int i = 0; i < obj.length(); i++) {

                JSONObject currObj = obj.getJSONObject(i);

                String countryName = currObj.getString("COUNTRY_TXT");
                String countryCode = currObj.getString("COUNTRY_CODE");

                codeObj.put(countryName, countryCode);

                SharedPreferences settings = getActivity().getSharedPreferences("JSONCTRYCODEPREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("JSONCTRYCODE", codeObj.toString());
                editor.apply();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getOriginDialog() {

        btnEduLvlOrigin.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaters = (getActivity()).getLayoutInflater();

        View convertView = inflaters.inflate(R.layout.fragment_nationality, null);
        builder.setView(convertView);

        btnNltyCancel = (Button) convertView.findViewById(R.id.btnCancelNationality);
        btnNltyOk = (Button) convertView.findViewById(R.id.btnAcceptNationality);

        countryList = new ArrayList<>();
        map = new HashMap<>();

        try {

            JSONArray obj = new JSONArray(loadJSONFromAsset());
            ArrayList<String> ctryName = new ArrayList<>();
            ArrayList<String> ctryCode = new ArrayList<>();


            for (int i = 0; i < obj.length(); i++) {

                JSONObject currObj = obj.getJSONObject(i);

                String countryName = currObj.getString("COUNTRY_TXT");
                String countryCode = currObj.getString("COUNTRY_CODE");

                ctryName.add(countryName);
                ctryCode.add(countryCode);

                map.put(CTRY_NAME, ctryName);
                map.put(CTRY_CODE, ctryCode);

                countryList.add(map);

            }

        }

        catch (JSONException e) {
            e.printStackTrace();
        }

        ListView lisCountry = (ListView) convertView.findViewById(R.id.lisNationality);
        adptsss = new NationalityAdapter(getActivity(), countryList);

        lisCountry.setAdapter(adptsss);

        lisCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adptsss.setSelectedIndex(position);
                ctryName = countryList.get(position).get(CTRY_NAME).get(position);
                ctryCode = countryList.get(position).get(CTRY_CODE).get(position);
                adptsss.notifyDataSetChanged();

            }
        });

        originAlertDialog = builder.create();

        btnNltyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                originAlertDialog.dismiss();
            }
        });

        btnNltyOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEditEduLvlOrigin.setText(ctryName);

                SharedPreferences settings = getActivity().getSharedPreferences("CTRYCODEPREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("CTRYCODE", ctryCode);
                editor.apply();

                originAlertDialog.dismiss();
            }
        });

    }

    public void getEduSystemDialog() {

        btnEduSystem.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaters = (getActivity()).getLayoutInflater();

        View convertView = inflaters.inflate(R.layout.fragment_nationality, null);
        builder.setView(convertView);

        btnNltyCancel = (Button) convertView.findViewById(R.id.btnCancelNationality);
        btnNltyOk = (Button) convertView.findViewById(R.id.btnAcceptNationality);
        TextView title = (TextView) convertView.findViewById(R.id.txtProfPopupTitle);

        eduSysList = new ArrayList<>();
        mapss = new HashMap<>();

        title.setText("Education System");

        try {

            JSONArray obj = new JSONArray(loadEduJSONFromAsset());
            ArrayList<String> eduSysName = new ArrayList<>();

            SharedPreferences getCtryCode = getActivity().getSharedPreferences("CTRYCODEPREF", Context.MODE_PRIVATE);
            String countryCode = getCtryCode.getString("CTRYCODE", "");

            for (int i = 0; i < obj.length(); i++) {

                JSONObject currObj = obj.getJSONObject(i);
                String edu_ctry_code = currObj.getString("country_code");

                if(edu_ctry_code.equals("00") || edu_ctry_code.equals(countryCode)) {

                    eduSysName.add(currObj.getString("sys_edu_txt"));
                    mapss.put(CTRY_NAME, eduSysName);
                    eduSysList.add(mapss);

                }

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        ListView lisCountry = (ListView) convertView.findViewById(R.id.lisNationality);
        adptsys = new EduSysAdapter(getActivity(), eduSysList);

        lisCountry.setAdapter(adptsys);

        lisCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adptsys.setSelectedIndex(position);
                eduSystems = eduSysList.get(position).get(CTRY_NAME).get(position);
                adptsys.notifyDataSetChanged();


            }
        });

        edysysAlertDialog = builder.create();
        btnNltyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edysysAlertDialog.dismiss();
            }
        });
        btnNltyOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtEditEduSystem.setText(eduSystems);
                edysysAlertDialog.dismiss();
                setEduSysTypeVisible(eduSystems);

            }
        });

    }

    public static void setEduSysTypeVisible(String eduSystem) {

       // eduSystemString = txtEditEduSystem.getText().toString();

        GradesTitleString = eduSystem + " " + "Grades";
        txtGradesTitle.setText(GradesTitleString);

        txtGradesTitle.setVisibility(View.VISIBLE);

        if(eduSystem.equalsIgnoreCase("First Year (University)") || eduSystem.equalsIgnoreCase("Second Year (University)")
                || eduSystem.equalsIgnoreCase("Degree") || eduSystem.equalsIgnoreCase("Diploma") || eduSystem.equalsIgnoreCase("GPA")
                || eduSystem.equalsIgnoreCase("SAT") || eduSystem.equalsIgnoreCase("HSC")) {

                if(singleGradesBody.getVisibility() == View.GONE) {
                    singleGradesBody.setVisibility(View.VISIBLE);
                }

            if(listGradesBody.getVisibility() == View.VISIBLE) {
                listGradesBody.setVisibility(View.GONE);
            }

        }

        else {
            if(listGradesBody.getVisibility() == View.GONE) {
                listGradesBody.setVisibility(View.VISIBLE);
            }

            if(singleGradesBody.getVisibility() == View.VISIBLE) {
                singleGradesBody.setVisibility(View.GONE);
            }
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("country/country_code.json");
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

    public String loadEduJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("edusystem/edu_sys_code.json");
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


    @Override
    public void onClick(View v) {

        if(v == btnLevel) {
            levelAlertDialog.show();
        }

        if(v == btnEduLvlOrigin) {
            originAlertDialog.show();
        }

        if (v == btnEduSystem) {
            getEduSystemDialog();
            edysysAlertDialog.show();

        }

    }
}
