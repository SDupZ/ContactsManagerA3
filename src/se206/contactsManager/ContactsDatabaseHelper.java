package se206.contactsManager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

public class ContactsDatabaseHelper extends SQLiteOpenHelper{
	
	// Database version
	public static final int DATABASE_VERSION = 1;
	
	// Database Name
	public static final String DATABASE_NAME = "ContactsManagerSDUPZ.db";
	
	// Contacts Table Name
	public static final String TABLE_CONTACTS = "ContactsTable";
	
	// Contacts Table Columns
	private static final String CONTACTS_FIRSTNAME 		= "first_name";
	private static final String CONTACTS_LASTNAME	 	= "last_name";
	private static final String CONTACTS_MOBILEPHONE	= "mobilephone";
	private static final String CONTACTS_HOMEPHONE 		= "homephone";
	private static final String CONTACTS_WORKPHONE 		= "workphone";
	private static final String CONTACTS_EMAILADDRESS 	= "emailAddress";
	private static final String CONTACTS_STREETNUMBER 	= "streetNumber";
	private static final String CONTACTS_STREETNAME 	= "streetName";
	private static final String CONTACTS_SUBURB 		= "suburb";
	private static final String CONTACTS_CITY 			= "city";	
	private static final String CONTACTS_DATEOFBIRTH 	= "dateOfBirth";
	private static final String CONTACTS_PHOTO 			= "photo";
	
	//SQL String for creating contacts table.
	private static final String CREATE_CONTACTS_TABLE = "???";
	
	//SQL String for deleting contacts table
	private static final String SQL_DELETE_CONTACTS_TABLE = "??";
	
	public ContactsDatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_CONTACTS_TABLE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(SQL_DELETE_CONTACTS_TABLE);
		onCreate(db);
	}
}
