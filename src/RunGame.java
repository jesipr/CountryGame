import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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

	String username;
	String country;

	public CountryGame(String name){
		System.out.println("Welcom to the Country Game "+name);
		this.username=name;
	}

	public void startGame(){
		Scanner in = new Scanner(System.in);

		while(true){
			System.out.println("Enter Country name");
			country = in.nextLine();
			country = country.replace(' ', '_');
			System.out.println("Country selected "+ country);

			try {
				URL my_url = new URL("http://en.wikipedia.org/wiki/"+country);
				BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
				//			word = new Scanner(new InputStreamReader(my_url.openStream()));
				String strTemp = "";
				while(null != (strTemp = br.readLine())){
					if(strTemp.contains("Capital"))
						System.out.println(strTemp);
				}
			} catch(NoSuchElementException e) {

			} catch (IOException e) {
				System.out.println("Could not find that file.");
			}
		}

	}
	
	public boolean checkIfExist(String country) throws IOException{
		URL countryList_url = new URL("http://www.ibiblio.org/ais/url.htm");
		BufferedReader br = new BufferedReader(new InputStreamReader(countryList_url.openStream()));
		return true;
	}

}