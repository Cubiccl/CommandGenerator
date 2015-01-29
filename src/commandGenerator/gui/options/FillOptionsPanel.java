package commandGenerator.gui.options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.gui.helper.commandSpecific.fill.FillNormalPanel;
import commandGenerator.gui.helper.commandSpecific.fill.FillReplacePanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.gui.helper.components.OptionsPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class FillOptionsPanel extends OptionsPanel
{

	private LangComboBox comboboxMode;
	private FillNormalPanel panelNormal, panelReplace;

	public FillOptionsPanel()
	{
		super();
	}

	public void setupFrom(Map<String, Object> data)
	{
		comboboxMode.setupFrom(data);
		if (comboboxMode.getSelectedIndex() == 0)
		{
			panelNormal.setupFrom(data);
		} else
		{
			panelReplace.setupFrom(data);
		}
		panelNormal.setVisible(comboboxMode.getSelectedIndex() == 0);
		panelReplace.setVisible(comboboxMode.getSelectedIndex() == 1);
	}

	@Override
	public String generateCommand()
	{
		if (comboboxMode.getSelectedIndex() == 0) return panelNormal.generateCommand();
		else return panelReplace.generateCommand();
	}

	@Override
	protected void createComponents()
	{
		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE2, "RESOURCES:fill.mode2", 2);

		panelNormal = new FillNormalPanel(600);
		panelReplace = new FillReplacePanel();
		panelReplace.setVisible(false);
	}

	@Override
	protected void addComponents()
	{
		add(comboboxMode);
		add(panelNormal);
		add(panelReplace);
	}

	@Override
	protected void createListeners()
	{
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelNormal.setVisible(comboboxMode.getSelectedIndex() == 0);
				panelReplace.setVisible(comboboxMode.getSelectedIndex() == 1);
			}
		});
	}

}
