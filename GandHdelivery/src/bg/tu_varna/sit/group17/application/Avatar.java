package bg.tu_varna.sit.group17.application;

import javafx.scene.image.Image;

public final class Avatar {
	private byte index = 0;
	
	private final Image[] icons = {
			new Image(getClass().getResourceAsStream("../img/defalut avatar.png")),
			new Image(getClass().getResourceAsStream("../img/ang.png")),
			new Image(getClass().getResourceAsStream("../img/avatar.png"))
	};
	
	public void reset() {
		index = 0;
	}
	public Image next() {
		if(index >= icons.length-1) index = 0;
		else index++;
		return icons[index];
	}
	public Image get() {
		return icons[index];
	}
}
