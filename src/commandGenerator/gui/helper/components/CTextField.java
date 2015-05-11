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

	public CTextField(int cols)
	{
		super(cols);
		this.init();
	}

	private void init()
	{
		this.setDrawType(GuiHandler.DEFAULT);
		this.addMouseListener(new CMouseListener(this));
		this.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e)
			{
				repaint();
			}

			public void focusGained(FocusEvent e)
			{
				repaint();
			}
		});
	}

	private void setDrawType(int drawType)
	{
		this.drawType = drawType;
	}

	public CTextField()
	{
		super();
		this.init();
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

	@Override
	public void paintComponent(Graphics g)
	{
		int width = this.getWidth() - 1;
		int height = this.getHeight() - 1;
		
		Color border = GuiHandler.BORDER_FIELD;
		if (this.isHovered && this.isEditable() && this.isEnabled()) border = GuiHandler.BORDER_FIELD_HOVER;
		if (this.isFocusOwner() && this.isEditable() && this.isEnabled()) border = GuiHandler.BORDER_FIELD_FOCUS;
		if (this.isEnabled()) this.setBackground(Color.WHITE);
		else
		{
			this.setBackground(GuiHandler.DEFAULT_BACKGROUND);
			border = GuiHandler.BORDER_DISABLED;
		}

		GuiHandler.clear(g, width, height);

		GuiHandler.drawDefault(g, width, height, this.getBackground());
		GuiHandler.drawDefaultBorder(g, width, height, border);

		GuiHandler.drawComponent(g, width, height, this.getBackground(), this.drawType);
		GuiHandler.drawBorder(g, width, height, border, this.drawType);

		GuiHandler.drawString(g, this.getText(), width, height, this.getForeground(), this.getFont());
	}

	@Override
	public void paintBorder(Graphics g)
	{}
}
