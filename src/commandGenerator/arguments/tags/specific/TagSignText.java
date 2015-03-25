package commandGenerator.arguments.tags.specific;

import java.util.regex.Matcher;

import javax.swing.JOptionPane;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.tags.DataTags;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.arguments.tags.TagString;
import commandGenerator.gui.helper.argumentSelection.dataTag.ListSelectionPanel;
import commandGenerator.main.Lang;

public class TagSignText extends TagString
{

	public TagSignText(String id, String... applicable)
	{
		super(id, applicable);
	}

	@Override
	public void askValue()
	{
		int choice = JOptionPane.showOptionDialog(null, Lang.get("GUI:text.choice"), getName(), JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				new String[] { Lang.get("GENERAL:text"), Lang.get("GENERAL:json") }, Lang.get("GENERAL:text"));

		if (choice == 0)
		{
			super.askValue();
			return;
		}

		panel = new ListSelectionPanel("GUI:json", ObjectBase.JSON);
		if (getValue() != null && getValue().startsWith("[{") && getValue().endsWith("}]")) ((ListSelectionPanel) panel).setList(DataTags
				.generateListFrom(getValue().replaceAll(Matcher.quoteReplacement("\\n"), "\n").replaceAll(Matcher.quoteReplacement("\\\""), "\"")));

		if (showPanel()) return;

		TagList list = new TagList() {
			@Override
			public void askValue()
			{}
		}.setValue(((ListSelectionPanel) panel).getList());

		setValue(list.commandStructure().replaceAll("\n", Matcher.quoteReplacement("\\n")).replaceAll("\"", Matcher.quoteReplacement("\\\"")));
	}
}
