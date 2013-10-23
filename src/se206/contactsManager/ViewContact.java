package se206.contactsManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewContact extends Activity {
	
	private ContactsDatabaseHelper dbHelper;
	private Contact viewContact;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_view_contact);
		
		dbHelper = ContactsDatabaseHelper.getHelper(ViewContact.this); 
		int clickedViewPosition = (Integer)this.getIntent().getExtras().get("clickedViewPosition");
		viewContact = dbHelper.getContact(clickedViewPosition);
		
		TextView viewContactFirstname 		= (TextView)findViewById(R.id.view_contact_firstname);
		TextView viewContactLastname 		= (TextView)findViewById(R.id.view_contact_lastname);
		TextView viewContactHomePhone 		= (TextView)findViewById(R.id.view_contact_homephone);
		TextView viewContactMobilePhone 	= (TextView)findViewById(R.id.view_contact_mobilephone);
		TextView viewContactWorkPhone 		= (TextView)findViewById(R.id.view_contact_workphone);
		TextView viewContactEmail 			= (TextView)findViewById(R.id.view_contact_email);		
		TextView viewContactAddressline1 	= (TextView)findViewById(R.id.view_contact_addressline1);
		TextView viewContactAddressline2 	= (TextView)findViewById(R.id.view_contact_addressline2);
		TextView viewContactCity 			= (TextView)findViewById(R.id.view_contact_city);
		TextView viewContactCountry 		= (TextView)findViewById(R.id.view_contact_country);
		TextView viewContactDateOfBirth 	= (TextView)findViewById(R.id.view_contact_dateofbirth);
		  
		viewContactFirstname.setText(viewContact.getFirstName());
		viewContactLastname.setText(viewContact.getLastName());
		viewContactHomePhone.setText(viewContact.getHomePhone());
		viewContactMobilePhone.setText(viewContact.getMobilePhone());
		viewContactWorkPhone.setText(viewContact.getWorkPhone());
		viewContactEmail.setText(viewContact.getEmailAddress());
		viewContactAddressline1.setText(viewContact.getAddressLine1());
		viewContactAddressline2.setText(viewContact.getAddressLine2());
		viewContactCity.setText(viewContact.getCity());
		viewContactCountry.setText(viewContact.getCountry());
		viewContactDateOfBirth.setText(viewContact.getDateOfBirth());
	}                           
	public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.edit_contact_button){        
        	Intent intent = new Intent(ViewContact.this, EditContact.class);
        	intent.putExtra("contact", viewContact);
	    	startActivity(intent);		    	
        }else if (item.getItemId() == R.id.delete_contact_button){
        	AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ViewContact.this);
        	
        	dialogBuilder.setTitle("Delete contact?");
        	dialogBuilder.setMessage("This contact will be removed.");
        	
        	dialogBuilder.setNegativeButton("Cancel", null);
        	dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
        		
        		@Override
        		public void onClick(DialogInterface arg0, int arg1){
        			onBackPressed();
        		}
        	});
        	dialogBuilder.setCancelable(true);
        	
        	dialogBuilder.create().show();
        }else if (item.getItemId() == R.id.back_contact_button){
        	onBackPressed(); 
        }
        return super.onOptionsItemSelected(item);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_contact, menu);
		return true;
	}

}
