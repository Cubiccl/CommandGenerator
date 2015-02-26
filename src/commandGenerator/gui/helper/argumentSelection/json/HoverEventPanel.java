package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextField;

import commandGenerator.arguments.objects.Achievement;
import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.Registerer;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.AchievementSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.Constants;

@SuppressWarnings("serial")
public class HoverEventPanel extends HelperPanel
{

	private LangComboBox comboboxAction;
	private ItemSelectionPanel panelItem;
	private JsonSelectionPanel panelJson;
	private AchievementSelectionPanel panelStat;
	private JTextField textfieldText;

	public HoverEventPanel()
	{
		super(Constants.DATAID_NONE, "GUI:json.hover");
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
		comboboxAction = new LangComboBox(Constants.DATAID_NONE, "RESOURCES:json.hover", 4);

		textfieldText = new JTextField(20);

		panelJson = new JsonSelectionPanel("GUI:json.text", false);
		panelJson.setVisible(false);
		panelItem = new ItemSelectionPanel(Constants.PANELID_ITEM, "GUI:json.item", Registerer.getList(Constants.LIST_ITEMS), true, false);
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
				comboboxAction.setSelectedIndex(1);
				TagCompound tag = new TagCompound() {
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
			comboboxAction.setSelectedIndex(2);
			TagCompound tag = new TagCompound() {
				public void askValue()
				{}
			};
			tag.setValue(DataTags.generateListFrom(value));

			Map<String, Object> data = new HashMap<String, Object>();
			data.put(Constants.PANELID_ITEM, ItemStack.generateFrom(tag));
			panelItem.setupFrom(data);
		}
		if (action.equals("show_achievement"))
		{
			comboboxAction.setSelectedIndex(3);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(Constants.PANELID_ACHIEVEMENT, ((Achievement) Registerer.getObjectFromId(value)));
			panelStat.setupFrom(data);
		}

		textfieldText.setVisible(comboboxAction.getSelectedIndex() == 0);
		panelJson.setVisible(comboboxAction.getSelectedIndex() == 1);
		panelItem.setVisible(comboboxAction.getSelectedIndex() == 2);
		panelStat.setVisible(comboboxAction.getSelectedIndex() == 3);
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		comboboxAction.updateLang();
	}
}
