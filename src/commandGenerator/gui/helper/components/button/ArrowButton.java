package commandGenerator.gui.helper.components.button;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.plaf.basic.BasicArrowButton;

import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.listeners.CMouseListener;
import commandGenerator.gui.helper.components.listeners.IEvent;

@SuppressWarnings("serial")
public class ArrowButton extends BasicArrowButton implements IEvent
{
	private boolean isHovered, isClicked;

	public ArrowButton(int direction)
	{
		super(direction);
		this.setSize(22, 12);
		this.addMouseListener(new CMouseListener(this));
	}

	@Override
	public void setSize(int width, int height)
	{
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
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

	@Override
	public void paintComponent(Graphics g) // HERE FOR DISPLAY use paint()
	{
		int width = this.getWidth() - 1;
		int height = this.getHeight() - 1;
		this.setComponentColor(g);

		GuiHandler.clear(g, width, height);

		GuiHandler.drawComponent(g, width, height, this.getBackground(), GuiHandler.FULL);
		GuiHandler.drawBorder(g, width, height, GuiHandler.BORDER, GuiHandler.FULL);

		GuiHandler.drawString(g, this.getText(), width, height, this.getForeground(), this.getFont());
	}

	private void setComponentColor(Graphics g)
	{
		this.setBackground(GuiHandler.DEFAULT_COMPONENT);
		if (this.isHovered) this.setBackground(GuiHandler.HOVERED);
		if (this.isClicked) this.setBackground(GuiHandler.CLICKED);
	}

	@Override
	public void paintBorder(Graphics g)
	{}

}
