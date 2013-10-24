package se206.contactsManager;

import java.util.Collections;
import java.util.List;

public class MyContacts {	
	public static final int FIRSTNAME_ORDER 	= 0;
	public static final int LASTNAME_ORDER 		= 1;
	public static final int HOMEPHONE_ORDER		= 2;
	public static final int MOBILEPHONE_ORDER	= 3;
	
	private static MyContacts instance;
	private List<Contact> myContacts;	
	
	//-------------------------------------------------------------------------------------------------------------------------
	// Singleton class. Only one instance of this class is used.
	//-------------------------------------------------------------------------------------------------------------------------
	public static synchronized MyContacts getMyContacts(){
		if (instance == null){
			instance = new MyContacts();
		}
		return instance;		
	}
	
	//Private constructor to ensure a new instance cannot be made by anything other than this class.
	private MyContacts(){		
	}
	//-------------------------------------------------------------------------------------------------------------------------
	
	public void updateContactsList(List<Contact> myContacts){
		this.myContacts = myContacts;
	}
	
	public List<Contact> getContactsList(){
		return myContacts;
	}
	
	public void reorderList(int ordering){
		String sortBy = "firstName";
		switch (ordering){
		case 1:
			sortBy = "lastName";
			break;
		case 2: 
			sortBy = "homePhone";
			break;
		case 3:
			sortBy = "mobilePhone";
			break;
		}
		Collections.sort(this.myContacts, Contact.getComparator(sortBy));
	}
}
