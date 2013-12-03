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
import location.Location;
import shortestPath.ShortestDistance;
import kml.PathToKML;

import java.awt.BorderLayout;
import java.awt.CardLayout;	
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * Class: LoginGui
 * 
 * This is the driving class. Everything starts here from the login screen.
 * @author amanda
 *
 */
public class LoginGui extends JFrame implements ActionListener, ItemListener
{
	final int MIN_LOCATIONS_CHECKED = 2;
	// User buttons
	JButton registerButton, loginButton, backButton1, logoutButton1, submitButton, createAccountButton;
	JButton nextScreenButton1, nextScreenButton2, nextScreenButton3, backButton2, backButton3;
	JButton logoutButton2, logoutButton3, backButton4, backButton5, backButton6, continueButton;
	
	// Admin specific buttons
	JButton addLocationButton, removeLocationButton, changeLocationButton;
	
	ButtonGroup startLocationGroup = new ButtonGroup();
	ButtonGroup stopLocationGroup = new ButtonGroup();
	
	JLabel usernameLabel, passwordLabel, chooseUsernameLabel, choosePasswordLabel, reenterPasswordLabel;
	JLabel userExists, emptyPassword;
	
	String userName = null;
	
	JTextArea introduction;
	
	// Error Labels
	JLabel badUserLabel, passwordsNotSame, needMoreLocError, selectStartError;
	
	// Admin specific labels
	JLabel newLocNameLabel, newLocLatLabel, newLocLongLabel;
	
	JTextField usernameField, passwordField, newUsernameField, newPasswordField, reenterPasswordField;
	
	// Admin specific textfields
	JTextField newLocNameField, newLocLatField, newLocLongField;
	
	JPanel cards, loginScreen, startLocationScreen, stopLocationScreen, allLocationsScreen, addLocScreen;
	JPanel removeLocScreen, changeLocScreen, newAccountScreen, startPanel, stopPanel, checkPanel;
	JPanel adminScreen;
	
	JScrollPane scrollThroughLocations;
	
	Location startLocation;
	Location stopLocation;
	
	ArrayList<JCheckBox> allLocCheckBoxes = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> checkedBoxes = new ArrayList<JCheckBox>();
	ArrayList<JRadioButton> startLocationButtons = new ArrayList<JRadioButton>();
	ArrayList<JRadioButton> stopLocationButtons = new ArrayList<JRadioButton>();
	ArrayList<Location> locations = new ArrayList<Location>();
	
    Color backGroundColor = new Color(0, 153, 153);	// Dark Turquois
    Color titleColor = new Color(153, 204, 255);	// Lighter turquois
    Color textColor = new Color(255, 255, 255);		// White
    Color otherColor = new Color(51, 153, 255);		// Blue
	
	public LoginGui()
	{
		super("Mapping Shortest Distance");	// The title appearing at the top
		setLocation(100, 100);	// Where the window pops up
		setSize(800, 500);		// Size of the window
		int wtfsPerMinute = 0;
		
		// Setting up the layout - each screen is a card that is shown one at a time
        cards = new JPanel(new CardLayout());
		
		// Set up the first screen - login to your account      
		loginScreen = new JPanel(null);
		loginScreen.setBackground(backGroundColor); 	
       
		usernameLabel = new JLabel("Username:");
		usernameLabel.setLocation(400, 90);
		usernameLabel.setSize(100, 30);
		usernameLabel.setForeground(textColor);
		loginScreen.add(usernameLabel);
		
		usernameField = new JTextField();
		usernameField.setLocation(500, 90);
		usernameField.setSize(200, 30);
		loginScreen.add(usernameField);
		
		passwordLabel = new JLabel("Password:");
		passwordLabel.setSize(100, 30);
		passwordLabel.setLocation(400, 130);
		passwordLabel.setForeground(textColor);
		loginScreen.add(passwordLabel);
		
		passwordField = new JTextField();
		passwordField.setLocation(500, 130);
		passwordField.setSize(200, 30);
		loginScreen.add(passwordField);
		
		loginButton = new JButton("Log in");
		loginButton.setLocation(520, 200);
		loginButton.setSize(160, 30);
		loginScreen.add(loginButton);
		
		registerButton = new JButton("Create a new account");
		registerButton.setLocation(520, 240);
		registerButton.setSize(160, 30);
		loginScreen.add(registerButton);
		
		badUserLabel = new JLabel("Your username or password was incorrect");
		badUserLabel.setForeground(Color.red);
		badUserLabel.setLocation(500, 10);
		badUserLabel.setSize(250, 15);
		loginScreen.add(badUserLabel);
		badUserLabel.setVisible(false);
		
		JLabel title = new JLabel("MappingMaestro");
		title.setSize(300, 50);
		title.setLocation(50, 20);
		title.setFont(new Font("Serif", Font.ITALIC, 40));
		title.setForeground(titleColor);
		loginScreen.add(title);
		
		String intro = "Welcome to MappingMaestro by Code2Joy.\n\n";
				intro += "MappingMaestro will allow you to select\n";
				intro += "from several National Parks in the United\n";
				intro += "States and Canada that you would like to\n";
				intro += "visit. Then you choose the parks where you\n";
				intro += "would like to start and stop. Next,\n";
				intro += "MappingMaestro will find the shortest\n";
				intro += "distance for you to travel to all of them\n";
				intro += "by plotting the trip on Google Earth.\n\n";
				intro += "Log in to your account or create a new\n";
				intro += "one to continue.";
		
		introduction = new JTextArea(intro);
		introduction.setEditable(false);
		introduction.setSize(280, 300);
		introduction.setFont(new Font("San Serif", Font.PLAIN, 14));
		introduction.setLocation(50, 100);
		introduction.setBackground(otherColor);
		introduction.setForeground(textColor);
		loginScreen.add(introduction);		
		
		// Set up the screen to create a new account
		newAccountScreen = new JPanel(null);
		newAccountScreen.setBackground(Color.cyan);
		
		JLabel newAccountLabel = new JLabel("Create Account");
		newAccountLabel.setFont(new Font("Serif", Font.ITALIC, 30));
		newAccountLabel.setLocation(300, 20);
		newAccountLabel.setSize(300, 30);
		newAccountScreen.add(newAccountLabel);
		
		chooseUsernameLabel = new JLabel("Choose a username: ");
		chooseUsernameLabel.setLocation(250, 100);
		chooseUsernameLabel.setSize(200, 30);
		newAccountScreen.add(chooseUsernameLabel);
		
		newUsernameField = new JTextField(20);
		newUsernameField.setLocation(400, 100);
		newUsernameField.setSize(200, 30);
		newAccountScreen.add(newUsernameField);
		
		choosePasswordLabel = new JLabel("Choose a password: ");
		choosePasswordLabel.setLocation(250, 150);
		choosePasswordLabel.setSize(200, 30);
		newAccountScreen.add(choosePasswordLabel);
		
		newPasswordField = new JTextField(20);
		newPasswordField.setLocation(400, 150);
		newPasswordField.setSize(200, 30);
		newAccountScreen.add(newPasswordField);
		
		reenterPasswordLabel = new JLabel("Re-enter password: ");
		reenterPasswordLabel.setLocation(250, 200);
		reenterPasswordLabel.setSize(200, 30);
		newAccountScreen.add(reenterPasswordLabel);
		
		reenterPasswordField = new JTextField(20);
		reenterPasswordField.setLocation(400, 200);
		reenterPasswordField.setSize(200, 30);
		newAccountScreen.add(reenterPasswordField);
		
		createAccountButton = new JButton("Create Account");
		createAccountButton.setLocation(450, 250);
		createAccountButton.setSize(150, 30);
		newAccountScreen.add(createAccountButton);
		
		/*
		backButton1 = new JButton("Back to Login Screen");
		fourthPanel.add(backButton1);
		*/
		
		passwordsNotSame = new JLabel("The passwords do not match.");
		passwordsNotSame.setForeground(Color.red);
		newAccountScreen.add(passwordsNotSame);
		passwordsNotSame.setVisible(false);
		
		userExists = new JLabel("This account already exists.");
		userExists.setForeground(Color.red);
		newAccountScreen.add(userExists);
		userExists.setVisible(false);
		
		emptyPassword = new JLabel("You must enter a username and password");
		emptyPassword.setForeground(Color.red);
		newAccountScreen.add(emptyPassword);
		emptyPassword.setVisible(false);
		
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
		
		showLocationScreen();
		
		needMoreLocError = new JLabel("You must choose at least " + MIN_LOCATIONS_CHECKED + " locations.");
		needMoreLocError.setForeground(Color.red);
		allLocationsScreen.add(needMoreLocError, BorderLayout.SOUTH);
		needMoreLocError.setVisible(false);
		
		// Set up the fourth screen - select start location
		
		startLocationScreen = new JPanel(new BorderLayout());
		
		startPanel = new JPanel(new GridLayout(0,1));

		JLabel selectStartLabel = new JLabel("Select the starting location");
		startLocationScreen.add(selectStartLabel, BorderLayout.NORTH);
			
		JPanel sidePanel = new JPanel(new BorderLayout());	// Sets gaps between components
		
		nextScreenButton2 = new JButton("NEXT");
		sidePanel.add(nextScreenButton2, BorderLayout.NORTH);
		
		selectStartError = new JLabel("You must choose a starting location");
		selectStartError.setForeground(Color.red);
		startLocationScreen.add(selectStartError, BorderLayout.SOUTH);
		selectStartError.setVisible(false);
		
		JScrollPane scrollThroughStart = new JScrollPane(startPanel);
		startLocationScreen.add(scrollThroughStart, BorderLayout.CENTER);
		
		
		/*
		backButton2 = new JButton("Back");
		sidePanel.add(backButton2);
		*/
		
		logoutButton2 = new JButton("Logout");
		sidePanel.add(logoutButton2, BorderLayout.SOUTH);
		
		startLocationScreen.add(sidePanel, BorderLayout.EAST);
		
		// Set up the fifth screen - select the stop location

		stopLocationScreen = new JPanel(new BorderLayout());
		
		stopPanel = new JPanel(new GridLayout(0,1));

		JLabel selectStopLabel = new JLabel("Select the ending location");
		stopLocationScreen.add(selectStopLabel, BorderLayout.NORTH);
				
		JScrollPane scrollThroughStop = new JScrollPane(stopPanel);
		stopLocationScreen.add(scrollThroughStop, BorderLayout.CENTER);
			
		JPanel sidePanel2 = new JPanel(new BorderLayout());
		
		nextScreenButton3 = new JButton("NEXT");
		sidePanel2.add(nextScreenButton3, BorderLayout.NORTH);
		
		/*
		backButton3 = new JButton("Back");
		sidePanel2.add(backButton3);
		*/
		
		logoutButton3 = new JButton("Logout");
		sidePanel2.add(logoutButton3, BorderLayout.SOUTH);
		
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
		
		// Set up admin screen - can choose if want to change locations or continue
		adminScreen = new JPanel(null);
		
		addLocationButton = new JButton("Add New Location");
		addLocationButton.setSize(200, 50);
		addLocationButton.setLocation(300, 50);
		adminScreen.add(addLocationButton);
		
		removeLocationButton = new JButton("Delete Location");
		removeLocationButton.setSize(200, 50);
		removeLocationButton.setLocation(300, 150);
		adminScreen.add(removeLocationButton);
		
		changeLocationButton = new JButton("Modify Existing Location");
		changeLocationButton.setSize(200, 50);
		changeLocationButton.setLocation(300, 250);
		adminScreen.add(changeLocationButton);
		
		continueButton = new JButton("Continue");
		continueButton.setSize(200, 50);
		continueButton.setLocation(300, 350);
		adminScreen.add(continueButton);
		
		
		// Allows something to happen when you press the button
		registerButton.addActionListener(this);
		//backButton1.addActionListener(this);
		//backButton2.addActionListener(this);
		//backButton3.addActionListener(this);
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
		continueButton.addActionListener(this);
		
		// Add all the "cards" or different screens to the deck
		cards.add(loginScreen, "Login");
		cards.add(newAccountScreen, "Create new account");
		cards.add(allLocationsScreen, "Locations");
		cards.add(startLocationScreen, "Start Location");
		cards.add(stopLocationScreen, "Stop Location");
		cards.add(adminScreen, "Admin Screen");
		cards.add(addLocScreen, "New Location");
		cards.add(removeLocScreen, "Remove Location");
		cards.add(changeLocScreen, "Modify Location");
		
		getContentPane().add(cards); 
		setVisible(true);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void itemStateChanged(ItemEvent e)
	{
		// The only thing that will make an item event is a checkbox, so it's safe to cast
		if (e.getStateChange() == ItemEvent.SELECTED)
		{
			JCheckBox checked = (JCheckBox) e.getSource();
			boolean containsItem = false;
			for (int i = 0; i < checkedBoxes.size(); i++)
			{
				if (checked.getText().equals(checkedBoxes.get(i).getText()))
				{
					containsItem = true;
				}
			}
			if (!containsItem)
			{
				checkedBoxes.add((JCheckBox) e.getSource());
			}
		}
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
        	cl.show(cards, "Admin Screen");
        }
        // From the remove location screen to all locations screen
        else if (e.getSource() == backButton5)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Admin Screen");
        }
        // From the modify location screen to all location screen
        else if (e.getSource() == backButton6)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Admin Screen");
        }
        // Creates a new user account
        if (e.getSource() == createAccountButton)
        {
			userExists.setVisible(false);
    		passwordsNotSame.setVisible(false);
    		emptyPassword.setVisible(false);
        	
        	String newUsernameFromGui = newUsernameField.getText().trim();
        	String newPasswordFromGui = newPasswordField.getText().trim();
        	String newPasswordAgainFromGui = reenterPasswordField.getText().trim();
        	userName = newUsernameFromGui;
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
	        	    			userName = usernameFromFile;
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
        // Click "login" to get to the location menu
        if (e.getSource() == loginButton)
        {
        	String usernameFromGui = usernameField.getText();
        	usernameFromGui = usernameFromGui.trim();
        	String passwordFromGui = passwordField.getText();
        	passwordFromGui = passwordFromGui.trim();
        	userName = usernameFromGui;
        	
        	User enteredUser = new User(usernameFromGui, passwordFromGui, false);
        	
        	// Each line should have a new user
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
    	    	boolean userIsAdmin = false;
    	    	for (int j = 0; j < users.size(); j++)
    	    	{
    	    		if (users.get(j).equals(enteredUser))
    	    		{
    	    			userExists = true;
    	    			if (users.get(j).isAdmin)
    	    			{
    	    				userIsAdmin = true;
    	    			}
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
    	    		// Show an extra screen to modify things if the user has admin privileges
    	    		if (userIsAdmin == true)
    	    		{
    	            	CardLayout cl = (CardLayout)(cards.getLayout());
    	            	cl.show(cards, "Admin Screen");    	    			
    	    		}
    	    		else
    	    		{
    	    			showLocationScreen();
    	    		}
        		
        		buffReader.close();
    	    	}
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
        // Go from picking all locations to picking start location
        if (e.getSource() == nextScreenButton1)
        {        	
        	if (checkedBoxes.size() < MIN_LOCATIONS_CHECKED)
        	{
        		needMoreLocError.setVisible(true);
        		validate();
        		repaint();
        	}
        	else
        	{       	
        		// Make radio buttons for the start location choices
        		
        		for (int i = 0; i < checkedBoxes.size(); i++)
        		{
    				JRadioButton startRadio = new JRadioButton(checkedBoxes.get(i).getText());
    				startLocationButtons.add(startRadio);
    				startLocationGroup.add(startRadio);
    				startPanel.add(startRadio);
        		}
        		CardLayout cl = (CardLayout)(cards.getLayout());
        		cl.show(cards, "Start Location");
        	}
        }
        // Go from picking start location to picking stop location
        else if (e.getSource() == nextScreenButton2)
        {
    		if (startLocationGroup.getSelection() == null)
    		{
    			selectStartError.setVisible(true);
    			validate();
    		}
    		else
    		{
    			for (int i = 0; i < startLocationButtons.size(); i++)
    			{
    				if (!startLocationButtons.get(i).isSelected())
    				{
    					stopLocationButtons.add(startLocationButtons.get(i));
    					stopLocationGroup.add(startLocationButtons.get(i));
    					stopPanel.add(startLocationButtons.get(i));
    				}
    				else
    				{
    					startLocation = getLocation(startLocationButtons.get(i).getText());
    				}
    			}
    			
    			
    			
    			CardLayout cl = (CardLayout)(cards.getLayout());
    			cl.show(cards, "Stop Location");
    		}
        }
        // Last next button - launch the application
        else if (e.getSource() == nextScreenButton3)
        {
        	if (stopLocationGroup.getSelection() == null)
        	{
        		JLabel  selectStopError = new JLabel("You must choose an end location");
        		selectStopError.setForeground(Color.red);
        		stopLocationScreen.add(selectStopError, BorderLayout.SOUTH);
        		validate();
        	}
        	else
        	{
        		for (int i = 0; i < stopLocationButtons.size(); i++)
        		{
        			if (stopLocationButtons.get(i).isSelected())
        			{
        				stopLocation = getLocation(stopLocationButtons.get(i).getText());
        			}
        		}
        		
        		ArrayList<Location> trip = getTrip();
        		trip = ShortestDistance.getShortestPath(trip);
        	
        		
        		try {
					PathToKML.createPath(trip, userName);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        		String programLoc = "D:\\Program Files (x86)\\Google\\Google Earth\\client\\googleearth.exe";
        		String fileLoc = "C:\\Users\\Matt\\Downloads\\Workspace\\CodeToJoy\\"+userName+".kml";
        				
        		PathToKML.openFile(programLoc, fileLoc);
        		
        		
        	/**
        	 * 
        	 * 
        	 * 
        	 * THIS IS WHERE APPLICATION IS LAUNCHED
        	 * Use the variables: "startLocation", "stopLocation", and "trip" to do the algorithm
        	 * 
        	 * 
        	 */
        		
        		e.setSource(logoutButton1);
        	

        	}
        }
        
        // Click "logout" from the application screen to go back to login screen
        if (e.getSource() == logoutButton1 || e.getSource() == logoutButton2 ||
        		e.getSource() == logoutButton3)
        {
        	
        	for (int i = 0; i < allLocCheckBoxes.size(); i++)
        	{
        		allLocCheckBoxes.get(i).setSelected(false);
        	}
        	
        	//allLocCheckBoxes.clear();
        	for (int j = 0; j < checkedBoxes.size(); j++)
        	{
        		checkedBoxes.get(j).setSelected(false);
        	}
        	checkedBoxes.clear();
        	//locations.clear();
        	
        	usernameField.setText("");
        	passwordField.setText("");
        	newUsernameField.setText("");
        	newPasswordField.setText("");
        	reenterPasswordField.setText("");
        	
        	// Takes all the buttons out of the group, then clears all the buttons
        	int numStartButtons = startLocationGroup.getButtonCount();
        	for (int k = 0; k < numStartButtons; k++)
        	{
        		startLocationGroup.remove(startLocationButtons.get(k));
        		startPanel.remove(startLocationButtons.get(k));
        	}
        	startLocationButtons.clear();
        	
        	int numStopButtons = stopLocationGroup.getButtonCount();
        	for (int m = 0; m < numStopButtons; m++)
        	{
        		stopLocationGroup.remove(stopLocationButtons.get(m));
        		stopPanel.remove(stopLocationButtons.get(m));
        	}
        	stopLocationButtons.clear();
        	
        	needMoreLocError.setVisible(false);
        	badUserLabel.setVisible(false);
        	passwordsNotSame.setVisible(false);
        	selectStartError.setVisible(false);
        	
        	validate();
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Login");
        }
        
        // Admin can add a new location to the list
        else if (e.getSource() == addLocationButton)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "New Location"); 
        	
        	String getName = newLocNameField.getText();
        	String getLat = newLocLatField.getText();
        	String getLong = newLocLongField.getText();
        	
        	// Add the new location to the file
        }
        // Admin can remove a location from the list
        else if (e.getSource() == removeLocationButton)
        {
        	
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Remove Location"); 
        }
        // Admin can modify a location in the list
        else if (e.getSource() == changeLocationButton)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Modify Location"); 
        }
        else if (e.getSource() == continueButton)
        {
        	showLocationScreen();
        }
    }
    
    private ArrayList<Location> getTrip()
    {
    	int start = 0;
    	int end = 0;
    	Location temp = null;
    	
    	ArrayList<Location> trip = new ArrayList<Location>();
    	for (int i = 0; i < checkedBoxes.size(); i++)
    	{
    		if(getLocation(checkedBoxes.get(i).getText()).getName().equals(startLocation.getName())){
    			start = i;
    		}
    		if(getLocation(checkedBoxes.get(i).getText()).getName().equals(stopLocation.getName())){
    			end = i;
    		}
    		trip.add(getLocation(checkedBoxes.get(i).getText()));
    	}
    
    	temp =trip.get(0);
    	trip.set(0, trip.get(start));
    	trip.set(start, temp);
    	
    	temp =trip.get(trip.size()-1);
    	trip.set(trip.size()-1, trip.get(end));
    	trip.set(end, temp);
    	
    	return trip;
    }
    
    private Location getLocation(String locationName)
    {
    	for (int i = 0; i < locations.size(); i++)
    	{
    		if (locations.get(i).getName().equals(locationName))
    		{
    			return locations.get(i);
    		}
    	}
    	return null;
    }
    
    private void showLocationScreen()	
    {
    	// 1. Parse in locations from the file
		// 2. Store in the ArrayList
		// 3. Use it to make checkboxes
		// 4. Go to the location screen	
	
		try 
		{
			locations.clear();
			BufferedReader bR = new BufferedReader(new FileReader("locations.txt"));
			
			String locLine = bR.readLine();
			while(locLine != null)
			{
				String[] data = locLine.split(",");
				double lat = Double.parseDouble(data[1]);
				double lon = Double.parseDouble(data[2]);
				locations.add(new Location(data[0], lat, lon));
				locLine = bR.readLine();
			}
			
		} 
		catch (FileNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e2)
		{
			e2.printStackTrace();
		}
		
		checkPanel = new JPanel(new GridLayout(0,1));
		
		allLocCheckBoxes.clear();
		
		for (int j = 0; j < locations.size(); j++)
		{
			JCheckBox locCheck = new JCheckBox(locations.get(j).getName());
			locCheck.addItemListener(this);
			locCheck.setSelected(false);
			allLocCheckBoxes.add(locCheck);
		}
		
		for (int k = 0; k < allLocCheckBoxes.size(); k++)
		{
			checkPanel.add(allLocCheckBoxes.get(k));
		}

		scrollThroughLocations = new JScrollPane(checkPanel);
		allLocationsScreen.add(scrollThroughLocations, BorderLayout.CENTER);
		scrollThroughLocations.revalidate();
		scrollThroughLocations.repaint();
			
		badUserLabel.setVisible(false);
		validate();
		
    	CardLayout cl = (CardLayout)(cards.getLayout());
    	cl.show(cards, "Locations");
    }
	
	public static void main(String[] args)
	{
		LoginGui logingui = new LoginGui();
	}
}
