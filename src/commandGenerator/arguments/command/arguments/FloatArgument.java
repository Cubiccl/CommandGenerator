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

public class FloatArgument extends Argument
{

	private float min, max, defaultValue;
	private boolean hasHelp;
	private CEntry entry;
	private JCheckBox box;
	private HelpButton button;

	public FloatArgument(String id, boolean isCompulsery)
	{
		super(id, Argument.FLOAT, isCompulsery, 1);
		this.hasHelp = false;
		this.min = Float.MIN_VALUE;
		this.max = Float.MAX_VALUE;
		this.defaultValue = 0.0F;
	}

	@Override
	public Component generateComponent()
	{
		JPanel panel = new JPanel();
		if (!this.isCompulsery()) panel.add(this.box);
		panel.add(this.entry);
		if (this.hasHelp) panel.add(this.button);
		return panel;
	}

	@Override
	public void initGui()
	{
		this.entry = new CEntry(this.getId(), "GUI:" + this.getId(), String.valueOf(defaultValue));
		this.box = new JCheckBox();
		if (this.hasHelp) this.button = new HelpButton(Lang.get("HELP:" + this.getId()), "");
		if (!this.isCompulsery())
		{
			this.box.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0)
				{
					entry.setEnabledContent(box.isSelected());
				}
			});
			entry.setEnabledContent(false);
		}
	}

	@Override
	public String generateCommand()
	{
		String value = this.entry.getText();
		try
		{
			float f = Float.parseFloat(value);
			if (f < this.min || f > this.max)
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

	public FloatArgument addHelpButton()
	{
		this.hasHelp = true;
		return this;
	}

	public FloatArgument setBounds(float minValue, float maxValue)
	{
		this.min = minValue;
		this.max = maxValue;
		return this;
	}

	public FloatArgument setDefaultValue(float defaultValue)
	{
		this.defaultValue = defaultValue;
		return this;
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
			DisplayHelper.log(data.get(0) + " is not a valid number.");
			return false;
		}
		return true;
	}

}
