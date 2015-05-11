package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.Generator;
import commandGenerator.arguments.objects.ObjectCreator;
import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.GuiHandler;
import commandGenerator.gui.helper.argumentSelection.TargetSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.HelpButton;
import commandGenerator.gui.helper.components.combobox.ChoiceComboBox;
import commandGenerator.gui.helper.components.panel.CPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class JsonSelectionPanel extends CPanel
{
	public static final String[] colors = { "black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray", "dark_gray", "blue",
			"green", "aqua", "red", "light_purple", "yellow", "white" };

	private HelpButton buttonHelpInsertion, buttonHelpHover, buttonHelpClick;
	private CCheckBox checkboxBold, checkboxUnderlined, checkboxItalic, checkboxStrikethrough, checkboxObfuscated, checkboxHover, checkboxClick;
	private ChoiceComboBox comboboxMode, comboboxColor;
	private CEntry entryText, entryInsertion;
	private boolean events;
	private ClickEventPanel panelClick;
	private TargetSelectionPanel panelEntity;
	private HoverEventPanel panelHover;
	private ScoreDisplayPanel panelScore;

	public JsonSelectionPanel(String title, boolean events)
	{
		super(title);
		this.events = events;
		
		this.initGui();
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
		buttonHelpInsertion = new HelpButton();
		buttonHelpInsertion.setData(Generator.translate("HELP:json.insertion"), "GUI:json.insertion");
		buttonHelpHover = new HelpButton();
		buttonHelpHover.setData(Generator.translate("HELP:json.hover"), "GUI:json.hover");
		buttonHelpHover.setDrawType(GuiHandler.DEFAULT);
		buttonHelpClick = new HelpButton();
		buttonHelpClick.setData(Generator.translate("HELP:json.click"), "GUI:json.click");
		buttonHelpClick.setDrawType(GuiHandler.DEFAULT);

		entryText = new CEntry("GUI:json.text", "");
		entryInsertion = new CEntry("GUI:json.insertion", "");
		entryInsertion.setDrawType(GuiHandler.RIGHT);

		checkboxBold = new CCheckBox("GUI:json.bold");
		checkboxUnderlined = new CCheckBox("GUI:json.underlined");
		checkboxItalic = new CCheckBox("GUI:json.italic");
		checkboxStrikethrough = new CCheckBox("GUI:json.strikethrough");
		checkboxObfuscated = new CCheckBox("GUI:json.obfuscated");
		if (events)
		{
			checkboxHover = new CCheckBox("GUI:json.hover.use");
			checkboxClick = new CCheckBox("GUI:json.click.use");
		}

		comboboxMode = new ChoiceComboBox("json.mode", new String[] { "text", "score", "entity" }, false);
		comboboxColor = new ChoiceComboBox("color", colors, false);
		comboboxColor.setSelected("white");

		panelEntity = new TargetSelectionPanel("GUI:json.display.entity", CGConstants.ENTITIES_PLAYERS);
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
				@Override
				public void actionPerformed(ActionEvent e)
				{
					panelClick.setEnabledContent(checkboxClick.isSelected());
				}
			});
			checkboxHover.addActionListener(new ActionListener() {
				@Override
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
			@Override
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
			if (tag.getId().equals("color")) this.comboboxColor.setSelected(((TagString) tag).getValue());

			if (tag.getId().equals("text")) entryText.setTextField(((TagString) tag).getValue());
			if (tag.getId().equals("score"))
			{
				comboboxMode.setSelected("score");
				panelScore.setup((TagCompound) tag);
			}
			if (tag.getId().equals("selector"))
			{
				comboboxMode.setSelected("entity");
				panelEntity.setupFrom(ObjectCreator.generateTarget(((TagString) tag).getValue()));
			}
		}

		entryText.setVisible(comboboxMode.getSelectedIndex() == 0);
		panelScore.setVisible(comboboxMode.getSelectedIndex() == 1);
		panelEntity.setVisible(comboboxMode.getSelectedIndex() == 2);
	}
}
