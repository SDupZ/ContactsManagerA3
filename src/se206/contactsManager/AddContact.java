package se206.contactsManager;

import android.net.Uri;
import android.os.AsyncTask;
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

public class AddContact extends Activity implements OnClickListener{
	private ImageView 	photo;
	private EditText 	firstName;   
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
	private static int RESULT_LOAD_IMAGE = 1;
	private ContactsDatabaseHelper dbHelper;
	
	private String photoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		photo = (ImageView)findViewById(R.id.add_contact_image_view);
		photo.setOnClickListener(this);
		photoPath = null;
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
	
	public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.done_button){
        	String f1 	= (firstName.getText().toString().trim().equals("") 	== true) 	? null 	:	firstName.getText().toString();
			String f2 	= (lastName.getText().toString().trim().equals("") 	== true) 		? null 	:	lastName.getText().toString();
			String f3	= (mobilePhone.getText().toString().trim().equals("") 	== true) 	? null 	:	mobilePhone.getText().toString();
			String f4 	= (homePhone.getText().toString().trim().equals("") 	== true) 	? null	:	homePhone.getText().toString();  
			String f5 	= (workPhone.getText().toString().trim().equals("") 	== true) 	? null	:	workPhone.getText().toString();  
			String f6 	= (emailAddress.getText().toString().trim().equals("") == true) 	? null 	:	emailAddress.getText().toString();  
			String f7 	= (addressLine1.getText().toString().trim().equals("") == true) 	? null 	:	addressLine1.getText().toString();  
			String f8 	= (addressLine2.getText().toString().trim().equals("") == true) 	? null	:	addressLine2.getText().toString();  
			String f9 	= (city.getText().toString().trim().equals("") 		== true) 		? null	:	city.getText().toString();  
			String f10	= (country.getText().toString().trim().equals("") 		== true) 	? null	:	country.getText().toString();  
			String f11	= (dateOfBirth.getText().toString().trim().equals("")	== true) 	? null	:	dateOfBirth.getText().toString();  
			String f12	= photoPath == null ? null : photoPath;
			
			final Contact newContact = new Contact(f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11, f12);
			
			MyContacts.getMyContacts().getContactsList().add(newContact);
			
			new AsyncTask<Void, Void,Void>(){
		    	@Override
		    	protected Void doInBackground(Void...voids){
		    		dbHelper.insertData(newContact);
		    		return null;
		    	}    	
		    }.execute();			
        	onBackPressed();      	
        }
        return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(photo)){
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
        	
        	dialogBuilder.setTitle("Choose a picture");
        	dialogBuilder.setNegativeButton("Cancel", null);
        	dialogBuilder.setPositiveButton("Choose a Picture", new DialogInterface.OnClickListener(){        		
        		@Override
        		public void onClick(DialogInterface arg0, int arg1){
        			Intent i = new Intent(
        					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);        					 
        			startActivityForResult(i, RESULT_LOAD_IMAGE);
        		}
        	});
        	dialogBuilder.setCancelable(true);
        	
        	dialogBuilder.create().show();
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            
            photoPath = picturePath;
            photo.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
