package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.panel.CPanel;

@SuppressWarnings("serial")
public class ClickEventPanel extends CPanel
{
	private static final String[] clickEvents = { "open_url", "run_command", "suggest_command" };

	private ChoiceComboBox comboboxAction;
	private CEntry entryText;

	public ClickEventPanel()
	{
		super("GUI:json.click");
		
		this.initGui();
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
		entryText = new CEntry("GUI:json.click.url", "");

		comboboxAction = new ChoiceComboBox("json.click", clickEvents, false);
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
			@Override
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

		this.comboboxAction.setSelected(action);
		entryText.setTextField(value);
	}

}
