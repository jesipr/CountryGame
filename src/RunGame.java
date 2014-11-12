import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
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
	public final static String KM = "x10000 km^2";
	
	private String username;
	private String country;
	private String capital;
	private String population;
	private String area;
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
				System.out.println("You lose");
				break;
			}

			selectedCountries.add(country);
			System.out.println("Country selected "+ country);

			capital = countryCapital(country);
			area = countryArea(country);
			
			System.out.println(capital+" "+ area +" " + " Population ");
		}
		System.out.println("Thanks for playing");
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
	public String countryCapital(String country){
		boolean control = false;
		System.out.println("Looking for " +country +" capital");
		country = country.replace(' ','_');
		String capital="";
		
		try { 
			URL countryURL = new URL("http://en.wikipedia.org/wiki/"+country);
			BufferedReader br = new BufferedReader(new InputStreamReader(countryURL.openStream()));
			String strTemp = "";
			while(null != (strTemp = br.readLine())){
				if(control && strTemp.contains("title=")){
					System.out.println(strTemp);
					capital = strTemp.replaceAll("\\<[^>]*>","");
					capital = capital.trim();
					break;
				}else if(strTemp.contains("<b>Capital</b>")){
					control = true;
				}
			}
		} catch (IOException e) {
			System.out.println("Could not find country wiki page");
		}
		
		return capital;

	}

	/**
	 * @param country
	 * @return
	 */
	public String countryPopulation(String country){
		boolean areaControl = false;
		boolean totalControl = false;
		System.out.println("Looking for " +country +" population");
		country = country.replace(' ','_');
		String population="";
		
		try { 
			URL countryURL = new URL("http://en.wikipedia.org/wiki/"+country);
			BufferedReader br = new BufferedReader(new InputStreamReader(countryURL.openStream()));
			String strTemp = "";
			while(null != (strTemp = br.readLine())){
				if(areaControl && totalControl && strTemp.contains("km")){
					System.out.println(strTemp);
					population = strTemp.replaceAll("\\<[^>]*>","");
					population = population.trim();
					break;
				}else if(strTemp.contains("Total")){
					totalControl = true;
				}else if(strTemp.contains("Area")){
					areaControl = true;
				}
			}
		} catch (IOException e) {
			System.out.println("Could not find country wiki page");
		}
		return population;

	}

	/**
	 * @param country
	 * @return
	 */
	public String countryArea(String country){
		boolean areaControl = false;
		boolean totalControl = false;
		System.out.println("Looking for " +country +" population");
		country = country.replace(' ','_');
		String area="";
		
		try { 
			URL countryURL = new URL("http://en.wikipedia.org/wiki/"+country);
			BufferedReader br = new BufferedReader(new InputStreamReader(countryURL.openStream()));
			String strTemp = "";
			while(null != (strTemp = br.readLine())){
				if(areaControl && totalControl && strTemp.contains("km")){
					System.out.println(strTemp);
					area = strTemp.replaceAll("\\<[^>]*>","");
					area = area.trim();
					break;
				}else if(strTemp.contains("Total")){
					totalControl = true;
				}else if(strTemp.contains("Area")){
					areaControl = true;
				}
			}
		} catch (IOException e) {
			System.out.println("Could not find country wiki page");
		}
		return area;

	}

}
