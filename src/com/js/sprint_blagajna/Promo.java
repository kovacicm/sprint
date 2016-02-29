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
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class Promo extends Activity {
	
	private EditText usernameEditText;
	private EditText passwordEditText;
	private String sUserName;
	private String sPassword;
	private Intent osvjezi, pocetna;
	private ScrollView scroll;
	private SQLiteDatabase mapdb=null;
	private Cursor mapscursor=null;
	
	public static final int OSVJEZI = Menu.FIRST+1;
	public static final int POCETNA = Menu.FIRST+2;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promo);        
        
        
        
        scroll=(ScrollView)findViewById(R.id.scroll);
 	   scroll.setBackgroundResource(R.drawable.bg_kupon);  
 	  
        pocetna=new Intent(this, Pocetna.class);
        
               
        
    }
    
    public void Osvjezi (){
    	
        TextView popust = (TextView)findViewById(R.id.popust);
        popust.setText("15% popusta na PIK hamburgere");
    	ImageView Ponisti = (ImageView)findViewById(R.id.ponisti);
    	Ponisti.setImageResource(R.drawable.ponisti);
    	ImageView Barcode = (ImageView)findViewById(R.id.barcode);
    	Barcode.setImageResource(R.drawable.barcode2);
    	
    	Ponisti.setOnClickListener( new OnClickListener()
        {
			public void onClick(View viewParam){                       
				Aktivacija();
			}
        } 
        ); 
    	
    }
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	ContextMenu.ContextMenuInfo menuInfo) {
	populateMenu(menu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	populateMenu(menu);
	return(super.onCreateOptionsMenu(menu));
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		populateMenu(menu);
		return super.onPrepareOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	return(applyMenuChoice(item) ||
	super.onOptionsItemSelected(item));
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	return(applyMenuChoice(item) ||
	super.onContextItemSelected(item));
	}
	
	private void populateMenu(Menu menu) {
		
		MenuItem menu1=menu.add(Menu.NONE, OSVJEZI, Menu.NONE, "Osvjezi");
		menu1.setIcon(R.drawable.ic_menu_refresh);
		MenuItem menu2=menu.add(Menu.NONE, POCETNA, Menu.NONE, "Pocetna");
		menu2.setIcon(R.drawable.ic_menu_home);
	}
	
	private boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
		
		case OSVJEZI:
			Toast.makeText(getApplicationContext(), "Dohvacam nove kupone...",
	    			   Toast.LENGTH_SHORT).show();
			Osvjezi();
		return(true);
		
		
		case POCETNA:
		startActivityForResult(pocetna,0);					
		return(true);

		
		}
		return(false);
		}
     
	private void Aktivacija(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Aktivacija popusta");
		alert.setMessage("Želite li stvarno iskoristiti kod?");
		alert.setPositiveButton("Iskoristi", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				UspjesnaAktivacija();
			}
		});

		alert.setNegativeButton("Odustani",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		alert.show();
        
	}
	
	private void UspjesnaAktivacija(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Aktivacija uspijela!");
		alert.setMessage("Popust je uspješno ostvaren");
		alert.setNeutralButton("Zatvori", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				UpdateAkcija(0,1);
			}
		});
		alert.show();
	}
	
	public void UpdateAkcija(int where, int value){

		Intent i = new Intent(this, Kosarica.class);
		i.putExtra("akcija", "1");
		startActivity(i);	
 }
}