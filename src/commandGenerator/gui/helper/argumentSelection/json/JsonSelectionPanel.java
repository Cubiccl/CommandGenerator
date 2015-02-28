package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import commandGenerator.arguments.objects.Target;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.LangComboBox;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Lang;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class JsonSelectionPanel extends HelperPanel
{

	private HelpButton buttonHelpInsertion, buttonHelpHover, buttonHelpClick;
	private CCheckBox checkboxBold, checkboxUnderlined, checkboxItalic, checkboxStrikethrough, checkboxObfuscated, checkboxHover, checkboxClick;
	private LangComboBox comboboxMode, comboboxColor;
	private CEntry entryText, entryInsertion;
	private boolean events;
	private ClickEventPanel panelClick;
	private TargetSelectionPanel panelEntity;
	private HoverEventPanel panelHover;
	private ScoreDisplayPanel panelScore;

	public JsonSelectionPanel(String title, boolean events)
	{
		super(CGConstants.DATAID_NONE, title, events);
	}

	@Override
	protected void addComponents()
	{
		add(comboboxMode);
		addStack(entryText, panelEntity, panelScore);
		add(comboboxColor);
		add(checkboxBold);
		add(checkboxUnderlined);
		add(checkboxItalic);
		add(checkboxStrikethrough);
		add(checkboxObfuscated);
		addLine(entryInsertion, buttonHelpInsertion);
		if (events)
		{
			addLine(checkboxClick, buttonHelpClick);
			add(panelClick);
			addLine(checkboxHover, buttonHelpHover);
			add(panelHover);
		}
	}

	@Override
	protected void createComponents()
	{
		buttonHelpInsertion = new HelpButton(Lang.get("HELP:json.insertion"), "GUI:json.insertion");
		buttonHelpHover = new HelpButton(Lang.get("HELP:json.hover"), "GUI:json.hover");
		buttonHelpClick = new HelpButton(Lang.get("HELP:json.click"), "GUI:json.click");

		entryText = new CEntry(CGConstants.DATAID_NONE, "GUI:json.text", "");
		entryInsertion = new CEntry(CGConstants.DATAID_NONE, "GUI:json.insertion", "");

		checkboxBold = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.bold");
		checkboxUnderlined = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.underlined");
		checkboxItalic = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.italic");
		checkboxStrikethrough = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.strikethrough");
		checkboxObfuscated = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.obfuscated");
		if (events)
		{
			checkboxHover = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.hover.use");
			checkboxClick = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.click.use");
		}

		comboboxMode = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:json.mode", 3);
		comboboxColor = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:color", 16);
		comboboxColor.setSelectedIndex(15);

		panelEntity = new TargetSelectionPanel(CGConstants.PANELID_TARGET, "GUI:json.display.entity", CGConstants.ENTITIES_PLAYERS);
		panelEntity.setVisible(false);
		panelScore = new ScoreDisplayPanel("GUI:json.display.score");
		panelScore.setVisible(false);
		if (events)
		{
			panelHover = new HoverEventPanel();
			panelClick = new ClickEventPanel();

			panelHover.setEnabledContent(false);
			panelClick.setEnabledContent(false);
		}
	}

	@Override
	protected void createListeners()
	{
		if (events)
		{
			checkboxClick.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					panelClick.setEnabledContent(checkboxClick.isSelected());
				}
			});
			checkboxHover.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					panelHover.setEnabledContent(checkboxHover.isSelected());
				}
			});
		}
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				entryText.setVisible(comboboxMode.getSelectedIndex() == 0);
				panelScore.setVisible(comboboxMode.getSelectedIndex() == 1);
				panelEntity.setVisible(comboboxMode.getSelectedIndex() == 2);
			}
		});
	}

	public TagCompound getTag()
	{

		TagCompound tag = new TagCompound() {
			public void askValue()
			{}
		};
		if (events && checkboxClick.isSelected() && panelClick.generateClickEvent() == null) return null;
		if (events && checkboxHover.isSelected() && panelHover.generateHoverEvent() == null) return null;

		switch (comboboxMode.getSelectedIndex())
		{
			case 0:
				tag.addTag(new TagString("text").setValue(entryText.getText()));
				break;
			case 1:
				tag.addTag(panelScore.generateScore());
				break;
			case 2:
				tag.addTag(new TagString("selector").setValue(panelEntity.generateEntity().commandStructure()));
				break;
			default:
				break;
		}

		tag.addTag(new TagString("color").setValue(Resources.colors[comboboxColor.getSelectedIndex()]));
		if (checkboxBold.isSelected()) tag.addTag(new TagString("bold").setValue("true"));
		if (checkboxUnderlined.isSelected()) tag.addTag(new TagString("underlined").setValue("true"));
		if (checkboxItalic.isSelected()) tag.addTag(new TagString("italic").setValue("true"));
		if (checkboxStrikethrough.isSelected()) tag.addTag(new TagString("strikethrough").setValue("true"));
		if (checkboxObfuscated.isSelected()) tag.addTag(new TagString("obfuscated").setValue("true"));
		if (!entryInsertion.getText().equals("")) tag.addTag(new TagString("insertion").setValue(entryInsertion.getText()));
		if (events && checkboxClick.isSelected()) tag.addTag(panelClick.generateClickEvent());
		if (events && checkboxHover.isSelected()) tag.addTag(panelHover.generateHoverEvent());

		return tag;
	}

	public void setup(TagCompound nbt)
	{
		for (int i = 0; i < nbt.size(); i++)
		{
			Tag tag = nbt.get(i);
			if (tag.getId().equals("bold")) checkboxBold.setSelected(((TagString) tag).getValue() == "true");
			if (tag.getId().equals("underlined")) checkboxUnderlined.setSelected(((TagString) tag).getValue() == "true");
			if (tag.getId().equals("italic")) checkboxItalic.setSelected(((TagString) tag).getValue() == "true");
			if (tag.getId().equals("strikethrough")) checkboxStrikethrough.setSelected(((TagString) tag).getValue() == "true");
			if (tag.getId().equals("obfuscated")) checkboxObfuscated.setSelected(((TagString) tag).getValue() == "true");
			if (tag.getId().equals("insertion")) entryInsertion.setTextField(((TagString) tag).getValue());
			if (tag.getId().equals("clickEvent")) panelClick.setup((TagCompound) tag);
			if (tag.getId().equals("hoverEvent")) panelHover.setup((TagCompound) tag);
			if (tag.getId().equals("color"))
			{
				String color = ((TagString) tag).getValue();
				for (int j = 0; j < Resources.colors.length; j++)
					if (Resources.colors[j].equals(color)) comboboxColor.setSelectedIndex(j);

			}

			if (tag.getId().equals("text")) entryText.setTextField(((TagString) tag).getValue());
			if (tag.getId().equals("score"))
			{
				comboboxMode.setSelectedIndex(1);
				panelScore.setup((TagCompound) tag);
			}
			if (tag.getId().equals("selector"))
			{
				comboboxMode.setSelectedIndex(2);
				Map<String, Object> data = new HashMap<String, Object>();
				data.put(CGConstants.PANELID_TARGET, Target.generateFrom(((TagString) tag).getValue()));
				panelEntity.setupFrom(data);
			}
		}

		entryText.setVisible(comboboxMode.getSelectedIndex() == 0);
		panelScore.setVisible(comboboxMode.getSelectedIndex() == 1);
		panelEntity.setVisible(comboboxMode.getSelectedIndex() == 2);
	}

	@Override
	protected void setupDetails(Object[] details)
	{
		events = (boolean) details[0];
	}

	@Override
	public void updateLang()
	{
		updateTitle();
		entryText.updateLang();
		entryInsertion.updateLang();
		checkboxBold.updateLang();
		checkboxClick.updateLang();
		checkboxHover.updateLang();
		checkboxItalic.updateLang();
		checkboxObfuscated.updateLang();
		checkboxStrikethrough.updateLang();
		checkboxUnderlined.updateLang();
		comboboxColor.updateLang();
		comboboxMode.updateLang();
	}
}
