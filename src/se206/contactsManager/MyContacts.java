package se206.contactsManager;

import java.util.Collections;
import java.util.List;

/**
 * This class is used as a singleton class to store the list of contacts for the session. This enables all activities
 * to have access to the contacts list. This is used in addition to a database for faster response. The GUI and lists
 * can be updated quickly while the database gets updated in a background thread.
 * 
 * @author 	Simon du Preez 	
 * 			5562045
 *			sdup571
 */			
public class MyContacts {	
	
	private List<Contact> myContacts;						//All contacts stored in a list.
	
	public static final int FIRSTNAME_ORDER 	= 0;		//The zero represents the option number in the pop-up dialog box.
	public static final int LASTNAME_ORDER 		= 1;		//Zero Means the item appeared first in the list. One means second.
	public static final int HOMEPHONE_ORDER		= 2;		//etc.
	public static final int MOBILEPHONE_ORDER	= 3;
	
	private static MyContacts instance;						//Used to implement singleton pattern.
	
	
	//-------------------------------------------------------------------------------------------------------------------------
	// Constructors and Getting the Instance
	//-------------------------------------------------------------------------------------------------------------------------
	/**
	 * 	This method is invoked by any class wanting to get an instance of this class.
	 * 	Only one instance of this class exists.
	 * 	@return MyContacts
	 */
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
	// Public Operations that other classes may use
	//-------------------------------------------------------------------------------------------------------------------------
	/**
	 * This method updates the contacts list with an entirely new contacts list. It replaces the old one.
	 * Essentially a setter method.
	 * @param myContacts
	 */
	public void updateContactsList(List<Contact> myContacts){
		this.myContacts = myContacts;
	}
	
	/**
	 * Getter method to retrieve all contacts.
	 * @return
	 */
	public List<Contact> getContactsList(){
		return myContacts;
	}
	
	/**
	 * This method reorders the list based on the integer that is passed in.
	 * @param ordering
	 */
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
		Collections.sort(this.myContacts, Contact.getComparator(sortBy));			//A comparator is retrieved from the contact class.
	}
}
