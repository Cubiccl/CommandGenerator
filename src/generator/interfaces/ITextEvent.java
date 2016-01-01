package generator.interfaces;

import java.awt.AWTEvent;

public interface ITextEvent
{

	/** Called when a sub-component is being written in.
	 * 
	 * @param eventID - The ID associated with this event.
	 * @param event - The generated event. */
	public void onEvent(int eventID, AWTEvent event);

}
