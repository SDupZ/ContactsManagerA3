package se206.contactsManager;

import java.util.ArrayList;
import java.util.List;

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

public class MainActivity extends Activity{
	private ListView contacts_listview;
	private ContactsDatabaseHelper dbHelper;
	private ArrayAdapter<Contact> listAdapter;
	
	private MyContacts globalContactsList;
	private List<Contact> contactsList;

	//-------------------------------------------------------------------------------------------------------------------------
	// On Create
	//-------------------------------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        
        setContentView(R.layout.activity_main);
        
        dbHelper = ContactsDatabaseHelper.getHelper(MainActivity.this);        
        contactsList = new ArrayList<Contact>(); 
        contacts_listview = (ListView)findViewById(R.id.contacts_listview);    
        globalContactsList = MyContacts.getMyContacts();
        
        
        populateContactListFromDatabase();
      //Setup ListView which displays contacts
        setupContactListView();
    }
  //-------------------------------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.    	
        getMenuInflater().inflate(R.menu.main, menu);        
        return true;
    } 
    
    //-------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onResume(){
    	super.onResume();
    	
    	if (listAdapter != null){ 
    		listAdapter.notifyDataSetChanged();
    	}
    }   
    
    //-------------------------------------------------------------------------------------------------------------------------
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
     
    //Method to deal with the action bar add contact button
    public boolean onOptionsItemSelected(MenuItem item) {
    	//Add a contact.
        if (item.getItemId() == R.id.add_contact_button){
        	
        	startActivity(new Intent().setClass(MainActivity.this, AddContact.class));  
        
        //Sort contacts.
        }else if (item.getItemId() == R.id.sort_order_button){
        	AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        	CharSequence[] myList = {"Firstname", "Lastname", "Home Phone","Mobile Phone"};
        	
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
    
    //This should only have to be called once. -At the initial load of the application.
    private void populateContactListFromDatabase(){
    	Cursor c = dbHelper.getAllData();
    	c.moveToFirst();
    	do{
    		Contact newContact = new Contact(
    				c.getString(c.getColumnIndex(c.getColumnName(1))),
    				c.getString(c.getColumnIndex(c.getColumnName(2))),
    				c.getString(c.getColumnIndex(c.getColumnName(3))),
    				c.getString(c.getColumnIndex(c.getColumnName(4))),
    				c.getString(c.getColumnIndex(c.getColumnName(5))),
    				c.getString(c.getColumnIndex(c.getColumnName(6))),    				
    				c.getString(c.getColumnIndex(c.getColumnName(7))),
    				c.getString(c.getColumnIndex(c.getColumnName(8))),
    				c.getString(c.getColumnIndex(c.getColumnName(9))),
    				c.getString(c.getColumnIndex(c.getColumnName(10))),
    				c.getString(c.getColumnIndex(c.getColumnName(11))),
    				c.getString(c.getColumnIndex(c.getColumnName(12)))    	
    				
    				);    		
    		
    		newContact.setID(Integer.parseInt(c.getString(c.getColumnIndex(c.getColumnName(0)))));
    		contactsList.add(newContact);
    		
    	}while(c.moveToNext());
    	
    	globalContactsList.updateContactsList(contactsList);
    	//MyContacts.getMyContacts().reorderList(0);
    }
    //**********************************************************************************************************************************
  	// 	Used to adapt the database to the listview on the main screen.
    //	First the data is put into an array and then adapted to then screen
  	//**********************************************************************************************************************************
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
			
			String text = "" + contactsList.get(position).getFirstName() + " " + contactsList.get(position).getLastName();
			String photoPath = contactsList.get(position).getPhoto();
			
			if(photoPath == null || BitmapFactory.decodeFile(photoPath) == null){
				photo.setImageDrawable(getResources().getDrawable(R.drawable.dummyphoto));
			}else{
				photo.setImageBitmap(BitmapFactory.decodeFile(photoPath));
			}
			
			photo.setAdjustViewBounds(true);
			photo.setMaxHeight(100);			
			photo.setMaxWidth(100);

			//Set the text for each view.
			name.setText(text);			
			return view;
		}
	}
}