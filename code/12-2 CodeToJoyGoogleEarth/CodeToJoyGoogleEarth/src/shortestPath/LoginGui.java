package shortestPath;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class LoginGui extends JFrame implements ActionListener
{
	JButton registerButton, loginButton, backButton, logoutButton, submitButton, createAccountButton;
	
	JLabel usernameLabel, passwordLabel, chooseUsernameLabel, choosePasswordLabel, reenterPasswordLabel;
	
	JTextField usernameField, passwordField, newUsernameField, newPasswordField, reenterPasswordField;
	
	JPanel cards;
	
	public LoginGui()
	{
		super("Mapping Shortest Distance");	// The title appearing at the top
		setLocation(100, 100);	// Where the window pops up
		setSize(900, 600);		// Size of the window
		
		// Setting up the layout
		
		JPanel loginScreen = new JPanel();
		JPanel newAccountScreen = new JPanel();
		JPanel appScreen = new JPanel();
		
        cards = new JPanel(new CardLayout());
		
		cards.add(loginScreen, "Login");
		cards.add(newAccountScreen, "Create new account");
		cards.add(appScreen, "Shortest Distance Application");
		
		// Set up the first screen - login to your account
		usernameLabel = new JLabel("Username:");
		loginScreen.add(usernameLabel);
		
		usernameField = new JTextField(20);
		loginScreen.add(usernameField);
		
		passwordLabel = new JLabel("Password:");
		loginScreen.add(passwordLabel);
		
		passwordField = new JTextField(20);
		loginScreen.add(passwordField);
		
		loginButton = new JButton("Log in");
		loginScreen.add(loginButton);
		
		registerButton = new JButton("Create a new account");
		loginScreen.add(registerButton);
		
		// Set up the second screen - register a new account
		chooseUsernameLabel = new JLabel("Choose a username: ");
		newAccountScreen.add(chooseUsernameLabel);
		
		newUsernameField = new JTextField(20);
		newAccountScreen.add(newUsernameField);
		
		choosePasswordLabel = new JLabel("Choose a password: ");
		newAccountScreen.add(choosePasswordLabel);
		
		newPasswordField = new JTextField(20);
		newAccountScreen.add(newPasswordField);
		
		reenterPasswordLabel = new JLabel("Re-enter password: ");
		newAccountScreen.add(reenterPasswordLabel);
		
		reenterPasswordField = new JTextField(20);
		newAccountScreen.add(reenterPasswordField);
		
		createAccountButton = new JButton("Create Account");
		newAccountScreen.add(createAccountButton);
		
		backButton = new JButton("Back to Login Screen");
		newAccountScreen.add(backButton);
		
		// Set up the third screen - actual mapping application
		logoutButton = new JButton("Logout");	// Go back to the login screen
		appScreen.add(logoutButton);
		
		submitButton = new JButton("Launch Application!");		// Makes the application map the locations
		appScreen.add(submitButton);
		
		JPanel checks = new JPanel(new GridLayout(3,1));
		JLabel chooseLocation = new JLabel("Choose the locations to map.");
		appScreen.add(chooseLocation);
		
		Checkbox location1 = new Checkbox("first location", false);
		checks.add(location1);
		Checkbox location2 = new Checkbox("second location", false);
		checks.add(location2);
		Checkbox location3 = new Checkbox("third location", false);
		checks.add(location3);
		
		appScreen.add(checks);
		
		JPanel startGrid = new JPanel(new GridLayout(3,1));
		CheckboxGroup startLocation = new CheckboxGroup();
		JLabel selectStart = new JLabel("Select the starting location");
		
		// Read in the locations from the file and create an array to use for the checkboxes.
		Checkbox loc1 = new Checkbox("location 1", startLocation, false);
		Checkbox loc2 = new Checkbox("location 2", startLocation, false);
		Checkbox loc3 = new Checkbox("location 3", startLocation, false);
		
		CheckboxGroup stopLocation = new CheckboxGroup();
		JLabel selectStop = new JLabel("Select the ending location");
		JPanel stopGrid = new JPanel(new GridLayout(3,1));
		
		Checkbox sloc1 = new Checkbox("location 1", stopLocation, false);
		Checkbox sloc2 = new Checkbox("location 2", stopLocation, false);
		Checkbox sloc3 = new Checkbox("location 3", stopLocation, false);
		
		appScreen.add(selectStart);
		startGrid.add(loc1);
		startGrid.add(loc2);
		startGrid.add(loc3);
		appScreen.add(startGrid);
		
		appScreen.add(selectStop);
		stopGrid.add(sloc1);
		stopGrid.add(sloc2);
		stopGrid.add(sloc3);
		appScreen.add(stopGrid);
		
		// Allows something to happen when you press the button
		registerButton.addActionListener(this);
		backButton.addActionListener(this);
		loginButton.addActionListener(this);
		logoutButton.addActionListener(this);
		submitButton.addActionListener(this);
		createAccountButton.addActionListener(this);
		
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
        else if (e.getSource() == backButton)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Login");
        }
        // Click "login" to get to the application
        // Need to add identity validation here
        else if (e.getSource() == loginButton)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Shortest Distance Application");
        }
        // Click "logout" from the application screen to go back to login screen
        else if (e.getSource() == logoutButton)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Login");
        }
        else if (e.getSource() == submitButton)
        {
        	// Does nothing yet. This is where we implement shortest distance mapping.
        }
        else if (e.getSource() == createAccountButton)
        {
        	CardLayout cl = (CardLayout)(cards.getLayout());
        	cl.show(cards, "Shortest Distance Application");
        }
    }
	
	public static void main(String[] args)
	{
		LoginGui logingui = new LoginGui();
	}
}
