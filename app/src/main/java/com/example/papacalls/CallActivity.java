package com.example.papacalls;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.opentok.android.OpentokError;
import com.opentok.android.Publisher;
import com.opentok.android.PublisherKit;
import com.opentok.android.Session;
import com.opentok.android.Stream;
import com.opentok.android.Subscriber;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class CallActivity extends AppCompatActivity implements Session.SessionListener, PublisherKit.PublisherListener {

    private static String API_KEY= "46962484";
    private static String Session_ID= "1_MX40Njk2MjQ4NH5-MTYwMzQ0OTU3Njg2MX5QWFRCZ3dBWjIrclR0ZDA5QzJ4SVcwSXl-fg";
    private static String Token= "T1==cGFydG5lcl9pZD00Njk2MjQ4NCZzaWc9MzBiM2Y0OTFjMTZlZTNiN2UxMTAxYmRlYjEwNmYwZjZiNTU3ODdkMTpzZXNzaW9uX2lkPTFfTVg0ME5qazJNalE0Tkg1LU1UWXdNelEwT1RVM05qZzJNWDVRV0ZSQ1ozZEJXaklyY2xSMFpEQTVReko0U1Zjd1NYbC1mZyZjcmVhdGVfdGltZT0xNjAzNDQ5NzAyJm5vbmNlPTAuMDI1Mjg2NjEwMjQ1NTA3Mzgmcm9sZT1wdWJsaXNoZXImZXhwaXJlX3RpbWU9MTYwNjA0NTMwMCZpbml0aWFsX2xheW91dF9jbGFzc19saXN0PQ==";
    private static String Log_Tag= CallActivity.class.getSimpleName();
    private static final int RC_SETTINGS = 123;

    private Session session;



    private FrameLayout PublisherContainer;
    private FrameLayout SubscriberContainer;

    private Publisher publisher;
    private Subscriber subscriber;
    private int x=0;
    private ImageButton callcancelBTN;
    private ImageButton messBTN;
    private ImageButton audioBTN;

    private int z=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

            requestPermissions();

    PublisherContainer= findViewById(R.id.publisher_container);
    SubscriberContainer=findViewById(R.id.subscriber_container);

    callcancelBTN= findViewById(R.id.callcancelbtn);
    messBTN= findViewById(R.id.messbtn);
    audioBTN= findViewById(R.id.audiocall);

    callcancelBTN.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            x=0;
            z=0;

            session.disconnect();
            startActivity(new Intent(CallActivity.this,MainActivity.class));
            finish();
        }
    });

        messBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(z%2==0) {
                    z++;
                    publisher.setPublishAudio(false);

                }
                else
                {
                    z++;
                    publisher.setPublishAudio(true);
                }}
        });

        audioBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(x%2==0) {
                x++;
                publisher.setPublishVideo(false);

            }
            else
            {
                x++;
                publisher.setPublishVideo(true);
            }
            }
        });
    }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
        }

        @AfterPermissionGranted(RC_SETTINGS)
        private void requestPermissions()
        {
        String[] perm={Manifest.permission.INTERNET, Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO};
        if(EasyPermissions.hasPermissions(this,perm))
        {

                session = new Session.Builder(this, API_KEY, Session_ID).build();
                session.setSessionListener(CallActivity.this);
                session.connect(Token);

            }

         else
        {
            EasyPermissions.requestPermissions(this,"",RC_SETTINGS,perm);
        }

        }

    @Override
    public void onConnected(Session session) {

    publisher = new Publisher.Builder(this).build();
    publisher.setPublisherListener(CallActivity.this);

    PublisherContainer.addView(publisher.getView());

    if(publisher.getView() instanceof GLSurfaceView)
    {
        ((GLSurfaceView)  publisher.getView()).setZOrderOnTop(true);
    }
    session.publish(publisher);
    }

    @Override
    public void onDisconnected(Session session) {
    startActivity(new Intent(CallActivity.this,MainActivity.class));
    finish();
    }

    @Override
    public void onStreamReceived(Session session, Stream stream) {
     if(subscriber==null)
     {
         subscriber= new Subscriber.Builder(this,stream).build();
         session.subscribe(subscriber);
         SubscriberContainer.addView(subscriber.getView());
     }


    }

    @Override
    public void onStreamDropped(Session session, Stream stream) {
if(subscriber!=null)
{
    subscriber=null;
    SubscriberContainer.removeAllViews();
}
        startActivity(new Intent(CallActivity.this,MainActivity.class));
    }

    @Override
    public void onError(Session session, OpentokError opentokError) {

    }

    @Override
    public void onStreamCreated(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onStreamDestroyed(PublisherKit publisherKit, Stream stream) {

    }

    @Override
    public void onError(PublisherKit publisherKit, OpentokError opentokError) {

    }
}