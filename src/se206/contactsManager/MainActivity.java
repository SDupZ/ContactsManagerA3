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

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

	private ListView contacts_listview;
	List<Contact> displayList;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);
               
        contacts_listview = (ListView)findViewById(R.id.contacts_listview);                
        //Setup ListView which displays contacts
        setupContactListView();
    }

    private void setupContactListView(){
    	displayList = new ArrayList<Contact>();
    	displayList.add(new Contact("John", "Smith"));
    	displayList.add(new Contact("John", "Smith"));
    	displayList.add(new Contact("John", "Smith"));
    	
    	ListAdapter listAdapter = new CustomListAdapter(MainActivity.this,displayList);
    	contacts_listview.setAdapter(listAdapter);
    	contacts_listview.setOnItemClickListener(this);  	
    }
  
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id){
		//String displayString = "You clicked item" + Integer.toString(clickedViewPosition);
		//Toast.makeText(clickedView.getContext(), displayString, Toast.LENGTH_SHORT).show();
		
		Intent intent = new Intent(MainActivity.this, ViewContact.class);
		startActivity(intent);
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

    private class CustomListAdapter extends ArrayAdapter<Contact>{
		private Context context;
		private List<Contact> contacts;
		
		CustomListAdapter(Context context, List<Contact> contacts){
			super(context, android.R.layout.simple_list_item_1, contacts);
			
			this.context = context;
			this.contacts = contacts;
		}
		
		public View getView(int position, View convertView, ViewGroup parent){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View listItemView = inflater.inflate(R.layout.custom_list_item_layout, null);
			
			TextView name = (TextView)listItemView.findViewById(R.id.contacts_listview_name);
			name.setText(""+ contacts.get(position).getFirstName() + " " + contacts.get(position).getLastName());
			
			ImageView photo = (ImageView)listItemView.findViewById(R.id.contacts_listview_photo);			
			Bitmap img = contacts.get(position).getPhoto(MainActivity.this);
			photo.setImageBitmap(img);
			photo.setAdjustViewBounds(true);
			photo.setMaxHeight(100);			
			photo.setMaxWidth(100);
			
			return listItemView;
		}
    }
	
}