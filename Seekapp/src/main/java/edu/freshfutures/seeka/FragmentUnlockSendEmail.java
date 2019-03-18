package edu.freshfutures.seeka;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.api.client.util.DateTime;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class FragmentUnlockSendEmail extends DialogFragment {

    Context ctx;
    Button sendEmail;
    EditText txtEmail;
    ImageButton imgCancel;
    Dialog d;
    Date date;

    public FragmentUnlockSendEmail() {
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
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            d.getWindow().setLayout(width, height);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.send_email_structure, container, false);

        sendEmail = (Button) view.findViewById(R.id.btnUnlockSendEmail);
        txtEmail = (EditText) view.findViewById(R.id.popupEmailMessage);
        imgCancel = (ImageButton) view.findViewById(R.id.imgEmailCancel);

        ctx = getActivity();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dismiss();
            }
        });

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = txtEmail.getText().toString();
                try {
                    if(!message.equals("")) {
                        send(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public boolean send(String typedMessage) throws Exception {

        String to="timoshimmo88@gmail.com";//change accordingly
        final String _user = "tokmangwang@gmail.com";
        final String _pass = "UNshakable14";


        if(!_user.equals("") && !_pass.equals("")) {


            //Get the session object
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(_user,_pass);//change accordingly
                        }
                    });

            //compose message
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(_user));//change accordingly
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Seeka Enquiry");

                message.setText(typedMessage);
                Transport.send(message);

                System.out.println("message sent successfully");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }

            return true;

        }

    else {
        return false;
    }

}

    public void Dismiss() {
        DialogFragment dialogFragment = (DialogFragment)getFragmentManager().findFragmentByTag("dialogsUnlockSendEmail");
        if (dialogFragment != null) {
            dialogFragment.dismiss();
        }
    }


}
