import java.util.ArrayList;

/**
 * Calculate stats on members of Congress
 * 
 * @author Tang
 * @version 2013.02.02
 */
public class CongressStats
{
    private int congressNum;

    /**
     * Create a CongressStats object for the given congress number
     */
    public CongressStats(int congressNum)
    {
        this.congressNum = congressNum;
    }

    /**
     * Calculate and print the number of Democrats, Republicans, and Independents in this Senate
     */
    public void printPartyBreakdownInSenate()
    {
        // Call fetchSenateData in CongressDataFetcher class to fetch data in the form of a JSON string

        
        // Call parseMembersOfCongress passing it the JSON string

        
        // Print a header message giving the number of members in this chamber in this congress number

        
        // Calculate and then print the number of democrats (party is "D"),
        //   republicans (party is "R"), and independents (party is anything else).
        // You will need to loop over the MemberOfCongress ArrayList returned by parseMembersOfCongress
        //   and count up how many of each party type there are.
        // Hint: Use 3 local variables, one for each party type, that are initialized to 0.
        //   In your loop check which party the current member belongs to and increment the proper variable.
        //   After the loop completes, print the totals

    }

    /**
     * Calculate and print the number of Democrats, Republicans, and Independents in this House
     */
    public void printPartyBreakdownInHouse()
    {
        // This method will be similar to printPartyBreakdownInSenate, but you will call
        // fetchHouseData in CongressDataFetcher to fetch the data
        
        // Hint: if you end up with a lot of duplicate code, think of using the Extract Method refactoring
    }

    // Don't forget to add a third method of your choice
    
    /**
     * @param jsonString a string listing members of congress in JSON format
     * @return an ArrayList of MemberOfCongress objects parsed from the given JSON string
     */
    private ArrayList<MemberOfCongress> parseMembersOfCongress(String jsonString)
    {
        ArrayList<MemberOfCongress> members = new ArrayList<MemberOfCongress>();
        String[] memberStrings = jsonString.split("\\},\\{");
        for (String memberStr : memberStrings) {
            members.add(parseMember(memberStr));
        }

        return members;
    }

    /**
     * @param member a JSON string for one member of congress, which looks something like this:
     *              "last_name":"Ayotte","first_name":"Kelly","state":"NH","party":"R"
     *              but might have [{ or }] as well
     * @return a MemberOfCongress object parsed from the given JSON string
     */
    private MemberOfCongress parseMember(String memberString)
    {
        // Annoyingly, String.split does not remove leading empty strings
        // The fields from a correctly formatted string will split as follows:
        // fields[0] = ""
        // fields[1] = "last_name"
        // fields[2] = (member's last name)
        // fields[3] = "first_name"
        // fields[4] = (member's first name)
        // fields[5] = "state"
        // fields[6] = (member's state)
        // fields[7] = "party"
        // fields[8] = (member's party)
        String[] fields = memberString.split("[\\[\\{\\]\\},:\"]+");
        if (fields.length < 9) {
            throw new java.util.InputMismatchException("JSON string for member of congress in wrong format");
        }
        return new MemberOfCongress(fields[2], fields[4], fields[6], fields[8]);
    }
}
