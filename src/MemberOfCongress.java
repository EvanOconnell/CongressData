/**
 * A member of congress.
 * 
 * @author Tang
 * @version 2013.02.02
 */
public class MemberOfCongress
{
    private String lastName;
    private String firstName;
    private String state;
    private String party;

    /**
     * Constructor for objects of class MemberOfCongress
     */
    public MemberOfCongress(String lastName, String firstName, String state, String party)
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.state = state;
        this.party = party;
    }

    public String getLastName()
    {
        return lastName;
    }
    
    public String getFirstName()
    {
        return firstName;
    }

    public String getState()
    {
        return state;
    }

    public String getParty()
    {
        return party;
    }
}