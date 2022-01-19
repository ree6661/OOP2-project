package bg.tu_varna.sit.group17.application;

import java.util.LinkedList;

public final class Notification {
	private Notification() {
		//utility
	}
	private static final int[] statusesId = {1, 2, 3, 4, 5};
	//1 неполучен 2 отказана 3 получена 4 взета 5 отказана за постоянно
	
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
	
	public static LinkedList<String> alertNotificationList = new LinkedList<>();
	public static LinkedList<Integer> ordersIdNotification = new LinkedList<>();
	public static boolean delivered = false;
	
	public static int getStatus(int status) throws IllegalArgumentException {
		if(status < 0 || status > statusesId.length-1) 
			throw new IllegalArgumentException("Wrong status");
		
		return statusesId[status];
	}
}
