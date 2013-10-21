package se206.contactsManager;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	private ListView contacts_listview;
	private ContactsDatabaseHelper dbHelper;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.activity_main);
        
        dbHelper = new ContactsDatabaseHelper(MainActivity.this);        
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
    	//ListAdapter listAdapter = new CustomListAdapter();
    	CursorAdapter listAdapter = new CustomCursorAdapter(MainActivity.this, dbHelper.getAllData());
    	contacts_listview.setAdapter(listAdapter);

    	contacts_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    		public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition, long id){	    
        		String displayString = "Yout clicked " + clickedViewPosition;
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
  
    private class CustomCursorAdapter extends CursorAdapter{
		public CustomCursorAdapter(Context context, Cursor c){
			super(context, c);
		}
		
		private Context context;
		
		public View newView(Context context, Cursor cursor, ViewGroup parent){
			
			//Create a layout inflator to inflate our xml layout for each item in the list.			
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			//Inflate the list item layout. Keep a reference to the inflated view. Note there is no view root specified.
			View listItemView = inflater.inflate(R.layout.custom_list_item_layout, null);
						
			return listItemView;
		}
		
		public void bindView(View view, Context context, Cursor cursor){
			//Access the textview element inside the view.
			TextView name = (TextView)view.findViewById(R.id.contacts_listview_name);
			
			//Set the text for each view.
			name.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
		}
    }
	
}