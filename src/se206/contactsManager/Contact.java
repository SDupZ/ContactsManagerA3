package se206.contactsManager;

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
	//private String photo;
	
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

	public String toString(){
		return "" + firstName + " " + lastName;
	}
}
