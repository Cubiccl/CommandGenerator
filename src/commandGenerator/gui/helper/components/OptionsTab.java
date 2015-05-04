package commandGenerator.gui.helper.components;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import commandGenerator.arguments.command.Structure;
import commandGenerator.gui.helper.components.panel.CPanel;

@SuppressWarnings("serial")
public class OptionsTab extends JTabbedPane implements CComponent
{

	private int selectedTab;
	private final CPanel[] panels;
	private final Structure[] structures;

	public OptionsTab(Structure... structures)
	{
		this.structures = structures;
		this.selectedTab = 0;
		this.panels = new CPanel[structures.length];
		for (int i = 0; i < this.structures.length; i++)
		{
			this.panels[i] = this.structures[i].generatePanel();
			JScrollPane pane = new JScrollPane(this.panels[i]);
			pane.getHorizontalScrollBar().setUnitIncrement(20);
			pane.getVerticalScrollBar().setUnitIncrement(20);

			this.add(this.structures[i].getName(), pane);
		}

		this.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				changeTab();
			}
		});
	}

	private void changeTab()
	{
		Structure selected = this.structures[this.selectedTab];
		for (Structure s : this.structures)
			if (s != selected) s.synchronize(selected);

		this.selectedTab = this.getSelectedIndex();
	}

	@Override
	public void reset()
	{}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
		for (CPanel p : this.panels)
			p.setEnabledContent(enable);

	}

	@Override
	public void updateLang()
	{
		for (int i = 0; i < this.getTabCount(); i++)
		{
			this.panels[i].updateLang();
			this.setTitleAt(i, this.structures[i].getName());
		}
	}

}
