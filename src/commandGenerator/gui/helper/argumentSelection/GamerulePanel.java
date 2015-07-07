package commandGenerator.gui.helper.argumentSelection;

import java.awt.AWTEvent;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.listeners.ClickListener;
import commandGenerator.gui.helper.components.listeners.IEvent;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class GamerulePanel extends CPanel implements IEvent
{

	private ChoiceComboBox comboboxArgument;
	private ChoiceComboBox comboboxValue;
	private CEntry entryTicks;

	public GamerulePanel()
	{
		super("");
		this.createComponents();
		this.addComponents();
		this.createListeners();
	}

	@Override
	protected void addComponents()
	{
		this.add(this.comboboxArgument);
		this.addLine(false, this.comboboxValue, this.entryTicks);
	}

	@Override
	protected void createComponents()
	{
		this.comboboxArgument = new ChoiceComboBox("gamerule", Resources.gamerules);
		this.comboboxValue = new ChoiceComboBox("value", new String[] { "true", "false" }, false);

		this.entryTicks = new CEntry("GUI:gamerule.value", "3");
		this.entryTicks.setVisible(false);
	}

	@Override
	protected void createListeners()
	{
		this.comboboxArgument.addActionListener(new ClickListener(this));
	}

	public void setupFrom(String gamerule, String value)
	{
		this.comboboxArgument.setSelected(gamerule);
		if (gamerule.equals("randomTickSpeed")) this.entryTicks.setTextField(value);
		else this.comboboxValue.setSelected(value);

		this.entryTicks.setVisible(gamerule.equals("randomTickSpeed"));
		this.comboboxValue.setVisible(!gamerule.equals("randomTickSpeed"));
	}

	public String generateText()
	{
		boolean ticks = Resources.gamerules[this.comboboxArgument.getSelectedIndex()].equals("randomTickSpeed");
		if (!ticks) return Resources.gamerules[this.comboboxArgument.getSelectedIndex()] + " " + (this.comboboxValue.getSelectedIndex() == 0);

		int value = 0;
		try
		{
			value = Integer.parseInt(this.entryTicks.getText());
			if (value < 0)
			{
				DisplayHelper.showWarning("WARNING:positive_integer");
				return null;
			}
		} catch (Exception e)
		{
			DisplayHelper.showWarning("WARNING:number");
			return null;
		}
		return Resources.gamerules[this.comboboxArgument.getSelectedIndex()] + " " + value;
	}

	@Override
	public void handleEvent(AWTEvent event, int eventID)
	{
		boolean ticks = Resources.gamerules[this.comboboxArgument.getSelectedIndex()].equals("randomTickSpeed");
		this.comboboxValue.setVisible(!ticks);
		this.entryTicks.setVisible(ticks);
	}

}
