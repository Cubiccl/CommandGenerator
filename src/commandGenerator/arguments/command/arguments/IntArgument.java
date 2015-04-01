package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

public class IntArgument extends Argument
{

	private JCheckBox box;
	private HelpButton button;
	private CEntry entry;
	private boolean hasHelp;
	private int min, max, defaultValue;

	public IntArgument(String id, boolean isCompulsery)
	{
		super(id, isCompulsery);
		this.hasHelp = false;
		this.min = Integer.MIN_VALUE;
		this.max = Integer.MAX_VALUE;
		this.defaultValue = 0;
	}

	public IntArgument addHelpButton()
	{
		this.hasHelp = true;
		return this;
	}

	@Override
	public String generateCommand()
	{
		String value = this.entry.getText();
		try
		{
			int x = Integer.parseInt(value);
			if (x < this.min || x > this.max)
			{
				DisplayHelper.warningBounds(this.min, this.max);
				return null;
			}
		} catch (Exception e)
		{
			DisplayHelper.warningBounds(this.min, this.max);
			return null;
		}
		return value;
	}

	@Override
	public Component generateComponent()
	{
		JPanel panel = new JPanel();
		if (!this.isCompulsery()) panel.add(this.box);
		if (this.hasHelp) panel.add(this.button);
		panel.add(this.entry);
		return panel;
	}

	@Override
	public void initGui()
	{
		this.entry = new CEntry("GUI:" + this.getId(), String.valueOf(defaultValue));
		this.box = new JCheckBox();
		if (this.hasHelp) this.button = new HelpButton(Lang.get("HELP:" + this.getId()), "");
		if (!this.isCompulsery())
		{
			this.box.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					entry.setEnabledContent(box.isSelected());
				}
			});
			entry.setEnabledContent(false);
		}
	}

	@Override
	public boolean isUsed()
	{
		return this.isCompulsery() || this.box.isSelected();
	}

	@Override
	public boolean matches(List<String> data)
	{
		try
		{
			Integer.parseInt(data.get(0));
		} catch (Exception e)
		{
			DisplayHelper.log(data.get(0) + " is not a valid integer.");
			return false;
		}
		return true;
	}

	public IntArgument setBounds(int minValue, int maxValue)
	{
		this.min = minValue;
		this.max = maxValue;
		return this;
	}

	public IntArgument setDefaultValue(int defaultValue)
	{
		this.defaultValue = defaultValue;
		return this;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		this.entry.setTextField(data.get(0));
		this.box.setSelected(true);
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof IntArgument)) return;
		if (!this.isCompulsery()) this.box.setSelected(toMatch.isUsed());
		String value = ((IntArgument) toMatch).entry.getText();
		try
		{
			int numeric = Integer.parseInt(value);
			if (numeric > this.max || numeric < this.min) this.entry.setTextField(String.valueOf(this.defaultValue));
			else this.entry.setTextField(value);
		} catch (Exception e)
		{
			this.entry.setText(String.valueOf(this.defaultValue));
		}
	}

}
