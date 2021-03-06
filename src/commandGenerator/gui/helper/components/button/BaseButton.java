package commandGenerator.gui.helper.components.button;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;

import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.listeners.CMouseListener;
import commandGenerator.gui.helper.components.listeners.IEvent;

@SuppressWarnings("serial")
public class BaseButton extends JButton implements CComponent, IEvent
{
	public static final int DEFAULT_HEIGHT = 20, DEFAULT_WIDTH = 200;
	private boolean isHovered, isClicked;
	private int drawType;
	private Color borderColor;

	public BaseButton(String text)
	{
		super(text);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.isHovered = false;
		this.isClicked = false;
		this.drawType = GuiHandler.DEFAULT;
		this.addMouseListener(new CMouseListener(this));
		this.setFont(GuiHandler.DEFAULT_FONT);
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

	@Override
	public void paintComponent(Graphics g)
	{
		int width = this.getWidth() - 1;
		int height = this.getHeight() - 1;
		this.setComponentColor();

		GuiHandler.clear(g, width, height);

		GuiHandler.drawComponent(g, width, height, this.getBackground(), this.drawType);
		GuiHandler.drawBorder(g, width, height, this.getBorderColor(), this.drawType);

		GuiHandler.drawString(g, this.getText(), width, height, this.getForeground(), this.getFont());
	}

	public Color getBorderColor()
	{
		return this.borderColor;
	}

	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
	}

	private void setComponentColor()
	{
		this.setBackground(GuiHandler.DEFAULT_COMPONENT);
		if (this instanceof HelpButton) this.setBackground(GuiHandler.HELP);
		if (this.isHovered) this.setBackground(GuiHandler.HOVERED);
		if (this.isClicked) this.setBackground(GuiHandler.CLICKED);
		if (!this.isEnabled())
		{
			this.setBackground(GuiHandler.DISABLED);
			this.setBorderColor(GuiHandler.DISABLED_BORDER);
			this.setForeground(GuiHandler.DISABLED_BORDER);
		} else
		{
			this.setBorderColor(GuiHandler.BORDER);
			this.setForeground(GuiHandler.FONT);
		}
	}

	@Override
	public void paintBorder(Graphics g)
	{}

	public void setDrawType(int drawType)
	{
		this.drawType = drawType;
	}
}
