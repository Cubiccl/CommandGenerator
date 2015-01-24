package commandGenerator.gui.helper.argumentSelection.dataTag;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import commandGenerator.arguments.tags.Tag;
import commandGenerator.arguments.tags.TagList;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class TradesSelectionPanel extends HelperPanel
{

	private CButton buttonAdd, buttonRemove;
	private JEditorPane editorpane;
	private JScrollPane scrollpane;
	private List<Tag> tags;

	public TradesSelectionPanel(String title)
	{
		super(CGConstants.DATAID_NONE, title, 400, 200);

		tags = new ArrayList<Tag>();

		buttonAdd = new CButton("GUI:trade.add");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TradeSelectionPanel panel = new TradeSelectionPanel("GUI:trade.add");
				JScrollPane scrollpane = new JScrollPane(panel);
				scrollpane.getHorizontalScrollBar().setUnitIncrement(20);
				scrollpane.getVerticalScrollBar().setUnitIncrement(20);
				scrollpane.setPreferredSize(new Dimension(800, 500));
				if (DisplayHelper.showQuestion(scrollpane, Lang.get("GUI:trade.add"))) return;
				tags.add(panel.generateTrade());
				displayTrades();
			}
		});
		buttonRemove = new CButton("GUI:trade.remove");
		buttonRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				TagSelection.askRemove(tags);
				displayTrades();
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
		gbc.gridy++;
		add(buttonRemove, gbc);

		gbc.gridx++;
		gbc.gridy--;
		gbc.gridheight = 2;
		add(scrollpane, gbc);
		gbc.gridheight = 1;
	}

	private void displayTrades()
	{
		String text = "";

		for (int i = 0; i < tags.size(); i++)
		{
			if (i != 0) text += ",";
			text += tags.get(i).display(CGConstants.DETAILS_ALL, 0);
		}

		editorpane.setText(text);
	}

	public List<Tag> getSelectedTrades()
	{

		TagList list = new TagList("Recipes") {
			public void askValue()
			{}
		};
		list.setValue(tags);
		List<Tag> array = new ArrayList<Tag>();
		array.add(list);

		return array;
	}

	@Override
	public void updateLang()
	{}

}
