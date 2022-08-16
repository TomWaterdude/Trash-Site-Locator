/*
 * @author Tom Waterman
 * ICS 440 - PA 3
 */
public class TrashSite {
	private String url;
	private String name;
	private String address;
	private double latitude;
	private double longitude;
	private double siteDistanceFromUser;

	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getDistance(double lat1, double long1) {	
		lat1 = Math.toRadians(lat1);
		long1 = Math.toRadians(long1);
		double lat2 = Math.toRadians(this.latitude);
		double long2 = Math.toRadians(this.longitude);
		return 6371 * 2 * Math.asin(Math.sqrt(Math.pow(Math.sin((lat2 - lat1) / 2), 2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin((long2 - long1) / 2), 2)));
	}

	public double getSiteDistanceFromUser() {
		return siteDistanceFromUser;
	}

	public void setSiteDistanceFromUser(double siteDistanceFromUser) {
		this.siteDistanceFromUser = siteDistanceFromUser;
	}

	@Override
	public String toString() {
		return "URL=" + url + ", name=" + name + ", address=" + address + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", distance=" + siteDistanceFromUser;
	}
}
