import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;


public class RunGame {
	
	public static void main(String[] args)  {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your name:");
		String name = in.nextLine();
		CountryGame game = new CountryGame(name);
		game.startGame();
		
		in.close();
	}
}

class CountryGame{
	
	private final static String KM = "x10000 km^2";
	
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
			country = country.toLowerCase();

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

			capital = countryCapital(country);
			area = countryArea(country);
			population = countryPopulation(country);
			
			System.out.println("Capital: "+capital);
			System.out.println("Population: ~ "+population+ " million" );
			System.out.println("Area: ~ "+area+"x10000 km2");
		}
		System.out.println("Thanks for playing");
		in.close();

	}

	/**
	 *  Checks if the submitted String is a country 
	 *  by comparing the string to a web page that contains a list of countries.
	 *  If it finds the string then the country exist.
	 *  
	 *  <i>Another way to implement this method is to add a list of countries to
	 *  an array and check if the selected country is in the array.</i>
	 * 
	 * @param country - The name of the country submitted by user
	 * @return If the country entered exist or not.
	 */
	private boolean countryExist(String country){

		try{
			URL countryListURL = new URL("http://en.wikipedia.org/wiki/List_of_sovereign_states");
			BufferedReader in = new BufferedReader(new InputStreamReader(countryListURL.openStream()));

			String temp="";
			while(null != (temp = in.readLine())){
				if(temp.toLowerCase().contains(country)){
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
	 * @param country - The name of the country
	 * @return The capital of the entered country
	 */
	private String countryCapital(String country){
		boolean control = false;
		country = country.replace(' ','_');
		String capital="";
		try { 
			URL countryURL = new URL("http://en.wikipedia.org/wiki/"+country);
			BufferedReader br = new BufferedReader(new InputStreamReader(countryURL.openStream(),"UTF-8"));
			String strTemp = "";
			while(null != (strTemp = br.readLine())){
				if(control && strTemp.contains("title=")){
					
					capital = strTemp.replaceAll("\\<[^>]*>","");
					capital = capital.replaceAll("\\(.*?\\)","");
					capital = capital.replaceAll("\\[.*?\\]","");
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
	 * Looks for the population of the entered country
	 * @param country - The name of the country
	 * @return The population of the entered country
	 */
	private String countryPopulation(String country){
		String population="";
		country = country.replace(' ','_');
		boolean control = false;
		
		try { 
			URL countryURL = new URL("http://en.wikipedia.org/wiki/"+country);
			BufferedReader br = new BufferedReader(new InputStreamReader(countryURL.openStream()));
			String strTemp = "";
			while(null != (strTemp = br.readLine())){
				if(strTemp.contains("List of countries by population")){
					population = strTemp.replaceAll("\\<[^>]*>","");
					population = population.replaceAll("\\[.*?\\]","");
					population = population.replaceAll("\\(.*?\\)","");
					population = population.replaceAll(",","");
					population = population.replaceAll("\\.","");
					population = population.trim();
					break;
				}
//				else if(strTemp.contains("estimate")){
//					control = true;
//				}
			}
		} catch (IOException e) {
			System.out.println("Could not find country wiki page 1");
		}
		
		int populationInt = Integer.parseInt(population);
		populationInt = populationInt/1000000;
		population = String.valueOf(populationInt);
		return population;

	}

	/**
	 * Looks for the area of the entered country
	 * @param country - The name of the country
	 * @return The area of the entered country
	 */
	private String countryArea(String country){
		boolean areaControl = false;
		boolean totalControl = false;
		country = country.replace(' ','_');
		String area="";
		
		try { 
			URL countryURL = new URL("http://en.wikipedia.org/wiki/"+country);
			BufferedReader br = new BufferedReader(new InputStreamReader(countryURL.openStream()));
			String strTemp = "";
			while(null != (strTemp = br.readLine())){
				if(areaControl && totalControl && strTemp.contains("km")){
					area = strTemp.replaceAll("\\<[^>]*>","");
					area = area.replaceAll("\\[.*?\\]","");
					area = area.replaceAll("\\(.*?\\)","");
					area = area.replace("&#160;km2", "");
					area = area.replace(",", "");
					area = area.replaceAll("\\.","");
					area = area.trim();
					break;
				}else if(strTemp.contains("Total")){
					totalControl = true;
				}else if(strTemp.contains("Area")){
					areaControl = true;
				}
			}
		} catch (IOException e) {
			System.out.println("Could not find country wiki page 2");
		}
		
		int areaInt = Integer.parseInt(area);
		areaInt = areaInt/10000;
		area = String.valueOf(areaInt);
		return area;

	}

}
