package commandGenerator.gui.helper.components.combobox;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class ChoiceComboBox extends JPanel implements CComponent
{

	private String name;
	private String[] choices;
	private JComboBox<String> box;
	private HelpButton button;

	public ChoiceComboBox(final String name, final String[] choices, boolean hasHelp)
	{
		super(new GridBagLayout());
		this.name = name;
		this.choices = choices;

		if (hasHelp) this.button = new HelpButton(Lang.get("HELP:" + name + "." + choices[0]), Lang.get("RESOURCES:" + name + "." + choices[0]));

		this.box = new JComboBox<String>(choices);
		if (hasHelp) this.box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				button.setData(Lang.get("HELP:" + name + "." + choices[box.getSelectedIndex()]),
						Lang.get("RESOURCES:" + name + "." + choices[box.getSelectedIndex()]));
			}
		});
		this.box.setMinimumSize(new Dimension(200, 20));
		this.box.setPreferredSize(new Dimension(200, 20));

		this.updateLang();
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.add(box, gbc);
		gbc.gridx++;
		if (hasHelp) this.add(button, gbc);
	}

	@Override
	public void reset()
	{
		this.box.setSelectedIndex(0);
	}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
		this.box.setEnabled(enable);
		this.button.setEnabled(enable);
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void updateLang()
	{
		String[] names = new String[this.choices.length];
		for (int i = 0; i < names.length; i++)
			names[i] = Lang.get("RESOURCES:" + this.name + "." + this.choices[i]);

		this.box.setModel(new JComboBox<String>(names).getModel());
	}

	public int getSelectedIndex()
	{
		return this.box.getSelectedIndex();
	}

}
