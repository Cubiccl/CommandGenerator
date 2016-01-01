package generator.gui.button;

import java.awt.AWTEvent;

import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.main.Utils;

/** A button that will display a message when clicked. */
@SuppressWarnings("serial")
public class CMessageButton extends CButton implements IClickEvent
{
	private static final int CLICK = 0;

	/** The message to display. */
	private String message;
	/** The title of the popup window. */
	private String title;

	public CMessageButton()
	{
		this("", "");
	}

	/** Creates a new Message Button.
	 * 
	 * @param title - The title of the popup window.
	 * @param message - The message to display. */
	public CMessageButton(String title, String message)
	{
		super("?", false);
		this.title = title;
		this.message = message;
		this.addActionListener(new ClickEvent(this, CLICK));
	}

	@Override
	public void onEvent(int componentID, AWTEvent event)
	{
		switch (componentID)
		{
			case CLICK:
				Utils.showMessage(this.title, this.message);
				break;

			default:
				break;
		}
	}

	/** Changes the message to display.
	 * 
	 * @param message - The new message. */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/** Changes the title of the popup window.
	 * 
	 * @param title - The new title. */
	public void setTitle(String title)
	{
		this.title = title;
	}

}
