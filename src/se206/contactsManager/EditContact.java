package se206.contactsManager;

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
import android.widget.ImageView;
import android.widget.EditText;

public class EditContact extends Activity implements OnClickListener{
	ImageView photo;
	String photoPath;
	Contact editContact;
	
	private ContactsDatabaseHelper dbHelper;
	private int rowNumber;
	
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
	
	private EditText[] editTextFields;
	
	private static int RESULT_LOAD_IMAGE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		editContact = (Contact) this.getIntent().getExtras().get("contact");
		rowNumber = (Integer) this.getIntent().getExtras().get("rowNumber");
		
		setContentView(R.layout.activity_edit_contact);

		dbHelper = ContactsDatabaseHelper.getHelper(EditContact.this);
		
		photo = (ImageView)findViewById(R.id.edit_contact_image_view);
		photo.setOnClickListener(this);
		photoPath = editContact.getPhoto();
		if(photoPath == null){
			photo.setImageDrawable(getResources().getDrawable(R.drawable.dummyphoto));
		}else{
			photo.setImageBitmap(BitmapFactory.decodeFile(photoPath));
		}
		
		firstName   	=  (EditText)findViewById(R.id.edit_first_name);
		lastName        =  (EditText)findViewById(R.id.edit_last_name);
		mobilePhone     =  (EditText)findViewById(R.id.edit_mobile_phone);
		homePhone       =  (EditText)findViewById(R.id.edit_home_phone);
		workPhone       =  (EditText)findViewById(R.id.edit_work_phone);
		emailAddress    =  (EditText)findViewById(R.id.edit_email_address);
		addressLine1    =  (EditText)findViewById(R.id.edit_addressline1);
		addressLine2    =  (EditText)findViewById(R.id.edit_addressline2);
		city            =  (EditText)findViewById(R.id.edit_city);
		country         =  (EditText)findViewById(R.id.edit_country);
		dateOfBirth 	=  (EditText)findViewById(R.id.edit_dateofbirth);
		
		EditText[] fields = {firstName,lastName,mobilePhone,homePhone,workPhone,
			    emailAddress,addressLine1,addressLine2,city,country,dateOfBirth};
		editTextFields = fields;
		
		String[] contactDetails = {
				editContact.getFirstName(),
				editContact.getLastName(),
				editContact.getMobilePhone(),
				editContact.getHomePhone(),
				editContact.getWorkPhone(),
				editContact.getEmailAddress(),
				editContact.getAddressLine1(),
				editContact.getAddressLine2(),
				editContact.getCity(),
				editContact.getCountry(),
				editContact.getDateOfBirth()
			};
		String[] fieldValues = {"\"First Name\"", "\"Last Name\"", "\"Mobile Phone\"", "\"Home Phone\"", "\"Work Phone\"", "\"Email Address\"",
				"\"Address (Line 1)\"", "\"Address (Line 2)\"", "\"City\"", "\"Country\"", "\"Date of Birth\""};
		
		for( int i = 0; i< editTextFields.length; i++){
			String hint = "";
			if (contactDetails[i] == null || contactDetails[i].trim() == ""){				
				hint = fieldValues[i];
			}else{
				hint = contactDetails[i];
			}
			editTextFields[i].setHint(hint);
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.done_button){
			String f1 	= (firstName.getText().toString().equals("") 	== true) 	? editContact.getFirstName() 	:	firstName.getText().toString();
			String f2 	= (lastName.getText().toString().equals("") 	== true) 	? editContact.getLastName() 	:	lastName.getText().toString();
			String f3	= (mobilePhone.getText().toString().equals("") 	== true) 	? editContact.getMobilePhone() 	:	mobilePhone.getText().toString();
			String f4 	= (homePhone.getText().toString().equals("") 	== true) 	? editContact.getHomePhone() 	:	homePhone.getText().toString();  
			String f5 	= (workPhone.getText().toString().equals("") 	== true) 	? editContact.getWorkPhone() 	:	workPhone.getText().toString();  
			String f6 	= (emailAddress.getText().toString().equals("") == true) 	? editContact.getEmailAddress() :	emailAddress.getText().toString();  
			String f7 	= (addressLine1.getText().toString().equals("") == true) 	? editContact.getAddressLine1() :	addressLine1.getText().toString();  
			String f8 	= (addressLine2.getText().toString().equals("") == true) 	? editContact.getAddressLine2()	:	addressLine2.getText().toString();  
			String f9 	= (city.getText().toString().equals("") 		== true) 	? editContact.getCity() 		:	city.getText().toString();  
			String f10	= (country.getText().toString().equals("") 		== true) 	? editContact.getCountry() 		:	country.getText().toString();  
			String f11	= (dateOfBirth.getText().toString().equals("")	== true) 	? editContact.getDateOfBirth() 	:	dateOfBirth.getText().toString();  
			
			String f12	= photoPath;
			Contact updatedContact = new Contact(f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12);
			
			dbHelper.updateData(updatedContact, rowNumber);	
			onBackPressed();   
		}        
        return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(photo)){
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditContact.this);
        	
        	dialogBuilder.setTitle("Choose a picture");
        	dialogBuilder.setPositiveButton("Choose a Picture", new DialogInterface.OnClickListener(){        		
        		@Override
        		public void onClick(DialogInterface arg0, int arg1){
        			Intent i = new Intent(
        					Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);        					 
        			startActivityForResult(i, RESULT_LOAD_IMAGE);
        		}
        	});
        	dialogBuilder.setNegativeButton("Canel", null);
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
