package se206.contactsManager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewContact extends Activity {
	
	private ContactsDatabaseHelper dbHelper;
	private Contact viewContact;
	private int rowNumber;
	
	private ImageView viewContactPhoto;
	
	private TextView viewContactFirstname; 	
	private TextView viewContactLastname; 	
	private TextView viewContactHomePhone; 	
	private TextView viewContactMobilePhone; 
	private TextView viewContactWorkPhone; 	
	private TextView viewContactEmail; 		
	private TextView viewContactAddressline1;
	private TextView viewContactAddressline2;
	private TextView viewContactCity; 		
	private TextView viewContactCountry; 	
	private TextView viewContactDateOfBirth; 
	
	//-------------------------------------------------------------------------------------------------------------------------
	// On Create
	//-------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_view_contact);
		
		dbHelper = ContactsDatabaseHelper.getHelper(ViewContact.this); 
		int clickedViewPosition = (Integer)this.getIntent().getExtras().get("clickedViewPosition");
		
		rowNumber = clickedViewPosition;
		
		viewContactFirstname 		= (TextView)findViewById(R.id.view_contact_firstname);
		viewContactLastname 		= (TextView)findViewById(R.id.view_contact_lastname);
		viewContactHomePhone 		= (TextView)findViewById(R.id.view_contact_homephone);
		viewContactMobilePhone 		= (TextView)findViewById(R.id.view_contact_mobilephone);
		viewContactWorkPhone 		= (TextView)findViewById(R.id.view_contact_workphone);
		viewContactEmail 			= (TextView)findViewById(R.id.view_contact_email);		
		viewContactAddressline1 	= (TextView)findViewById(R.id.view_contact_addressline1);
		viewContactAddressline2 	= (TextView)findViewById(R.id.view_contact_addressline2);
		viewContactCity 			= (TextView)findViewById(R.id.view_contact_city);
		viewContactCountry 			= (TextView)findViewById(R.id.view_contact_country);
		viewContactDateOfBirth 		= (TextView)findViewById(R.id.view_contact_dateofbirth);		
		viewContactPhoto = (ImageView)findViewById(R.id.view_contact_photo);
		
		updateView();		
	} 
	
	//-------------------------------------------------------------------------------------------------------------------------
	protected void onResume(){
		super.onResume();
		updateView();
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	public boolean onOptionsItemSelected(MenuItem item) {
        
		//Edit Contact Button Pushed
        if (item.getItemId() == R.id.edit_contact_button){        
        	Intent intent = new Intent(ViewContact.this, EditContact.class);
        	intent.putExtra("contact", viewContact);
        	intent.putExtra("rowNumber", rowNumber);
	    	startActivity(intent);		
	    	
	    //Delete Contact Button Pushed
        }else if (item.getItemId() == R.id.delete_contact_button){
        	AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ViewContact.this);
        	
        	dialogBuilder.setTitle("Delete contact?");
        	dialogBuilder.setMessage("This contact will be removed.");
        	
        	dialogBuilder.setNegativeButton("Cancel", null);
        	dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){
        		
        		@Override
        		public void onClick(DialogInterface arg0, int arg1){

        			MyContacts.getMyContacts().getContactsList().remove(rowNumber);
        			new AsyncTask<Void, Void,Void>(){
        		    	@Override
        		    	protected Void doInBackground(Void...voids){
        		    		dbHelper.deleteContact(viewContact);
        		    		return null;
        		    	}    	
        		    }.execute();
        			onBackPressed();
        		}
        	});
        	dialogBuilder.setCancelable(true);        	
        	dialogBuilder.create().show();
        	
        //Back Contact Button Pushed
        }else if (item.getItemId() == R.id.back_contact_button){
        	onBackPressed(); 
        }
        return super.onOptionsItemSelected(item);
    }
	
	//-------------------------------------------------------------------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_contact, menu);
		return true;
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	// This method just updates the view page with updated information.
	//-------------------------------------------------------------------------------------------------------------------------	
	public void updateView(){
		viewContact = MyContacts.getMyContacts().getContactsList().get(rowNumber);
		
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
		if(viewContact.getPhoto() == null || BitmapFactory.decodeFile(viewContact.getPhoto()) == null){
			viewContactPhoto.setImageDrawable(getResources().getDrawable(R.drawable.dummyphoto));
		}else{
			viewContactPhoto.setImageBitmap(BitmapFactory.decodeFile(viewContact.getPhoto()));
		}
	}	
	
	

}
