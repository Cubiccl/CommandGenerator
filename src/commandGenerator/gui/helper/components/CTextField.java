package commandGenerator.gui.helper.components;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.listeners.CMouseListener;
import commandGenerator.gui.helper.components.listeners.IEvent;

@SuppressWarnings("serial")
public class CTextField extends JTextField implements IEvent
{
	private int drawType;
	private boolean isHovered;
	private Color borderColor;

	public CTextField()
	{
		super();
		this.init();
	}

	public CTextField(int cols)
	{
		super(cols);
		this.init();
	}

	public Color getBorderColor()
	{
		return this.borderColor;
	}

	public void setBorderColor(Color borderColor)
	{
		this.borderColor = borderColor;
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

			default:
				break;
		}
		this.repaint();
	}

	private void init()
	{
		this.setDrawType(GuiHandler.DEFAULT);
		this.addMouseListener(new CMouseListener(this));
		this.setOpaque(false);
		this.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e)
			{
				repaint();
			}

			public void focusLost(FocusEvent e)
			{
				repaint();
			}
		});
	}

	protected void paintBorder(Graphics g)
	{
		GuiHandler.drawBorder(g, this.getWidth() - 1, this.getHeight() - 1, this.getBorderColor(), this.drawType);
	}

	protected void paintComponent(Graphics g)
	{
		this.setComponentColor();
		GuiHandler.drawComponent(g, this.getWidth() - 1, this.getHeight() - 1, this.getBackground(), this.drawType);
		super.paintComponent(g);
	}

	private void setComponentColor()
	{
		this.setBackground(GuiHandler.DEFAULT_BACKGROUND);
		this.setBorderColor(GuiHandler.BORDER_FIELD);
		if (this.isEditable())
		{
			this.setBackground(GuiHandler.FIELD_EDITABLE);
			if (this.isEnabled())
			{
				this.setBackground(Color.WHITE);
				this.setForeground(GuiHandler.FONT);
			}
			if (this.isHovered) this.setBorderColor(GuiHandler.BORDER_FIELD_HOVER);
			if (this.isFocusOwner()) this.setBorderColor(GuiHandler.BORDER_FIELD_FOCUS);
		}
		if (!this.isEnabled())
		{
			this.setBackground(GuiHandler.DISABLED);
			this.setBorderColor(GuiHandler.DISABLED_BORDER);
			this.setForeground(GuiHandler.DISABLED_BORDER);
		}
	}

	public void setDrawType(int drawType)
	{
		this.drawType = drawType;
	}

}
