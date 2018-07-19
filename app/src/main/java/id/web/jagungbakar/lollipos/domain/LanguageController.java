package id.web.jagungbakar.lollipos.domain;

import java.util.List;

import android.content.ContentValues;

import id.web.jagungbakar.lollipos.techicalservices.Database;
import id.web.jagungbakar.lollipos.techicalservices.DatabaseContents;

/**
 * Saves and loads language preference from database.
 *
 *
 */
public class LanguageController {
	
	private static final String DEFAULT_LANGUAGE = "en";
	private static Database database;
	private static LanguageController instance;
	
	private LanguageController() {
		
	}
	
	public static LanguageController getInstance() {
		if (instance == null)
			instance = new LanguageController();
		return instance;
	}
	
	/**
	 * Sets database for use in this class.
	 * @param db database. 
	 */
	public static void setDatabase(Database db) {
		database = db;
	}
	
	/**
	 * Sets language for use in application.
	 * @param localeString local string of country.
	 */
	public void setLanguage(String localeString) {
		database.execute("UPDATE " + DatabaseContents.LANGUAGE + " SET language = '" + localeString + "'");
		//database.execute("DELETE FROM " + DatabaseContents.TABLE_SALE_LINEITEM);
		//database.execute("DELETE FROM " + DatabaseContents.TABLE_SALE);
		/*database.execute("DROP TABLE " + DatabaseContents.TABLE_SALE);
		database.execute("CREATE TABLE "+ DatabaseContents.TABLE_SALE + "("

				+ "_id INTEGER PRIMARY KEY,"
				+ "status TEXT(40),"
				+ "payment TEXT(50),"
				+ "total DOUBLE,"
				+ "start_time DATETIME,"
				+ "end_time DATETIME,"
				+ "customer_id INTEGER,"
				+ "orders INTEGER"

				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_SALE + " Successfully.");
		/*database.execute("CREATE TABLE " + DatabaseContents.TABLE_CUSTOMER + "("

				+ "_id INTEGER PRIMARY KEY,"
				+ "name TEXT(100),"
				+ "email TEXT(100),"
				+ "phone TEXT(20),"
				+ "address TEXT(256),"
				+ "status INTEGER,"
				+ "date_added DATETIME"

				+ ");");
		Log.d("CREATE DATABASE", "Create " + DatabaseContents.TABLE_CUSTOMER + " Successfully.");*/
	}
	
	/**
	 * Returns current language. 
	 * @return current language.
	 */
	public String getLanguage() {
		List<Object> contents = database.select("SELECT * FROM " + DatabaseContents.LANGUAGE);

		if (contents.isEmpty()) {
			ContentValues defualtLang = new ContentValues();
			defualtLang.put("language", DEFAULT_LANGUAGE);
			database.insert( DatabaseContents.LANGUAGE.toString(), defualtLang);
	
			return DEFAULT_LANGUAGE;
		}

		ContentValues content = (ContentValues) contents.get(0);
		return content.getAsString("language");	
	}

	public Object getLanguages() {
		List<Object> contents = database.select("SELECT * FROM " + DatabaseContents.LANGUAGE);

		return contents;
	}
}
