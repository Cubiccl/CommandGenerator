package commandGenerator.gui.helper.components.button;

import java.awt.AWTEvent;

import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.listeners.ClickListener;
import commandGenerator.gui.helper.components.listeners.IEvent;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class HelpButton extends BaseButton implements IEvent
{

	private String message, title;

	public HelpButton()
	{
		super("?");
		this.message = "";
		this.title = "";
		this.addActionListener(new ClickListener(this));
		this.setSize(20, 20);
		this.setDrawType(GuiHandler.LEFT);
	}

	public void setData(String newMessage, String newTitle)
	{
		this.message = newMessage;
		this.title = newTitle;
	}

	@Override
	public void handleEvent(AWTEvent e, int eventID)
	{
		super.handleEvent(e, eventID);
		if (eventID != IEvent.CLICK_EVENT) return;
		if (!(this.message.equals("") && this.title.equals(""))) DisplayHelper.showHelp(this.message, this.title);
	}

}
