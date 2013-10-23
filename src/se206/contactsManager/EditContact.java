package se206.contactsManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.EditText;

public class EditContact extends Activity implements OnClickListener{
	ImageView photo;
	Contact editContact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		editContact = (Contact) this.getIntent().getExtras().get("contact");		
		setContentView(R.layout.activity_edit_contact);
		
		photo = (ImageView)findViewById(R.id.edit_contact_image_view);
		photo.setOnClickListener(this);
		
		EditText 	firstName   	=  (EditText)findViewById(R.id.edit_first_name);
		EditText  	lastName        =  (EditText)findViewById(R.id.edit_last_name);
		EditText  	mobilePhone     =  (EditText)findViewById(R.id.edit_mobile_phone);
		EditText  	homePhone       =  (EditText)findViewById(R.id.edit_home_phone);
		EditText  	workPhone       =  (EditText)findViewById(R.id.edit_work_phone);
		EditText  	emailAddress    =  (EditText)findViewById(R.id.edit_email_address);
		EditText  	addressLine1    =  (EditText)findViewById(R.id.edit_addressline1);
		EditText  	addressLine2    =  (EditText)findViewById(R.id.edit_addressline2);
		EditText  	city            =  (EditText)findViewById(R.id.edit_city);
		EditText  	country         =  (EditText)findViewById(R.id.edit_country);
		EditText 	dateOfBirth 	=  (EditText)findViewById(R.id.edit_dateofbirth);
		
		EditText[] editTextFields = {				                
				    firstName   ,
				    lastName    ,
				    mobilePhone ,
				    homePhone   ,
				    workPhone   ,
				    emailAddress,
				    addressLine1,
				    addressLine2,
				    city        ,
				    country     ,
				    dateOfBirth
		};
		
		String[] contactDetails = 
			{
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
