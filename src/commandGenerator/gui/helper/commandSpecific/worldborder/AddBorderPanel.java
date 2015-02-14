package commandGenerator.gui.helper.commandSpecific.worldborder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class AddBorderPanel extends HelperPanel
{

	private boolean canBeNegative;
	private CCheckBox checkboxTime;
	private CEntry entrySize, entryTime;

	public AddBorderPanel(boolean canBeNegative)
	{
		super(CGConstants.PANELID_OPTIONS, "GENERAL:options", canBeNegative);
	}

	@Override
	protected void addComponents()
	{
		add(entrySize);
		add(checkboxTime);
		add(entryTime);
	}

	@Override
	protected void createComponents()
	{
		entrySize = new CEntry(CGConstants.DATAID_NONE, "GUI:worldborder.add.size", "10");
		entryTime = new CEntry(CGConstants.DATAID_NONE, "GUI:worldborder.add.time", "10");
		entryTime.setEnabledContent(false);

		checkboxTime = new CCheckBox(CGConstants.DATAID_NONE, "GUI:worldborder.add.timer");
	}

	@Override
	protected void createListeners()
	{
		checkboxTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				entryTime.setEnabledContent(checkboxTime.isSelected());
			}
		});
	}

	public String generateText()
	{

		String size = entrySize.getText();
		String time = entryTime.getText();

		try
		{
			int test = Integer.parseInt(size);
			if (!canBeNegative && test < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		if (checkboxTime.isSelected())
		{
			try
			{
				int test = Integer.parseInt(time);
				if (test < 0)
				{
					DisplayHelper.warningPositiveInteger();
					return null;
				}
			} catch (Exception ex)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		}

		String text = size;
		if (checkboxTime.isSelected()) text += " " + time;

		return text;
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		this.canBeNegative = (boolean) details[0];
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		Object[] options = (Object[]) data.get(getPanelId());
		entrySize.setTextField((String) options[0]);
		if (options.length > 1) entryTime.setTextField((String) options[1]);
		checkboxTime.setSelected(options.length > 1);
		entryTime.setEnabledContent(options.length > 1);
	}

}
