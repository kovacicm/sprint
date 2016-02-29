package com.js.sprint_blagajna;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

public class Pocetna extends Activity {
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	private String sUserName;
	private String sPassword;
	private Intent mkupon, novikod, info, arhiva, promocije, izlaz;
	private ScrollView scroll;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pocetna);        
        
        Button NoviKod = (Button)findViewById(R.id.novikod);
        Button Info = (Button)findViewById(R.id.info);
        Button Promocije = (Button)findViewById(R.id.promo);
        Button Odjava = (Button)findViewById(R.id.izlaz);
        
        scroll=(ScrollView)findViewById(R.id.scroll);
 	   scroll.setBackgroundResource(R.drawable.bg_new1);
        

        novikod=new Intent(this, Promo.class);
        info=new Intent(this, Kosarica.class);
        promocije=new Intent(this, Promo.class);
        izlaz=new Intent(this, Login.class);
        
        NoviKod.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
                startActivity(novikod);
                }  
        } 
        );
        
        
        
        Info.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){  
				StartInfo();
                }  
        } 
); 
       
        
        Promocije.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
                startActivity(promocije);
                }  
        } 
        ); 
        
        Odjava.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
                startActivity(izlaz);
                }  
        } 
        ); 
        

    }
	public void StartInfo(){

		Intent i = new Intent(this, Kosarica.class);
		i.putExtra("akcija", "0");
		startActivity(i);	
 }
    
    
}