package commandGenerator.gui.helper.components.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickListener implements ActionListener
{
	private IClick component;

	public ClickListener(IClick component)
	{
		this.component = component;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.component.click();
	}

}
