package se206.contactsManager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

	private ListView contacts_listview;
	private List<Contact> displayList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);
        
        contacts_listview = (ListView)findViewById(R.id.contacts_listview);    
        
        //Setup ListView which displays contacts
        setupContactListView();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.    	
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    } 
      
    private void setupContactListView(){
    	displayList = new ArrayList<Contact>();
    	displayList.add(new Contact("John", "Smith"));
    	displayList.add(new Contact("John", "Smith"));
    	displayList.add(new Contact("John", "Smith"));
    	
    	ListAdapter listAdapter = new CustomListAdapter();
    	contacts_listview.setAdapter(listAdapter);
    	contacts_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id){	    
        		Contact selectedContact = displayList.get(clickedViewPosition);
        		
    	    	String displayString = "Yout clicked " + selectedContact;
    	    	Toast.makeText(clickedView.getContext(), displayString, Toast.LENGTH_SHORT).show();
    	    	//Intent intent = new Intent(MainActivity.this, ViewContact.class);
    	    	//startActivity(intent);		
        	}    		
    	});  	
    }
     
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //Method to deal with the action bar add contact button
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if (item.getItemId() == R.id.add_contact_button){
        	startActivity(new Intent().setClass(MainActivity.this, AddContact.class));        	
        }
        return super.onOptionsItemSelected(item);
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //Custom List adapter to adapt list of contacts to a graphical view.    
    private class CustomListAdapter extends ArrayAdapter<Contact>{
		CustomListAdapter(){
			super(MainActivity.this, android.R.layout.simple_list_item_1, displayList);
		}
		
		public View getView(int position, View convertView, ViewGroup parent){
			
			//Create a layout inflator to inflate our xml layout for each item in the list.			
			LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			//Inflate the list item layout. Keep a reference to the inflated view. Note there is no view root specified.
			View listItemView = inflater.inflate(R.layout.custom_list_item_layout, null);
			
			//Access the textview element inside the view.
			TextView name = (TextView)listItemView.findViewById(R.id.contacts_listview_name);
			
			//Set the text for each view.
			name.setText(""+ displayList.get(position).getFirstName() + " " + displayList.get(position).getLastName());
			
			//Add image to view.
			ImageView photo = (ImageView)listItemView.findViewById(R.id.contacts_listview_photo);			
			Bitmap img = displayList.get(position).getPhoto(MainActivity.this);
			photo.setImageBitmap(img);
			
			photo.setAdjustViewBounds(true);
			photo.setMaxHeight(100);			
			photo.setMaxWidth(100);
			
			return listItemView;
		}
    }
	
}