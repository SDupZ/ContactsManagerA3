package se206.contactsManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Contact{
	private String firstName;
	private String lastName;
	private String mobilePhone;
	private String homePhone;
	private String workPhone;
	private String emailAddress;
	private int streetNumber;
	private String streetName;
	private String suburb;
	private String city;
	
	//private String dateOfBirth;
	private Bitmap photo;
	
	public Contact(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName(){
		return firstName;
	}	
	public String getLastName(){
		return lastName;
	}	
	public String getMobilePhone(){
		return mobilePhone;
	}
	public String getWorkPhone(){
		return workPhone;
	}
	public String getHomePhone(){
		return homePhone;
	}
	public String emailAddress(){
		return emailAddress;
	}
	
	public Bitmap getPhoto(Context context){
		Bitmap photo = BitmapFactory.decodeResource(context.getResources(), R.drawable.dummyphoto);
		return photo;
		//return this.photo
	}
	
	public String toString(){
		return "" + firstName + " " + lastName;
	}
}
