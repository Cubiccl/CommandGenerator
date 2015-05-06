package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import commandGenerator.Generator;
import commandGenerator.arguments.command.Argument;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.main.DisplayHelper;

public class FloatArgument extends Argument
{

	private JCheckBox box;
	private HelpButton button;
	private CEntry entry;
	private boolean hasHelp;
	private float min, max, defaultValue;

	public FloatArgument(String id, boolean isCompulsery)
	{
		super(id, isCompulsery);
		this.hasHelp = false;
		this.min = Float.MIN_VALUE;
		this.max = Float.MAX_VALUE;
		this.defaultValue = 0.0F;
	}

	public FloatArgument addHelpButton()
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
		this.entry = new CEntry("GUI:" + this.getId(), String.valueOf(defaultValue));
		this.box = new JCheckBox();
		if (this.hasHelp)
		{
			this.button = new HelpButton();
			this.button.setData(Generator.translate("HELP:" + this.getId()), "");
		}
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
			DisplayHelper.log(data.get(0) + " is not a valid number.");
			return false;
		}
		return true;
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
	public void setupFrom(List<String> data)
	{
		this.entry.setTextField(data.get(0));
		this.box.setSelected(true);
	}

	@Override
	public void synchronize(Argument toMatch)
	{
		if (!(toMatch instanceof FloatArgument)) return;
		if (!this.isCompulsery()) this.box.setSelected(toMatch.isUsed());
		String value = ((FloatArgument) toMatch).entry.getText();
		try
		{
			float numeric = Float.parseFloat(value);
			if (numeric > this.max || numeric < this.min) this.entry.setText(String.valueOf(this.defaultValue));
			else this.entry.setText(value);
		} catch (Exception e)
		{
			this.entry.setText(String.valueOf(this.defaultValue));
		}
	}

	@Override
	public void reset()
	{
		this.entry.reset();
		if (!this.isCompulsery())
		{
			this.box.setSelected(false);
			this.entry.setEnabledContent(false);
		}
	}

}
