/*
 * @author Tom Waterman
 * ICS 440 - PA 3
 */
import java.util.Comparator;

/* TrashSiteDistanceComparer is used to sort any collection of 
 * type TrashSite, by the distance of the site from user, from 
 * least to greatest.
 */
public class TrashSiteDistanceComparer implements Comparator<TrashSite>{

	@Override
	public int compare(TrashSite site1, TrashSite site2) {
		if(site2.getSiteDistanceFromUser() - site1.getSiteDistanceFromUser() < 0) {
			return 1;
		} else if (site2.getSiteDistanceFromUser() - site1.getSiteDistanceFromUser() > 0) {
			return - 1;
		} else {
			return 0;
		}
	}
}
