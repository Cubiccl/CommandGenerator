package commandGenerator.gui.helper.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickListener implements ActionListener
{
	private IEvent listener;

	public ClickListener(IEvent listener)
	{
		this.listener = listener;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.listener.handleEvent(e, IEvent.CLICK_EVENT);
	}

}
