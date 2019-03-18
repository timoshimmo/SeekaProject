package edu.freshfutures.seeka.adapters;

import android.content.Context;
import android.content.SharedPreferences;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.freshfutures.seeka.Models.NotificationModel;
import edu.freshfutures.seeka.R;

/**
 * Created by tokmang on 4/19/2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private static LayoutInflater inflater = null;
    private Context context;


    JSONObject settings1Obj;
    JSONObject settings2Obj;
    JSONObject settings3Obj;
    JSONObject settings4Obj;
    JSONObject notifyDataObj;

    String positions;

    NotificationModel model;
    ArrayList<NotificationModel> arrNotifyModel;

    String[] titles;

    public NotificationAdapter(Context ctx, ArrayList<NotificationModel> nm, String[] ttls) {

        this.context = ctx;
        this.arrNotifyModel = nm;
        this.titles = ttls;
        settings1Obj = new JSONObject();
        settings2Obj = new JSONObject();
        settings3Obj = new JSONObject();
        settings4Obj = new JSONObject();
        notifyDataObj = new JSONObject();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.notification_list_structure, parent, false);

        ViewHolder viewholder = new ViewHolder(v);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(NotificationAdapter.ViewHolder holder, final int position) {


        final NotificationModel nModel = arrNotifyModel.get(position);

        int setNotifyPos = nModel.getNotifiPosition();
        int getEmailValue = nModel.getEmailValue();
        int getMobileValue = nModel.getMobileValue();

        holder.noticeTitles.setText(titles[position]);

        try {
            settings1Obj.put("emailNotification", "0");
            settings1Obj.put("popupNotification", "0");

            settings2Obj.put("emailNotification", "0");
            settings2Obj.put("popupNotification", "0");

            settings3Obj.put("emailNotification", "0");
            settings3Obj.put("popupNotification", "0");

            settings4Obj.put("emailNotification", "0");
            settings4Obj.put("popupNotification", "0");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(getEmailValue == 1) {
            holder.emailCheck.setChecked(true);

            try {
                if(position == 0) {
                    settings1Obj.put("emailNotification", "1");
                }
                if(position == 1) {
                    settings2Obj.put("emailNotification", "1");
                }
                if(position == 2) {
                    settings3Obj.put("emailNotification", "1");
                }

                if(position == 3) {
                    settings4Obj.put("emailNotification", "1");
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }

        else {
            try {
                if(position == 0) {
                    settings1Obj.put("emailNotification", "0");
                }
                if(position == 1) {
                    settings2Obj.put("emailNotification", "0");
                }
                if(position == 2) {
                    settings3Obj.put("emailNotification", "0");
                }

                if(position == 3) {
                    settings4Obj.put("emailNotification", "0");
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if(getMobileValue == 1) {
            holder.mobileCheck.setChecked(true);

            try {

                if(position == 0) {
                    settings1Obj.put("popupNotification", "1");
                }

                if(position == 1) {
                    settings2Obj.put("popupNotification", "1");
                }

                if(position == 2) {
                    settings3Obj.put("popupNotification", "1");
                }

                if(position == 3) {
                    settings4Obj.put("popupNotification", "1");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        else {
            try {
                if(position == 0) {
                    settings1Obj.put("popupNotification", "0");
                }
                if(position == 1) {
                    settings2Obj.put("popupNotification", "0");
                }

                if(position == 2) {
                    settings3Obj.put("popupNotification", "0");
                }

                if(position == 3) {
                    settings4Obj.put("popupNotification", "0");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        holder.emailCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                // model = new NotificationModel();

                if (isChecked) {

                        try {
                            //model.setMobileValue();
                            if(position == 0) {
                                settings1Obj.put("emailNotification", "1");
                            }
                            if(position == 1) {
                                settings2Obj.put("emailNotification", "1");
                            }
                            if(position == 2) {
                                settings3Obj.put("emailNotification", "1");
                            }

                            if(position == 3) {
                                settings4Obj.put("emailNotification", "1");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                } else {
                    try {
                        if(position == 0) {
                            settings1Obj.put("emailNotification", "0");
                        }

                        if(position == 1) {

                            settings2Obj.put("emailNotification", "0");
                        }
                        if(position == 2) {

                            settings3Obj.put("emailNotification", "0");
                        }

                        if(position == 3) {

                            settings4Obj.put("emailNotification", "0");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        holder.mobileCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                model = new NotificationModel();

                if (isChecked) {
                    try {

                        if(position == 0) {
                            settings1Obj.put("popupNotification", "1");
                        }

                        if(position == 1) {
                            settings2Obj.put("popupNotification", "1");
                        }

                        if(position == 2) {
                            settings3Obj.put("popupNotification", "1");
                        }

                        if(position == 3) {
                            settings4Obj.put("popupNotification", "1");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                else {
                    try {
                        if(position == 0) {
                            settings1Obj.put("popupNotification", "0");
                        }
                        if(position == 1) {
                            settings2Obj.put("popupNotification", "0");
                        }

                        if(position == 2) {
                            settings3Obj.put("popupNotification", "0");
                        }

                        if(position == 3) {
                            settings4Obj.put("popupNotification", "0");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void setNotifyOptions() {

        try {
            notifyDataObj.put("1",settings1Obj);
            notifyDataObj.put("2",settings2Obj);
            notifyDataObj.put("3",settings3Obj);
            notifyDataObj.put("4",settings4Obj);

            SharedPreferences prefCreditBal = context.getSharedPreferences("PREFNOTIFISETTINGS", Context.MODE_PRIVATE);
            SharedPreferences.Editor balEditor = prefCreditBal.edit();
            balEditor.putString("notifiSettings", notifyDataObj.toString());
            balEditor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return this.titles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView noticeTitles;
        CheckBox mobileCheck;
        CheckBox emailCheck;
        LinearLayout checkBoxBody;

        public ViewHolder(View itemView) {
            super(itemView);

            noticeTitles = (TextView) itemView.findViewById(R.id.txtUnNotificationTitle);
            mobileCheck = (CheckBox) itemView.findViewById(R.id.noticeMobileCheckbox);
            emailCheck = (CheckBox) itemView.findViewById(R.id.emailNoticeCheckbox);
            checkBoxBody = (LinearLayout) itemView.findViewById(R.id.NotifycheckBoxContainer);


        }
    }
}



/*  @Override
    public int getCount() {
        return this.noticeTitle.length;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Holder holder = new Holder();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.notification_list_structure, parent, false);
        }

        holder.noticeTitles = (TextView) convertView.findViewById(R.id.txtUnNotificationTitle);
        holder.mobileCheck = (CheckBox) convertView.findViewById(R.id.noticeMobileCheckbox);
        holder.emailCheck = (CheckBox) convertView.findViewById(R.id.emailNoticeCheckbox);
        holder.checkBoxBody = (LinearLayout) convertView.findViewById(R.id.NotifycheckBoxContainer);

        holder.noticeTitles.setText(noticeTitle[position]);
        holder.noticeTitles.setTag(position);

        //positions = String.valueOf(position);


        holder.emailCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               // model = new NotificationModel();

                if (isChecked) {
                    try {
                        //model.setMobileValue();
                        settingsObj.put("emailNotification", "1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        settingsObj.put("emailNotification", "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        holder.mobileCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                model = new NotificationModel();

                if (isChecked) {
                    try {
                        settingsObj.put("popupNotification", "1");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                else {
                    try {
                        settingsObj.put("popupNotification", "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return convertView;
    }

    public void setNotifyOptions() {

        try {
            notifyDataObj.put("notify",settingsObj);

            SharedPreferences prefCreditBal = context.getSharedPreferences("PREFNOTIFISETTINGS", Context.MODE_PRIVATE);
            SharedPreferences.Editor balEditor = prefCreditBal.edit();
            balEditor.putString("notifiSettings", notifyDataObj.toString());
            balEditor.apply();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public class Holder {
        TextView noticeTitles;
        CheckBox mobileCheck;
        CheckBox emailCheck;
        LinearLayout checkBoxBody;
    }

    */