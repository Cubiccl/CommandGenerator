package commandGenerator.gui.helper.components.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CKeyListener implements KeyListener
{
	private IEvent listener;

	public CKeyListener(IEvent listener)
	{
		this.listener = listener;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		this.listener.handleEvent(e, IEvent.KEY_PRESS);
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		this.listener.handleEvent(e, IEvent.KEY_RELEASE);
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		this.listener.handleEvent(e, IEvent.KEY_TYPE);
	}

}
