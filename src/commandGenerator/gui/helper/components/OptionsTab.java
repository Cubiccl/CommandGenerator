package commandGenerator.gui.helper.components;

import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import commandGenerator.arguments.command.Structure;
import commandGenerator.gui.helper.components.panel.HelperPanel;

@SuppressWarnings("serial")
public class OptionsTab extends JTabbedPane implements CComponent
{

	private final Structure[] structures;
	private HelperPanel[] panels;

	public OptionsTab(Structure... structures)
	{
		this.structures = structures;
		this.panels = new HelperPanel[structures.length];
		for (int i = 0; i < this.structures.length; i++)
		{
			this.panels[i] = this.structures[i].generatePanel();
			JScrollPane pane = new JScrollPane(this.panels[i]);
			pane.getHorizontalScrollBar().setUnitIncrement(20);
			pane.getVerticalScrollBar().setUnitIncrement(20);

			add(this.structures[i].getName(), pane);
		}
	}

	@Override
	public void reset()
	{
		for (HelperPanel p : this.panels) p.reset();
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		setEnabled(enable);
		for (HelperPanel p : this.panels) p.setEnabledContent(enable);

	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		if (!isEnabled() || !isVisible()) return;
		for (HelperPanel p : this.panels) p.setupFrom(data);
	}

	@Override
	public void updateLang()
	{
		for (int i = 0; i < this.getTabCount(); i++)
		{
			this.panels[i].updateLang();
			setTitleAt(i, this.structures[i].getName());
		}
	}

	public HelperPanel getPanel()
	{
		return this.panels[this.getSelectedIndex()];
	}

}
