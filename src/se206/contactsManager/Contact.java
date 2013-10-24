package se206.contactsManager;

import java.util.Comparator;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {
	private long id;
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
	private String photo;
	
	/**
	 * Main Constructor for creating a contact. All Fields required at this point but any of which can be null;
	 * Future improvements will work on this.
	 */
	public Contact(String firstName, String lastName, String mobilePhone, String homePhone, String workPhone, String emailAddress
			, String addressLine1, String addressLine2, String city, String country, String dateOfBirth, String photo){
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
		this.photo 		  =   photo;
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
	
    public static Comparator<Contact> getComparator(final String sortBy) {
    	if ("firstName".equals(sortBy)){
    		return (new Comparator<Contact>(){
    			@Override
				public int compare(Contact c1, Contact c2){
    				if (c1.getFirstName() == null){
    					return 1;
    				}
    				if (c2.getFirstName() == null){
    					return -1;
    				}
    				return (c1.getFirstName().toUpperCase()).compareTo
    						(c2.getFirstName().toUpperCase());
    			}	    			
    		});    	
    	} else if ("lastName".equals(sortBy)) {
    		return (new Comparator<Contact>(){
    			@Override 
    			public int compare(Contact c1, Contact c2){
    				if (c1.getLastName() == null){
    					return 1;
    				}
    				if (c2.getLastName() == null){
    					return -1;
    				}
    				return (c1.getLastName().toUpperCase()).compareTo
    						(c2.getLastName().toUpperCase());
    			}	    			
    		});
    	}else if ("homePhone".equals(sortBy)) {
    		return (new Comparator<Contact>(){
    			@Override 
    			public int compare(Contact c1, Contact c2){
    				if (c1.getHomePhone() == null){
    					return 1;
    				}
    				if (c2.getHomePhone() == null){
    					return -1;
    				}
    				return c1.getHomePhone().compareTo(c2.getHomePhone());
    			}	    			
    		});
    	}else if ("mobilePhone".equals(sortBy)) {
    		return (new Comparator<Contact>(){
    			@Override 
    			public int compare(Contact c1, Contact c2){
    				if (c1.getMobilePhone() == null){
    					return 1;
    				}
    				if (c2.getMobilePhone() == null){
    					return -1;
    				}
    				return c1.getMobilePhone().compareTo(c2.getMobilePhone());
    			}	    			
    		});
    	}else {
    		throw new IllegalArgumentException("invalid sort field: " + sortBy);
    	}
    }
    
	@Override
	public void writeToParcel(Parcel dest, int flags){
		//Just need to write each field into the parcel.
		//When we read from the parcel, they will come
		//back in the same order.
		dest.writeLong(id);
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
		dest.writeString(photo);
	}
	
	private void readFromParcel (Parcel in){
		this.id 				= 	in.readLong();
		this.firstName			=	in.readString();     
		this.lastName       	=	in.readString();  
		this.mobilePhone    	=	in.readString();  
		this.homePhone      	=	in.readString();  
		this.workPhone      	=	in.readString();  
		this.emailAddress   	=	in.readString();  
		this.addressLine1   	=	in.readString();  
		this.addressLine2   	=	in.readString();  
		this.city           	=	in.readString();  
		this.country        	=	in.readString();  
		this.dateOfBirth    	=	in.readString(); 
		this.photo				= 	in.readString();
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
	public String getPhoto(){
		return photo;
	}
	public long getID(){
		return id;
	}
	
	public void setID(long id){
		this.id = id;
	}

}
