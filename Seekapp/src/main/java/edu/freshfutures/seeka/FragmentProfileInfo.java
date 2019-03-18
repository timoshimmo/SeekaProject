package edu.freshfutures.seeka;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pkmmte.view.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import edu.freshfutures.seeka.Models.ProfileInfoModel;
import edu.freshfutures.seeka.Models.ProfileMainInterestModel;
import edu.freshfutures.seeka.adapters.NationalityAdapter;
import edu.freshfutures.seeka.util.urls.Url;
import edu.freshfutures.seeka.volley.custom.application.AppController;

public class FragmentProfileInfo extends Fragment implements View.OnClickListener{

    ImageButton btnEditPic;

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "ProfileActivity";

    public static TextView txtEmail;
    public static TextView txtFirstName;
    public static TextView txtLastName;
    public static TextView txtSkypeID;
    public static TextView txtPhoneNo;

    public static EditText txtUserEmail;
    public static EditText txtUserFirstName;
    public static EditText txtUserLastName;
    public static EditText txtUserSkypeId;
    public static EditText txtUserPhoneNo;

    public static Button btnUpdate;

    public static TextView txtGender;
    public static TextView txtBirthday;
    public static TextView txtOrigin;
    public static TextView txtCitizenship;

    public static TextView txtEditGender;
    public static TextView txtEditBirthday;
    public static TextView txtEditOrigin;
    public static TextView txtEditCitizenship;

    public static LinearLayout btnGender;
    public static LinearLayout btnBirthday;
    public static LinearLayout btnOrigin;
    public static LinearLayout btnCitizenship;

    CircularImageView circularImageView;
    ImageView imgBlurredPic;

    RadioGroup genderGroup;
    RadioButton rdMale, rdFemale, rdOther;
    Button btnGenderCancel, btnGenderOk;
    Button btnNltyCancel, btnNltyOk;

    String genderMValue;
    String genderFValue;
    String genderOValue;

    String ctryName;

    SimpleDateFormat dateFormatter;

    AlertDialog alertDialog;
    AlertDialog originAlertDialog;
    AlertDialog citizenAlertDialog;

    NationalityAdapter adpt;

    NationalityAdapter adpts;

    public static final String CTRY_NAME = "country_name";

    static HashMap<String, ArrayList<String>> map;
    static ArrayList<HashMap<String, ArrayList<String>>> countryList = null;

    String getEditEmail;
    String getEditfname;
    String getEditLname;
    String getEditSkypeID;
    String getEditPhoneNo;
    String getEditGender;
    String getEditBday;
    String getEditOrigin;
    String getEditCitizenship;

    protected static String TOKENID = "token_id";

    ProfileInfoModel infoModel;

    public FragmentProfileInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_info, container, false);

        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        circularImageView = (CircularImageView) view.findViewById(R.id.imageProfImg);
        imgBlurredPic = (ImageView) view.findViewById(R.id.imgBlurredPic);

        txtEmail = (TextView) view.findViewById(R.id.txtEmail);
        txtFirstName = (TextView) view.findViewById(R.id.txtFName);
        txtLastName = (TextView) view.findViewById(R.id.txtLName);
        txtSkypeID = (TextView) view.findViewById(R.id.txtSkypeId);
        txtPhoneNo = (TextView) view.findViewById(R.id.txtUserPhone);

        txtUserEmail = (EditText) view.findViewById(R.id.txtUserEmail);
        txtUserFirstName = (EditText) view.findViewById(R.id.txtUserFirstName);
        txtUserLastName = (EditText) view.findViewById(R.id.txtUserLastName);
        txtUserSkypeId = (EditText) view.findViewById(R.id.txtUserSkypeId);
        txtUserPhoneNo = (EditText) view.findViewById(R.id.txtUserPhoneNo);

        btnUpdate = (Button) view.findViewById(R.id.btnUpdateInfo);

        txtGender = (TextView) view.findViewById(R.id.txtUserGender);
        txtBirthday = (TextView) view.findViewById(R.id.txtUserBday);
        txtOrigin = (TextView) view.findViewById(R.id.txtUserOrigin);
        txtCitizenship = (TextView) view.findViewById(R.id.txtUserCitizenship);

        txtEditGender = (TextView) view.findViewById(R.id.txtEditedUserGender);
        txtEditBirthday = (TextView) view.findViewById(R.id.txtEditedUserBday);
        txtEditOrigin = (TextView) view.findViewById(R.id.txtEditedUserOrigin);
        txtEditCitizenship = (TextView) view.findViewById(R.id.txtEditedUserCitizenship);

        btnGender = (LinearLayout) view.findViewById(R.id.btnGender);
        btnBirthday = (LinearLayout) view.findViewById(R.id.btnBday);
        btnOrigin = (LinearLayout) view.findViewById(R.id.btnOrigin);
        btnCitizenship = (LinearLayout) view.findViewById(R.id.btnCitizenship);

        btnEditPic = (ImageButton) view.findViewById(R.id.imgEditProfPic);

        infoModel = new ProfileInfoModel();

        btnGender.setEnabled(false);
        btnBirthday.setEnabled(false);
        btnOrigin.setEnabled(false);
        btnCitizenship.setEnabled(false);

       // displayDetails();

        genderDialog();
        btnBirthday.setOnClickListener(this);
        getOriginDialog();
        getCitizenshipDialog();


        btnEditPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getEditEmail = txtUserEmail.getText().toString();
                getEditfname = txtUserFirstName.getText().toString();
                getEditLname = txtUserLastName.getText().toString();
                getEditSkypeID = txtUserSkypeId.getText().toString();
                getEditPhoneNo = txtUserPhoneNo.getText().toString();

                getEditGender = txtEditGender.getText().toString();
                getEditBday = txtEditBirthday.getText().toString();
                getEditOrigin = txtEditOrigin.getText().toString();
                getEditCitizenship = txtEditCitizenship.getText().toString();


                if (!getEditEmail.equals("")) {
                    txtEmail.setText(getEditEmail);
                } else {
                    Toast.makeText(getActivity(), "You did not enter an email", Toast.LENGTH_LONG).show();
                }

                if (!getEditfname.equals("") || !getEditfname.equals("-")) {
                    txtFirstName.setText(getEditfname);
                } else {
                    Toast.makeText(getActivity(), "You did not enter first name", Toast.LENGTH_LONG).show();
                }

                if (!getEditLname.equals("") || !getEditLname.equals("-")) {
                    txtLastName.setText(getEditLname);
                } else {
                    Toast.makeText(getActivity(), "You did not enter last name", Toast.LENGTH_LONG).show();
                }

                if (!getEditSkypeID.equals("") || !getEditSkypeID.equals("-")) {
                    txtSkypeID.setText(getEditSkypeID);
                } else {
                    txtSkypeID.setText("-");
                }

                if (!getEditPhoneNo.equals("") || !getEditPhoneNo.equals("-")) {
                    txtPhoneNo.setText(getEditPhoneNo);
                } else {
                    txtPhoneNo.setText("-");
                }

                if (!getEditGender.equals("") || !getEditGender.equals("-")) {
                    txtGender.setText(getEditGender);
                } else {
                    Toast.makeText(getActivity(), "You did not select a gender", Toast.LENGTH_LONG).show();
                }

                if (!getEditBday.equals("") || !getEditBday.equals("-")) {
                    txtBirthday.setText(getEditBday);
                } else {
                    Toast.makeText(getActivity(), "You did not select a date of birth", Toast.LENGTH_LONG).show();
                }

                if (!getEditOrigin.equals("") || !getEditOrigin.equals("-")) {
                    txtOrigin.setText(getEditOrigin);
                } else {
                    Toast.makeText(getActivity(), "You did not select your country of origin", Toast.LENGTH_LONG).show();
                }

                if (!getEditCitizenship.equals("") || !getEditCitizenship.equals("-")) {
                    txtCitizenship.setText(getEditCitizenship);
                } else {
                    txtCitizenship.setText("-");
                }

                txtEmail.setVisibility(View.VISIBLE);
                txtFirstName.setVisibility(View.VISIBLE);
                txtLastName.setVisibility(View.VISIBLE);
                txtSkypeID.setVisibility(View.VISIBLE);
                txtPhoneNo.setVisibility(View.VISIBLE);

                txtUserEmail.setVisibility(View.GONE);
                txtUserFirstName.setVisibility(View.GONE);
                txtUserLastName.setVisibility(View.GONE);
                txtUserSkypeId.setVisibility(View.GONE);
                txtUserPhoneNo.setVisibility(View.GONE);

                txtGender.setVisibility(View.VISIBLE);
                txtBirthday.setVisibility(View.VISIBLE);
                txtOrigin.setVisibility(View.VISIBLE);
                txtCitizenship.setVisibility(View.VISIBLE);

                txtEditGender.setVisibility(View.GONE);
                txtEditBirthday.setVisibility(View.GONE);
                txtEditOrigin.setVisibility(View.GONE);
                txtEditCitizenship.setVisibility(View.GONE);

                makeJsonUpdateRequest();

            }
        });

        return view;
    }

    private void makeJsonUpdateRequest() {

        String requestURL = Url.URL_UPDATE_PROFILE_INFO;
        JSONObject obj = new JSONObject();

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getResources().getString(R.string.SEEKA_PREF_SESSION), Context.MODE_PRIVATE);

        String sId = sharedPref.getString(TOKENID, "");

        try {

            obj.put("firstName", txtUserFirstName.getText().toString());
            obj.put("lastName", txtUserLastName.getText().toString());
            obj.put("dob", txtEditBirthday.getText().toString());
            obj.put("skypeId", txtUserSkypeId.getText().toString());
            obj.put("mobileNo", txtUserPhoneNo.getText().toString());
            obj.put("gender", txtEditGender.getText().toString());
            obj.put("coutOfOrign", txtEditOrigin.getText().toString());
            obj.put("citizenship", txtEditCitizenship.getText().toString());
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

    public void genderDialog() {

        btnGender.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaters = (getActivity()).getLayoutInflater();

        View dialogView = inflaters.inflate(R.layout.gender_dialog_layout, null);
        builder.setView(dialogView);

        btnGenderCancel = (Button) dialogView.findViewById(R.id.btnCancelGender);
        btnGenderOk = (Button) dialogView.findViewById(R.id.btnAcceptGender);

        genderGroup = (RadioGroup) dialogView.findViewById(R.id.rdGenderGroup);

        rdMale = (RadioButton) dialogView.findViewById(R.id.radioMale);
        rdFemale = (RadioButton) dialogView.findViewById(R.id.radioFemale);
        rdOther = (RadioButton) dialogView.findViewById(R.id.radioOther);


        alertDialog = builder.create();

        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioMale) {
                    genderMValue = "Male";

                } else if (checkedId == R.id.radioFemale) {

                    genderFValue = "Female";

                } else {

                    genderOValue = "Other";

                }

            }
        });

        btnGenderCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnGenderOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = genderGroup.getCheckedRadioButtonId();

                // find which radioButton is checked by id
                if (selectedId == rdMale.getId()) {
                    txtEditGender.setText(genderMValue);
                } else if (selectedId == rdFemale.getId()) {
                    txtEditGender.setText(genderFValue);
                } else {
                    txtEditGender.setText(genderOValue);
                }

                alertDialog.dismiss();

            }
        });

    }

    public void getOriginDialog() {

        btnOrigin.setOnClickListener(this);

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

            for (int i = 0; i < obj.length(); i++) {

                JSONObject currObj = obj.getJSONObject(i);

                ctryName.add(currObj.getString("COUNTRY_TXT"));
                map.put(CTRY_NAME, ctryName);
                countryList.add(map);

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        ListView lisCountry = (ListView) convertView.findViewById(R.id.lisNationality);
        adpt = new NationalityAdapter(getActivity(), countryList);

        lisCountry.setAdapter(adpt);

        lisCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adpt.setSelectedIndex(position);
                ctryName = countryList.get(position).get(CTRY_NAME).get(position);
                adpt.notifyDataSetChanged();


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
                txtEditOrigin.setText(ctryName);
                originAlertDialog.dismiss();

            }
        });

    }

    public void getCitizenshipDialog() {

        btnCitizenship.setOnClickListener(this);

        AlertDialog.Builder builders = new AlertDialog.Builder(getActivity());
        LayoutInflater inflaters = (getActivity()).getLayoutInflater();

        View convertView = inflaters.inflate(R.layout.fragment_nationality, null);
        builders.setView(convertView);

        btnNltyCancel = (Button) convertView.findViewById(R.id.btnCancelNationality);
        btnNltyOk = (Button) convertView.findViewById(R.id.btnAcceptNationality);

        countryList = new ArrayList<>();
        map = new HashMap<>();

        try {

            JSONArray obj = new JSONArray(loadJSONFromAsset());
            ArrayList<String> ctryName = new ArrayList<>();

            for (int i = 0; i < obj.length(); i++) {

                JSONObject currObj = obj.getJSONObject(i);
                ctryName.add(currObj.getString("COUNTRY_TXT"));

                map.put(CTRY_NAME, ctryName);
                countryList.add(map);

            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        ListView lisCountry = (ListView) convertView.findViewById(R.id.lisNationality);
        adpts = new NationalityAdapter(getActivity(), countryList);

        lisCountry.setAdapter(adpts);

        lisCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                adpts.setSelectedIndex(position);
                ctryName = countryList.get(position).get(CTRY_NAME).get(position);
                adpts.notifyDataSetChanged();


            }
        });

        citizenAlertDialog = builders.create();

        btnNltyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                citizenAlertDialog.dismiss();
            }
        });

        btnNltyOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEditCitizenship.setText(ctryName);
                citizenAlertDialog.dismiss();

            }
        });

    }

    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ProfileActivity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Bitmap bm = null;
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    bm = BitmapFactory.decodeFile(path);

                    circularImageView.setImageBitmap(bm);
                    imgBlurredPic.setImageBitmap(bm);
                }

            }
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
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

    @Override
    public void onClick(View v) {

        if(v == btnGender) {
            alertDialog.show();
        }

        if(v == btnBirthday) {

            DateDialogFragment ddp = new DateDialogFragment();
            ddp.show(getFragmentManager(),"DialogDatePicker");

        }

        if(v == btnOrigin) {
            originAlertDialog.show();
        }

        if(v == btnCitizenship) {
            citizenAlertDialog.show();
        }

    }


}
