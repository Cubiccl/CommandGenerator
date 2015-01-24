package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.EntitySelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.gui.helper.components.LangComboBox;
import commandGenerator.main.CGConstants;
import commandGenerator.main.Resources;

@SuppressWarnings("serial")
public class JsonTextSelectionPanel extends HelperPanel
{

	private CEntry entryText, entryInsertion;
	private CCheckBox checkboxBold, checkboxUnderlined, checkboxItalic, checkboxStrikethrough, checkboxObfuscated, checkboxHover, checkboxClick;
	private LangComboBox comboboxMode, comboboxColor;
	private EntitySelectionPanel panelEntity;
	private ScoreDisplayPanel panelScore;
	private HoverEventPanel panelHover;
	private ClickEventPanel panelClick;
	private boolean events;

	public JsonTextSelectionPanel(String title, boolean events)
	{
		super(CGConstants.DATAID_NONE, title, 1200, 800);
		this.events = events;

		entryText = new CEntry(CGConstants.DATAID_NONE, "GUI:json.text");
		entryInsertion = new CEntry(CGConstants.DATAID_NONE, "GUI:json.insertion");

		checkboxBold = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.bold");
		checkboxUnderlined = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.underlined");
		checkboxItalic = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.italic");
		checkboxStrikethrough = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.strikethrough");
		checkboxObfuscated = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.obfuscated");
		if (events) checkboxHover = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.hover.use");
		if (events) checkboxClick = new CCheckBox(CGConstants.DATAID_NONE, "GUI:json.click.use");

		comboboxMode = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:json.mode", 3);
		comboboxMode.setPreferredSize(new Dimension(200, 20));
		comboboxMode.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				entryText.setVisible(comboboxMode.getSelectedIndex() == 0);
				panelScore.setVisible(comboboxMode.getSelectedIndex() == 1);
				panelEntity.setVisible(comboboxMode.getSelectedIndex() == 2);
			}
		});
		comboboxColor = new LangComboBox(CGConstants.DATAID_NONE, "RESOURCES:color", 16);
		comboboxColor.setPreferredSize(new Dimension(200, 20));
		comboboxColor.setSelectedIndex(15);

		panelEntity = new EntitySelectionPanel(CGConstants.DATAID_NONE, "GUI:json.display.entity", CGConstants.ENTITIES_PLAYERS);
		panelEntity.setVisible(false);
		panelScore = new ScoreDisplayPanel("GUI:json.display.score");
		panelScore.setVisible(false);
		if (events) panelHover = new HoverEventPanel("GUI:json.hover");
		if (events) panelClick = new ClickEventPanel("GUI:json.click");

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(comboboxMode, gbc);
		gbc.gridy++;
		gbc.gridheight = 5;
		add(entryText, gbc);
		add(panelEntity, gbc);
		add(panelScore, gbc);
		gbc.gridheight = 1;
		gbc.gridy = 8;
		if (events) add(checkboxHover, gbc);
		gbc.gridy++;
		if (events) add(panelHover, gbc);

		gbc.gridx++;
		gbc.gridy = 0;
		add(comboboxColor, gbc);
		gbc.gridy++;
		add(checkboxBold, gbc);
		gbc.gridy++;
		add(checkboxUnderlined, gbc);
		gbc.gridy++;
		add(checkboxItalic, gbc);
		gbc.gridy++;
		add(checkboxStrikethrough, gbc);
		gbc.gridy++;
		add(checkboxObfuscated, gbc);
		gbc.gridy++;
		add(entryInsertion, gbc);
		gbc.gridy++;
		add(entryInsertion, gbc);
		gbc.gridy++;
		if (events) add(checkboxClick, gbc);
		gbc.gridy++;
		if (events) add(panelClick, gbc);
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
				tag.addTag(new TagString("selector").setValue(panelEntity.generateEntity().display()));
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
