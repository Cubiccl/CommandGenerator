package commandGenerator.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import commandGenerator.arguments.objects.SavedObjects;
import commandGenerator.gui.helper.components.CButton;
import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class SavedObjectsPanel extends JPanel
{

	private CButton buttonAdd, buttonEdit, buttonRemove;
	private JList<String> listTypes, listObjects;
	private JEditorPane pane;
	private JScrollPane scrollbarText, scrollbarObjects;
	private GridBagConstraints gbc = new GridBagConstraints();

	public SavedObjectsPanel()
	{
		super(new GridLayout(1, 3));
		((GridLayout) getLayout()).setHgap(10);

		buttonAdd = new CButton("GENERAL:add_only");
		buttonEdit = new CButton("GENERAL:edit_only");
		buttonRemove = new CButton("GENERAL:remove");

		listTypes = new JList<String>(translateTypes());
		listTypes.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		listTypes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		listObjects = new JList<String>(new String[] { "loul", "yolo" });
		listObjects.setBorder(BorderFactory.createLineBorder(Color.blue));
		listObjects.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		pane = new JEditorPane("text/html", "Descritption!");
		pane.setEditable(false);

		scrollbarText = new JScrollPane(pane);
		scrollbarObjects = new JScrollPane(listObjects);
		scrollbarObjects.getVerticalScrollBar().setUnitIncrement(20);

		add(listTypes);
		add(scrollbarObjects);
		add(scrollbarText);

		JPanel subPanel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		subPanel.add(buttonAdd, gbc);
		gbc.gridy++;
		subPanel.add(buttonEdit, gbc);
		gbc.gridy++;
		subPanel.add(buttonRemove, gbc);
		add(subPanel);
	}

	private String[] translateTypes()
	{
		String[] names = new String[SavedObjects.typeNames.length];
		for (int i = 0; i < names.length; i++)
			names[i] = Lang.get("GENERAL:" + SavedObjects.typeNames[i]);
		return names;
	}

}
