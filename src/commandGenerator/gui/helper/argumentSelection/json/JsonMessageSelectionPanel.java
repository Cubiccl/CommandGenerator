package commandGenerator.gui.helper.argumentSelection.json;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagCompound;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.dataTag.TagSelection;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class JsonMessageSelectionPanel extends HelperPanel
{

	private CButton buttonAdd, buttonRemove;
	private JEditorPane editorpane;
	private JScrollPane scrollpane;
	private List<Tag> texts;

	public JsonMessageSelectionPanel(String title)
	{
		super(CGConstants.PANELID_JSON, title, 600, 200);

		texts = new ArrayList<Tag>();

		buttonAdd = new CButton("GUI:json.add");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JsonTextSelectionPanel panel = new JsonTextSelectionPanel("GUI:json.title", true);
				JScrollPane scrollpane = new JScrollPane(panel);
				scrollpane.setPreferredSize(new Dimension(1000, 600));
				scrollpane.getVerticalScrollBar().setUnitIncrement(20);
				scrollpane.getHorizontalScrollBar().setUnitIncrement(20);
				if (DisplayHelper.showQuestion(scrollpane, Lang.get("GUI:json.title"))) return;
				TagCompound json = panel.getTag();
				if (json != null) texts.add(json);
				displayText();
			}
		});
		buttonRemove = new CButton("GUI:json.remove");
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TagSelection.askRemove(texts);
				displayText();
			}
		});

		editorpane = new JEditorPane("text/html", "");
		editorpane.setEditable(false);
		editorpane.setPreferredSize(new Dimension(200, 120));

		scrollpane = new JScrollPane(editorpane);
		scrollpane.setBorder(BorderFactory.createLineBorder(Color.MAGENTA));

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(buttonAdd, gbc);
		gbc.gridx++;
		add(scrollpane, gbc);
	}

	private void displayText()
	{
		String text = "";
		for (int i = 0; i < texts.size(); i++)
		{

			if (i != 0) text += "<br />";
			text += texts.get(i).display(CGConstants.DETAILS_ALL, 0);
		}
		editorpane.setText(text);
	}

	public TagCompound generateMessage()
	{
		DisplayHelper.log("Generating JSON message");
		TagCompound message = new TagCompound() {
			public void askValue()
			{}
		};
		message.addTag(new TagString("text").setValue(""));
		TagList list = new TagList("extra") {
			public void askValue()
			{}
		};
		list.setValue(texts);
		message.addTag(list);
		return message;

	}

	@Override
	public void updateLang()
	{
		updateTitle();
		buttonAdd.updateLang();
		buttonRemove.updateLang();
	}

	@SuppressWarnings("unchecked")
	public void setupFrom(Map<String, Object> data)
	{
		if (data.get(getPanelId()) == null)
		{
			reset();
			return;
		}
		texts = (List<Tag>) data.get(getPanelId());
		displayText();
	}

}
