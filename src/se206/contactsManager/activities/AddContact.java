package se206.contactsManager.activities;

import se206.contactsManager.Contact;
import se206.contactsManager.MyContacts;
import se206.contactsManager.R;
import se206.contactsManager.database.ContactsDatabaseHelper;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * This class is the Add contact activity. It is responsible for creating and adding a new
 * contact to the database as well as the contactslist for this session.
 * 
 * @author 	Simon du Preez 	
 * 			5562045
 * 			sdup571
 */
public class AddContact extends Activity implements OnClickListener{
	
	private static int RESULT_LOAD_IMAGE = 1;				//Used to retrieve a photo from the gallery
	
	private ImageView 	photo;								//ImageView for photo
	private String 		photoPath;							//Stores the path to an image stored in the gallery.
	
	private EditText 	firstName;   						//Editable Text fields to get contact details.
	private EditText  	lastName;    
	private EditText  	mobilePhone; 
	private EditText  	homePhone;   
	private EditText  	workPhone;   
	private EditText  	emailAddress;
	private EditText  	addressLine1;
	private EditText  	addressLine2;
	private EditText  	city;        
	private EditText  	country;     
	private EditText 	dateOfBirth; 
	
	private ContactsDatabaseHelper dbHelper;				//Instance of databasehelper to enable reading / writing to/from database.
	
	//-------------------------------------------------------------------------------------------------------------------------
	// Oncreate for this activity.
	//-------------------------------------------------------------------------------------------------------------------------
	/**
	 * This oncreate method must setup all the edit text fields to allow the user to enter information 
	 * regarding a contact.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		photo = (ImageView)findViewById(R.id.add_contact_image_view);		
		photo.setOnClickListener(this);												//Set an onclick listener so we can choose a photo.
		photoPath = null;															//Photo path is initially null as we have no photo yet!
		
		firstName   	=  (EditText)findViewById(R.id.first_name);
		lastName        =  (EditText)findViewById(R.id.last_name);
		mobilePhone     =  (EditText)findViewById(R.id.mobile_phone);
		homePhone       =  (EditText)findViewById(R.id.home_phone);
		workPhone       =  (EditText)findViewById(R.id.work_phone);
		emailAddress    =  (EditText)findViewById(R.id.email_address);
		addressLine1    =  (EditText)findViewById(R.id.addressline1);
		addressLine2    =  (EditText)findViewById(R.id.addressline2);
		city            =  (EditText)findViewById(R.id.city);
		country         =  (EditText)findViewById(R.id.country);
		dateOfBirth 	=  (EditText)findViewById(R.id.dateofbirth);
		
		dbHelper = ContactsDatabaseHelper.getHelper(AddContact.this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	// Done button pressed. Methods in charge of saving details and storing them in database and contacts list.
	//-------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method is called when the menu button "done" is pressed. This means we must save the contact to the database and
	 * add it to our contact list. Adding a contact takes a litter longer than the other functions as an id must be retrieved
	 * from the database and then put into the contact list. This means that we can't put this in a background thread as the 
	 * contact will just randomly pop-up on the main list when the background thread finishes. This won't look as good as
	 * a slight delay in switching screens.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		
        if (item.getItemId() == R.id.done_button){        	
        	String f1 	= (firstName.getText().toString().trim().equals("") 	== true) 	? null 	:	firstName.getText().toString();
			String f2 	= (lastName.getText().toString().trim().equals("") 		== true) 	? null 	:	lastName.getText().toString();
			String f3	= (mobilePhone.getText().toString().trim().equals("") 	== true) 	? null 	:	mobilePhone.getText().toString();
			String f4 	= (homePhone.getText().toString().trim().equals("") 	== true) 	? null	:	homePhone.getText().toString();  
			String f5 	= (workPhone.getText().toString().trim().equals("") 	== true) 	? null	:	workPhone.getText().toString();  
			String f6 	= (emailAddress.getText().toString().trim().equals("") 	== true) 	? null 	:	emailAddress.getText().toString();  
			String f7 	= (addressLine1.getText().toString().trim().equals("") 	== true) 	? null 	:	addressLine1.getText().toString();  
			String f8 	= (addressLine2.getText().toString().trim().equals("") 	== true) 	? null	:	addressLine2.getText().toString();  
			String f9 	= (city.getText().toString().trim().equals("") 			== true) 	? null	:	city.getText().toString();  
			String f10	= (country.getText().toString().trim().equals("") 		== true) 	? null	:	country.getText().toString();  
			String f11	= (dateOfBirth.getText().toString().trim().equals("")	== true) 	? null	:	dateOfBirth.getText().toString();  
			
			String f12	= (photoPath == null ? null : photoPath);		//Photo path is either null or the path to a photo in the gallery. 
			
			//Create the new contact from the data provided.
			final Contact newContact = new Contact(f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12);	
			
			//Insert the data into the database. This will give the contact a unique id.
			dbHelper.insertData(newContact);	
			
			//Retrieve this id assigned by the database and then set the id in our new contact.
			newContact.setID(getLastId());		
			
			//Finally update our contacts list with the new contact.
			MyContacts.getMyContacts().getContactsList().add(newContact);
			
        	onBackPressed();      	
        }
        return super.onOptionsItemSelected(item);
	}
	
	/**
	 *  This method gets the last id assigned in the database. This is useful as we need to assign this id to the contact we just
	 *  created otherwise we won't be able to identify the contact in the database.
	 * @return long
	 */
	private long getLastId(){
		String query = "SELECT ROWID from "+ ContactsDatabaseHelper.TABLE_NAME +" order by ROWID DESC limit 1";				
		Cursor c = dbHelper.getReadableDatabase().rawQuery(query, null);
		
		long lastId = -1;								//long variable to store the last id assigned by database.
		if (c != null && c.moveToFirst()) {	
		    lastId = c.getLong(0);						//Get id in first column. We know this is accurate as we just put something there.
		}		
		return lastId;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	// Listener "onclick" method for the image view. Methods below are for choosing a photo.
	//-------------------------------------------------------------------------------------------------------------------------
	/**
	 *	This method is the onClick method for the listener attached to the image view. When the image view is clicked
	 *	the user is taken to the android gallery and allowed to choose a photo to set as the contact photo.
	 */
	@Override
	public void onClick(View v) {
		
		if (v.equals(photo)){			
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
        	dialogBuilder.setTitle("Choose a picture");
        	dialogBuilder.setNegativeButton("Cancel", null);
        	dialogBuilder.setPositiveButton("Choose a Picture", new DialogInterface.OnClickListener(){        		
        		@Override
        		public void onClick(DialogInterface arg0, int arg1){
        			Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);        					 
        			startActivityForResult(i, RESULT_LOAD_IMAGE);
        		}
        	});
        	dialogBuilder.setCancelable(true);			//Allow the user to cancel the dialog by touching outside the box.
        	
        	dialogBuilder.create().show();
		}
	}
	
	/**
	 *	Used to obtain the image path from the gallery.
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            
            photoPath = picturePath;
            photo.setImageBitmap(BitmapFactory.decodeFile(picturePath));			//Update photo to make it look nice.
        }
    }
}
