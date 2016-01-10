package generator.gui.button;

import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.main.Text;
import generator.main.Utils;

import java.awt.AWTEvent;

/** A button that will display a message when clicked. */
@SuppressWarnings("serial")
public class CMessageButton extends CButton implements IClickEvent
{
	private static final int CLICK = 0;

	/** The message to display. */
	private Text message;
	/** The title of the popup window. */
	private Text title;

	public CMessageButton()
	{
		this(new Text(""), new Text(""));
	}

	/** Creates a new Message Button.
	 * 
	 * @param title - The title of the popup window.
	 * @param message - The message to display. */
	public CMessageButton(Text title, Text message)
	{
		super(new Text("?"));
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
	public void setMessage(Text message)
	{
		this.message = message;
	}

	/** Changes the title of the popup window.
	 * 
	 * @param title - The new title. */
	public void setTitle(Text title)
	{
		this.title = title;
	}

}
