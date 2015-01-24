package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import commandGenerator.arguments.objects.ItemStack;
import commandGenerator.arguments.objects.ObjectLists;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.AchievementSelectionPanel;
import commandGenerator.gui.helper.argumentSelection.ItemSelectionPanel;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class HoverEventPanel extends HelperPanel
{

	private LangComboBox comboboxAction;
	private JTextField textfieldText;
	private ItemSelectionPanel panelItem;
	private AchievementSelectionPanel panelStat;
	private JsonTextSelectionPanel panelJson;

	public HoverEventPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title, 620, 350);

		comboboxAction = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:json.hover", 4);
		comboboxAction.setPreferredSize(new Dimension(200, 20));
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

		textfieldText = new JTextField(20);

		panelJson = new JsonTextSelectionPanel("GUI:json.text", false);
		panelJson.setVisible(false);
		panelItem = new ItemSelectionPanel(CGConstants.DATAID_NONE, "GUI:json.item", ObjectLists.get(CGConstants.LIST_ITEMS), true, false);
		panelItem.setVisible(false);
		panelStat = new AchievementSelectionPanel();
		panelStat.setVisible(false);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxAction, gbc);
		gbc.gridy++;
		add(textfieldText, gbc);
		add(panelJson, gbc);
		add(panelItem, gbc);
		add(panelStat, gbc);
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

	@Override
	public void updateLang()
	{
		updateTitle();
		comboboxAction.updateLang();
	}

}
