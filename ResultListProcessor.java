/*
 * @author Tom Waterman
 * ICS 440 - PA 3 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

/*
 * Class ResultListProcessor takes in an array list of type <TrashSite>
 * and returns the 8 closest TrashSite locations from the provided list. 
 * 
 * @param ArrayList<TrashSite>
 * @returns ArrayList<TrashSite>
 */
public class ResultListProcessor implements Callable<ArrayList<TrashSite>>{
	ArrayList<TrashSite> list;
	ArrayList<TrashSite> results = new ArrayList<TrashSite>();

	public ResultListProcessor (ArrayList<TrashSite> list) {
		this.list = list;
	}
	
	@Override
	public ArrayList<TrashSite> call() throws Exception {
		Collections.sort(list, new TrashSiteDistanceComparer());		
		for (int i = 0; i < 8; i ++) {
			TrashSite siteToReturn = list.get(i);
			results.add(siteToReturn);
		}
		return results;
	}
}
