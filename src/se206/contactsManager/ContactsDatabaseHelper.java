package se206.contactsManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDatabaseHelper extends SQLiteOpenHelper{
	private static ContactsDatabaseHelper instance;
	
	public static final int COLUMN_FIRSTNAME = 1;
	public static final int COLUMN_LASTNAME = 2;
	public static final int COLUMN_PHOTO = 12;
	
	public static final String DATABASE_NAME = "ContactsManager.db";
	// Database version
	public static final int DATABASE_VERSION = 1;
	
	
	
	// Contacts Table Name
	public static final String TABLE_NAME = "ContactsTable";
	
	// Contacts Table Columns
	private static final String CONTACTS_ID				= "id";
	private static final String CONTACTS_FIRSTNAME 		= "firstname";
	private static final String CONTACTS_LASTNAME	 	= "lastname";
	private static final String CONTACTS_MOBILEPHONE	= "mobilephone";
	private static final String CONTACTS_HOMEPHONE 		= "homephone";
	private static final String CONTACTS_WORKPHONE 		= "workphone";
	private static final String CONTACTS_EMAILADDRESS 	= "emailAddress";
	private static final String CONTACTS_ADDRESSLINE1 	= "addressLine1";
	private static final String CONTACTS_ADDRESSLINE2 	= "addressLine2";
	private static final String CONTACTS_CITY			= "city";
	private static final String CONTACTS_COUNTRY		= "country";	
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
			+ CONTACTS_ADDRESSLINE1		+ " TEXT,"
			+ CONTACTS_ADDRESSLINE2 	+ " TEXT,"
			+ CONTACTS_CITY				+ " TEXT,"
			+ CONTACTS_COUNTRY 			+ " TEXT,"
			+ CONTACTS_DATEOFBIRTH 		+ " TEXT,"
			+ CONTACTS_PHOTO 			+ " TEXT);";
	
	//SQL String for deleting contacts table
	private static final String SQL_DELETE_CONTACTS_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	//-------------------------------------------------------------------------------------------------------------------------
	// Singleton design pattern. Only one instance of this class is used.
	//-------------------------------------------------------------------------------------------------------------------------
	public static synchronized ContactsDatabaseHelper getHelper(Context context){
		if (instance == null){
			instance = new ContactsDatabaseHelper(context);
		}
		return instance;		
	}
	//Private constructor to ensure a new instance cannot be made by anything other than this class.
	private ContactsDatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);		
	}
	//-------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_CONTACTS_TABLE);
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
	
	private ContentValues getContentValues(Contact contact){
		ContentValues contentValues = new ContentValues();		
		contentValues.put(ContactsDatabaseHelper.CONTACTS_FIRSTNAME    	,	contact.getFirstName());	
		contentValues.put(ContactsDatabaseHelper.CONTACTS_LASTNAME	   	,	contact.getLastName());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_MOBILEPHONE	,	contact.getMobilePhone());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_HOMEPHONE 	,	contact.getHomePhone());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_WORKPHONE 	,	contact.getWorkPhone());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_EMAILADDRESS 	,	contact.getEmailAddress());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_ADDRESSLINE1 	,	contact.getAddressLine1());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_ADDRESSLINE2 	,	contact.getAddressLine2());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_CITY			,	contact.getCity());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_COUNTRY		,	contact.getCountry());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_DATEOFBIRTH  	,	contact.getDateOfBirth());
		contentValues.put(ContactsDatabaseHelper.CONTACTS_PHOTO			, 	contact.getPhoto());
		
		return contentValues;
		
	}
	public void insertData (Contact contact){
		ContentValues contentValues = getContentValues(contact);		
		SQLiteDatabase db = this.getWritableDatabase();
		db.insert(ContactsDatabaseHelper.TABLE_NAME, null, contentValues);
		db.close();
	}
	
	public void updateData(Contact contact){
		ContentValues contentValues = getContentValues(contact);
		SQLiteDatabase db = this.getWritableDatabase();
		db.update(ContactsDatabaseHelper.TABLE_NAME, contentValues,CONTACTS_ID+"="+ contact.getID(), null);	
		db.close();
	}
	
	public void deleteContact(Contact contact){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME, CONTACTS_ID+"="+ contact.getID(), null);
		db.close();
	}
}                                                
