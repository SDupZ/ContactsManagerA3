package se206.contactsManager;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class AddContact extends Activity implements OnClickListener{
	ImageView photo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		photo = (ImageView)findViewById(R.id.add_contact_image_view);
		photo.setOnClickListener(this);
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
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v.equals(photo)){
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(AddContact.this);
        	
        	dialogBuilder.setTitle("Choose a picture");
        	dialogBuilder.setNegativeButton("Choose a Picture", null);
        	dialogBuilder.setPositiveButton("Take a photo", null);
        	dialogBuilder.setCancelable(true);
        	
        	dialogBuilder.create().show();
		}
	}
	
	

}
