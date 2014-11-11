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
		System.out.println("Enter Country name");
		String country = in.nextLine();
		Scanner word;
		try {
			URL my_url = new URL("https://en.wikipedia.org/wiki/"+country);
//			BufferedReader br = new BufferedReader(new InputStreamReader(my_url.openStream()));
			word = new Scanner(new InputStreamReader(my_url.openStream()));
			String strTemp = "";
			while(null != (strTemp = word.next())){
				if(strTemp.equalsIgnoreCase("capital")){
					System.out.println(strTemp);
				}
			}
		} catch(NoSuchElementException e) {
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not find that file.");;
		} finally{
			in.close();
		}
	}

}
