package se206.contactsManager;

import java.util.List;

public class MyContacts {	
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
}
