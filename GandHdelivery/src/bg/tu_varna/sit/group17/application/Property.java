package bg.tu_varna.sit.group17.application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;

import bg.tu_varna.sit.group17.database.Create;
import bg.tu_varna.sit.group17.database.TableQuery;
import javafx.scene.image.Image;

public final class Property {
	private Property() {
		//utility
	}
	public static HashMap<String, Integer> citiesMap;
	public static HashMap<String, Integer> companiesMap;
	private static final int[] statusesId = {1, 2, 3, 4, 5};
	//1 неполучен 2 отказана 3 получена 4 взета 5 отказана за постоянно
	public static String username = "";
	public static User user = User.Guest;//1 customer 2 courier 3 admin
	private static byte avatarIndex = 0;
	public static final Image[] avatars = {
			new Image(Property.class.getResourceAsStream("../img/defalut avatar.png")),
			new Image(Property.class.getResourceAsStream("../img/ang.png")),
			new Image(Property.class.getResourceAsStream("../img/avatar.png"))
	};
	public static LinkedList<String> alertNotificationList = new LinkedList<>();
	public static LinkedList<Integer> ordersIdNotification = new LinkedList<>();
	public static boolean delivered = false;
	
	public static final String izv = "-fx-background-color: black;"
			+ "-fx-background-image: url("+Property.class.getResource("../img/izv.png").toExternalForm()+");"
			+ "	-fx-background-position:  center, center;"
			+ "	-fx-background-repeat: no-repeat;"
			+ "	-fx-background-size: 70% 90%;"
			+ "	-fx-border-radius: 50;";
	public static final String izv2 = "-fx-background-color: black;"
			+ "-fx-background-image: url("+Property.class.getResource("../img/izv2.png").toExternalForm()+");"
			+ "	-fx-background-position:  center, center;"
			+ "	-fx-background-repeat: no-repeat;"
			+ "	-fx-background-size: 70% 90%;"
			+ "	-fx-border-radius: 50;";
	
	public static int getStatus(int status) throws IllegalArgumentException {
		if(status < 0 || status > statusesId.length-1) 
			throw new IllegalArgumentException("Wrong status");
		return statusesId[status];
	}
	
	public static void initAll() throws SQLException {
		initCityMap();
		initCompaniesMap();
	}
	public static void resetAvatar() {
		avatarIndex = 0;
	}
	
	public static Image nextAvatar() {
		if(avatarIndex >= avatars.length-1) avatarIndex = 0;
		else avatarIndex++;
		return avatars[avatarIndex];
	}
	public static Image getAvatar() {
		return avatars[avatarIndex];
	}
	
	public static void initCityMap() throws SQLException {
		
		ResultSet rs = TableQuery.execute("select * from cities");
		
		Property.citiesMap = new HashMap<String, Integer>();
		if(rs == null) return;
		do {
			Property.citiesMap.put(rs.getString("city"),
					Integer.parseInt(rs.getString("id_city")));			
		}
		while(rs.next());

	}
	
	public static void initCompaniesMap() throws SQLException {
		
		Connection con = Create.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from companies");
		
		companiesMap = new HashMap<String, Integer>();
		
		while(rs.next()) 
			companiesMap.put(rs.getString(2),
						Integer.parseInt(rs.getString(1)));
	}
}
