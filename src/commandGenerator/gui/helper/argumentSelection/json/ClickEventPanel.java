package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.combobox.LangComboBox;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class ClickEventPanel extends HelperPanel
{
	private static final String[] clickEvents = { "open_url", "run_command", "suggest_command" };

	private LangComboBox comboboxAction;
	private CEntry entryText;

	public ClickEventPanel()
	{
		super(CGConstants.DATAID_NONE, "GUI:json.click");
	}

	@Override
	protected void addComponents()
	{
		add(comboboxAction);
		add(entryText);
	}

	@Override
	protected void createComponents()
	{
		entryText = new CEntry(CGConstants.DATAID_NONE, "GUI:json.click.url", "");

		comboboxAction = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:json.click", 3);
	}

	@Override
	protected void createListeners()
	{
		comboboxAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (comboboxAction.getSelectedIndex() == 0) entryText.setText("GUI:json.click.url");
				if (comboboxAction.getSelectedIndex() == 1) entryText.setText("GUI:json.click.run");
				if (comboboxAction.getSelectedIndex() == 2) entryText.setText("GUI:json.click.suggest");
			}
		});
	}

	public TagCompound generateClickEvent()
	{

		String text = entryText.getText();

		TagCompound tag = new TagCompound("clickEvent") {
			public void askValue()
			{}
		};
		tag.addTag(new TagString("action").setValue(clickEvents[comboboxAction.getSelectedIndex()]));
		tag.addTag(new TagString("value").setValue(text));

		return tag;
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

	@Override
	public void updateLang()
	{
		updateTitle();
		entryText.updateLang();
		comboboxAction.updateLang();
	}

}
