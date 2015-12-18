package generator.gui;

import java.awt.Color;

import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class CTabbedPaneUI extends BasicTabbedPaneUI
{
	public static final Color BORDER_COLOR = Color.gray, BG_COLOR = new Color(170, 170, 210);

	public CTabbedPaneUI()
	{
		super();
	}

	@Override
	protected void installDefaults()
	{
		super.installDefaults();
		this.focus = BORDER_COLOR;
		this.shadow = BORDER_COLOR;
		this.darkShadow = BORDER_COLOR;
		this.highlight = BORDER_COLOR;
		this.lightHighlight = BORDER_COLOR;
	}

}
