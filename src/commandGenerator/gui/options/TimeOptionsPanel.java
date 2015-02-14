package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelpButton;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TimeOptionsPanel extends OptionsPanel
{

	private LangComboBox comboboxMode, comboboxValue;
	private CEntry entryTime;
	private CLabel labelMode;
	private HelpButton buttonHelp;

	public TimeOptionsPanel()
	{
		super();
	}

	@Override
	protected void addComponents()
	{
		addLine(labelMode, comboboxMode);
		add(comboboxValue);
		add(entryTime);
	}

	@Override
	protected void createComponents()
	{
		labelMode = new CLabel("GUI:time.mode");

		entryTime = new CEntry(CGConstants.DATAID_VALUE, "GUI:time.time", "0");
		entryTime.setVisible(false);

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:time.mode", 3);
		comboboxValue = new LangComboBox(CGConstants.DATAID_MODE, "RESOURCES:time.value", 3);

		buttonHelp = new HelpButton(Lang.get("HELP:time_0"), (String) comboboxMode.getSelectedItem());
	}

	@Override
	protected void createListeners()
	{
		comboboxMode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				comboboxValue.setVisible(comboboxMode.getSelectedIndex() == 0);
				entryTime.setVisible(comboboxMode.getSelectedIndex() != 2);
				if (comboboxMode.getSelectedIndex() == 0 && comboboxValue.getSelectedIndex() != 2) entryTime.setVisible(false);
				buttonHelp.setData(Lang.get("HELP:time_" + comboboxMode.getSelectedIndex()), (String) comboboxMode.getSelectedItem());
			}
		});
		comboboxValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				entryTime.setVisible(comboboxValue.getSelectedIndex() == 2);
			}
		});
	}

	@Override
	public String generateCommand()
	{
		String time = entryTime.getText();

		try
		{
			int flag = Integer.parseInt(time);
			if (flag < 0)
			{
				DisplayHelper.warningPositiveInteger();
				return null;
			}
		} catch (Exception ex)
		{
			DisplayHelper.warningPositiveInteger();
			return null;
		}

		switch (comboboxMode.getSelectedIndex())
		{
			case 0:
				switch (comboboxValue.getSelectedIndex())
				{
					case 0:
						return "time set day";
					case 1:
						return "time set night";
					case 2:
						return "time set " + entryTime.getText();

				}
			case 1:
				return "time add " + entryTime.getText();
			case 2:
				return "time query";
		}
		
		return "error xD";
	}

}
