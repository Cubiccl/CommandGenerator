package generator.interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Allows easier ActionListener managing. */
public class ClickEvent implements ActionListener
{
	private int eventID;
	private IClickEvent listener;

	/** @param listener - The actual Action Listener
	 * @param eventID - The ID associated with the event. */
	public ClickEvent(IClickEvent listener, int eventID)
	{
		this.listener = listener;
		this.eventID = eventID;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.listener.onEvent(this.eventID, e);
	}

}
