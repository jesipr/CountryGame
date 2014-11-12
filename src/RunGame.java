import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class RunGame {
	public final static String KM = "x10000 km^2";


	public static void main(String[] args)  {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your name.");
		String name = in.nextLine();
		CountryGame game = new CountryGame(name);
		game.startGame();

		in.close();
	}


}

class CountryGame{

	private String username;
	private String country;
	private ArrayList<String> selectedCountries = new ArrayList<String>();


	public CountryGame(String name){
		System.out.println("Hello "+name+", lets play Country Game!");
		this.username=name;
	}

	/**
	 * Starts the Country Game
	 */
	public void startGame(){
		Scanner in = new Scanner(System.in);

		while(true){
			System.out.println(username + " enter a country name");
			country = in.nextLine();

			if(selectedCountries.contains(country)){
				System.out.println(username + " that country was already selected");
				System.out.println("You lose");
				break;
			}
			else if(!countryExist(country)){
				System.out.println("Country does not exist.");
				break;
			}

			country = country.replace(' ', '_');
			System.out.println("Country selected "+ country);

			try {
				URL my_url = new URL("http://en.wikipedia.org/wiki/"+country);
				BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
				//			word = new Scanner(new InputStreamReader(my_url.openStream()));
				String strTemp = "";
				while(null != (strTemp = br.readLine())){
					if(strTemp.contains("<b>Capital</b>")){
						System.out.println(strTemp);
						break;
					}
				}
			} catch(NoSuchElementException e) {

			} catch (IOException e) {
				System.out.println("Could not find that file.");
			}
		}

		in.close();

	}

	/**
	 *  Checks if the submitted String is a country 
	 *  by comparing the string to a web page that contains a list of countries.
	 *  If it finds the string then the country exist.
	 * 
	 * @param country - The name of the country submitted by user
	 * @return If the country entered exist or not.
	 */
	public boolean countryExist(String country){

		try{
			URL countryListURL = new URL("http://en.wikipedia.org/wiki/List_of_sovereign_states");
			BufferedReader in = new BufferedReader(new InputStreamReader(countryListURL.openStream()));

			String temp="";
			System.out.println("Looking for country...");
			while(null != (temp = in.readLine())){
				if(temp.contains(country)){
					System.out.println("Country exist!");
					selectedCountries.add(country);
					return true;
				}
			}
		}catch(IOException e){
			System.out.println("Error in countryExist method");
		}
		return false;
	}

	/**
	 * Looks for the capital of the entered country
	 * @param country - The country selected
	 * @return The capital of the selected country
	 */
	public String countrysCapital(String country){
		String capital="";



		return capital;

	}

}
