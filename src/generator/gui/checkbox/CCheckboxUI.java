package generator.gui.checkbox;

import generator.main.Constants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicCheckBoxUI;

public class CCheckboxUI extends BasicCheckBoxUI
{

	@Override
	public Icon getDefaultIcon()
	{
		return new CheckboxIcon();
	}

	@Override
	public void paint(Graphics g, JComponent c)
	{
		if (((AbstractButton) c).getModel().isPressed()) c.setBackground(Constants.BACKGROUND_CLICKED);
		else if (((AbstractButton) c).getModel().isRollover()) c.setBackground(Constants.BACKGROUND_HOVERED);
		else c.setBackground(Constants.BACKGROUND_NORMAL);
		super.paint(g, c);
	}

	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b)
	{}

}

class CheckboxIcon implements Icon
{
	private static final Color COLOR = new Color(50, 50, 50);

	@Override
	public int getIconHeight()
	{
		return 20;
	}

	@Override
	public int getIconWidth()
	{
		return 20;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y)
	{
		g.setColor(COLOR);
		int margin = c.getHeight() / 4;
		int size = c.getHeight() / 2;
		g.drawRect(x + margin, margin, size, size);

		if (((CCheckbox) c).isSelected())
		{
			g.drawLine(x + margin + 2, margin + 2, x + margin + 2, margin + size - 2);
			g.drawLine(x + margin + 2 + 1, margin + 2, x + margin + 2 + 1, margin + size - 2);
			g.drawLine(x + margin + 2, margin + size - 2, x + margin + size - 2, margin + 2);
			g.drawLine(x + margin + 2 + 1, margin + size - 2, x + margin + size - 2 + 1, margin + 2);
		}
	}

}
