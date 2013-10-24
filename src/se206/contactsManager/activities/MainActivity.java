package se206.contactsManager.activities;

import java.util.ArrayList;
import java.util.List;

import se206.contactsManager.Contact;
import se206.contactsManager.MyContacts;
import se206.contactsManager.R;
import se206.contactsManager.database.ContactsDatabaseHelper;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

/**
 * 	This class is the main activity. It shows the contacts list and is the access point
 * 	for users to the program.
 * 
 * @author 	Simon du Preez 	
 * 			5562045
 * 			sdup571
 */
public class MainActivity extends Activity{
	
	private static int sortOrder;					//Variable to store what the current sort order is.
	
	private List<Contact> contactsList;				//Contacts to be display.
	private ListView contacts_listview;				//ListView to display contacts.
	private ArrayAdapter<Contact> listAdapter;		//Adapter to make above two data types compatable.
	
	private ContactsDatabaseHelper dbHelper;	

	//-------------------------------------------------------------------------------------------------------------------------
	// On Create for main activity.
	//-------------------------------------------------------------------------------------------------------------------------
	/**
	 * This is where the application loads. When the application is first started, the contacts list in the MyContacts class
	 * is updated and synced with the database.
	 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        dbHelper = ContactsDatabaseHelper.getHelper(MainActivity.this);
        
        contactsList = new ArrayList<Contact>(); 
        contacts_listview = (ListView)findViewById(R.id.contacts_listview);   
        sortOrder = MyContacts.FIRSTNAME_ORDER;									//Initalize the sort order to be by firstname.
        
        populateContactListFromDatabase();										//Get the contacts from the database.
        setupContactListView();													//Populate the listview with contacts.
    }
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.    	
        getMenuInflater().inflate(R.menu.main, menu);        
        return true;
    } 
    
    /**
     * On resume method is called when this activity is resumed. We resort the list to the sort order as it may
     * have had data removed or inserted and we want it to be in the right order.
     * Then tell the listadapater it needs to refresh the screen as data has changed.
     */
    @Override
    public void onResume(){
    	super.onResume();
    	
    	if (listAdapter != null){
    		MyContacts.getMyContacts().reorderList(sortOrder);
    		listAdapter.notifyDataSetChanged();
    	}
    }   
    
    //-------------------------------------------------------------------------------------------------------------------------
  	// Helper Methods for initialising the application
  	//-------------------------------------------------------------------------------------------------------------------------
    /**
     * This method adapts the contact list and the listview so that the listview is populated with contacts.
     */
    private void setupContactListView(){ 	
    	
    	listAdapter = new CustomListAdapter(MainActivity.this);    	    	
    	contacts_listview.setAdapter(listAdapter);    	
    	contacts_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {    	
    		
    		public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id){    			
    	    	Intent intent = new Intent(MainActivity.this, ViewContact.class);
    	    	intent.putExtra("clickedViewPosition", clickedViewPosition);
    	    	startActivity(intent);	
    	    	
        	}
    	});    	
    }   
    
    //This is called to get the contacts from the database and sync that to the contacts in the MyContacts class.
    private void populateContactListFromDatabase(){
    	Cursor c = dbHelper.getAllData();
    	if(c.moveToFirst()){
	    	do{
	    		Contact newContact = new Contact(								//Create a new contact for each row in the database.
	    				c.getString(c.getColumnIndex(c.getColumnName(2))),		//Each column represents a field for a contact.
	    				c.getString(c.getColumnIndex(c.getColumnName(3))),
	    				c.getString(c.getColumnIndex(c.getColumnName(4))),
	    				c.getString(c.getColumnIndex(c.getColumnName(5))),
	    				c.getString(c.getColumnIndex(c.getColumnName(6))),
	    				c.getString(c.getColumnIndex(c.getColumnName(7))),    				
	    				c.getString(c.getColumnIndex(c.getColumnName(8))),
	    				c.getString(c.getColumnIndex(c.getColumnName(9))),
	    				c.getString(c.getColumnIndex(c.getColumnName(10))),
	    				c.getString(c.getColumnIndex(c.getColumnName(11))),
	    				c.getString(c.getColumnIndex(c.getColumnName(12))),
	    				c.getString(c.getColumnIndex(c.getColumnName(13)))
	    				);    		
	    		
	    		newContact.setID(Integer.parseInt(c.getString(c.getColumnIndex(c.getColumnName(1)))));	    		
	    		contactsList.add(newContact);
	    		
	    	}while(c.moveToNext());
    	}
    	MyContacts.getMyContacts().updateContactsList(contactsList);	
    	MyContacts.getMyContacts().reorderList(sortOrder);				
    }
    
    //-------------------------------------------------------------------------------------------------------------------------
  	// Add Contact button pressed. Or Sort pressed.
  	//-------------------------------------------------------------------------------------------------------------------------
    /**
     * This is the method called from the listener attached to the add contact button in the action bar.
     * It is also called if the sort button is pushed inside the menu bar.
     * 
     * If the add contact button is pushed, it simply loads the add contact activity.
     * If the sort button is pushed it opens up a dialog with a number of options to enable the user to 
     * choose what order the list should be displayed in.
     */
    public boolean onOptionsItemSelected(MenuItem item) {
    	
        if (item.getItemId() == R.id.add_contact_button){        	
        	startActivity(new Intent().setClass(MainActivity.this, AddContact.class));          	//Add a contact.

        }else if (item.getItemId() == R.id.sort_order_button){
        	CharSequence[] myList = {"Firstname", "Lastname", "Home Phone","Mobile Phone"};			//These are the sort options.
        	
        	AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        	dialogBuilder.setTitle("Choose a sort order:");        	
        	dialogBuilder.setSingleChoiceItems(myList, 0, null);
        	dialogBuilder.setNegativeButton("Cancel", null);
        	dialogBuilder.setPositiveButton("Select", new DialogInterface.OnClickListener(){		
        		public void onClick(DialogInterface dialog, int id){
        			int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
        			
        			MyContacts.getMyContacts().reorderList(selectedPosition);
        			listAdapter.notifyDataSetChanged();
        		}
        	});
        	dialogBuilder.setCancelable(true);        	
        	dialogBuilder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }
    
    //-------------------------------------------------------------------------------------------------------------------------
  	//	Adapter. Used to adapt the database to the listview on the main screen.
  	//-------------------------------------------------------------------------------------------------------------------------
    /**
     * An ArrayAdapter is extended to be able to display contact objects.
     * 
     */
	private class CustomListAdapter extends ArrayAdapter<Contact>{		
		private Context context;
		
		CustomListAdapter(Context context){			
			super(context, android.R.layout.simple_list_item_1, contactsList);
			this.context = context;
		}
		
		public View getView(int position, View convertView, ViewGroup parent){
			
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.custom_list_item_layout, null);
			
			TextView name = (TextView)view.findViewById(R.id.contacts_listview_name);
			ImageView photo = (ImageView)view.findViewById(R.id.contacts_listview_photo);
			
			String text = "";
			if (contactsList.get(position).getFirstName() != null){							//Make sure we don't write "null" to the display!
				text += contactsList.get(position).getFirstName() + " ";
			}
			if (contactsList.get(position).getLastName() != null){
				text += contactsList.get(position).getLastName();
			}
			String photoPath = contactsList.get(position).getPhoto();
			
			if(photoPath == null || BitmapFactory.decodeFile(photoPath) == null){			//Shows either dummy photo or contact photo.
				photo.setImageDrawable(getResources().getDrawable(R.drawable.dummyphoto));
			}else{
				photo.setImageBitmap(BitmapFactory.decodeFile(photoPath));
			}
			
			photo.setAdjustViewBounds(true);
			photo.setMaxHeight(100);			
			photo.setMaxWidth(100);

			name.setText(text);			
			return view;
		}
	}
}