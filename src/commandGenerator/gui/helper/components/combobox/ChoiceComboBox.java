package commandGenerator.gui.helper.components.combobox;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import commandGenerator.gui.helper.components.CComponent;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class ChoiceComboBox extends JPanel implements CComponent
{

	private JComboBox<String> box;
	private HelpButton button;
	private String[] choices;
	private String title;
	private boolean translate, hasHelp;

	public ChoiceComboBox(String title, String[] choices)
	{
		this(title, choices, true, true);
	}

	public ChoiceComboBox(String title, String[] choices, boolean hasHelp)
	{
		this(title, choices, hasHelp, true);
	}

	public ChoiceComboBox(final String title, final String[] choices, boolean hasHelp, boolean translate)
	{
		super(new GridBagLayout());
		this.title = title;
		this.choices = choices;
		this.hasHelp = hasHelp;
		this.translate = translate;

		if (this.hasHelp)
		{
			this.button = new HelpButton(Lang.get("HELP:" + title + "." + this.choices[0]), this.choices[0]);
		}

		this.box = new JComboBox<String>(choices);
		if (hasHelp) this.box.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				button.setData(Lang.get("HELP:" + title + "." + choices[box.getSelectedIndex()]), (String) box.getSelectedItem());
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
		if (hasHelp) this.add(this.button, gbc);
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
		if (this.hasHelp) this.button.setEnabled(enable);
	}

	public String getSelectedValue()
	{
		return this.choices[this.box.getSelectedIndex()];
	}

	public void setSelected(String selection)
	{
		for (int i = 0; i < this.choices.length; i++)
			if (this.choices[i].equals(selection)) this.box.setSelectedIndex(i);
	}

	@Override
	public void updateLang()
	{
		String[] names = new String[this.choices.length];
		for (int i = 0; i < names.length; i++)
		{
			if (this.translate) names[i] = Lang.get("RESOURCES:" + this.title + "." + this.choices[i]);
			else names[i] = this.choices[i];
		}

		this.box.setModel(new JComboBox<String>(names).getModel());
		if (this.hasHelp) button.setData(Lang.get("HELP:" + title + "." + choices[this.box.getSelectedIndex()]), (String) this.box.getSelectedItem());
	}

	public void addActionListener(ActionListener actionListener)
	{
		this.box.addActionListener(actionListener);
	}

	public void setData(String title, String[] choices)
	{
		this.title = title;
		this.choices = choices;
		this.updateLang();
	}

	public int getSelectedIndex()
	{
		return this.box.getSelectedIndex();
	}

}
