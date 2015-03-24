package commandGenerator.gui.helper.components.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.arguments.objects.SavedObjects;

@SuppressWarnings("serial")
public class LoadPanel extends JPanel
{
	private Map<String, Object> displayed;
	private JList<String> list;
	private JEditorPane pane;
	private JScrollPane scrollPane, scrollList;

	public LoadPanel(byte type)
	{
		super(new GridLayout(1, 2));
		displayed = SavedObjects.getList(type);

		String[] names = new String[displayed.size()];
		for (int i = 0; i < names.length; i++)
			names[i] = displayed.keySet().toArray(new String[0])[i];

		list = new JList<String>(names);
		list.setBorder(BorderFactory.createLineBorder(Color.blue));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				display();
			}
		});

		pane = new JEditorPane("text/html", "");
		pane.setEditable(false);
		pane.setMinimumSize(new Dimension(300, 300));
		pane.setPreferredSize(new Dimension(300, 300));

		scrollPane = new JScrollPane(pane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollList = new JScrollPane(list);
		scrollList.getVerticalScrollBar().setUnitIncrement(20);
		
		add(scrollList);
		add(scrollPane);
	}

	private void display()
	{
		pane.setText(ObjectBase.display(displayed.get(list.getSelectedValue())));
	}

	public Object getSelection()
	{
		return displayed.get(list.getSelectedValue());
	}

}
