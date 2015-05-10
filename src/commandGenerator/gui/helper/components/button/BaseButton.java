package commandGenerator.gui.helper.components.button;

import java.awt.AWTEvent;
import java.awt.Dimension;

import javax.swing.JButton;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.listeners.CMouseListener;
import commandGenerator.gui.helper.components.listeners.IEvent;

@SuppressWarnings("serial")
public class BaseButton extends JButton implements CComponent, IEvent
{
	public static final int DEFAULT_HEIGHT = 20, DEFAULT_WIDTH = 200;
	private boolean isHovered, isClicked;

	public BaseButton(String text)
	{
		super(text);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.isHovered = false;
		this.isClicked = false;
		this.addMouseListener(new CMouseListener(this));
	}

	@Override
	public void reset()
	{}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
	}

	@Override
	public void updateLang()
	{}

	@Override
	public void setSize(int width, int height)
	{
		this.setPreferredSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));
	}

	@Override
	public void handleEvent(AWTEvent event, int eventID)
	{
		switch (eventID)
		{
			case IEvent.MOUSE_ENTER:
				this.isHovered = true;
				break;
			case IEvent.MOUSE_EXIT:
				this.isHovered = false;
				break;
			case IEvent.MOUSE_PRESS:
				this.isClicked = true;
				break;
			case IEvent.MOUSE_RELEASE:
				this.isClicked = false;
				break;

			default:
				break;
		}
	}
}
