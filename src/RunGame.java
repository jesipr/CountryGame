import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


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

	private ArrayList<String> countryList = new ArrayList<String>();
	private ArrayList<String> selectedCountries = new ArrayList<String>();
	private String username;
	private String country;
	private String capital;
	private String population;
	private String area;
	private int correctAnswer = 0;
	private String userAnswer;



	public CountryGame(String name){
		System.out.println("Hello "+name+", lets play Country Game!");
		this.username=name;
	}

	/**
	 * Starts the Country Game
	 */
	public void startGame(){
		Scanner in = new Scanner(System.in);
		initCountryList();

		while(correctAnswer<10){
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


			country = computerAnswer();
			selectedCountries.add(country);
			System.out.println("Enter info for " + country);
			userAnswer = in.nextLine(); //Suppose to be in less than 15 seconds,
			if(!correctInfo(country,userAnswer)){
				System.out.println("Wrong input! You lost. Game over. By the way, the right answer is");

				capital = countryCapital(country);
				area = countryArea(country);
				population = countryPopulation(country);

				System.out.println("Capital: "+capital);
				System.out.println("Population: ~ "+population+ " million" );
				System.out.println("Area: ~ "+area+"x10000 km2");
				break;

			}else{
				System.out.println("Very good!");
			}
			correctAnswer++;
			if(correctAnswer==10){
				System.out.println("You have won!");
			}



		}
		System.out.println("Thanks for playing");
		in.close();

	}

	/**
	 *  Checks if the submitted String is a country 
	 *  by comparing the string to an array and check if the selected 
	 *  country is in the array.
	 * 
	 * @param country - The name of the country submitted by user
	 * @return If the country entered exist or not.
	 */
	private boolean countryExist(String country){
		if(countryList.contains(country)){
			return true;
		}
		return false;
	}

	/**
	 * Looks for the capital of the entered country
	 * <b>(precondition: Wikipedia must redirect contry names with lowercases to the correct wikipage) </b>
	 * @param country - The name of the country
	 * @return The capital of the entered country
	 */
	private String countryCapital(String country){
		boolean control = false;
		country = country.replaceAll(" ", "_");
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
					capital = capital.replaceAll("\\&.*?\\;","");
					capital = capital.trim();
					break;
				}else if(strTemp.contains("<b>Capital</b>")){
					control = true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not find country wiki page");
		}
		return capital;
	}

	/**
	 * Looks for the population of the entered country
	 * 
	 * <b>(precondition: Wikipedia must redirect contry names with lowercases to the correct wikipage) </b>
	 * @param country - The name of the country
	 * @return The population of the entered country
	 */
	private String countryPopulation(String country){
		String population="";
		country = country.replace(' ','_');

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
	 * <b>(precondition: Wikipedia must redirect contry names with lowercases to the correct wikipage) </b>
	 * 
	 * @param country - The name of the country
	 * @return The area of the entered country
	 * 
	 * (precondition: Wikipedia must redirect contry names with lowercases to the correct wikipage) 
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
		double areaReal = Double.parseDouble(area);
		int areaInt = (int) areaReal;
		areaInt = areaInt/10000;
		area = String.valueOf(areaInt);
		return area;

	}

	/**
	 * Initiate a country ArrayList
	 * @throws IOException 
	 */
	private void initCountryList(){
		try{
			System.out.println("Looking for countries...");
			HttpResponse<JsonNode> response = Unirest.get("https://restcountries-v1.p.mashape.com/all").header("X-Mashape-Key", "xJrFtTxyDPmsh54w4upwKJMv4Fxzp1MJunXjsnFO2BzUfq2GJK").asJson();
			JSONArray a = response.getBody().getArray();
			for(int i = 0; i<a.length();i++){
				JSONObject rec = a.getJSONObject(i);
				countryList.add(rec.getString("name").toLowerCase());
			}
			Unirest.shutdown();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * Selects a random unselected country from the list of countries
	 * @return - The selected country
	 */
	private String computerAnswer(){
		Random rand = new Random();
		String computerSelect="";
		int index;
		do{
			index = rand.nextInt(countryList.size());
			computerSelect = countryList.get(index);
		}while(selectedCountries.contains(computerSelect));
		return computerSelect;
	}

	/**
	 * Verifies if the entered info is the correct info of the country.
	 * @param country - County selected by the computer
	 * @param info - Info entered by the user.
	 * @return
	 */
	private boolean correctInfo(String country, String info){
		String capital;
		String population;
		String area;
		String comparisonString;

		capital = countryCapital(country);
		population = countryPopulation(country);
		area = countryArea(country);
		comparisonString = capital+" " +population+"000000 "+area+"0000";
		if(info.equalsIgnoreCase(comparisonString)){
			return true;
		}
		return false;

	}

}
