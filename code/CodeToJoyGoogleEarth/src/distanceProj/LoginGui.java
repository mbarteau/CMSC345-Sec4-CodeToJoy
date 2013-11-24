package distanceProj;

/*	TODO: 	1. Parse in locations from the file
			2. Store in the Arraylist
			3. Use it to make checkboxes
			4. Get whether or not a checkbox is checked
			5. Go to next screen
			6. Of the checkboxes that were checked, display radio buttons to choose start and stop locations
			7. Get which were the start and stop
			8. Somehow transform them back to location objects to do the shortest distance magic
*/
			
/* TODO:	9. Create files for users
			10. Parse the user files
			11. Be able to validate the users and deny if not one			
*/

/* TODO:	12. Parse the file for locations and do first todos
 * 			13. I get an error when nothing is entered and you push login
 * 
 */

import java.awt.BorderLayout;
import java.awt.CardLayout;	
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import location.Location;

/**
 * Class: LoginGui
 * 
 * This is the driving class. Everything starts here from the login screen.
 * @author amanda
 *
 */
public class LoginGui extends JFrame implements ActionListener
{
	// User buttons
	JButton registerButton, loginButton, backButton1, logoutButton1, submitButton, createAccountButton;
	JButton nextScreenButton1, nextScreenButton2, nextScreenButton3, backButton2, backButton3;
	JButton logoutButton2, logoutButton3, backButton4, backButton5, backButton6;
	
	// Admin specific buttons
	JButton addLocationButton, removeLocationButton, changeLocationButton;
	
	JLabel usernameLabel, passwordLabel, chooseUsernameLabel, choosePasswordLabel, reenterPasswordLabel;
	JLabel userExists, emptyPassword;
	
	// Error Labels
	JLabel badUserLabel, passwordsNotSame;
	
	// Admin specific labels
	JLabel newLocNameLabel, newLocLatLabel, newLocLongLabel;
	
	JTextField usernameField, passwordField, newUsernameField, newPasswordField, reenterPasswordField;
	
	// Admin specific textfields
	JTextField newLocNameField, newLocLatField, newLocLongField;
	
	JPanel cards, loginScreen, startLocationScreen, stopLocationScreen, allLocationsScreen, addLocScreen;
	JPanel removeLocScreen, changeLocScreen, newAccountScreen;

	private boolean isNext;
	
	public LoginGui()
	{
		super("Mapping Shortest Distance");	// The title appearing at the top
		setLocation(100, 100);	// Where the window pops up
		setSize(500, 200);		// Size of the window
		int wtfsPerMinute = 0;
		this.isNext = false;
		
		// Setting up the layout	
        cards = new JPanel(new CardLayout());
		
		// Set up the first screen - login to your account
		loginScreen = new JPanel(new GridLayout(0,1));
		loginScreen.setBackground(Color.cyan);
		
		JPanel uPanel = new JPanel(new FlowLayout());
		JPanel pPanel = new JPanel(new FlowLayout());
		JPanel bPanel = new JPanel(new FlowLayout());

        
		usernameLabel = new JLabel("Username:");
		uPanel.add(usernameLabel);
		uPanel.setBackground(Color.cyan);
		
		usernameField = new JTextField(20);
		uPanel.add(usernameField);
		
		passwordLabel = new JLabel("Password:");
		pPanel.add(passwordLabel);
		pPanel.setBackground(Color.cyan);
		
		passwordField = new JTextField(20);
		pPanel.add(passwordField);
		
		loginButton = new JButton("Log in");
		bPanel.add(loginButton);
		bPanel.setBackground(Color.cyan);
		
		registerButton = new JButton("Create a new account");
		bPanel.add(registerButton);
		
		loginScreen.add(uPanel);
		loginScreen.add(pPanel);
		loginScreen.add(bPanel);
		
		badUserLabel = new JLabel("Your username or password was incorrect");
		badUserLabel.setForeground(Color.red);
		loginScreen.add(badUserLabel);
		badUserLabel.setVisible(false);
		
		// Set up the second screen - register a new account
		newAccountScreen = new JPanel(new GridLayout(0,1));
		newAccountScreen.setBackground(Color.cyan);
		
		JPanel firstPanel = new JPanel(new FlowLayout());
		JPanel secondPanel = new JPanel(new FlowLayout());
		JPanel thirdPanel = new JPanel(new FlowLayout());
		JPanel fourthPanel = new JPanel(new FlowLayout());
		JPanel fifthPanel = new JPanel(new FlowLayout());
		
		chooseUsernameLabel = new JLabel("Choose a username: ");
		firstPanel.add(chooseUsernameLabel);
		
		newUsernameField = new JTextField(20);
		firstPanel.add(newUsernameField);
		
		choosePasswordLabel = new JLabel("Choose a password: ");
		secondPanel.add(choosePasswordLabel);
		
		newPasswordField = new JTextField(20);
		secondPanel.add(newPasswordField);
		
		reenterPasswordLabel = new JLabel("Re-enter password: ");
		thirdPanel.add(reenterPasswordLabel);
		
		reenterPasswordField = new JTextField(20);
		thirdPanel.add(reenterPasswordField);
		
		createAccountButton = new JButton("Create Account");
		fourthPanel.add(createAccountButton);
		
		backButton1 = new JButton("Back to Login Screen");
		fourthPanel.add(backButton1);
		
		passwordsNotSame = new JLabel("The passwords do not match.");
		passwordsNotSame.setForeground(Color.red);
		fifthPanel.add(passwordsNotSame);
		passwordsNotSame.setVisible(false);
		
		userExists = new JLabel("This account already exists.");
		userExists.setForeground(Color.red);
		fifthPanel.add(userExists);
		userExists.setVisible(false);
		
		emptyPassword = new JLabel("You must enter a username and password");
		emptyPassword.setForeground(Color.red);
		fifthPanel.add(emptyPassword);
		emptyPassword.setVisible(false);
		
		newAccountScreen.add(firstPanel);
		newAccountScreen.add(secondPanel);
		newAccountScreen.add(thirdPanel);
		newAccountScreen.add(fourthPanel);
		newAccountScreen.add(fifthPanel);
		
		// Set up the third screen - choose the locations
		
		allLocationsScreen = new JPanel(new BorderLayout());
		
		// Set up the top panel and its buttons/labels
		JPanel topPanel = new JPanel(new BorderLayout());	
		JLabel chooseLocation = new JLabel("Choose the locations to map.");
		chooseLocation.setForeground(Color.black);
		topPanel.add(chooseLocation, BorderLayout.CENTER);
		logoutButton1 = new JButton("Logout");	// Go back to the login screen
		topPanel.add(logoutButton1, BorderLayout.EAST);
		topPanel.setBackground(Color.cyan);
		allLocationsScreen.add(topPanel, BorderLayout.NORTH);
		
		// Set up the right panel and its buttons/labels
		JPanel rightPanel = new JPanel(new FlowLayout());
		nextScreenButton1 = new JButton("NEXT");
		rightPanel.add(nextScreenButton1);
		allLocationsScreen.add(rightPanel, BorderLayout.EAST);
		
		// 1. Parse in locations from the file
		// 2. Store in the Arraylist
		// 3. Use it to make checkboxes
		// 4. Get whether or not a checkbox is checked
		// 5. Go to next screen
		// 6. Of the checkboxes that were checked, display radio buttons to choose start and stop locations
		// 7. Get which were the start and stop
		// 8. Somehow transform them back to location objects to do the shortest distance magic		
		
		//ArrayList<Location> locationsSelected = new ArrayList<Location>();
		JPanel checks = new JPanel(new GridLayout(0,1));
		ArrayList<JCheckBox> checkArray = new ArrayList<JCheckBox>();
		//get locations.txt to read and write to
		File f = new File("locations.txt");
		//create list of current locations in list
		ArrayList<Location> inList = getLocList();
		//sort list alphabetically by name
		Collections.sort(inList);
		//search list, remove any matching locations
		JCheckBox currentLoc;
		for(int i = 0; i < inList.size(); i++){
			currentLoc = new JCheckBox(inList.get(i).getName(), false);
			checks.add(currentLoc);
			checkArray.add(currentLoc);
		}
	//	while(this.isNext == false){
			
	//	}
		this.isNext = false;
		wtfsPerMinute += 10;

		JScrollPane scrollThroughLocations = new JScrollPane(checks);
		allLocationsScreen.add(scrollThroughLocations, BorderLayout.CENTER);
		
		// Set up the fourth screen - select start location
		
		startLocationScreen = new JPanel(new BorderLayout());
		
		JPanel startPanel = new JPanel(new GridLayout(0,1));

		JLabel selectStartLabel = new JLabel("Select the starting location");
		startLocationScreen.add(selectStartLabel, BorderLayout.NORTH);
		
		ButtonGroup startLocationGroup = new ButtonGroup();
		JRadioButton sloc1 = new JRadioButton("location 1");
		startLocationGroup.add(sloc1);
		startPanel.add(sloc1);
		JRadioButton sloc2 = new JRadioButton("location 2");
		startLocationGroup.add(sloc2);
		startPanel.add(sloc2);
		JRadioButton sloc3 = new JRadioButton("location 3");
		startLocationGroup.add(sloc3);
		startPanel.add(sloc3);		
		JRadioButton sloc4 = new JRadioButton("location 3");
		startLocationGroup.add(sloc4);
		startPanel.add(sloc4);
		JRadioButton sloc5 = new JRadioButton("location 3");
		startLocationGroup.add(sloc5);
		startPanel.add(sloc5);
		JRadioButton sloc6 = new JRadioButton("location 3");
		startLocationGroup.add(sloc6);
		startPanel.add(sloc6);
		JRadioButton sloc7 = new JRadioButton("location 3");
		startLocationGroup.add(sloc7);
		startPanel.add(sloc7);
		
		
		/*JRadioButton currentJRLoc;
		for(int i = 0; i < inList.size(); i++){
			//System.out.println(checkArray.get(i).getText());
			if(checkArray.get(i).isSelected() == true){
				currentJRLoc = new JRadioButton(checkArray.get(i).getText());
				System.out.println(checkArray.get(i).getText());
				startLocationGroup.add(currentJRLoc);
				startPanel.add(currentJRLoc);
			}
		}	*/	
		
		//end of my addition
				
		JScrollPane scrollThroughStart = new JScrollPane(startPanel);
		startLocationScreen.add(scrollThroughStart, BorderLayout.CENTER);
			
		JPanel sidePanel = new JPanel(new GridLayout(0,1));
		
		nextScreenButton2 = new JButton("NEXT");
		sidePanel.add(nextScreenButton2);
		
		addLocationButton = new JButton("Add New Location");
		sidePanel.add(addLocationButton);
		
		removeLocationButton = new JButton("Delete Location");
		sidePanel.add(removeLocationButton);
		
		changeLocationButton = new JButton("Modify Existing Location");
		sidePanel.add(changeLocationButton);
		
		backButton2 = new JButton("Back");
		sidePanel.add(backButton2);
		
		logoutButton2 = new JButton("Logout");
		sidePanel.add(logoutButton2);
		
		startLocationScreen.add(sidePanel, BorderLayout.EAST);
		
		// Set up the fifth screen - select the stop location

		stopLocationScreen = new JPanel(new BorderLayout());
		
		JPanel stopPanel = new JPanel(new GridLayout(0,1));

		JLabel selectStopLabel = new JLabel("Select the ending location");
		stopLocationScreen.add(selectStopLabel, BorderLayout.NORTH);
		
		ButtonGroup stopLocationGroup = new ButtonGroup();
		JRadioButton sploc1 = new JRadioButton("location 1");
		stopLocationGroup.add(sploc1);
		stopPanel.add(sploc1);
		JRadioButton sploc2 = new JRadioButton("location 2");
		stopLocationGroup.add(sploc2);
		stopPanel.add(sploc2);
		JRadioButton sploc3 = new JRadioButton("location 3");
		stopLocationGroup.add(sploc3);
		stopPanel.add(sploc3);		
		JRadioButton sploc4 = new JRadioButton("location 3");
		stopLocationGroup.add(sploc4);
		stopPanel.add(sploc4);
		JRadioButton sploc5 = new JRadioButton("location 3");
		stopLocationGroup.add(sploc5);
		stopPanel.add(sploc5);
		JRadioButton sploc6 = new JRadioButton("location 3");
		stopLocationGroup.add(sploc6);
		stopPanel.add(sploc6);
		JRadioButton sploc7 = new JRadioButton("location 3");
		stopLocationGroup.add(sploc7);
		stopPanel.add(sploc7);
				
		JScrollPane scrollThroughStop = new JScrollPane(stopPanel);
		stopLocationScreen.add(scrollThroughStop, BorderLayout.CENTER);
			
		JPanel sidePanel2 = new JPanel(new GridLayout(0,1));
		
		nextScreenButton3 = new JButton("NEXT");
		sidePanel2.add(nextScreenButton3);
		
		backButton3 = new JButton("Back");
		sidePanel2.add(backButton3);
		
		logoutButton3 = new JButton("Logout");
		sidePanel2.add(logoutButton3);
		
		stopLocationScreen.add(sidePanel2, BorderLayout.EAST);
		
		// Set up the screen to add a location
		JPanel newLocPanel = new JPanel(new GridLayout(0,1));
		
		addLocScreen = new JPanel(new BorderLayout());
		JLabel addLocTitle = new JLabel("Add a new Location");
		addLocScreen.add(addLocTitle, BorderLayout.NORTH);
		
		newLocNameLabel = new JLabel("Location Name: ");
		newLocNameField = new JTextField(20);
		newLocPanel.add(newLocNameLabel);
		newLocPanel.add(newLocNameField);
		
		newLocLatLabel = new JLabel("Location Latitude: ");
		newLocLatField = new JTextField(20);
		newLocPanel.add(newLocLatLabel);
		newLocPanel.add(newLocLatField);
		
		newLocLongLabel = new JLabel("Location Longitude: ");
		newLocLongField = new JTextField(20);
		newLocPanel.add(newLocLongLabel);
		newLocPanel.add(newLocLongField);
		
		addLocScreen.add(newLocPanel, BorderLayout.CENTER);
		
		backButton4 = new JButton("BACK");
		addLocScreen.add(backButton4, BorderLayout.EAST);
		
		// Set up the screen to remove a location
		removeLocScreen = new JPanel(new BorderLayout());
		JLabel removeLocTitle = new JLabel("Remove a Location");
		removeLocScreen.add(removeLocTitle);
		
		backButton5 = new JButton("BACK");
		removeLocScreen.add(backButton5, BorderLayout.EAST);
		
		// Set up the screen to modify a location
		changeLocScreen = new JPanel(new BorderLayout());
		JLabel changeLocTitle = new JLabel("Change a Location");
		changeLocScreen.add(changeLocTitle);
		
		backButton6 = new JButton("BACK");
		changeLocScreen.add(backButton6, BorderLayout.EAST);
		
		// Allows something to happen when you press the button
		registerButton.addActionListener(this);
		backButton1.addActionListener(this);
		backButton2.addActionListener(this);
		backButton3.addActionListener(this);
		backButton4.addActionListener(this);
		backButton5.addActionListener(this);
		backButton6.addActionListener(this);
		loginButton.addActionListener(this);
		logoutButton1.addActionListener(this);
		logoutButton2.addActionListener(this);
		logoutButton3.addActionListener(this);
		nextScreenButton1.addActionListener(this);
		nextScreenButton2.addActionListener(this);
		nextScreenButton3.addActionListener(this);
		createAccountButton.addActionListener(this);
		addLocationButton.addActionListener(this);
		removeLocationButton.addActionListener(this);
		changeLocationButton.addActionListener(this);
		
		// Add all the "cards" or different screens to the deck
		cards.add(loginScreen, "Login");
		cards.add(newAccountScreen, "Create new account");
		cards.add(allLocationsScreen, "Locations");
		cards.add(startLocationScreen, "Start Location");
		cards.add(stopLocationScreen, "Stop Location");
		cards.add(addLocScreen, "New Location");
		cards.add(removeLocScreen, "Remove Location");
		cards.add(changeLocScreen, "Modify Location");
		
		getContentPane().add(cards); 
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	// Make the buttons do things 
    public void actionPerformed(ActionEvent e)
    {    
    	// Click register to create a new account
        if (e.getSource() == registerButton)
        {    
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Create new account");       	
        }
        // Click back from the new account page to get back to the login screen
        else if (e.getSource() == backButton1)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Login");
        }
        // Go back from start locations page to all locations page
        else if (e.getSource() == backButton2)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Locations");
        }
        // Go back from stop locations page to start location page
        else if (e.getSource() == backButton3)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Start Location");
        }
        // From the add location screen back to all locations screen
        else if (e.getSource() == backButton4)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Locations");
        }
        // From the remove location screen to all locations screen
        else if (e.getSource() == backButton5)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Locations");
        }
        // From the modify location screen to all location screen
        else if (e.getSource() == backButton6)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Locations");
        }
        // Go from picking all locations to picking start location
        else if (e.getSource() == nextScreenButton1)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Start Location");
        	this.isNext = true;
        }
        // Go from picking start location to picking stop location
        else if (e.getSource() == nextScreenButton2)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Stop Location");
        	this.isNext = true;
        }
        // Last next button - launch the application
        else if (e.getSource() == nextScreenButton3)
        {
        	/**
        	 * 
        	 * 
        	 * 
        	 * THIS IS WHERE APPLICATION IS LAUNCHED
        	 * 
        	 * 
        	 * 
        	 */
        }
        // Click "login" to get to the location menu
        else if (e.getSource() == loginButton)
        {
        	String usernameFromGui = usernameField.getText();
        	usernameFromGui = usernameFromGui.trim();
        	String passwordFromGui = passwordField.getText();
        	passwordFromGui = passwordFromGui.trim();
        	
        	User enteredUser = new User(usernameFromGui, passwordFromGui, false);
        	// Each line should have a new user
        	// There may not be extra space at the end of a line or this may not work
        	try
        	{
        		BufferedReader buffReader = new BufferedReader(new FileReader("users.txt"));
        		
        		// An array of users already entered into the file "users.txt"
    			ArrayList<User> users = new ArrayList<User>();
        		
    			// Read in each line from the users file
        		String line = buffReader.readLine();
        		
        		while (line != null)
        		{
        			// Each line has comma separated values of username, password, A/U
        			// Make that into a user and add to the array
        	    	String delimiter = "[,]+";
        	    	String[] userTokens = line.split(delimiter);
        	    	
        	    	for (int i = 0; i < userTokens.length; i++)
        	    	{
        	    		String usernameFromFile = userTokens[i];
        	    		usernameFromFile = usernameFromFile.trim();
        	    		i++;
        	    		String passwordFromFile = userTokens[i];
        	    		passwordFromFile = passwordFromFile.trim();
        	    		i++;
        	    		String isAdminFromFile = userTokens[i];
        	    		isAdminFromFile = isAdminFromFile.trim();
        	    		boolean admin = false;
        	    		if (isAdminFromFile.equals("A") || isAdminFromFile.equals("a"))
        	    		{
        	    			admin = true;
        	    		}
        	    		users.add(new User(usernameFromFile, passwordFromFile, admin));
        	    	}
        			
        			line = buffReader.readLine();        			
        		}
        		
        		// Check if the user has an account
    	    	boolean userExists = false;
    	    	for (int j = 0; j < users.size(); j++)
    	    	{
    	    		if (users.get(j).equals(enteredUser))
    	    		{
    	    			userExists = true;
    	    		}
    	    	}
    	    	
    	    	// If the username or password are incorrect
    	    	if (!userExists)
    	    	{
    	    		throw new BadUserException();
    	    	}
    	    	// Otherwise, continue to the next screen
    	    	else
    	    	{
    	    		badUserLabel.setVisible(false);
    	        	CardLayout cl = (CardLayout)(cards.getLayout());
    	        	cl.show(cards, "Locations");
    	    	}
        		
        		buffReader.close();
        	}
        	catch(FileNotFoundException fnf) 
        	{
                System.out.println("Unable to open 'users.txt'");				
            }
            catch(IOException io) 
            {
                io.printStackTrace();
            }
        	catch(BadUserException bu)
        	{
        		badUserLabel.setVisible(true);
        		validate();
        	}
        }
        // Click "logout" from the application screen to go back to login screen
        else if (e.getSource() == logoutButton1 || e.getSource() == logoutButton2 ||
        		e.getSource() == logoutButton3)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Login");
        	
        	usernameField.setText("");
        	passwordField.setText("");
        	newUsernameField.setText("");
        	newPasswordField.setText("");
        	reenterPasswordField.setText("");
        	
        	// Clear all the other fields here too like checkboxes and radio buttons
        	
        	validate();
        }
        // Creates a new user account
        else if (e.getSource() == createAccountButton)
        {
			userExists.setVisible(false);
    		passwordsNotSame.setVisible(false);
    		emptyPassword.setVisible(false);
        	
        	String newUsernameFromGui = newUsernameField.getText().trim();
        	String newPasswordFromGui = newPasswordField.getText().trim();
        	String newPasswordAgainFromGui = reenterPasswordField.getText().trim();
        	
        	if (!newPasswordFromGui.equals(newPasswordAgainFromGui))
        	{
        		passwordsNotSame.setVisible(true);
        	}
        	else if (newUsernameFromGui.equals("") || newPasswordFromGui.equals("") || 
        			newPasswordAgainFromGui.equals(""))
        	{
        		emptyPassword.setVisible(true);
        	}
        	else
        	{      		
				try 
				{
					// Check if the user has an account
	    	    	boolean usernameExists = false;
	    	    	
	    	    	BufferedReader buffReader = new BufferedReader(new FileReader("users.txt"));
	        		
	    			// Read in each line from the users file
	        		String line = buffReader.readLine();
	        		
	        		while (line != null)
	        		{
	        			// Each line has comma separated values of username, password, A/U
	        			// Make that into a user and add to the array
	        	    	String delimiter = "[,]+";
	        	    	String[] userTokens = line.split(delimiter);
	        	    	
	        	    	for (int i = 0; i < userTokens.length; i++)
	        	    	{
	        	    		String usernameFromFile = userTokens[i];
	        	    		usernameFromFile = usernameFromFile.trim();
	        	    		i++;	// pass the password
	        	    		i++;	// pass the user/admin letter
	        	    		if (usernameFromFile.equals(newUsernameFromGui))
	        	    		{
	        	    			usernameExists = true;
	        	    		}
	        	    	}
	        			
	        			line = buffReader.readLine();        			
	        		}
	        		
	        		if (usernameExists)
	        		{
	        			throw new BadUserException("This account already exists");
	        		}
	        		else
	        		{
		        		// Add the new user to the user file
		        		// Right now the users get added as regular users. Admins must be added manually.
		        		BufferedWriter out;
						
						out = new BufferedWriter(new FileWriter("users.txt", true));
		        		out.write(newUsernameFromGui + ", " + newPasswordFromGui + ", U\n");
		        		out.close();
		        		
		        		// Go to the next screen
		            	CardLayout cl = (CardLayout)(cards.getLayout());
		            	cl.show(cards, "Locations");
	        		}	        		
				} 
				catch (FileNotFoundException e1)
				{
					e1.printStackTrace();
				}
				catch (IOException e2) 
				{
					e2.printStackTrace();
				} 
				catch (BadUserException e3)
				{
					userExists.setVisible(true);
					validate();
				}
        	}
        }
        else if (e.getSource() == addLocationButton)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "New Location"); 
        	
        	String getName = newLocNameField.getText();
        	String getLat = newLocLatField.getText();
        	String getLong = newLocLongField.getText();
        	
        	// Add the new location to the file
        }
        // 
        else if (e.getSource() == removeLocationButton)
        {
        	
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Remove Location"); 
        }
        else if (e.getSource() == changeLocationButton)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Modify Location"); 
        }
    }
	
	private ArrayList<Location> getLocList() {
		//file of locations
		File f = new File("locations.txt");
		//initialize arraylist for storage
		ArrayList<Location> locA= new ArrayList<Location>();
		try {
			//create scanner to read from file
			Scanner s = new Scanner(f);
			while(s.hasNext()){
				String line = s.nextLine();
				//split into 3 parameters of a location
				String[] data = line.split(",");
				double lat = Double.parseDouble(data[1]);
				double lon = Double.parseDouble(data[2]);
				//make location from input
				Location m = new Location(data[0], lat, lon);
				//add to array
				locA.add(m);
			}
			//close file
			s.close();
			//return
			return locA;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args)
	{
		LoginGui logingui = new LoginGui();
	}
}
