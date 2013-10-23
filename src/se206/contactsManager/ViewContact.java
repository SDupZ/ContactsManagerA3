package se206.contactsManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewContact extends Activity {
	private ContactsDatabaseHelper dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_view_contact);
		
		dbHelper = ContactsDatabaseHelper.getHelper(ViewContact.this); 
		int clickedViewPosition = (Integer)this.getIntent().getExtras().get("clickedViewPosition");
		Contact viewContact = dbHelper.getContact(clickedViewPosition);
		
		TextView viewContactFirstname = (TextView)findViewById(R.id.view_contact_firstname);
		TextView viewContactLastname = (TextView)findViewById(R.id.view_contact_lastname);
		TextView viewContactHomePhone = (TextView)findViewById(R.id.view_contact_homephone);
		TextView viewContactMobilePhone = (TextView)findViewById(R.id.view_contact_mobilephone);
		TextView viewContactWorkPhone = (TextView)findViewById(R.id.view_contact_workphone);
		  
		viewContactFirstname.setText(viewContact.getFirstName());
		viewContactLastname.setText(viewContact.getLastName());
		viewContactHomePhone.setText(viewContact.getHomePhone());
		viewContactMobilePhone.setText(viewContact.getMobilePhone());
		viewContactWorkPhone.setText(viewContact.getWorkPhone());
		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.edit_contact_button){        	   	
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
