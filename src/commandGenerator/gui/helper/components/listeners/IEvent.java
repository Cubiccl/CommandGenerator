package commandGenerator.gui.helper.components.listeners;

import java.awt.AWTEvent;

public interface IEvent
{
	public static final int MOUSE_CLICK = 0, MOUSE_ENTER = 1, MOUSE_EXIT = 2, MOUSE_PRESS = 3, MOUSE_RELEASE = 4, CLICK_EVENT = 5, KEY_PRESS = 6, KEY_TYPE = 7,
			KEY_RELEASE = 8;

	public void handleEvent(AWTEvent event, int eventID);

}
