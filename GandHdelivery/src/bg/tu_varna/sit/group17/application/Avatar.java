package bg.tu_varna.sit.group17.application;

import javafx.scene.image.Image;

/**
 * Used for updating the avatar image.
 */
public final class Avatar {
	private byte index = 0;

	private final Image[] icons = { new Image(getClass().getResourceAsStream("../img/defalut avatar.png")),
			new Image(getClass().getResourceAsStream("../img/ang.png")),
			new Image(getClass().getResourceAsStream("../img/avatar.png")) };

	/**
	 * Reset the avatar to the default one.
	 */
	public void reset() {
		index = 0;
	}

	/**
	 * Changes the avatar to the next image.
	 * 
	 * @return the next avatar image.
	 */
	public Image next() {
		if (index >= icons.length - 1)
			index = 0;
		else
			index++;
		return icons[index];
	}

	/**
	 * @return the current avatar image.
	 */
	public Image get() {
		return icons[index];
	}
}
