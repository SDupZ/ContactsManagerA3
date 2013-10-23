package se206.contactsManager;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
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
	//private String photo;
	
	/**
	 * Main Constructor for creating a contact. All Fields required at this point but any of which can be null;
	 * Future improvements will work on this.
	 * @param firstName
	 * @param lastName
	 * @param mobilePhone
	 * @param homePhone
	 * @param workPhone
	 * @param emailAddress
	 * @param addressLine1
	 * @param addressLine2
	 * @param city
	 * @param country
	 * @param dateOfBirth
	 */
	public Contact(String firstName, String lastName, String mobilePhone, String homePhone, String workPhone, String emailAddress
			, String addressLine1, String addressLine2, String city, String country, String dateOfBirth){
		this.firstName    =   firstName;    
		this.lastName     =   lastName;     
		this.mobilePhone  =   mobilePhone;  
		this.homePhone    =   homePhone;    
		this.workPhone    =   workPhone;    
		this.emailAddress =   emailAddress; 
		this.addressLine1 =   addressLine1; 
		this.addressLine2 =   addressLine2; 
		this.city         =   city;         
		this.country      =   country;      
		this.dateOfBirth  =   dateOfBirth;  
	}
	
	/**
	 * Constructor that is used when re-constructing the Contact object from 
	 * a parcel.
	 * @param in
	 */
	public Contact(Parcel in){
		readFromParcel(in);		
	}
	
	@Override
	public int describeContents(){
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags){
		//Just need to write each field into the parcel.
		//When we read from the parcel, they will come
		//back in the same order.
		dest.writeString(firstName);   
		dest.writeString(lastName);    
		dest.writeString(mobilePhone); 
		dest.writeString(homePhone);   
		dest.writeString(workPhone);   
		dest.writeString(emailAddress);
		dest.writeString(addressLine1);
		dest.writeString(addressLine2);
		dest.writeString(city);        
		dest.writeString(country);     
		dest.writeString(dateOfBirth);
	}
	
	private void readFromParcel (Parcel in){
		firstName		=	in.readString();     
		lastName       	=	in.readString();  
		mobilePhone    	=	in.readString();  
		homePhone      	=	in.readString();  
		workPhone      	=	in.readString();  
		emailAddress   	=	in.readString();  
		addressLine1   	=	in.readString();  
		addressLine2   	=	in.readString();  
		city           	=	in.readString();  
		country        	=	in.readString();  
		dateOfBirth    	=	in.readString();  
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Contact createFromParcel(Parcel in){
			return new Contact(in);
		}
		public Contact[] newArray(int size){
			return new Contact[size];
		}
	};
	@Override
	public String toString(){
		return "" + firstName + " " + lastName;
	}
	//--------------------------------------------------------------------------------------------------------------------
	//Getters and setters
	//--------------------------------------------------------------------------------------------------------------------
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
	public String getEmailAddress(){
		return emailAddress;
	}
	public String getAddressLine1(){
		return addressLine1;
	}
	public String getAddressLine2(){
		return addressLine2;
	}
	public String getCity(){
		return city;
	}
	public String getCountry(){
		return country;
	}
	public String getDateOfBirth(){
		return dateOfBirth;
	}	

}
