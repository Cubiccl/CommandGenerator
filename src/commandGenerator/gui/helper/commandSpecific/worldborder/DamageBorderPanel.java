package commandGenerator.gui.helper.commandSpecific.worldborder;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.CLabel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class DamageBorderPanel extends HelperPanel
{

	private CLabel labelMode;
	private CEntry entryValue;
	private LangComboBox comboboxMode;
	private static final String[] modes = { "amount", "buffer" };

	public DamageBorderPanel()
	{
		super(CGConstants.PANELID_OPTIONS, "GENERAL:options", 400, 100);

		labelMode = new CLabel("GUI:worldborder.mode");

		entryValue = new CEntry(CGConstants.DATAID_NONE, "GUI:worldborder.damage");

		comboboxMode = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:worldborder.damage.mode", 2);
		comboboxMode.setPreferredSize(new Dimension(200, 20));
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (comboboxMode.getSelectedIndex() == 0) entryValue.setText("GUI:worldborder.damage");
				else entryValue.setText("GUI:worldborder.buffer");
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(labelMode, gbc);
		gbc.gridx++;
		add(comboboxMode, gbc);
		gbc.gridx--;
		gbc.gridy++;
		gbc.gridwidth = 2;
		add(entryValue, gbc);
		gbc.gridwidth = 1;
	}

	public String generateText()
	{
		String value = entryValue.getText();

		try
		{
			float test = Float.parseFloat(value);
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

		return modes[comboboxMode.getSelectedIndex()] + " " + value;
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		Object[] options = (Object[]) data.get(getPanelId());
		comboboxMode.setSelectedIndex((int) options[0]);
		entryValue.setTextField((String) options[1]);
		
	}

}