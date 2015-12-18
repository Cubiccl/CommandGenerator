package generator.gui;

import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class CTabbedPane extends JTabbedPane
{
	
	public CTabbedPane()
	{
		super(TOP, WRAP_TAB_LAYOUT);
		
		this.setUI(new CTabbedPaneUI());
		this.setBackground(CTabbedPaneUI.BG_COLOR);
	}

}
