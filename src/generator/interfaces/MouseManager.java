package generator.interfaces;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener
{
	private Component component;
	private boolean isClicked;
	private boolean isHovered;

	/** @param component - The component this manager will be added to. Needed for updates. */
	public MouseManager(Component component)
	{
		this.isClicked = false;
		this.isHovered = false;
		this.component = component;
	}

	public boolean isClicked()
	{
		return this.isClicked;
	}

	public boolean isHovered()
	{
		return this.isHovered;
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		this.isHovered = true;
		this.component.repaint();
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		this.isHovered = false;
		this.component.repaint();
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		this.isClicked = true;
		this.component.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		this.isClicked = false;
		this.component.repaint();
	}

}
