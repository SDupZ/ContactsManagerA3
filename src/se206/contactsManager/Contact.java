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
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String country;
	private String dateOfBirth;
	//private Bitmap photo;
	
	public Contact(String firstName, String lastName, String mobilePhone, String homePhone, String workPhoen, String emailAddress
			, String addressLine1, String addressLine2, String city, String country, String dateOfBirth){
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
