/**
 * @author Jake Musleh
 * Email: jmusleh@wisc.edu
 * September 3, 2019
 * COMP SCI 400-001
 * Given a year and a number, this program returns the song that was
 * nth most popular song in that year according to Billboard. When
 * the user sees one that they like, they have the ability to add it
 * to a playlist, which are written to a text file.
 */

import java.util.Scanner;
import java.net.*;
import java.io.*;
import java.awt.Desktop;
public class ScannerPractice {
	
	    private static final Scanner STDIN = new Scanner(System.in); //Reads from and writes data to the console
	    private static File playlist = null; //The file in which user's chosen songs reside
	    private static String song = ""; //The most recently retrieved song
	    
	    
	    /**
	     * Allows the user to select which function they want to execute
	     * @param args - Unused
	     * @throws IOException - If an I/O error occurs while reading from website or reading from/writing to file
	     * @throws MalformedURLException - If the URL is invalid
	     * @throws URISyntaxException - When a String is unable to be parsed into a URI reference
	     */
	    public static void main(String [] args) throws IOException, MalformedURLException, URISyntaxException
	    {
	    	System.out.println("This program allows you to search for a song in the Billboard " 
	    						+ "Hot 100 given any year from 1958-2019 and any number from 1-100");
	    	System.out.println("");
	    	printMenu();
	    	String input = STDIN.nextLine().trim();
	    	while(!(input.charAt(0) == 'Q' || input.charAt(0) == 'q'))
	    	{
	    		if(input.charAt(0) == 'F' || input.charAt(0) == 'f')
	    		{
	    			String[] split = input.split(" ");
	    			if(split.length != 3)
	    			{
	    				System.out.println("Invalid input. Please try again");
	    			}
	    			else
	    			{
	    				String song = getSongInformation(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
	    				System.out.println(song);
	    			}
	    		}
	    		
	    		else if(input.charAt(0) == 'O' || input.charAt(0) == 'o')
	    		{
	    			openSongInBrowser(song);
	    		}
	    		
	    		else if(input.charAt(0) == 'A' || input.charAt(0) == 'a')
	    		{
	    			if(playlist == null)
	    			{
	    				System.out.println("Please enter the file path of your playlist");
		    			String pathname = STDIN.nextLine().trim();
		    			setPlaylistPath(pathname);
		    			addToPlaylist(song, playlist);
	    			}
	    			
	    			else
	    			{
	    				addToPlaylist(song, playlist);
	    			}
	    			
	    		}
	    		
	    		else if(input.charAt(0) == 'V' || input.charAt(0) == 'v')
	    		{
	    			System.out.println(viewPlaylist(playlist));
	    		}
	    		
	    		else if(input.charAt(0) == 'S' || input.charAt(0) == 's')
	    		{
	    			System.out.println("Please enter the file path of your playlist");
	    			String pathname = STDIN.nextLine().trim();
	    			setPlaylistPath(pathname);
	    		}
	    		
	    		else
	    		{
	    			System.out.println("Please enter valid input");
	    		}
	    		
	    		printMenu();
	    		input = STDIN.nextLine().trim();
	    	}
	    }
	       
	    
	    /**
	     * Returns the title and artist name for a song on the Hot 100 given its year and position
	     * Makes use of Scanner
	     * @param year - The year of the song
	     * @param number - Where the song placed on the Hot 100 chart for that year
	     * @return - A string containing the song's title and artist
	     * @throws IllegalArgumentException - If the year and/or number are out of range
	     * @throws MalformedURLException - If the URL is invalid
	     * @throws IOException - If data from the Internet cannot be read
	     */
	    public static String getSongInformation(int year, int number) throws IllegalArgumentException, MalformedURLException, IOException
	    {
	    	if(year < 1958 || year > 2019)
	    	{
	    		throw new IllegalArgumentException("The entered year is invalid. Valid years range from 1958-2019.");
	    	}
	    	
	    	else if(number < 1 || number > 100)
	    	{
	    		throw new IllegalArgumentException("The entered number is invalid. Valid numbers range from 1-100.");
	    	}
	    	
	    	else
	    	{
	    		try
	    		{
		    		String webAddress = "https://en.wikipedia.org/wiki/Billboard_Year-End_Hot_100_singles_of_" + year;
		    		URL hot100Link = new URL(webAddress);
		    		Scanner reader = new Scanner(hot100Link.openStream());
		    		
		    		while(reader.hasNextLine())
		    		{
		    			//Years before 1982 were encoded using slightly different syntax than the years after
		    			String nextLine = reader.nextLine();
		    			boolean before1982 = nextLine.equals("<td>" + number + "</td>");
		    			boolean atOrAfter1982 = nextLine.equals("<th scope=\"row\">" + number);
		    			if(before1982 || atOrAfter1982)
		    			{
		    				if(atOrAfter1982)
		    				{
		    					reader.nextLine(); //Slightly different syntax
		    				}
		    				
		    				//The line in which the title of the song resides
		    				String s = reader.nextLine();
		    				//The line in which the artist's name resides
		    				s+= reader.nextLine();
		    				
		    				//Because the title and name both reside in-between a set of
		    				//<, > in the HTML code, this makes for easier parsing
		    				String[] split = s.split(">");
		    				
		    				//Bounds of the substring prevent quotes from being included in title and artist Strings
		    				//Indices 2 and 5 are used because those are always where instances of the 
		    				//title and author can be found in Wikipedia's HTML code
		    				String title = split[2].substring(0, split[2].indexOf("<"));
		    				String artist = split[6].substring(0, split[6].indexOf("<"));
		    				song = title + " by " + artist;
		    				reader.close();
		    				
		    				//Returns a song that correctly displays any ampersands (replaces their HTML encoding)
 	    				    //Note: There may be additional HTML encodings similar to the ampersand, but for purposes
		    				//of time-management, I have not addressed those cases
		    				return song.replaceAll("&amp;", "&");
		    			}
		    			
		    		}
		    		return null;
	    		}
		    	
	    		catch(MalformedURLException e)
	    		{
	    			System.out.println("Error has occurred. Cannot retrieve information.");
	    			return null;
	    		}
	    		
	    		catch(IOException e)
	    		{
	    			System.out.println("Error has ocurred. Cannot retrieve information.");
	    			return null;
	    		}
	    		
	    	}
	    }
	    
	    
	    /**
	     * Searches for the most recent song on Youtube, opening a browser window
	     * Does not make use of PrintWriter or Scanner
	     * @param song - The song to be searched for
	     * @throws URISyntaxException - If the URL is invalid
	     */
	    public static void openSongInBrowser(String song) throws URISyntaxException
	    {
	    	try
	    	{
	    		//Creates a Youtube URL that corresponds to searching for the specified song
		    	//Note: Youtube uses "+" to represent space in their URLs
		    	String youtubeURL = "https://www.youtube.com/results?search_query=";
		    	youtubeURL += song.replaceAll(" ", "+");
		    	
		    	URI youtubeURI = new URI(youtubeURL); //Must be in URI form to open in browser
		    	
		    	try
		    	{
		    		Desktop.getDesktop().browse(youtubeURI);
		    		System.out.println("A search of Youtube for the song has been opened in your browser");
		    	}
		    	catch(IOException e)
		    	{
		    		System.out.println("Error occurred. Song cannot be opened in browser.");
		    	}
		    	
	    	}
	    	catch(URISyntaxException e)
	    	{
	    		System.out.println("Error occurred. Song cannot be opened in browser.");
	    	}
	    	
	    }
	    
	    
	    /**
	     * Adds a song to the playlist that is located in the specified file
	     * @param song - The song to be added
	     * @param playlist - The file of the playlist
	     * @throws IOException - If an I/O error occurs while writing the song to the file
	     */
	    public static void addToPlaylist(String song, File playlist) throws IOException
	    {
	    	try
	    	{
		    	if(!playlist.exists())
		    	{
		    		throw new FileNotFoundException("Error occurred. File could not be found.");
		    	}
		    	Scanner reader = new Scanner(playlist);
		    	
		    	//Creates a PrintWriter object that will append to, not overwrite, the data
		    	//currently in the playlist
		    	PrintWriter writer = new PrintWriter(new FileWriter(playlist, true));
		    	writer.println(song);
		    	writer.close();
	    	}
	    	
	    	catch(IOException e)
	    	{
	    		System.out.println("Error occurred. Could not add to playlist");
	    	}
		    	
	    }
	    
	    /**
	     * Reads the contents of the playlist
	     * @param playlist - The file in which the playlist resides
	     * @return - A string containing the contents of the playlist
	     * @throws IOException - If an I/O error occurs while reading the playlist
	     */
	    public static String viewPlaylist(File playlist) throws IOException
	    {
	    	try
	    	{
	    		String s = "";
	    		Scanner reader = new Scanner(playlist);
	    		if(!playlist.exists())
	    		{
	    			throw new FileNotFoundException("Error occurred. File could not be found");
	    		}
	    		while(reader.hasNextLine())
	    		{
	    			
	    			s += reader.nextLine() + "\n";
	    		}
	    		return s;
	    	}
	    	
	    	catch(IOException e)
	    	{
	    		System.out.println("Error occurred. Could not display playlist");
	    		return null;
	    	}
	    }
	    
	    /**
	     * Sets the file path of the playlist
	     * @param pathname - The path of the new playlist
	     * @throws FileNotFoundException - If a file at pathname cannot be found
	     */
	    public static void setPlaylistPath(String pathname) throws FileNotFoundException
	    {
	    	playlist = new File(pathname);
	    	if(!playlist.exists())
	    	{
	    		throw new FileNotFoundException("Error occurred. File could not be found");
	    	}
	    }
	    
	    
	    /**
	     * Prints the user guide menu
	     */
	    private static void printMenu()
	    {
	    	System.out.println("[F <year, number>] - Find the song on the Hot 100 in the specified year that placed in the specified number");
	    	System.out.println("[O] - Opens the most recent song in a browser");
	    	System.out.println("[A] - Adds the most recent song to the file residing at the specified path");
	    	System.out.println("[V] - View your playlist");
	    	System.out.println("[S] - Set/change the file in which you want your playlist to reside");
	    	System.out.println("[Q] - Quit the application");
	    	
	    }
}
	


