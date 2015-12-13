package generator.gui.button;

import generator.main.Utils;

import java.awt.Graphics;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicButtonUI;

public class CButtonUI extends BasicButtonUI
{

	@Override
	public void paint(Graphics g, JComponent c)
	{
		if (((AbstractButton) c).getModel().isPressed()) c.setBackground(Utils.BACKGROUND_CLICKED);
		else if (((AbstractButton) c).getModel().isRollover()) c.setBackground(Utils.BACKGROUND_HOVERED);
		else c.setBackground(Utils.BACKGROUND_NORMAL);
		super.paint(g, c);
	}

	@Override
	protected void paintButtonPressed(Graphics g, AbstractButton b)
	{}

}
