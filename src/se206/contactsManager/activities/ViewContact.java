package se206.contactsManager.activities;

import se206.contactsManager.Contact;
import se206.contactsManager.MyContacts;
import se206.contactsManager.R;
import se206.contactsManager.database.ContactsDatabaseHelper;
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

/**
 * This class view contact activity which is used to display details about a contact.
 * From here a user can also edit or delete the contact. Or return to the main screen.
 * 
 * @author 	Simon du Preez 	
 * 			5562045
 * 			sdup571
 */
public class ViewContact extends Activity {
	
	private Contact viewContact;					//The current contact that is being view.
	private int rowNumber;							//The position of the contact in the contacts array.
	
	private ImageView viewContactPhoto;				//For displaying the photo of the current contact.
	
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
	
	private ContactsDatabaseHelper dbHelper;
	
	//-------------------------------------------------------------------------------------------------------------------------
	// On Create. Initialises this activity.
	//-------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_view_contact);
		
		dbHelper = ContactsDatabaseHelper.getHelper(ViewContact.this); 
		
		rowNumber = (Integer)this.getIntent().getExtras().get("clickedViewPosition");			//Get the rownumber from previous activity.	
		
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
		
		updateView();						//Helper method to populate the view with all the details.
	} 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_contact, menu);
		return true;
	}
	
	/**
	 * On resume method. Calls update view incase anything has changed. eg user presses edit and comes back to this screen.
	 */
	protected void onResume(){
		super.onResume();
		updateView();
	}
	
	//-------------------------------------------------------------------------------------------------------------------------
	// Update View  Method
	//-------------------------------------------------------------------------------------------------------------------------	
	/**
	 * This method is called whenever data has changed so that the most up to date information is shown to the user
	 * about the contact.
	 */
	public void updateView(){
		viewContact = MyContacts.getMyContacts().getContactsList().get(rowNumber);		//Get the contact from the position.
		
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
	
	//-------------------------------------------------------------------------------------------------------------------------
	// Listener for the menu buttons. Edit contact, delete or back.
	//-------------------------------------------------------------------------------------------------------------------------
	public boolean onOptionsItemSelected(MenuItem item) {        
		
        if (item.getItemId() == R.id.edit_contact_button){        						//Edit Contact Button Pushed
        	Intent intent = new Intent(ViewContact.this, EditContact.class);	
        	intent.putExtra("contact", viewContact);
        	intent.putExtra("rowNumber", rowNumber);
	    	startActivity(intent);		
	    
        }else if (item.getItemId() == R.id.delete_contact_button){						//Delete Contact Button Pushed
        	
        	AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ViewContact.this);       	
        	dialogBuilder.setTitle("Delete contact?");
        	dialogBuilder.setMessage("This contact will be removed.");        	
        	dialogBuilder.setNegativeButton("Cancel", null);
        	dialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener(){        		
        		@Override
        		public void onClick(DialogInterface arg0, int arg1){
        			MyContacts.getMyContacts().getContactsList().remove(rowNumber);		//Remove contact from contactslist
        			new AsyncTask<Void, Void,Void>(){									
        		    	@Override
        		    	protected Void doInBackground(Void...voids){
        		    		dbHelper.deleteContact(viewContact);		//Delete contact from database in background. This causes 
        		    		return null;								//a delay if not done in the background.
        		    	}    	
        		    }.execute();
        			onBackPressed();
        		}
        	});
        	dialogBuilder.setCancelable(true);        	
        	dialogBuilder.create().show();
        	
        }else if (item.getItemId() == R.id.back_contact_button){						//Back Contact Button Pushed
        	onBackPressed(); 
        }
        return super.onOptionsItemSelected(item);
    }

	
	
	

}
