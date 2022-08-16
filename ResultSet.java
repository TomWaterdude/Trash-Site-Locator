/*
 * @author Tom Waterman
 * ICS 440 - PA 3
 */
import java.util.ArrayList;
/*
 * The ResultSet object has two variables. It contains an array list of trash sites
 * (the 8 closest sites from each file) as well as an integer that represents 
 * the thread ID of the thread that processed the ResultSet.
 */
public class ResultSet {
	long threadId;
	ArrayList<TrashSite> list;
	
	public ResultSet() {

	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long l) {
		this.threadId = l;
	}

	public ArrayList<TrashSite> getList() {
		return list;
	}

	public void setList(ArrayList<TrashSite> list) {
		this.list = list;
	}
}
