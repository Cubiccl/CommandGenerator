package generator.interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** Allows easier ActionListener managing. */
public class ClickEvent implements ActionListener
{
	private int componentID;
	private IClickEvent listener;

	/** @param listener - The actual Action Listener
	 * @param componentID - The ID associated with the component. */
	public ClickEvent(IClickEvent listener, int componentID)
	{
		this.listener = listener;
		this.componentID = componentID;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.listener.onClick(this.componentID);
	}

}
