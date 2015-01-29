package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class ClickEventPanel extends HelperPanel
{
	private static final String[] clickEvents = { "open_url", "run_command", "suggest_command" };

	private CEntry entryText;
	private LangComboBox comboboxAction;

	public ClickEventPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title, 400, 100);

		entryText = new CEntry(CGConstants.DATAID_NONE, "GUI:json.click.url");

		comboboxAction = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:json.click", 3);
		comboboxAction.setPreferredSize(new Dimension(200, 20));
		comboboxAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (comboboxAction.getSelectedIndex() == 0) entryText.setText("GUI:json.click.url");
				if (comboboxAction.getSelectedIndex() == 1) entryText.setText("GUI:json.click.run");
				if (comboboxAction.getSelectedIndex() == 2) entryText.setText("GUI:json.click.suggest");
			}
		});

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(comboboxAction);
		gbc.gridwidth = 1;
		gbc.gridy++;
		add(entryText);
		gbc.gridx++;
		add(entryText);
	}

	public TagCompound generateClickEvent()
	{

		String text = entryText.getText();
		if (text.equals("") || text == null)
		{
			DisplayHelper.showWarning("WARNING:missing_text");
			return null;
		}

		TagCompound tag = new TagCompound("clickEvent") {
			public void askValue()
			{}
		};
		tag.addTag(new TagString("action").setValue(clickEvents[comboboxAction.getSelectedIndex()]));
		tag.addTag(new TagString("value").setValue(text));

		return tag;
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		entryText.updateLang();
		comboboxAction.updateLang();
	}

	public void setup(TagCompound nbt)
	{
		String action = "open_url", value = "";

		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("action")) action = ((TagString) tag).getValue();
			if (tag.getId().equals("value")) value = ((TagString) tag).getValue();
		}

		if (action.equals("open_url")) comboboxAction.setSelectedIndex(0);
		if (action.equals("run_command")) comboboxAction.setSelectedIndex(1);
		if (action.equals("suggest_command")) comboboxAction.setSelectedIndex(2);

		entryText.setTextField(value);
	}

}
