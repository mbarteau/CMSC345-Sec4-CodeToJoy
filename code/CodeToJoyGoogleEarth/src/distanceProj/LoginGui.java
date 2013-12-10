package distanceProj;

import location.Location;
import kml.PathToKML;
import email.GoogleMail;
import shortestPath.ShortestDistance;

import java.awt.BorderLayout;
import java.awt.CardLayout;        
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import helpFiles.HelpWindow;

/**
* Class: LoginGui
*
* This is the driving class. Everything starts here from the login screen.
* @author amanda
*
*/
public class LoginGui extends JFrame implements ActionListener, ItemListener
{
	String userName = null;

	final int MIN_LOCATIONS_CHECKED = 2;
	// User buttons
	JButton registerButton, loginButton, backButton1, logoutButton1, submitButton, createAccountButton;
	JButton nextScreenButton1, nextScreenButton2, nextScreenButton3, backButton2, backButton3;
	JButton logoutButton2, logoutButton3, backButton4, backButton5, backButton6, continueButton;
	JButton helpButton1, helpButton2, helpButton3, helpButton4, helpButton5, helpButton6, helpButton7;

	// Admin specific buttons
	JButton addLocationButton, removeLocationButton, changeLocationButton, submitButton1, submitButton2;
	JButton submitButton3, submitButton4;

	JRadioButton chosenModifyLocButton;

	ButtonGroup startLocationGroup = new ButtonGroup();
	ButtonGroup stopLocationGroup = new ButtonGroup();
	ButtonGroup modifyLocGroup = new ButtonGroup();

	JLabel usernameLabel, passwordLabel, chooseUsernameLabel, choosePasswordLabel, reenterPasswordLabel;
	JLabel userExists, emptyPassword;

	JTextArea dateLabel;

	JTextArea emailLabel;

	// Admin specific labels
	JLabel modifiedNameLabel, modifiedLatLabel, modifiedLongLabel, modifyTitle;

	JTextArea introduction;

	// Error Labels
	JLabel badUserLabel, passwordsNotSame, needMoreLocError, selectStartError;

	// Admin specific error labels
	JLabel newLocNameLabel, newLocLatLabel, newLocLongLabel, existingLocError, emptyFieldsError;
	JLabel emptyFieldsError2, chooseLocError;

	JPasswordField passwordField;

	JTextField usernameField, newUsernameField, newPasswordField, reenterPasswordField, emailField;
	JTextField dateField;

	// Admin specific textfields
	JTextField newLocNameField, newLocLatField, newLocLongField, modifiedNameField, modifiedLatField;
	JTextField modifiedLongField;

	JPanel cards, loginScreen, startLocationScreen, stopLocationScreen, allLocationsScreen, addLocScreen;
	JPanel removeLocScreen, changeLocScreen, newAccountScreen, startPanel, stopPanel, checkPanel;
	JPanel adminScreen;

	// Admin specific panels
	JPanel removeLocPanel, modifyPanel, changeLocScreen2, modifySidePane, infoPane, addErrorPanel;
	JPanel addLocSidePane;

	JScrollPane scrollThroughLocations, scrollThroughChangeLocs, scrollThroughRemoveLocs;

	Location startLocation;
	Location stopLocation;

	ArrayList<JCheckBox> allLocCheckBoxes = new ArrayList<JCheckBox>();
	ArrayList<JCheckBox> checkedBoxes = new ArrayList<JCheckBox>();
	ArrayList<JRadioButton> startLocationButtons = new ArrayList<JRadioButton>();
	ArrayList<JRadioButton> stopLocationButtons = new ArrayList<JRadioButton>();
	ArrayList<JRadioButton> modifyLocButtons = new ArrayList<JRadioButton>();
	ArrayList<Location> locations = new ArrayList<Location>();

	Color backGroundColor = new Color(0, 153, 153);        	// Dark Turquois
	Color titleColor = new Color(153, 204, 255);        	// Lighter turquois
	Color textColor = new Color(255, 255, 255);             // White
	Color otherColor = new Color(51, 153, 255);             // Blue
	Color backGroundColor2 = new Color(0, 102, 51); 		// Forest Green
	Color lightBackColor = new Color(204, 255, 153);		// Light Green
	Color textColor2 = new Color(0, 0, 0);					// Black
	Color errorColor = new Color(255, 144, 88);				// Orange
	Color errorColor2 = Color.red;
	
	public LoginGui()
	{
		super("Mapping Shortest Distance");        // The title appearing at the top
		setLocation(100, 100);        // Where the window pops up
		setSize(800, 500);                // Size of the window

		// Setting up the layout - each screen is a card that is shown one at a time
		cards = new JPanel(new CardLayout());

		// Set up the first screen - login to your account
		loginScreen = new JPanel(null);
		loginScreen.setBackground(backGroundColor2);         

		usernameLabel = new JLabel("Username:");
		usernameLabel.setLocation(520, 90);
		usernameLabel.setSize(100, 30);
		usernameLabel.setForeground(textColor);
		loginScreen.add(usernameLabel);

		usernameField = new JTextField();
		usernameField.setLocation(600, 90);
		usernameField.setSize(160, 30);
		loginScreen.add(usernameField);

		passwordLabel = new JLabel("Password:");
		passwordLabel.setSize(100, 30);
		passwordLabel.setLocation(520, 130);
		passwordLabel.setForeground(textColor);
		loginScreen.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setLocation(600, 130);
		passwordField.setSize(160, 30);
		loginScreen.add(passwordField);

		loginButton = new JButton("Log in");
		loginButton.setLocation(600, 200);
		loginButton.setSize(160, 30);
		loginScreen.add(loginButton);

		registerButton = new JButton("Create a new account");
		registerButton.setLocation(600, 240);
		registerButton.setSize(160, 30);
		loginScreen.add(registerButton);

		helpButton1 = new JButton("HELP");
		helpButton1.setLocation(600, 5);
		helpButton1.setSize(100, 30);
		loginScreen.add(helpButton1);
		
		badUserLabel = new JLabel("Your username or password was incorrect");
		badUserLabel.setForeground(errorColor);
		badUserLabel.setLocation(500, 300);
		badUserLabel.setSize(250, 15);
		loginScreen.add(badUserLabel);
		badUserLabel.setVisible(false);

		ImageIcon titleIcon = new ImageIcon("mappingMaestroTitle.jpg");
		JLabel title = new JLabel(titleIcon);
		title.setSize(320, 320);
		title.setLocation(240, 5);

		String intro = "\n Welcome to MappingMaestro by Code2Joy.\n\n";
            intro += " MappingMaestro will allow you to select\n";
            intro += " from several National Parks in the United\n";
            intro += " States and Canada that you would like to\n";
            intro += " visit. Then you choose the parks where you\n";
            intro += " would like to start and stop. Next,\n";
            intro += " MappingMaestro will find the shortest\n";
            intro += " distance for you to travel to all of them\n";
            intro += " by plotting the trip on Google Earth.\n\n";
            intro += " Log in to your account or create a new\n";
            intro += " one to continue.";

        introduction = new JTextArea(intro);
        introduction.setEditable(false);
        introduction.setSize(280, 300);
        introduction.setLocation(30, 90);
        introduction.setOpaque(false);
        introduction.setForeground(textColor);
        loginScreen.add(introduction);
        loginScreen.add(title);

        // Set up the screen to create a new account
        newAccountScreen = new JPanel(null);
        newAccountScreen.setBackground(lightBackColor);

        JLabel newAccountLabel = new JLabel("Create Account");
        newAccountLabel.setFont(new Font("Serif", Font.ITALIC, 30));
        newAccountLabel.setLocation(300, 20);
        newAccountLabel.setSize(300, 30);
        newAccountLabel.setForeground(backGroundColor2);
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
        createAccountButton.setLocation(400, 250);
		createAccountButton.setSize(200, 30);
		newAccountScreen.add(createAccountButton);
		
		helpButton2 = new JButton("HELP");
		helpButton2.setLocation(600, 5);
		helpButton2.setSize(100, 30);
		newAccountScreen.add(helpButton2);
		
		
		backButton1 = new JButton("Back to Login Screen");
		backButton1.setSize(200, 30);
		backButton1.setLocation(400, 300);
		newAccountScreen.add(backButton1);
	
		
		passwordsNotSame = new JLabel("The passwords do not match.");
		passwordsNotSame.setLocation(450, 300);
		passwordsNotSame.setSize(250, 30);
		passwordsNotSame.setForeground(errorColor2);
		newAccountScreen.add(passwordsNotSame);
		passwordsNotSame.setVisible(false);
		
		userExists = new JLabel("This account already exists.");
		userExists.setForeground(errorColor2);
		userExists.setLocation(450, 300);
		userExists.setSize(250, 30);
		newAccountScreen.add(userExists);
		userExists.setVisible(false);
		
		emptyPassword = new JLabel("You must enter a username and password");
		emptyPassword.setForeground(errorColor2);
		emptyPassword.setLocation(450, 300);
		emptyPassword.setSize(250, 30);
		newAccountScreen.add(emptyPassword);
		emptyPassword.setVisible(false);
		
		// Set up the third screen - choose the locations
		allLocationsScreen = new JPanel(new BorderLayout());
		
		// Set up the top panel and its buttons/labels
		JPanel topPanel = new JPanel(new FlowLayout());        
		JLabel chooseLocation = new JLabel("Choose the locations to map.");
		topPanel.add(chooseLocation);
		topPanel.setBackground(lightBackColor);
		
		helpButton3 = new JButton("HELP");
		topPanel.add(helpButton3);
		allLocationsScreen.add(topPanel, BorderLayout.NORTH);
		
		// Set up the right panel and its buttons/labels
		JPanel rightPanel = new JPanel(new BorderLayout());
		nextScreenButton1 = new JButton("NEXT");
		rightPanel.add(nextScreenButton1, BorderLayout.NORTH);
		
		logoutButton1 = new JButton("Logout");        // Go back to the login screen
		topPanel.add(logoutButton1);
		
		rightPanel.setBackground(backGroundColor2);
		allLocationsScreen.add(rightPanel, BorderLayout.EAST);
		
		// Make that panel scrollable
		checkPanel = new JPanel(new GridLayout(0,1));
		
		scrollThroughLocations = new JScrollPane(checkPanel);
		allLocationsScreen.add(scrollThroughLocations, BorderLayout.CENTER);
		
		needMoreLocError = new JLabel("You must choose at least " + MIN_LOCATIONS_CHECKED + " locations.");
		needMoreLocError.setForeground(Color.red);
		allLocationsScreen.add(needMoreLocError, BorderLayout.SOUTH);
		needMoreLocError.setVisible(false);
		
		// Set up the fourth screen - select start location
		
		startLocationScreen = new JPanel(new BorderLayout());
		
		startPanel = new JPanel(new GridLayout(0,1));
		
		JPanel startTopPanel = new JPanel(new FlowLayout());
		JLabel selectStartLabel = new JLabel("Select the starting location");
		startTopPanel.setBackground(lightBackColor);
		startTopPanel.add(selectStartLabel);
		
		helpButton4 = new JButton("HELP");
		startTopPanel.add(helpButton4);
		startLocationScreen.add(startTopPanel, BorderLayout.NORTH);
		    
		JPanel sidePanel = new JPanel(new BorderLayout());        
		sidePanel.setBackground(backGroundColor2);
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
		startTopPanel.add(logoutButton2);
		
		startLocationScreen.add(sidePanel, BorderLayout.EAST);
		
		// Set up the fifth screen - select the stop location
		
		stopLocationScreen = new JPanel(new BorderLayout());
		
		stopPanel = new JPanel(new GridLayout(0,1));
		
		JPanel stopTopPanel = new JPanel(new FlowLayout());
		JLabel selectStopLabel = new JLabel("Select the ending location");
		stopTopPanel.setBackground(lightBackColor);
		stopTopPanel.add(selectStopLabel);
		
		helpButton5 = new JButton("HELP");
		stopTopPanel.add(helpButton5);
		
		stopLocationScreen.add(stopTopPanel, BorderLayout.NORTH);
		            
		JScrollPane scrollThroughStop = new JScrollPane(stopPanel);
		stopLocationScreen.add(scrollThroughStop, BorderLayout.CENTER);
		    
		
		
		JPanel emailPanel = new JPanel();
		emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.Y_AXIS));
		
		emailPanel.add(Box.createRigidArea(new Dimension(0,30)));		
		String emailInstructions = "\n If you would like to ";
		emailInstructions += "\n send your trip to your";
		emailInstructions += "\n email address, enter your ";
		emailInstructions += "\n information below. \n\n";
		JTextArea emailInfo = new JTextArea(emailInstructions);
		emailInfo.setOpaque(false);
		emailInfo.setForeground(textColor);
		emailPanel.add(emailInfo);
		
		emailPanel.add(Box.createVerticalGlue());
		
		emailLabel = new JTextArea("Enter your email address:");
		emailLabel.setForeground(textColor);
		emailLabel.setOpaque(false);
		emailPanel.add(emailLabel);
		emailPanel.add(Box.createRigidArea(new Dimension(0,10)));
		emailPanel.add(Box.createVerticalGlue());
		emailField = new JTextField();
		emailField.setPreferredSize(new Dimension(100, 30));
		emailField.setMaximumSize(new Dimension(200, 30));
		emailPanel.add(emailField);
		emailPanel.add(Box.createRigidArea(new Dimension(0,30)));
		
		dateLabel = new JTextArea("Enter the date of the trip:");
		dateLabel.setForeground(textColor);
		dateLabel.setOpaque(false);
		emailPanel.add(dateLabel);
		emailPanel.add(Box.createRigidArea(new Dimension(0,10)));
		emailPanel.add(Box.createVerticalGlue());
		dateField = new JTextField();
		dateField.setPreferredSize(new Dimension(100, 30));
		dateField.setMaximumSize(new Dimension(200, 30));
		emailPanel.add(dateField);
		emailPanel.add(Box.createRigidArea(new Dimension(0,10)));
		emailPanel.setBackground(backGroundColor2);
		
		nextScreenButton3 = new JButton("LAUNCH TRIP");
		emailPanel.add(nextScreenButton3);
		/*
		backButton3 = new JButton("Back");
		sidePanel2.add(backButton3);
		*/
		
		logoutButton3 = new JButton("Logout");
		stopTopPanel.add(logoutButton3);
		
		stopLocationScreen.add(emailPanel, BorderLayout.EAST);
		
		// Set up the screen to add a location
		addLocScreen = new JPanel(new BorderLayout());
		JLabel addLocTitle = new JLabel("Add a new Location");
		addLocTitle.setBackground(lightBackColor);
		addLocScreen.add(addLocTitle, BorderLayout.NORTH);
		
		JPanel newLocPanel = new JPanel();
		newLocPanel.setLayout(new BoxLayout(newLocPanel, BoxLayout.Y_AXIS));
		
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
		
		addLocSidePane = new JPanel();
		addLocSidePane.setLayout(new BoxLayout(addLocSidePane, BoxLayout.Y_AXIS));
		
		backButton4 = new JButton("BACK");        
		addLocSidePane.add(backButton4);
		
		addLocSidePane.add(Box.createRigidArea(new Dimension(0, 10)));
		
		submitButton1 = new JButton("Submit new location");
		addLocSidePane.add(submitButton1);
		
		addLocScreen.add(addLocSidePane, BorderLayout.EAST);
		
		existingLocError = new JLabel("That location already exists, you may not use it.");
		existingLocError.setVisible(false);
		existingLocError.setForeground(Color.red);
		addLocScreen.add(existingLocError, BorderLayout.SOUTH);
		
		emptyFieldsError = new JLabel("You must enter something in each field.");
		emptyFieldsError.setVisible(false);
		emptyFieldsError.setForeground(Color.red);
		addLocScreen.add(emptyFieldsError, BorderLayout.SOUTH);
		
		addErrorPanel = new JPanel();
		addErrorPanel.setLayout(new BoxLayout(addErrorPanel, BoxLayout.Y_AXIS));
		
		addErrorPanel.add(existingLocError);
		addErrorPanel.add(emptyFieldsError);
		
		addLocScreen.add(newLocPanel, BorderLayout.CENTER);
		addLocScreen.add(addErrorPanel, BorderLayout.SOUTH);
		
		// Set up the screen to remove a location
		removeLocPanel = new JPanel(new GridLayout(0,1));
		
		removeLocScreen = new JPanel(new BorderLayout());
		JLabel removeLocTitle = new JLabel("Remove a Location");
		removeLocTitle.setBackground(lightBackColor);
		removeLocScreen.add(removeLocTitle, BorderLayout.NORTH);
		
		submitButton2 = new JButton("Submit removed locations");
		removeLocScreen.add(submitButton2, BorderLayout.SOUTH);
		
		backButton5 = new JButton("BACK");
		removeLocScreen.add(backButton5, BorderLayout.EAST);
		
		// Make that panel scrollable
		scrollThroughRemoveLocs = new JScrollPane(removeLocPanel);
		removeLocScreen.add(scrollThroughRemoveLocs, BorderLayout.CENTER);
		
		// Set up the screen to choose a location to modify
		changeLocScreen = new JPanel(new BorderLayout());
		JLabel changeLocTitle = new JLabel("Change a Location");
		changeLocTitle.setBackground(lightBackColor);
		changeLocScreen.add(changeLocTitle, BorderLayout.NORTH);
		
		modifyPanel = new JPanel(new GridLayout(0,1));
		
		// Make that panel scrollable
		scrollThroughChangeLocs = new JScrollPane(modifyPanel);
		changeLocScreen.add(scrollThroughChangeLocs, BorderLayout.CENTER);
		
		modifySidePane = new JPanel();
		modifySidePane.setLayout(new BoxLayout(modifySidePane, BoxLayout.Y_AXIS));
		//modifySidePane.add(Box.createVerticalGlue());
		
		backButton6 = new JButton("BACK");
		modifySidePane.add(backButton6);
		
		modifySidePane.add(Box.createRigidArea(new Dimension(0, 10)));
		
		submitButton3 = new JButton("Submit location to modify");
		modifySidePane.add(submitButton3);
		
		chooseLocError = new JLabel("You must choose a location.");
		chooseLocError.setVisible(false);
		chooseLocError.setForeground(Color.red);
		changeLocScreen.add(chooseLocError, BorderLayout.SOUTH);
		
		changeLocScreen.add(modifySidePane, BorderLayout.EAST);
		
		// Set up the screen to actually input information to modify
		changeLocScreen2 = new JPanel(new BorderLayout());
		
		infoPane = new JPanel();
		infoPane.setLayout(new BoxLayout(infoPane, BoxLayout.Y_AXIS));
		infoPane.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		modifyTitle = new JLabel();        // Depends on what location to modify
		modifyTitle.setBackground(lightBackColor);
		changeLocScreen2.add(modifyTitle, BorderLayout.NORTH);
		
		modifiedNameLabel = new JLabel("Enter the new name: ");
		infoPane.add(modifiedNameLabel);
		    
		modifiedNameField = new JTextField(20);
		infoPane.add(modifiedNameField);
		
		modifiedLatLabel = new JLabel("Enter the new latitude: ");
		infoPane.add(modifiedLatLabel);
		    
		modifiedLatField = new JTextField(20);
		infoPane.add(modifiedLatField);
		
		modifiedLongLabel = new JLabel("Enter the new longitude: ");
		infoPane.add(modifiedLongLabel);
		    
		modifiedLongField = new JTextField(20);
		infoPane.add(modifiedLongField);
		
		JPanel sidePane2 = new JPanel(new FlowLayout());                
		submitButton4 = new JButton("Submit the new location.");
		sidePane2.add(submitButton4);
		
		emptyFieldsError2 = new JLabel("You must enter something in each field.");
		emptyFieldsError2.setForeground(Color.red);
		changeLocScreen2.add(emptyFieldsError2, BorderLayout.SOUTH);
		emptyFieldsError2.setVisible(false);
		
		changeLocScreen2.add(infoPane);
		changeLocScreen2.add(sidePane2, BorderLayout.EAST);
		
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
		backButton1.addActionListener(this);
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
		submitButton1.addActionListener(this);
		submitButton2.addActionListener(this);
		submitButton3.addActionListener(this);
		submitButton4.addActionListener(this);
		helpButton1.addActionListener(this);
		helpButton2.addActionListener(this);
		helpButton3.addActionListener(this);
		helpButton4.addActionListener(this);
		helpButton5.addActionListener(this);
		//helpButton6.addActionListener(this);
		
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
		cards.add(changeLocScreen2, "Modify Chosen Location");
		
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
		else if (e.getSource() == helpButton1 ||e.getSource() == helpButton2 ||e.getSource() == helpButton3 ||e.getSource() == helpButton4 ||e.getSource() == helpButton5)
		{
			URL index = ClassLoader.getSystemResource("helpInfo.html");
		    new HelpWindow("Help", index);
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
			showLocationScreen();
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
		// Remove all the radiobuttons from the panel because they get re-added each time
		for (int n = 0; n < modifyLocButtons.size(); n++)
		{
		        modifyLocGroup.remove(modifyLocButtons.get(n));
		        modifyPanel.remove(modifyLocButtons.get(n));
		}
		modifyLocButtons.clear();
		
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
		    validate();
		}
		else if (newUsernameFromGui.equals("") || newPasswordFromGui.equals("") ||
		            newPasswordAgainFromGui.equals(""))
		{
		    emptyPassword.setVisible(true);
		    validate();
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
		              i++;        // pass the password
		              i++;        // pass the user/admin letter
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
		     
		     buffReader.close();
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
		    JLabel selectStopError = new JLabel("You must choose an end location");
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
		    
		
		
		    try
		    {
		            PathToKML.createPath(trip, userName);
		    }
		    catch (FileNotFoundException e1) 
		    {
		    	e1.printStackTrace();
		    }

		    String path = new File(LoginGui.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
		    String programLoc = path + "\\client\\googleearth.exe";
		    String fileLoc = path + "\\" + userName + ".kml";
		    PathToKML.openFile(programLoc, fileLoc);
		    
		    // If the fields aren't empty, email the trip to the user
		    if (!emailField.getText().equals("") && !dateField.getText().equals(""))
		    {
		    	String email = emailField.getText();
		        String date = dateField.getText();
		        
		        String title = "Scheduled trip for " + date;
		        String message = "You have a schedule'd trip for " + date + ".\nHere is your trip's order of locations:\n";
		        int count = 1;
		        for(Location a: trip)
		        {
		                message += "\t" + count++ + ".) " + a.getName() + "\n";
		        }
		        try 
		        {
		        	GoogleMail.Send("CodeToJoy.345", "cmsc345pw", email, title, message);
		        } 
		        catch (AddressException e1) 
		        {
		                        // TODO Auto-generated catch block
		                        e1.printStackTrace();
		        } 
		        catch (MessagingException e1) 
		        {
		                        // TODO Auto-generated catch block
		                        e1.printStackTrace();
		        }
		    }
		
		
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
		
		// Remove all the checkboxes from the panel because they get re-added each time
		for (int n = 0; n < allLocCheckBoxes.size(); n++)
		{
		        removeLocPanel.remove(allLocCheckBoxes.get(n));
		}
		
		
		// Remove all the checkboxes from the panel because they get re-added each time
		for (int p = 0; p < allLocCheckBoxes.size(); p++)
		{
		        checkPanel.remove(allLocCheckBoxes.get(p));
		}
		
		
		// Remove all the radiobuttons from the panel because they get re-added each time
		for (int n = 0; n < modifyLocButtons.size(); n++)
		{
		        modifyPanel.remove(modifyLocButtons.get(n));
		        modifyLocGroup.remove(modifyLocButtons.get(n));
		}
		
		validate();
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "Login");
		}
		
		// Admin can add a new location to the list
		else if (e.getSource() == addLocationButton)
		{
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "New Location");
		}
		// Add the new location to the file and go back to the admin screen
		else if (e.getSource() == submitButton1)
		{
		// Get the input from the gui
		String getName = newLocNameField.getText();
		getName = getName.trim();
		String getLat = newLocLatField.getText();
		getLat = getLat.trim();
		String getLong = newLocLongField.getText();
		getLong = getLong.trim();
		
		
		
		// Make sure the location does not already exist in the file
		// Maybe because cannot add two widget to the same space.
		if (getLocation(getName) != null)
		{
		    emptyFieldsError.setVisible(false);
		    existingLocError.setVisible(true);
		    validate();
		}
		// Make sure the user has entered something in each field
		else if (getName.equals("") || getLat.equals("") || getLong.equals(""))
		{
		    existingLocError.setVisible(false);
		    emptyFieldsError.setVisible(true);
		    validate();
		}
		// If not, we're good to go. Add it to the file.
		// Idk why else isn't working correctly
		//if (getLocation(getName) == null && (!(getName.equals("") || getLat.equals("") || getLong.equals(""))))
		else
		{
		    BufferedWriter writeToLocFile;
		    try
		    {
		            writeToLocFile = new BufferedWriter(new FileWriter("locations.txt", true));
		            writeToLocFile.write(getName + ", " + getLat + ", " + getLong + "\n");
		            writeToLocFile.close();
		    }
		    catch(IOException e1)
		    {
		            e1.printStackTrace();
		    }
		    
		    // Remove all the checkboxes from the panel because they get re-added each time
		    for (int i = 0; i < allLocCheckBoxes.size(); i++)
		    {
		            removeLocPanel.remove(allLocCheckBoxes.get(i));
		    }
		    
		    // Go to the next screen
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "Admin Screen");
		}
		}
		// Admin can remove a location from the list
		else if (e.getSource() == removeLocationButton)
		{        
		// Remove all the checkboxes from the panel because they get re-added each time
		for (int n = 0; n < allLocCheckBoxes.size(); n++)
		{
		        removeLocPanel.remove(allLocCheckBoxes.get(n));
		}
		allLocCheckBoxes.clear();
		
		locations = createLocationList();
		
		// For each location in the file, make it into a checkbox
		for (int j = 0; j < locations.size(); j++)
		{
		        JCheckBox locCheck = new JCheckBox(locations.get(j).getName());
		        locCheck.addItemListener(this);
		        locCheck.setSelected(false);
		        allLocCheckBoxes.add(locCheck);
		}
		
		// Add each checkbox to the panel on the locations screen
		for (int k = 0; k < allLocCheckBoxes.size(); k++)
		{
		        removeLocPanel.add(allLocCheckBoxes.get(k));
		}
		
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "Remove Location");
		
		}
		// Remove the selected locations from the file
		else if (e.getSource() == submitButton2)
		{
		try
		{
		File inputFile = new File("locations.txt");
		File tempFile = new File("temp.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		
		String currentLine;
		
		while((currentLine = reader.readLine()) != null)
		{
		// Search the location checked to remove
		String trimmedLine = currentLine.trim();
		boolean canDelete = false;
		for (int i = 0; i < checkedBoxes.size(); i++)
		{
		        if (trimmedLine.startsWith(checkedBoxes.get(i).getText()))
		        {
		                canDelete = true;
		        }
		}
		
		// If the location in the line was not one of those checked,
		// rewrite it to the temporary file.
		if(!canDelete)
		{
		        writer.write(currentLine + "\n");
		}
		}
		writer.close();
		reader.close();
		
		if(!inputFile.delete())
		{
		    System.out.println("Cannot remove.");
		}
		
		if(!tempFile.renameTo(inputFile))
		{
		    System.out.println("Cannot rename");
		}
		
		    // Remove all the checkboxes from the panel because they get re-added each time
		    for (int i = 0; i < allLocCheckBoxes.size(); i++)
		    {
		            removeLocPanel.remove(allLocCheckBoxes.get(i));
		    }
		}
		catch(Exception e1)
		{
		e1.printStackTrace();
		}
		
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "Admin Screen");
		}
		// Admin can modify a location in the list
		else if (e.getSource() == changeLocationButton)
		{
		// Remove all the radiobuttons from the panel because they get re-added each time
		for (int n = 0; n < modifyLocButtons.size(); n++)
		{
		        modifyLocGroup.remove(modifyLocButtons.get(n));
		        modifyPanel.remove(modifyLocButtons.get(n));
		}
		modifyLocButtons.clear();
		
		locations = createLocationList();
		
		// For each location in the file, make it into a radio button
		for (int j = 0; j < locations.size(); j++)
		{
		        JRadioButton locRadio = new JRadioButton(locations.get(j).getName());
		        locRadio.setSelected(false);
		        modifyLocButtons.add(locRadio);
		}
		
		// Add each radio button to the panel on the change locations screen
		for (int k = 0; k < modifyLocButtons.size(); k++)
		{
		        modifyLocGroup.add(modifyLocButtons.get(k));
		        modifyPanel.add(modifyLocButtons.get(k));
		}
		
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "Modify Location");
		}
		// Submit a location to modify
		else if (e.getSource() == submitButton3)
		{
		// Get the chosen button
		boolean buttonSelected = false;
		for (int i = 0; i < modifyLocButtons.size(); i++)
		{
		    if (modifyLocButtons.get(i).isSelected())
		    {
		            chosenModifyLocButton = modifyLocButtons.get(i);
		            buttonSelected = true;
		    }
		}
		
		if (!buttonSelected)
		{
		    chooseLocError.setVisible(true);
		    validate();
		}
		else
		{
		    modifyTitle.setText("Selected location to modify: " + chosenModifyLocButton.getText());
		    validate();
		
		    CardLayout cl = (CardLayout)(cards.getLayout());
		    cl.show(cards, "Modify Chosen Location");
		}
		}
		// Show the screen to enter new info to modify location
		else if (e.getSource() == submitButton4)
		{
		// Remove all the radiobuttons from the panel because they get re-added each time
		for (int n = 0; n < modifyLocButtons.size(); n++)
		{
		        modifyLocGroup.remove(modifyLocButtons.get(n));
		        modifyPanel.remove(modifyLocButtons.get(n));
		}
		modifyLocButtons.clear();
		
		String modifiedLocName = modifiedNameField.getText();
		modifiedLocName = modifiedLocName.trim();
		String modifiedLocLat = modifiedLatField.getText();
		modifiedLocLat = modifiedLocLat.trim();
		String modifiedLocLong = modifiedLongField.getText();
		modifiedLocLong = modifiedLocLong.trim();
		
		// Make sure all the fields aren't empty
		if (modifiedLocName.equals("") || modifiedLocLat.equals("") || modifiedLocLong.equals(""))
		{
		    emptyFieldsError2.setVisible(true);
		    validate();
		}
		// Else, find that line in the file, and rewrite as given
		// Continue back to the admin screen
		else
		{
		try
		{
		File inputFile = new File("locations.txt");
		File tempFile = new File("temp.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		
		String currentLine;
		
		while((currentLine = reader.readLine()) != null)
		{
		    // Search the location checked to remove
		    String trimmedLine = currentLine.trim();
		    boolean canDelete = false;
		    if (trimmedLine.startsWith(chosenModifyLocButton.getText()))
		    {
		            canDelete = true;
		    }
		    
		    // If the location in the line was not the one to be modified, just rewrite it
		    if(!canDelete)
		    {
		            writer.write(currentLine + "\n");
		    }
		    // Otherwise, modify it and write it to the file
		    else
		    {
		            writer.write(modifiedLocName + ", " + modifiedLocLat + ", " + modifiedLocLong + "\n");
		    }
		}
		writer.close();
		reader.close();
		
		// Delete the original file
		if(!inputFile.delete())
		{
		        System.out.println("Cannot remove.");
		}
		// Rename the temporary file as the original
		if(!tempFile.renameTo(inputFile))
		{
		        System.out.println("Cannot rename");
		}
		}
		catch(Exception e1)
		{
		    e1.printStackTrace();
		}
		    
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, "Admin Screen");
		}
		}
		else if (e.getSource() == continueButton)
		{
		showLocationScreen();
		}
		}
		
		private ArrayList<Location> getTrip()
		{
		ArrayList<Location> trip = new ArrayList<Location>();
		for (int i = 0; i < checkedBoxes.size(); i++)
		{
		trip.add(getLocation(checkedBoxes.get(i).getText()));
		}
		return trip;
		}
		
		//
		private Location getLocation(String locationName)
		{
		createLocationList();
		for (int i = 0; i < locations.size(); i++)
		{
		if (locations.get(i).getName().equals(locationName))
		{
		        return locations.get(i);
		}
		}
		return null;
		}
		
		private ArrayList<Location> createLocationList()
		{
		try
		{
		    locations.clear();
		    BufferedReader bR = new BufferedReader(new FileReader("locations.txt"));
		    
		    // Read in all the locations from the file
		    String locLine = bR.readLine();
		    while(locLine != null)
		    {
		            String[] data = locLine.split(",");
		            double lat = Double.parseDouble(data[1]);
		            double lon = Double.parseDouble(data[2]);
		            locations.add(new Location(data[0], lat, lon));
		            locLine = bR.readLine();
		    }
		    
		    bR.close();
		}
		catch (FileNotFoundException e1)
		{
		    e1.printStackTrace();
		}
		catch (IOException e2)
		{
		    e2.printStackTrace();
		}
		return locations;
		}
		
		/** 1. Parse in locations from the file
		2. Store in the ArrayList
		3. Use it to make checkboxes
		4. Go to the location screen        
		**/
		private void showLocationScreen()        
		{
		createLocationList();
		
		
		for (int h = 0; h < checkedBoxes.size(); h++)
		{
		    checkedBoxes.get(h).setSelected(false);
		}
		checkedBoxes.clear();         // If the admin used this, make sure it is clear again.
		
		for (int g = 0; g < allLocCheckBoxes.size(); g++)
		{
		    allLocCheckBoxes.get(g).setSelected(false);
		}
		allLocCheckBoxes.clear();
		
		locations = createLocationList();
		
		// For each location in the file, make it into a checkbox
		for (int j = 0; j < locations.size(); j++)
		{
		    JCheckBox locCheck = new JCheckBox(locations.get(j).getName());
		    locCheck.addItemListener(this);
		    locCheck.setSelected(false);
		    allLocCheckBoxes.add(locCheck);
		}
		
		// Add each checkbox to the panel on the locations screen
		for (int k = 0; k < allLocCheckBoxes.size(); k++)
		{
		    checkPanel.add(allLocCheckBoxes.get(k));
		}
		    
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
