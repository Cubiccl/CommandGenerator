package commandGenerator.gui.helper.components.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CMouseListener implements MouseListener
{
	
	private IEvent listener;

	public CMouseListener(IEvent listener)
	{
		super();
		this.listener = listener;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		this.listener.handleEvent(e, IEvent.MOUSE_CLICK);
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		this.listener.handleEvent(e, IEvent.MOUSE_ENTER);
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		this.listener.handleEvent(e, IEvent.MOUSE_EXIT);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		this.listener.handleEvent(e, IEvent.MOUSE_PRESS);
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		this.listener.handleEvent(e, IEvent.MOUSE_RELEASE);
	}

}
