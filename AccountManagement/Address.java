package LibraryManagementSystem.AccountManagement;

/**
 * Contains user’s address including: Street Number, Street Name, City, State,
 * and Zip Code
 * 
 * @author Kwesi Quallis
 * @version 1.0
 */

public class  Address {
	private String streetNum;
	private String streetName;
	private String city;
	private String state;
	private String zipCode;

		/**
	 * 
	 * @param streetNum
	 *            House number of user/staff to be set
	 * @param streetName
	 *            Name of the street of user/staff that is to be set
	 * @param city
	 *            Name of the city of user/staff that is to be set
	 * @param state
	 *            Name of state of user/staff that is to be set
	 * @param zipCode
	 *            Five digit number which indicates zipcode of user that is to be
	 *            set
	 * 
	 * @author Kyle Coltellino
	 */
	public Address(String streetNum, String streetName, String city, String state, String zipCode) {
		super();
		this.streetNum = streetNum;
		this.streetName = streetName;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	


	/**
	 * @return streetNum
	 */
	
	public String getStreetNum() {
		return streetNum;
	}
	
	/**
	 * @param String streetNum to set
	 */
	
	public void setStreetNum(String streetNum) {
		
		this.streetNum = streetNum;
	}
	
	/**
	 * @return streetName
	 */
	
	public String getStreetName() {
		return streetName;
	}
	
	/**
	 * @param String streetName to set
	 */
	
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	
	/**
	 * @return city
	 */
	
	public String getCity() {
		return city;
	}
	
	/**
	 * @param String city to set
	 */
	
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * @return state
	 */
	
	public String getState() {
		return state;
	}
	
	/**
	 * @param String state to set
	 */
	
	public void setState(String state) {
		this.state = state;
	}
	
	/**
	 * @return zipCode
	 */
	
	public String getZipCode() {
		return zipCode;
	}
	
	/**
	 * @param String zipCode to set
	 */
	
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	@Override
	public String toString() {
		return "Address [streetNum=" + streetNum + ", streetName=" + streetName + ", city=" + city + ", state=" + state
				+ ", zipCode=" + zipCode + "]";
	}
	
}
