package commandGenerator.gui.helper.commandSpecific.worldborder;

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

	private static final String[] modes = { "amount", "buffer" };
	private LangComboBox comboboxMode;
	private CEntry entryValue;
	private CLabel labelMode;

	public DamageBorderPanel()
	{
		super(CGConstants.PANELID_OPTIONS, "GENERAL:options");
	}

	@Override
	protected void addComponents()
	{
		addLine(labelMode, comboboxMode);
		add(entryValue);
	}

	@Override
	protected void createComponents()
	{
		labelMode = new CLabel("GUI:worldborder.mode");

		entryValue = new CEntry(CGConstants.DATAID_NONE, "GUI:worldborder.damage", "1");

		comboboxMode = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:worldborder.damage.mode", 2);
	}

	@Override
	protected void createListeners()
	{
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if (comboboxMode.getSelectedIndex() == 0) entryValue.setText("GUI:worldborder.damage");
				else entryValue.setText("GUI:worldborder.buffer");
			}
		});
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