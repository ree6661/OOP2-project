package bg.tu_varna.sit.group17.application;

import javafx.scene.image.Image;

public final class Avatar {
	private static byte index = 0;
	
	private Avatar() {
		//utility
	
	}
	public static final Image[] icons = {
			new Image(Property.class.getResourceAsStream("../img/defalut avatar.png")),
			new Image(Property.class.getResourceAsStream("../img/ang.png")),
			new Image(Property.class.getResourceAsStream("../img/avatar.png"))
	};
	
	public static void reset() {
		index = 0;
	}
	public static Image next() {
		if(index >= icons.length-1) index = 0;
		else index++;
		return icons[index];
	}
	public static Image get() {
		return icons[index];
	}
}
