 
package com.js.sprint_blagajna;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.SensorManager;
import android.util.Log;

public class MapDatabase extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="sprintblag";

	//user tablica
	public static final String USERNAME="username";
	public static final String PASS="pass";
	public static final String IME="ime";
	public static final String PREZIME="prezime";
	public static final String ADRESA="adresa";
	public static final String GRAD="GRAD";
	

	//tablica akcije
	public static final String KAT="kat";
	public static final String POP="pop";
	public static final String CIJENA="cijena";
	public static final String LABEL="label";
	public static final String DD="dd";
	public static final String MM="mm";
	public static final String YY="yy";
	public static final String OPIS="opis";
	public static final String SLIKA="slika";
	public static final String FAV="fav";
	public static final String KOL="kol";
	public static final String AKC="akc";
	public static final String BAR="bar";
	
	public MapDatabase(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	
	
		//korisnici
		
		db.execSQL("CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT, username STRING, pass STRING, ime STRING, prezime STRING, adresa STRING, grad STRING);");
		
		ContentValues cv3=new ContentValues();

		cv3.put(USERNAME, "srdanovicj"); cv3.put(PASS, "srdanovicj"); cv3.put(IME, "Josip");
		cv3.put(PREZIME, "Srdanovic"); cv3.put(ADRESA, "Slavonska Avenija 7"); cv3.put(GRAD, "Zagreb");
		
		db.insert("users", null, cv3);
		


		
db.execSQL("CREATE TABLE akcije (_id INTEGER PRIMARY KEY AUTOINCREMENT, kat INT, pop INT, cijena DOUBLE, label STRING, dd INT, mm INT, yy INT, opis STRING, slika INT, fav INT, kol INT, akc INT, bar INT );");
		
		ContentValues cv6=new ContentValues();
		
		cv6.put(KAT, 1); cv6.put(POP, 10); cv6.put(CIJENA, 19.99);
		cv6.put(LABEL, "Hamburger");
		cv6.put(DD, 21); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "PIK svježi hamburger 600g"); cv6.put(SLIKA, R.drawable.hamburger); cv6.put(FAV, 1); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 112231);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 5); cv6.put(POP, 12); cv6.put(CIJENA, 5.99);
		cv6.put(LABEL, "Svježi sir");
		cv6.put(DD, 16); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Dukat svježi posni sir 10% 200g"); cv6.put(SLIKA, R.drawable.svjezisir); cv6.put(FAV, 1); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 112232);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 4); cv6.put(POP, 14); cv6.put(CIJENA, 18.99);
		cv6.put(LABEL, "Pivo");
		cv6.put(DD, 8); cv6.put(MM, 9); cv6.put(YY, 2011);
		cv6.put(OPIS, "Ožujsko pivo Q pack 2l"); cv6.put(SLIKA, R.drawable.pivo); cv6.put(FAV, 1); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 112233);
		db.insert("akcije", null, cv6); 
		
		cv6.put(KAT, 2); cv6.put(POP, 18); cv6.put(CIJENA, 16.49);
		cv6.put(LABEL, "Sladoled");
		cv6.put(DD, 21); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Ledo Sladoled Twice vanilija-cokolada 2l"); cv6.put(SLIKA, R.drawable.sladoled); cv6.put(FAV, 0); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 112234);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 1); cv6.put(POP, 15); cv6.put(CIJENA, 24.99);
		cv6.put(LABEL, "Piletina");
		cv6.put(DD, 21); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Pileći mix 640 g K Plus"); cv6.put(SLIKA, R.drawable.piletina); cv6.put(FAV, 0); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 112235);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 4); cv6.put(POP, 20); cv6.put(CIJENA, 29.99);
		cv6.put(LABEL, "Vino");
		cv6.put(DD, 21); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Vino duet bib, kvalitetno, 3l"); cv6.put(SLIKA, R.drawable.vino); cv6.put(FAV, 0); cv6.put(KOL, 0); cv6.put(AKC, 0); cv6.put(BAR, 112236);
		db.insert("akcije", null, cv6);

		cv6.put(KAT, 2); cv6.put(POP, 23); cv6.put(CIJENA, 18.99);
		cv6.put(LABEL, "Sladoled");
		cv6.put(DD, 28); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Ledo sladoled Quattro italiano 900 ml"); cv6.put(SLIKA, R.drawable.quattro); cv6.put(FAV, 0); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 112237);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 3); cv6.put(POP, 16); cv6.put(CIJENA, 1.49);
		cv6.put(LABEL, "Paprika");
		cv6.put(DD, 28); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Paprika Babura II klasa 1kg"); cv6.put(SLIKA, R.drawable.paprika); cv6.put(FAV, 0); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 112238);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 3); cv6.put(POP, 12); cv6.put(CIJENA, 2.38);
		cv6.put(LABEL, "Kupus");
		cv6.put(DD, 28); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Kupus svježi klasa II 1kg"); cv6.put(SLIKA, R.drawable.kupus); cv6.put(FAV, 0); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 112239);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 3); cv6.put(POP, 19); cv6.put(CIJENA, 8.90);
		cv6.put(LABEL, "Kruška");
		cv6.put(DD, 28); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Kruška limonera, Hrvatska, 1 kg"); cv6.put(SLIKA, R.drawable.kruska); cv6.put(FAV, 0); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 1122310);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 4); cv6.put(POP, 10); cv6.put(CIJENA, 8.99);
		cv6.put(LABEL, "Pepsi");
		cv6.put(DD, 16); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Pepsi Twist 2l, 0.5l gratis "); cv6.put(SLIKA, R.drawable.pepsi); cv6.put(FAV, 0); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 1122311);
		db.insert("akcije", null, cv6);
		
		cv6.put(KAT, 5); cv6.put(POP, 15); cv6.put(CIJENA, 3.90);
		cv6.put(LABEL, "Majoneza");
		cv6.put(DD, 16); cv6.put(MM, 8); cv6.put(YY, 2011);
		cv6.put(OPIS, "Zvijezda majoneza 90 g"); cv6.put(SLIKA, R.drawable.majoneza); cv6.put(FAV, 0); cv6.put(KOL, 1); cv6.put(AKC, 0); cv6.put(BAR, 1122312);
		db.insert("akcije", null, cv6);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db2, int oldVersion, int newVersion) {
		android.util.Log.w("akcija", "Upgrading database, which will destroy all old data");
		db2.execSQL("DROP TABLE IF EXISTS score");
		onCreate(db2);
	}
	
	public void updatemaps() {
		
		
		}
}
