package se206.contactsManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.EditText;

public class EditContact extends Activity implements OnClickListener{
	ImageView photo;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		editContact = (Contact) this.getIntent().getExtras().get("contact");
		rowNumber = (Integer) this.getIntent().getExtras().get("rowNumber");
		
		setContentView(R.layout.activity_edit_contact);

		dbHelper = ContactsDatabaseHelper.getHelper(EditContact.this);
		photo = (ImageView)findViewById(R.id.edit_contact_image_view);
		photo.setOnClickListener(this);

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
			if (contactDetails[i] == null){				
				hint = fieldValues[i];
			}else{
				hint = contactDetails[i];
			}
			editTextFields[i].setHint(hint);
		}
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		String f1 	= (firstName.getText().toString().equals("") 	== true) 	? null :	firstName.getText().toString();
		String f2 	= (lastName.getText().toString().equals("") 	== true) 	? null :	lastName.getText().toString();
		String f3	= (mobilePhone.getText().toString().equals("") 	== true) 	? null :	mobilePhone.getText().toString();
		String f4 	= (homePhone.getText().toString().equals("") 	== true) 	? null :	homePhone.getText().toString();  
		String f5 	= (workPhone.getText().toString().equals("") 	== true) 	? null :	workPhone.getText().toString();  
		String f6 	= (emailAddress.getText().toString().equals("") == true) 	? null :	emailAddress.getText().toString();  
		String f7 	= (addressLine1.getText().toString().equals("") == true) 	? null :	addressLine1.getText().toString();  
		String f8 	= (addressLine2.getText().toString().equals("") == true) 	? null :	addressLine2.getText().toString();  
		String f9 	= (city.getText().toString().equals("") 		== true) 	? null :	city.getText().toString();  
		String f10	= (country.getText().toString().equals("") 		== true) 	? null :	country.getText().toString();  
		String f11	= (dateOfBirth.getText().toString().equals("")	== true) 	? null :	dateOfBirth.getText().toString();  

		Contact updatedContact = new Contact(f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11);
		
		dbHelper.updateData(updatedContact, rowNumber);
		
		// Handle presses on the action bar items
		if (item.getItemId() == R.id.done_button){
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
        	dialogBuilder.setNegativeButton("Choose a Picture", null);
        	dialogBuilder.setPositiveButton("Take a photo", null);
        	dialogBuilder.setCancelable(true);
        	
        	dialogBuilder.create().show();
		}
	}
}
