package distanceProj;

/**
 * Class: User
 * 
 * There are no mutators because after a user's account is created, there
 * should be no reason to change it, even if the user is an admin.
 * Maybe to change the password, but as that is not a requirement, it can be added
 * later if wanted.
 * 
 * @author amanda
 *
 */
public class User 
{
	String username = "";
	String password = "";
	boolean isAdmin = false;
	
	public User(String username, String password, boolean isAdmin)
	{
		this.username = username;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	// This method is only to be used for login validation
	public String getPassword()
	{
		return password;
	}
	
	public boolean isAdmin()
	{
		return isAdmin;
	}
	
	public String toString()
	{
		return username + " " + password + " " + isAdmin;
	}
	
	
	public boolean equals(Object other)
	{
		if (other == null)
		{
			return false;
		}
		if (this.getClass() != other.getClass())
		{
			return false;
		}
		if (!this.getUsername().equals(((User) other).getUsername()))
		{
			return false;
		}
		if (!this.getPassword().equals(((User) other).getPassword()))
		{
			return false;
		}
		return true;
	}
	
	public static void main(String[] args)
	{
		User user1 = new User("ahart2", "pass1", true);
		User user2 = new User("ahart3", "pass2", false);
		User user3 = new User("ahart3", "pass2", false);
		System.out.println(user1.equals(user2)); // Should be false
		System.out.println(user2.equals(user3)); // Should be true
	}
}
