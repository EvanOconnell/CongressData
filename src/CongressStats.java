import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

/**
 * Calculate stats on members of Congress
 * 
 * @author EvanOconnell
 * @version 2013.02.21
 * 
 * Commits can be seen at http://github.com/EvanOconnell/CongressData
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
    	
    	System.out.println("Info for 113th congress...\n");
    	stats.printPartyBreakdownInSenate();
    	stats.printPartyBreakdownInHouse();
    	stats.printMemberListStateBreakdown(Chamber.SENATE);
    }

    /**
     * Calculate and print the number of Democrats, Republicans, and Independents in this Senate
     */
    public void printPartyBreakdownInSenate()
    {
		List<MemberOfCongress> memlist = parseMembersOfCongress(CongressDataFetcher.fetchSenateData(congressNum));
		
		System.out.println("-----------SENATE INFO-----------\n\n"+
				"There are "+memlist.size()+" members in the Senate.");
		printMemberListPartyBreakdown(memlist);
    }
    
    /**
     * Prints out summary of party info on members of this chamber
     * 
     * @param memlist List of members in Chamber of congress
     */
    private void printMemberListPartyBreakdown(List<MemberOfCongress> memlist){
    	int r_count = 0, d_count = 0, i_count = 0;
		
		for(MemberOfCongress mem : memlist){
			switch (mem.getParty().charAt(0)) {
				case 'R': r_count++; break;
				case 'D': d_count++; break;
				default: i_count++; break;
			}
		}
		
		System.out.println(r_count+" are in the Republican Party, "+d_count+" are Democrats and "+i_count+" are independent.\n");
    }
    
    /**
     * Prints out summary of state info on members of this chamber
     * 
     * @param memlist List of members in Chamber of congress
     */
    private void printMemberListStateBreakdown(Chamber chamber){
		List<MemberOfCongress> memlist = parseMembersOfCongress(CongressDataFetcher.fetchCongressData(chamber, congressNum));
    	
    	HashMap<String, Integer> state_list = new HashMap<String, Integer>();
    	
		for(MemberOfCongress mem : memlist){
			String state = mem.getState();
			if(state_list.containsKey(state)){
				int new_val = state_list.get(state)+1;
				state_list.put(state, new_val);
			} else {
				state_list.put(state, 1);
			}
		}
		
		System.out.println("------MEMBER'S STATE INFO------\n\nThere are:");
		printMemberListPartyBreakdown(memlist);
		for(Iterator<Entry<String, Integer>> iterator = state_list.entrySet().iterator(); iterator.hasNext();){
			Entry<String, Integer> entry = iterator.next();
			System.out.print(entry.getValue()+" in the state "+entry.getKey());
		}
    }

    /**
     * Calculate and print the number of Democrats, Republicans, and Independents in this House
     */
    public void printPartyBreakdownInHouse()
    {
    	List<MemberOfCongress> memlist = parseMembersOfCongress(CongressDataFetcher.fetchHouseData(congressNum));
		
		System.out.println("-----------HOUSE INFO-----------\n\n"+
				"There are "+memlist.size()+" members in the House.");
		printMemberListPartyBreakdown(memlist);
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
