package LibraryManagementSystem.RentableManagement;

public class Rentable {
	
	private int sku;
	private String title;
	private String isbn;
	private String condition;
	private String genre;
	private String type;
	private String roomNumber;
	
	
	/**
	 * Constructor for creating a DVD
	 * @param sku The sku of the Rentable
	 * @param title The title of the Rentable
	 * @param condition the condition/damage of the Rentable
	 * @param genre the genre of the Rentable
	 */
	public Rentable(int sku, String title, String condition, String genre){
		this.sku = sku;
		this.title = title;
		this.condition = condition;
		this.genre = genre;
		type = "DVD";
	}
	
	/**
	 * Constructor for creating a book, or eBook
	 * @param sku The sku of the Rentable
	 * @param title The title of the Rentable
	 * @param isbn The ISBN number of the Rentable
	 * @param condition the condition/damage of the Rentable
	 * @param genre the genre of the Rentable
	 * @param type whether the Rentable is an eBook, or book
	 */
	public Rentable(int sku, String title, String isbn, String condition, String genre, String type) {
		this.sku = sku;
		this.title = title;
		this.isbn = isbn;
		this.condition = condition;
		this.genre = genre;
		this.type = type;
	}
	
	/**
	 * Constructor for creating a room
	 * @param sku The sku of the Rentable
	 * @param roomNumber The Number of the room to be added
	 */
	public Rentable(int sku, String roomNumber) {
		this.sku = sku;
		this.roomNumber = roomNumber;
		type = "Room";
	}

	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 * @return True if condition successfully changed
	 */
	public boolean setCondition(String condition) {
		this.condition = condition;
		return true;
	}

	/**
	 * @return the sku
	 */
	public int getSku() {
		return sku;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the isbn
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the roomNumber
	 */
	public String getRoomNumber() {
		return roomNumber;
	}
	
	
}
