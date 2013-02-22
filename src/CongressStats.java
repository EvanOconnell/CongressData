import java.util.ArrayList;
import java.util.List;

/**
 * Calculate stats on members of Congress
 * 
 * @author EvanOconnell
 * @version 2013.02.21
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
    
    public static void main(String[] args){
    	CongressStats stats = new CongressStats(113);
    	
    	stats.printPartyBreakdownInSenate();
    }

    /**
     * Calculate and print the number of Democrats, Republicans, and Independents in this Senate
     */
    public void printPartyBreakdownInSenate()
    {
		String json = CongressDataFetcher.fetchSenateData(congressNum);
		List<MemberOfCongress> memlist = parseMembersOfCongress(json);
		
		System.out.println("--------------------------------------\n"+
				"There are "+memlist.size()+" members in the Senate.");
		
		int r_count = 0, d_count = 0, i_count = 0;
		
		for(MemberOfCongress mem : memlist){
			switch (mem.getParty().charAt(0)) {
				case 'R': r_count++; break;
				case 'D': d_count++; break;
				default: i_count++; break;
			}
		}
		
		System.out.println(r_count+" are in the Republican Party, "+d_count+" are Democrats and "+i_count+" are independent.");

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
