package se206.contactsManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

public class ContactsDatabaseHelper extends SQLiteOpenHelper{
	
	// Database version
	public static final int DATABASE_VERSION = 1;
	
	// Database Name
	public static final String DATABASE_NAME = "ContactsSDupZ.db";
	
	// Contacts Table Name
	public static final String TABLE_NAME = "ContactsTable";
	
	// Contacts Table Columns
	private static final String CONTACTS_ID 			= "id";
	private static final String CONTACTS_FIRSTNAME 		= "firstname";
	private static final String CONTACTS_LASTNAME	 	= "lastname";
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
	private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
			+ CONTACTS_ID				+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ CONTACTS_FIRSTNAME 		+ " TEXT,"
			+ CONTACTS_LASTNAME			+ " TEXT,"
			+ CONTACTS_MOBILEPHONE		+ " TEXT,"
			+ CONTACTS_HOMEPHONE 		+ " TEXT,"
			+ CONTACTS_WORKPHONE 		+ " TEXT,"
			+ CONTACTS_EMAILADDRESS 	+ " TEXT,"
			+ CONTACTS_STREETNUMBER 	+ " INTEGER,"
			+ CONTACTS_STREETNAME 		+ " TEXT,"
			+ CONTACTS_SUBURB 			+ " TEXT,"
			+ CONTACTS_CITY 			+ " TEXT,"
			+ CONTACTS_DATEOFBIRTH 		+ " TEXT);";
			//+ CONTACTS_PHOTO 			+ " STRING);";
	
	//SQL String for deleting contacts table
	private static final String SQL_DELETE_CONTACTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	public ContactsDatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_CONTACTS_TABLE);
		
		//Just testing purposes
		final String FIRST_ENTRY = "INSERT INTO " + TABLE_NAME + " VALUES('test1', '1')";
		db.execSQL(FIRST_ENTRY);
		final String SECOND_ENTRY = "INSERT INTO " + TABLE_NAME + " VALUES('test1', '2')";
		db.execSQL(SECOND_ENTRY);
		final String THIRD_ENTRY = "INSERT INTO " + TABLE_NAME + " VALUES('test1', '3')";
		db.execSQL(THIRD_ENTRY);
		
	}
	
	public Cursor getAllData(){
		String buildSQL = "SELECT rowid _id,* FROM " + TABLE_NAME;
		return this.getReadableDatabase().rawQuery(buildSQL, null);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(SQL_DELETE_CONTACTS_TABLE);
		onCreate(db);
	}
}
