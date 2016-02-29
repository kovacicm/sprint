package com.js.sprint_blagajna;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Kosarica extends ListActivity  {
    /** Called when the activity is first created. */
	
	private DisplayMetrics dm;
	private Calendar tempD;
	private TextView label, popust,precijena1, precijena2, precijena3, precijena4, precijena5, precijena6, cijena1, cijena2,cijena3,cijena4,cijena5,cijena6, opis, kol1, kol2, kol3, kol4, kol5, kol6, kol7;
	private TextView [] kol = {kol1, kol2, kol3, kol4, kol5, kol6, kol7};
	private TextView [] cijena = {cijena1, cijena2, cijena3, cijena4, cijena5, cijena6};
	private TextView [] precijena = {precijena1, precijena2, precijena3, precijena4, precijena5, precijena6};
	private boolean  [] kategorija = {true, true, true, true, true, true};
	private boolean [] kat = {true, true, true, true, true, true};
	private ImageView icon, akcija, minus, plus, novisken, naplata;
	private Intent kuponi, izlaz, pocetna;
	private Integer movedir, listcount, currpos, barkod;
	private Integer [] nrA = {0,0,0,0,0,0,0,0,0,0};
	private List<Entry> EntriesList = new ArrayList<Entry>();
	private List<Entry> EntriesListTemp = new ArrayList<Entry>();
	private Entry [] EntryArray = new Entry [12];
	public SQLiteDatabase mapdb=null;
	private Cursor mapscursor=null;
	private ImageView refresh;
	private Integer ak;
	public ListAdapter mAdapter;
	public double ukcijenag, ukpopustg;
	
	public static final int SORTER = Menu.FIRST+1;
	public static final int FILTER = Menu.FIRST+2;
	public static final int KUPONI = Menu.FIRST+3;	
	public static final int POCETNA = Menu.FIRST+4;
			
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		int i;
		
		        Intent intent = getIntent();
		        ak=Integer.valueOf(intent.getStringExtra("akcija"));
		        
		
		super.onCreate(savedInstanceState);        
        setContentView(R.layout.kosarica);
        
        listcount=0;
   //     ak = 0;
        
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
        
        kategorija[0]=prefs.getBoolean("meso", true);
        kategorija[1]=prefs.getBoolean("smrznuto", true);
        kategorija[2]=prefs.getBoolean("voce", true);
        kategorija[3]=prefs.getBoolean("pica", true);
        kategorija[4]=prefs.getBoolean("mlijecni", true);
        
        for (i=0;i<5;i++){
        	kat[i]=kategorija[i];
        }
        
        kuponi=new Intent(this, Promo.class); 
        pocetna=new Intent(this, Pocetna.class);
        izlaz=new Intent(this, Login.class);
        
        registerForContextMenu(getListView());
        ukcijenag=0;
        ukpopustg=0;
        
        mapdb=(new MapDatabase(this)).getWritableDatabase();
        mapscursor= mapdb.rawQuery("SELECT * "+
    			"FROM akcije",
    			null);
        mapscursor.moveToFirst();
        
        for(i=0; i<12; i++){ 
        	tempD=Calendar.getInstance();
        	tempD.set(Calendar.DAY_OF_MONTH, mapscursor.getInt(5));
        	tempD.set(Calendar.MONTH, mapscursor.getInt(6));
        	tempD.set(Calendar.YEAR, mapscursor.getInt(7));
        	if (mapscursor.getInt(10) == 1){
        		listcount = listcount +1;
        	EntryArray[i]=new Entry(mapscursor.getInt(0), mapscursor.getInt(1), mapscursor.getInt(2), mapscursor.getDouble(3), mapscursor.getString(4), tempD, mapscursor.getString(8), mapscursor.getInt(9), mapscursor.getInt(10), mapscursor.getInt(11), mapscursor.getInt(12), mapscursor.getInt(13));
        	EntriesList.add(EntryArray[i]);
        	EntriesListTemp.add(EntryArray[i]);
        	ukcijenag = ukcijenag + mapscursor.getDouble(3)*mapscursor.getInt(11);
        	ukpopustg = 0;
        	}
        	mapscursor.moveToNext();
        }
        SetTekst(ukcijenag, ukpopustg);

        novisken=(ImageView)findViewById(R.id.novisken);
		 novisken.setOnClickListener( new OnClickListener()
	        {
			 @Override
				public void onClick(View viewParam){    
				 Skener();
	                }  
	        } 
	        );
		 
		 naplata=(ImageView)findViewById(R.id.naplata);
		 naplata.setOnClickListener( new OnClickListener()
	        {
			 @Override
				public void onClick(View viewParam){   
				 
				 	Izlaz();
	                }  
	        } 
	        );
		 
		 
        FilterList();
        setListAdapter(new IconicAdapter());
        mAdapter = getListAdapter();
        
        
    }
	
	public void SetTekst (double ukupno, double ukpopust){
		
		TextView uk = (TextView)findViewById(R.id.uk);
		TextView ukpop = (TextView)findViewById(R.id.ukpop);
		TextView ukplat = (TextView)findViewById(R.id.ukplat);
		
		ukupno = Math.round(ukupno*100);
		ukupno = ukupno/100;
		ukpopust = Math.round(ukpopust*100);
		ukpopust = ukpopust/100;
		
		
		uk.setText("Ukupno: " + String.valueOf(ukupno) + " kn");
		ukpop.setText("Ukupan popust: " + String.valueOf(ukpopust) + " kn");
		ukplat.setText("Ukupno za platiti: " + String.valueOf(ukupno-ukpopust) + " kn");
	}
	
	public void Izlaz (){
		String qrdata;
		int j;
		Intent i = new Intent(this, Izlaz.class);
		qrdata = "";
		
		for (j=0; j<listcount; j++){
			qrdata = qrdata + String.valueOf(EntriesListTemp.get(j).getBar()) + ',' + String.valueOf(EntriesListTemp.get(j).getKol()) + ',';
		}
		
		i.putExtra("qrcode", qrdata);
		startActivity(i);
	}
	
	@Override
	public void onResume() {
	super.onResume();
	int i;
	SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(this);
    setListAdapter(new IconicAdapter());
    
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
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
	super.onActivityResult(requestCode, resultCode, data); 
	if (resultCode == 123) { 
	Intent in = new Intent();
	setResult(123,in);	
	this.finish(); 
	} 

	} 
	
	
	@Override
	public void onBackPressed() {
	}
	
	public void sort(final String field, List<Entry> itemLocationList) {
	    Collections.sort(itemLocationList, new Comparator<Entry>() {
	        @Override
	        public int compare(Entry o1, Entry o2) {
	            if(field.equals("naslov")) {
	                return o1.getLabel().compareTo(o2.getLabel());
	            } if(field.equals("cijena")) {
	                return o1.getCijena().compareTo(o2.getCijena());
	            } else if(field.equals("popust")) {
	                return o1.getPop().compareTo(o2.getPop());
	            } 
	            else if(field.equals("datum")) {
	                return o1.getRok().compareTo(o2.getRok());
	            } 
	            else {
	            	return 0;
	            }
	        }           
	    });
	    
	//    Collections.reverse(itemLocationList);
	}
	
	private void FilterList(){
		
		int position, maxpos;
		
		maxpos=listcount;
		
		for (position=0; position<maxpos; position++){
			

			if(kat[EntriesListTemp.get(position).getKat()-1]){
				
			}
			
			else{
				
				EntriesListTemp.remove(position);
				maxpos=maxpos-1;
				position=position-1;
			}

		}
	}
	
	private void FillList(){
		
		int position;
		
		EntriesListTemp.clear();
		
		for (position=0; position<listcount; position++){			
			EntriesListTemp.add(EntriesList.get(position));
		}
	
		
	}
	
	private void Dodaj (int barkod){
		int i;
		mapscursor.moveToFirst();
		for(i=0; i<12; i++){ 
			
        	if (mapscursor.getInt(13) == barkod){
        		listcount = listcount +1;
        		EntryArray[listcount]=new Entry(mapscursor.getInt(0), mapscursor.getInt(1), mapscursor.getInt(2), mapscursor.getDouble(3), mapscursor.getString(4), tempD, mapscursor.getString(8), mapscursor.getInt(9), mapscursor.getInt(10), mapscursor.getInt(11), mapscursor.getInt(12), mapscursor.getInt(13));
        		EntriesList.add(EntryArray[listcount]);
        		EntriesListTemp.add(EntryArray[listcount]);
        		UpdateKosarica(i+1,1);
        	}
        	mapscursor.moveToNext();
        }
	}
	
	private void Remove(int i){
		EntriesListTemp.remove(i);
		listcount=listcount-1;
		UpdateKosarica(i+1,0);
	}
	
	private void Promijeni(int pos, int newkol){
		EntriesListTemp.get(pos).setKol(EntriesListTemp.get(pos).getKol()+newkol);
		setListAdapter(new IconicAdapter());
	}
	
	
	private void populateMenu(Menu menu) {

		MenuItem menu1=menu.add(Menu.NONE, SORTER, Menu.NONE, "Sortiraj");
		menu1.setIcon(R.drawable.ic_menu_sort_alphabetically);
		MenuItem menu2=menu.add(Menu.NONE, FILTER, Menu.NONE, "Filtriraj");
		menu2.setIcon(R.drawable.ic_menu_agenda);						
		MenuItem menu3=menu.add(Menu.NONE, KUPONI, Menu.NONE, "Kuponi");
		menu3.setIcon(R.drawable.ic_menu_cc);
		MenuItem menu4=menu.add(Menu.NONE, POCETNA, Menu.NONE, "Pocetna");
		menu4.setIcon(R.drawable.ic_menu_home);
	}
	
	private boolean applyMenuChoice(MenuItem item) {
		switch (item.getItemId()) {
			
		case SORTER:				
			
			CharSequence [] maps={"Abecedi", "Cijeni"};
			movedir=0;
			
			 new AlertDialog.Builder(this)
				.setTitle("Sortiraj prema")
				.setSingleChoiceItems(maps,movedir, new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dlg, int sumthin) {
						
						movedir=sumthin;

						}	
				})
				.setNegativeButton("Poništi", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
	
				}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
					
					
					if(movedir==0){//abecedi
						
						sort("naslov", EntriesListTemp);
						setListAdapter(new IconicAdapter());

					}
					if(movedir==1){//cijeni
						
						sort("cijena", EntriesListTemp);
						setListAdapter(new IconicAdapter());
						
					}		
					
				}
				})
				.show();

		return(true);
		
		case FILTER:
			
			CharSequence [] maps2={"Meso", "Smrznuto","Voće i povrće", "Pića", "Mliječni proizvodi"};
			
			 new AlertDialog.Builder(this)
				.setTitle("Filtriraj po kategoriji")
				.setMultiChoiceItems(maps2,kat, new DialogInterface.OnMultiChoiceClickListener(){
					public void onClick(DialogInterface dlg, int sumthin, boolean value) {
						
						kat[sumthin]=value;
						
						}	
				})
				.setNegativeButton("Poništi", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
	
				}
				})
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
						
						FillList();
						FilterList();
						setListAdapter(new IconicAdapter());					    			
					
				}
				})
				.show();
				
		return(true);
		
		case KUPONI:
		startActivityForResult(kuponi,0);
		return(true);
			
		case POCETNA:
		startActivityForResult(pocetna,0);	
		return(true);
		
		}
		return(false);
		}
		
	@Override
	public void onListItemClick(ListView parent, View v,
			int position, long id) {
		
			movedir=position;
				
				new AlertDialog.Builder(this)
				.setTitle("Ukloni iz kosarice?")
				
				.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {

				}
				})
				.setPositiveButton("Da", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dlg, int sumthin) {
					    
					Remove(movedir);
					setListAdapter(new IconicAdapter());
					
				}
				})
				.show();
				
			
	}
			

	class IconicAdapter extends ArrayAdapter {
		IconicAdapter() {
			
		super(Kosarica.this, R.layout.row, EntriesListTemp);
		}
		public View getView(int position, View convertView,
		ViewGroup parent) {
				
		Integer intkol;
		currpos = position;
		LayoutInflater inflater=getLayoutInflater();
		View row=inflater.inflate(R.layout.row, parent, false);		
			
		TextView label=(TextView)row.findViewById(R.id.label);
		label.setText(EntriesListTemp.get(position).getLabel());
		
		TextView opis=(TextView)row.findViewById(R.id.opis);
		opis.setText(EntriesListTemp.get(position).getOpis());
				
		cijena[position]=(TextView)row.findViewById(R.id.cijena);
		precijena[position]=(TextView)row.findViewById(R.id.precijena);
		cijena[position].setText(Double.toString(EntriesListTemp.get(position).getCijena()*EntriesListTemp.get(position).getKol())+" kn");
	
		ImageView icon=(ImageView)row.findViewById(R.id.icon);
		
		kol[position]=(TextView)row.findViewById(R.id.kol);
		intkol = EntriesListTemp.get(position).getKol();
		kol[position].setText(Integer.toString(intkol));
		
		ImageView minus=(ImageView)row.findViewById(R.id.minus);
		minus.setTag(position);
		 minus.setOnClickListener( new OnClickListener()
	        {
			 @Override
				public void onClick(View viewParam){   
				 final int position = (Integer)viewParam.getTag();
				 int temp;
				 Integer intkol, a;
				 Promijeni(position, -1);
				 	intkol = EntriesListTemp.get(position).getKol();
	                kol[position].setText(Integer.toString(intkol));
	                UpdateKol(position+1,intkol);
	                cijena[position].setText(Double.toString(EntriesListTemp.get(position).getCijena()*intkol)+" kn");
	                
	                ukcijenag =0;
	                for (temp = 0; temp < listcount; temp ++){
	                	ukcijenag = ukcijenag + EntriesListTemp.get(temp).getCijena()*EntriesListTemp.get(temp).getKol();
	                }
	                SetTekst(ukcijenag, ukpopustg);
	                
	                if(intkol == 0){
	                	Ukloni(position);

	                }
	                }  
	        }
	        );
		 
		 ImageView plus=(ImageView)row.findViewById(R.id.plus);
		 plus.setTag(position);
		 plus.setOnClickListener( new OnClickListener()
	        {
			 @Override
				public void onClick(View viewParam){    
				 final int position = (Integer)viewParam.getTag();
				 int temp;
				 Integer intkol;
				 Promijeni(position, 1);
				 	intkol = EntriesListTemp.get(position).getKol();
	                kol[position].setText(Integer.toString(intkol));
	                UpdateKol(position+1,intkol);
	                cijena[position].setText(Double.toString(EntriesListTemp.get(position).getCijena()*intkol)+" kn");
	                ukcijenag =0;
	                for (temp = 0; temp < listcount; temp ++){
	                	ukcijenag = ukcijenag + EntriesListTemp.get(temp).getCijena()*EntriesListTemp.get(temp).getKol();
	                }
	                SetTekst(ukcijenag, ukpopustg);

	                }  
	        } 
	        );
		
		 
			
		icon.setImageResource(EntriesListTemp.get(position).getSlika());
		
		if (ak == 1 && EntriesListTemp.get(position).getCijena() == 19.99){
			ImageView popust=(ImageView)row.findViewById(R.id.popust);
			popust.setImageResource(R.drawable.popust);
			precijena[position]=(TextView)row.findViewById(R.id.precijena);
			precijena[position].setText(Double.toString(EntriesListTemp.get(position).getCijena()*intkol)+" kn");
			precijena[position].setPaintFlags(precijena[position].getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			
			double number = Math.round(EntriesListTemp.get(position).getCijena()*intkol/1.1 * 100);
			number = number/100;
			cijena[position].setText(Double.toString(number)+" kn");
			TextView izpopust=(TextView)row.findViewById(R.id.izpopust);
			izpopust.setText("-"+EntriesListTemp.get(position).getPop().toString()+"%");
			ukpopustg = EntriesListTemp.get(position).getCijena()*intkol - number;
			SetTekst (ukcijenag, ukpopustg);
		}	
		
		
		return(row);

		
		}
		}
	
	public void Skener(){
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Novi artikl");
		alert.setMessage("Odaberite način unosa");
		alert.setPositiveButton("Barkod skener", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.cancel();
			}
		});

		alert.setNegativeButton("Rucno",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						Rucno();
					}
				});
		alert.show();
        
	}
	
	
	
	public void Ukloni(int pos){
		movedir = pos;
		new AlertDialog.Builder(this)
		.setTitle("Ukloni iz kosarice?")
		
		.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dlg, int sumthin) {


		}
		})
		.setPositiveButton("Da", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dlg, int sumthin) {
			    
			Remove(movedir);
			setListAdapter(new IconicAdapter());

			
		}
		})
		.show();
	}
	
	public void Rucno(){
		
		final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final EditText input = new EditText(this);			
		alert.setTitle("Novi kod");
		alert.setMessage("Unesite novi kod");
		alert.setView(input);
		alert.setPositiveButton("Unesi", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString().trim();
				barkod=Integer.valueOf(value);
				Dodaj(barkod);
				setListAdapter(new IconicAdapter());
				
			}
		});

		alert.setNegativeButton("Odustani",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
		alert.show();
		return;
		
	}
	
	public void UpdateKosarica(int where, int value){

		String FAV="fav";
		
		ContentValues cv=new ContentValues();
		
		cv.put(FAV, value);
		
		mapdb.update("akcije", cv, "_id="+Integer.toString(where) , null);
		
 }
	
	public void UpdateKol(int where, int value){

		String KOL="kol";
		
		ContentValues cv=new ContentValues();
		
		cv.put(KOL, value);
		
		mapdb.update("akcije", cv, "_id="+Integer.toString(where) , null);
 }
	
		
}



