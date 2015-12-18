package generator.gui.combobox;

import generator.gui.button.CArrow;
import generator.interfaces.IMouseManager;
import generator.interfaces.MouseManager;
import generator.main.Utils;

import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicComboBoxUI;

public class CComboBoxUI extends BasicComboBoxUI implements IMouseManager
{
	private MouseManager mouseManager;

	@Override
	protected JButton createArrowButton()
	{
		CArrow arrowButton = new CArrow(CArrow.SOUTH);
		this.mouseManager = new MouseManager(this.comboBox);
		arrowButton.addMouseListener(this.mouseManager);
		return arrowButton;
	}

	@Override
	public boolean isClicked()
	{
		return this.mouseManager.isClicked();
	}

	@Override
	public boolean isHovered()
	{
		return this.mouseManager.isHovered();
	}

	@Override
	public void paint(Graphics g, JComponent c)
	{
		if (c instanceof IMouseManager)
		{
			if (((IMouseManager) c).isClicked()) c.setBackground(Utils.BACKGROUND_CLICKED);
			else if (((IMouseManager) c).isHovered()) c.setBackground(Utils.BACKGROUND_HOVERED);
			else c.setBackground(Utils.BACKGROUND_NORMAL);
		}
		super.paint(g, c);
	}

}
