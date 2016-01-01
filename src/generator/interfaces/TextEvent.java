package generator.interfaces;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Allows easier KeyListener managing. */
public class TextEvent implements KeyListener
{
	private int eventID;
	private ITextEvent listener;

	/** @param listener - The actual Key Listener
	 * @param eventID - The ID associated with the event. */
	public TextEvent(ITextEvent listener, int eventID)
	{
		this.listener = listener;
		this.eventID = eventID;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (this.listener != null) this.listener.onEvent(eventID, e);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{}

}
