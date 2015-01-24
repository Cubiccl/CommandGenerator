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
	private CEntry entrySize, entryTime;
	private CCheckBox checkboxTime;

	public AddBorderPanel(boolean canBeNegative)
	{
		super(CGConstants.PANELID_OPTIONS, "GENERAL:options", 400, 100);

		this.canBeNegative = canBeNegative;

		entrySize = new CEntry(CGConstants.DATAID_NONE, "GUI:worldborder.add.size");
		entryTime = new CEntry(CGConstants.DATAID_NONE, "GUI:worldborder.add.time");
		entryTime.setEnabledContent(false);

		checkboxTime = new CCheckBox(CGConstants.DATAID_NONE, "GUI:worldborder.add.timer");
		checkboxTime.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				entryTime.setEnabledContent(checkboxTime.isSelected());
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(entrySize, gbc);
		gbc.gridy++;
		add(checkboxTime, gbc);
		gbc.gridy++;
		add(entryTime, gbc);

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
	public void setupFrom(Map<String, Object> data)
	{
		Object[] options = (Object[]) data.get(getPanelId());
		entrySize.setTextField((String) options[0]);
		if (options.length > 1) entryTime.setTextField((String) options[1]);
		checkboxTime.setSelected(options.length > 1);
		entryTime.setEnabledContent(options.length > 1);
	}

}
