package commandGenerator.gui.helper.components.spinner;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.components.listeners.CMouseListener;
import commandGenerator.gui.helper.components.listeners.IEvent;

@SuppressWarnings("serial")
public class CFormattedTextField extends JFormattedTextField implements IEvent
{
	private int drawType;
	private boolean isHovered;

	public CFormattedTextField(NumberFormatter formatter)
	{
		super(formatter);
		this.init();
	}

	private Color getBorderColor()
	{
		Color color = GuiHandler.BORDER_FIELD;
		if (this.isHovered && this.isEditable() && this.isEnabled()) color = GuiHandler.BORDER_FIELD_HOVER;
		if (this.isFocusOwner() && this.isEditable() && this.isEnabled()) color = GuiHandler.BORDER_FIELD_FOCUS;
		if (!this.isEnabled()) color = GuiHandler.BORDER_DISABLED;
		return color;
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
		if (this.isEnabled() && this.isEditable()) this.setBackground(Color.WHITE);
		else if (this.isEnabled()) this.setBackground(GuiHandler.FIELD_EDITABLE);
		else this.setBackground(GuiHandler.DEFAULT_BACKGROUND);
		GuiHandler.drawComponent(g, this.getWidth() - 1, this.getHeight() - 1, this.getBackground(), this.drawType);
		super.paintComponent(g);
	}

	public void setDrawType(int drawType)
	{
		this.drawType = drawType;
	}

}
