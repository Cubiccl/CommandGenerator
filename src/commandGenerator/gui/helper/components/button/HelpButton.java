package commandGenerator.gui.helper.components.button;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class HelpButton extends JButton implements CComponent
{

	private String message, title;

	public HelpButton(String messageArg, String titleArg)
	{
		super("?");
		this.message = messageArg;
		this.title = titleArg;
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				DisplayHelper.showHelp(message, title);
			}
		});
		setFont(new Font(getName(), Font.PLAIN, 11));
		setPreferredSize(new Dimension(40, 20));
		setMinimumSize(new Dimension(40, 20));
	}

	@Override
	public void reset()
	{}

	@Override
	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{}

	@Override
	public void updateLang()
	{}

	public void setData(String newMessage, String newTitle)
	{
		this.message = newMessage;
		this.title = newTitle;
	}

}
