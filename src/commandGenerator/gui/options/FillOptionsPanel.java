package commandGenerator.gui.options;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.gui.OptionsPanel;
import commandGenerator.gui.helper.commandSpecific.fill.FillNormalPanel;
import commandGenerator.gui.helper.commandSpecific.fill.FillReplacePanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class FillOptionsPanel extends OptionsPanel
{

	private LangComboBox comboboxMode;
	private FillNormalPanel panelNormal, panelReplace;

	public FillOptionsPanel()
	{
		super(1000);

		comboboxMode = new LangComboBox(CGConstants.DATAID_MODE2, "RESOURCES:fill.mode2", 2);
		comboboxMode.setPreferredSize(new Dimension(200, 20));
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				panelNormal.setVisible(comboboxMode.getSelectedIndex() == 0);
				panelReplace.setVisible(comboboxMode.getSelectedIndex() == 1);
			}
		});

		panelNormal = new FillNormalPanel(600);
		panelReplace = new FillReplacePanel();
		panelReplace.setVisible(false);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxMode, gbc);
		gbc.gridy++;
		add(panelNormal, gbc);
		add(panelReplace, gbc);
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

}
