package commandGenerator.gui.helper.components;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class TextCombobox extends JPanel implements CComponent
{

	private String id;
	private IBox parent;
	private JTextField text;
	private CLabel label;
	private JComboBox<String> box;
	private String[] names;

	public TextCombobox(String id, String title, String[] names, final IBox parent)
	{
		super(new GridBagLayout());
		text = new JTextField(18);
		text.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0)
			{}

			public void keyReleased(KeyEvent e)
			{
				search(e);
			}

			public void keyPressed(KeyEvent arg0)
			{}
		});

		label = new CLabel(title);
		box = new JComboBox<String>(names);
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				if (parent != null) parent.updateCombobox();
			}
		});
		box.setPreferredSize(new Dimension(200, 20));
		this.names = names;
		this.parent = parent;
		this.id = id;

		setPreferredSize(new Dimension(300, 60));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		add(label, gbc);
		gbc.gridx++;
		gbc.gridheight = 1;
		add(text, gbc);
		gbc.gridy++;
		add(box, gbc);
	}

	protected void search(KeyEvent key)
	{

		if (text.getText() == null || text.getText() == "") return;

		if (key != null && key.getKeyCode() == 10 && box.getSelectedItem() != null)
		{
			String selected = (String) box.getSelectedItem();
			if (parent != null)
			{
				text.setText("");
				parent.updateCombobox();
				box.setModel(new JComboBox<String>(names).getModel());
				box.setSelectedItem(selected);
			}
			return;
		}

		List<String> matching = new ArrayList<String>();
		for (String test : names)
			if (test.contains(text.getText())) matching.add(test);

		box.setModel(new JComboBox<String>(matching.toArray(new String[0])).getModel());

	}

	public void updateLang()
	{
		label.updateLang();
	}

	/** Returns the selected Object. */
	public String getValue()
	{
		if (box.getSelectedItem() == null) return null;
		return (String) box.getSelectedItem();
	}

	/** Returns the selected index. */
	public int getIndex()
	{
		return box.getSelectedIndex();
	}

	public void setData(String[] names)
	{
		this.names = names;
		text.setText("");
		search(null);
		box.setPreferredSize(new Dimension(200, 20));
	}

	public void setSelected(String selection)
	{
		box.setSelectedItem(selection);
	}

	public void setEnabledContent(boolean enable)
	{
		text.setEnabled(enable);
		label.setEnabled(enable);
		box.setEnabled(enable);
	}

	@Override
	public void setupFrom(Map<String, Object> data)
	{
		if (!id.equals(CGConstants.DATAID_NONE)) box.setSelectedIndex((int) data.get(id));
	}

	@Override
	public void reset()
	{
		text.setText("");
		box.setSelectedIndex(0);
	}

}
