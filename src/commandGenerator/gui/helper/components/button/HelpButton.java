package commandGenerator.gui.helper.components.button;

import java.awt.Font;

import commandGenerator.gui.helper.components.listeners.ClickListener;
import commandGenerator.gui.helper.components.listeners.IClick;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class HelpButton extends BaseButton implements IClick
{

	private String message, title;

	public HelpButton()
	{
		super("?");
		this.message = "";
		this.title = "";
		this.addActionListener(new ClickListener(this));
		this.setFont(new Font(this.getName(), Font.PLAIN, 11));
		this.setSize(40, 20);
	}

	public void setData(String newMessage, String newTitle)
	{
		this.message = newMessage;
		this.title = newTitle;
	}

	@Override
	public void click()
	{
		DisplayHelper.showHelp(this.message, this.title);
	}

}
