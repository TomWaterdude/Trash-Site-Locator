/*
 * @author Thomas Waterman
 * ICS 440 - PA3
 */
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 * Please see documentation in TrashSiteLocationProcessor regarding 
 * issues with data in select files that is parsed in, and how these were handled
 * through out this project.
 */
public class Driver {
	
	public static void main(String[] args) {
		double userLatitude = 0;
		double userLongitude = 0;
		File fileList[] = null;

		// Prompt the user for longitude,latitude, and absolute file path of directory
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter your latitude: ");
		userLatitude = scan.nextDouble();
		
		System.out.println("Enter your longitude: ");
		userLongitude = scan.nextDouble();
		
		System.out.println("Enter the absolute pathname of your data directory: ");
		String userInput = scan.next();
		scan.close();
		System.out.println("\nprocessing...\n");
		
		// get files from users input directory
		File inputFile = new File(userInput);
		fileList = inputFile.listFiles();
		
		// kicks off program
		ExecutorService executor = Executors.newFixedThreadPool(8); 
		List<Future<ResultSet>> futureList = new ArrayList<Future<ResultSet>>();
		for (File file : fileList) {
			Callable<ResultSet> callable = new TrashSiteLocationProcessor(file, userLatitude, userLongitude);
			Future<ResultSet> future = executor.submit(callable);
			futureList.add(future);
		}
		// create 4 lists to store 8 closest sites from each file into
		ArrayList<TrashSite> resultList1 = new ArrayList<TrashSite>();
		ArrayList<TrashSite> resultList2 = new ArrayList<TrashSite>();
		ArrayList<TrashSite> resultList3 = new ArrayList<TrashSite>();
		ArrayList<TrashSite> resultList4 = new ArrayList<TrashSite>();
		// depending on thread that executed file, store into appropriate list
		for(Future<ResultSet> fut: futureList) {
			ResultSet r;
			try {
				r = fut.get();
				if (r.getList().size() > 0) {
					if (r.getThreadId() % 4 == 0) {
						resultList1.addAll(r.getList());
					} else if (r.getThreadId() % 4 == 1) {
						resultList2.addAll(r.getList());
					} else if (r.getThreadId() % 4 == 2) {
						resultList3.addAll(r.getList());
					} else {
						resultList4.addAll(r.getList());
					} 
				}
			/*
			 * Due to issues with accurate parsing on select files, and not being able to create
			 * valid trash sites from data that cannot be read properly, not all futures return a valid 
			 * ResultSet. IndexOutOfBoundsException ignores ArrayLists returned of size(0) that throw errors
			 * out of bound index when adding to lists below.
			 */
			} catch (InterruptedException | ExecutionException | IndexOutOfBoundsException e) {}
		}
		
		// combine 4 result groups into an ArrayList to iterate over
		ArrayList<ArrayList<TrashSite>> resultListArray = new ArrayList<ArrayList<TrashSite>>();		
		resultListArray.add(0, resultList1);
		resultListArray.add(1, resultList2);
		resultListArray.add(2, resultList3);
		resultListArray.add(3, resultList4);

		/*
		 * Push each array list from array list array through result list processor.
		 * The 4 array lists will be executed by 4 threads.
		 */
		ArrayList<TrashSite> finalResults = new ArrayList<TrashSite>();
		List<Future<ArrayList<TrashSite>>> finalFinalResults = new ArrayList<Future<ArrayList<TrashSite>>>();
		for(ArrayList<TrashSite> resultList : resultListArray) {
			Callable<ArrayList<TrashSite>> callable = new ResultListProcessor(resultList);
			Future<ArrayList<TrashSite>> future = executor.submit(callable);
			finalFinalResults.add(future);
		}
		for(Future<ArrayList<TrashSite>> fut : finalFinalResults) {
			ArrayList<TrashSite> sites;
			try {
				sites = fut.get();
				finalResults.addAll(sites);
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		executor.shutdown();
		
		// print 8 closest locations to user
		Collections.sort(finalResults, new TrashSiteDistanceComparer());
		for (int i = 0; i < 8; i ++) {
			System.out.println(finalResults.get(i).toString());
		}
	}
}
