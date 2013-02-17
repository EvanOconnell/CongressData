import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Fetches data on members of Congress.
 * The NY Times RESTful Web API is used, via an intermediary web service.
 * 
 * @author Tang 
 * @version 2013.02.01
 */
public class CongressDataFetcher
{
    private static final String BASE_URL = "http://www.cis195p.net/congressService/congress.php";

    private CongressDataFetcher() { } // prevent instantiation

    /**
     * Fetch data on members of the Senate for the given congress number.
     * The returned string lists for each member the following data:
     * <ul>
     *  <li>last name</li>
     *  <li>first name</li>
     *  <li>state</li>
     *  <li>party</li>
     * </ul>
     * @param congressNum the number of congress, according to
     *        http://www.senate.gov/pagelayout/reference/one_item_and_teasers/Years_to_Congress.htm <br/>
     *        Valid values are 80-113
     * @return a string listing the members of the given Senate in JSON format,
     *        or null if an error occurred
     */
    public static String fetchSenateData(int congressNum)
    {
        return fetchCongressData(Chamber.SENATE, congressNum);
    }

    /**
     * Fetch data on members of the House for the given congress number.
     * The returned string lists for each member the following data:
     * <ul>
     *  <li>last name</li>
     *  <li>first name</li>
     *  <li>state</li>
     *  <li>party</li>
     * </ul>
     * @param congressNum the number of congress, according to
     *        http://www.senate.gov/pagelayout/reference/one_item_and_teasers/Years_to_Congress.htm <br/>
     *        Valid values are 102-113
     * @return a string listing the members of the given House in JSON format,
     *        or null if an error occurred
     */
    public static String fetchHouseData(int congressNum)
    {
        return fetchCongressData(Chamber.HOUSE, congressNum);
    }

    /**
     * Fetch member data for the given chamber and congress number
     */
    private static String fetchCongressData(Chamber chamber, int congressNum)
    {
        try {
            URL url = new URL(BASE_URL + "?chamber=" + chamber + "&congressNum=" + congressNum);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String result = br.readLine();
            br.close();
            return result;
        } catch (IOException e) {
            System.out.println("ERROR occured during fetch: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
