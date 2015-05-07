package commandGenerator.gui.helper.components.combobox;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.icomponent.IBox;

@SuppressWarnings("serial")
public class LabeledSearchBox extends JPanel implements CComponent
{
	private CLabel label;
	private SearchComboBox combobox;

	public LabeledSearchBox(String labelTextId, String[] values, IBox parent)
	{
		super(new GridLayout(1, 2));

		this.label = new CLabel(labelTextId);
		this.label.setSize(new Dimension(200, 40));

		this.combobox = new SearchComboBox(values, parent);

		this.add(label);
		this.add(combobox);
	}

	public void setSelectedItem(String value)
	{
		this.combobox.setSelectedItem(value);
	}

	public String getSelectedItem()
	{
		return this.combobox.getSelectedItem();
	}

	@Override
	public void reset()
	{
		this.combobox.reset();
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.combobox.setEnabledContent(enable);
		this.label.setEnabledContent(enable);
	}

	@Override
	public void updateLang()
	{
		this.label.updateLang();
	}

	public void setValues(String[] names)
	{
		this.combobox.setValues(names);
	}

}
