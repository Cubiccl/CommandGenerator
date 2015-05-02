package commandGenerator.gui.helper.components.button;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class HelpButton extends CButton
{

	private String message, title;

	public HelpButton(String messageArg, String titleArg)
	{
		super("?");
		this.message = messageArg;
		this.title = titleArg;
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(message, title);
			}
		});
		this.setFont(new Font(this.getName(), Font.PLAIN, 11));
		this.setPreferredSize(new Dimension(20, 20));
		this.setMinimumSize(new Dimension(20, 20));
	}

	public void setData(String newMessage, String newTitle)
	{
		this.message = newMessage;
		this.title = newTitle;
	}

	public void setTitle(String title)
	{}

	@Override
	public void updateLang()
	{}

}
