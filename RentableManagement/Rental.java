package LibraryManagementSystem.RentableManagement;

import java.util.Date;

public class Rental {
	private Date startDate;
	private Date endDate;
	private int renewLimit;
	private int SKU;
	private String userId;
	
	/**
	 * Creates a rental with a set start and end date
	 * @param SKU The SKU of the Rentable being rented out
	 * @param start When the Rental is created
	 * @param end When the Rentable will need to be returned
	 */
	Rental(int SKU, Date start, Date end){
		this.SKU = SKU;
		this.startDate = start;
		this.endDate = end;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @return the sKU
	 */
	public int getSKU() {
		return SKU;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	
	
}
