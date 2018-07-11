package id.web.jagungbakar.lollipos.techicalservices;

/**
 * Enum for name of tables in database.
 * 
 * @author Refresh Team
 *
 */
public enum DatabaseContents {
	
	DATABASE("id.web.jagungbakar.db1"),
	TABLE_PRODUCT_CATALOG("product_catalog"),
	TABLE_STOCK("stock"),
	TABLE_PRODUCT_DISCOUNT("product_discount"),
	TABLE_SALE("sale"),
	TABLE_SALE_LINEITEM("sale_lineitem"),
	TABLE_STOCK_SUM("stock_sum"),
	LANGUAGE("language"),
	CURRENCY("currency");

	private String name;
	
	/**
	 * Constructs DatabaseContents.
	 * @param name name of this content for using in database.
	 */
	private DatabaseContents(String name) {
		this.name = name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
}
