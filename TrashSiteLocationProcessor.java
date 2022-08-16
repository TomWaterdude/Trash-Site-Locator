
/*
 * @author Tom Waterman
 * ICS 440 - PA 3
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

/*
 * Class TrashSiteLocationProcessor reads in a file, and two values of type double, userLatitude
 * and userLongitude. It then parses through each row of the file, and creates an object of type
 * TrashSite with from the values read from certain columns of each row. 
 * 
 * The distance of the TrashSite created from the parse is then calculated using the 
 * userLongitude and userLatitude passed in. The 8 closest sites from the file to the user's location are
 * added stored to an object of type ResultSet.
 * 
 * @resultSet is an object that holds an ArrayList<TrashSite>, and and integer representation
 * of the thread that processed the object.
 * 
 * There is extensive error checking (that may seem redundant, or overkill) but is necessary as 
 * through extensive testing against the Epa_Facitlity_Data directory, at least 16 files cannot
 * parse in correctly and create issues within the data. The error checking is in attempt to 
 * ignore these files. Please note, that I tested both .split() arguments below, and both had
 * issues with files read in. In fact, .split(",") seems to yield a larger sum of usable results
 * than .split( "\"(,\")?"); - Issue seems to be quotes and commas that are still not removed.
 * 
 * @param file, userLatitude, userLongitude
 * @returns resultSet
 * 
 */
public class TrashSiteLocationProcessor implements Callable<ResultSet> {
	File file;
	double userLatitude;
	double userLongitude;
	ArrayList<TrashSite> results = new ArrayList<TrashSite>();
	ArrayList<TrashSite> trashSiteList = new ArrayList<TrashSite>();
	ResultSet resultSet = new ResultSet();

	public TrashSiteLocationProcessor(File file, double userLatitude, double userLongitude) {
		this.file = file;
		this.userLatitude = userLatitude;
		this.userLongitude = userLatitude;
	}

	public ResultSet call() {
		String line = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {

				/*
				 * It does not seem to matter which split() argument is passed, during testing I
				 * found 16 files from the Epa_Facility_Data that do not parse in correctly.
				 */
				String[] fields = line.split(",");
				// String[] fields = line.split( "\"(,\")?");

				
				/*
				 * Sets values from parsed in fields into a new TrashSite.
				 * Includes error checking to ensure that array trash site is setting field values to is valid
				 * troublesome files did not generate arrays of expected length, so TrashSites are only created
				 * from arrays of the expected full length of parsed in rows.
				 */
				TrashSite trashSite = new TrashSite();
				if (fields.length > 28) {
					trashSite.setUrl(fields[0].replaceAll("^\"|\"$", ""));
					trashSite.setName(fields[1].replaceAll("^\"|\"$", ""));
					trashSite.setAddress(fields[2].replaceAll("^\"|\"$", ""));
					try {
						trashSite.setLatitude(Double.parseDouble(fields[27].replaceAll("^\"|\"$", "")));
						 } catch (NumberFormatException e) { }
					try {
						trashSite.setLongitude(Double.parseDouble(fields[28].replaceAll("^\"|\"$", "")));
						 } catch (NumberFormatException e) { }
					double distance = trashSite.getDistance(userLatitude, userLongitude);
					trashSite.setSiteDistanceFromUser(distance);
					trashSiteList.add(trashSite);
				}
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Sort the trash sites in file from least to greatest distance from user
		Collections.sort(trashSiteList, new TrashSiteDistanceComparer());
		// Add 8 closest sites to results list now that collection is organized
		for (int i = 0; i < 8; i++) {
			TrashSite siteToReturn;
			if (trashSiteList.get(i) != null) {
				siteToReturn = trashSiteList.get(i);
				results.add(siteToReturn);
			}
		}
		resultSet.setThreadId(Thread.currentThread().getId());
		resultSet.setList(results);
		return resultSet;
	}
}
