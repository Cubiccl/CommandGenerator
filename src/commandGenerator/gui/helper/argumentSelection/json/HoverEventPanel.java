package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.Achievement;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectCreator;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.AchievementSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class HoverEventPanel extends CPanel
{

	private ChoiceComboBox comboboxAction;
	private ItemSelectionPanel panelItem;
	private JsonSelectionPanel panelJson;
	private AchievementSelectionPanel panelStat;
	private JTextField textfieldText;

	public HoverEventPanel()
	{
		super("GUI:json.hover");
		
		this.initGui();
	}

	@Override
	protected void addComponents()
	{
		add(comboboxAction);
		addStack(textfieldText, panelJson, panelItem, panelStat);
	}

	@Override
	protected void createComponents()
	{
		comboboxAction = new ChoiceComboBox("json.hover", new String[] { "show_text", "json", "show_item", "show_achievement" }, false);

		textfieldText = new JTextField(20);

		panelJson = new JsonSelectionPanel("GUI:json.text", false);
		panelJson.setVisible(false);
		panelItem = new ItemSelectionPanel("GUI:json.item", Generator.registry.getList(CGConstants.LIST_ITEMS), true, false);
		panelItem.setVisible(false);
		panelStat = new AchievementSelectionPanel();
		panelStat.setVisible(false);
	}

	@Override
	protected void createListeners()
	{
		comboboxAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				textfieldText.setVisible(comboboxAction.getSelectedIndex() == 0);
				panelJson.setVisible(comboboxAction.getSelectedIndex() == 1);
				panelItem.setVisible(comboboxAction.getSelectedIndex() == 2);
				panelStat.setVisible(comboboxAction.getSelectedIndex() == 3);
			}
		});
	}

	public TagCompound generateHoverEvent()
	{

		TagCompound tag = new TagCompound("hoverEvent") {
			@Override
			public void askValue()
			{}
		};

		switch (comboboxAction.getSelectedIndex())
		{
			case 0:
				tag.addTag(new TagString("action").setValue("show_text"));
				tag.addTag(new TagString("value").setValue(textfieldText.getText()));
				break;

			case 1:
				tag.addTag(new TagString("action").setValue("show_text"));
				tag.addTag(panelJson.getTag());
				break;

			case 2:
				tag.addTag(new TagString("action").setValue("show_item"));
				tag.addTag((new ItemStack(panelItem.generateItem(), panelItem.getDamage(), panelItem.getCount(), panelItem.getItemTag())).toNBT("value"));
				break;

			case 3:
				tag.addTag(new TagString("action").setValue("show_achievement"));
				tag.addTag(new TagString("value").setValue(panelStat.getSelectedAchievement().getId()));
				break;

			default:
				break;
		}

		return tag;
	}

	public void setup(TagCompound nbt)
	{
		String action = "show_text", value = "";

		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("action")) action = ((TagString) tag).getValue();
			if (tag.getId().equals("value")) value = ((TagString) tag).getValue();
		}

		if (action.equals("show_text"))
		{
			if (value.startsWith("{") && value.endsWith("}"))
			{
				comboboxAction.setSelected("show_text");
				TagCompound tag = new TagCompound() {
					@Override
					public void askValue()
					{}
				};
				tag.setValue(DataTags.generateListFrom(value));

				panelJson.setup(tag);
			} else
			{
				textfieldText.setText(value);
			}
		}
		if (action.equals("show_item"))
		{
			comboboxAction.setSelected("show_item");
			TagCompound tag = new TagCompound() {
				@Override
				public void askValue()
				{}
			};
			tag.setValue(DataTags.generateListFrom(value));

			panelItem.setupFrom(ObjectCreator.generateItemStack(tag));
		}
		if (action.equals("show_achievement"))
		{
			comboboxAction.setSelected("show_achievement");
			panelStat.setupFrom(((Achievement) Generator.registry.getObjectFromId(value)));
		}

		textfieldText.setVisible(comboboxAction.getSelectedIndex() == 0);
		panelJson.setVisible(comboboxAction.getSelectedIndex() == 1);
		panelItem.setVisible(comboboxAction.getSelectedIndex() == 2);
		panelStat.setVisible(comboboxAction.getSelectedIndex() == 3);
	}
}
