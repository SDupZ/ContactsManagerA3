package se206.contactsManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDatabaseHelper extends SQLiteOpenHelper{
	private static ContactsDatabaseHelper instance;
	
	public static final int COLUMN_FIRSTNAME = 1;
	public static final int COLUMN_LASTNAME = 2;
	
	// Database version
	public static final int DATABASE_VERSION = 1;
	
	// Contacts Table Name
	public static final String TABLE_NAME = "ContactsTable";
	
	// Contacts Table Columns
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
	//private static final String CONTACTS_PHOTO 			= "photo";
	
	//SQL String for creating contacts table.
	private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
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
			+ CONTACTS_DATEOFBIRTH 		+ " TEXT);";
			//+ CONTACTS_PHOTO 			+ " STRING);";
	
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
		super(context, TABLE_NAME, null, DATABASE_VERSION);		
	}
	//-------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL(CREATE_CONTACTS_TABLE);
		
		//Just testing purposes
		final String FIRST_ENTRY = "INSERT INTO " + TABLE_NAME + " VALUES('Simon', 'du Preez', null, null, null, null, null, null, null, null, null)";
		db.execSQL(FIRST_ENTRY);
		final String SECOND_ENTRY = "INSERT INTO " + TABLE_NAME + " VALUES('John','Snow', null, null, null, null, null, null, null, null, null)";
		db.execSQL(SECOND_ENTRY);
		final String THIRD_ENTRY = "INSERT INTO " + TABLE_NAME + " VALUES('Nic', 'Cage', null, null, null, null, null, null, null, null, null)";
		db.execSQL(THIRD_ENTRY);
		
	}
	
	public Cursor getAllData(){
		String buildSQL = "SELECT rowid _id,* FROM " + TABLE_NAME;
		return this.getReadableDatabase().rawQuery(buildSQL, null);
	}
	
	/**
	 *  Will return null value if rowNumber given is invalid.
	 * @param rowNumber
	 * @return Contact
	 */
	public Contact getContact(int rowNumber){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor rows = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		
		Contact contact = null;
		if (rows.moveToPosition(rowNumber)){
			contact	= new Contact(rows.getString(0),rows.getString(1),rows.getString(2),rows.getString(3),rows.getString(4),rows.getString(5),rows.getString(6),
					rows.getString(7),rows.getString(8),rows.getString(9),rows.getString(10));
		}
		
		db.close();
		return contact;
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(SQL_DELETE_CONTACTS_TABLE);
		onCreate(db);
	}
}
