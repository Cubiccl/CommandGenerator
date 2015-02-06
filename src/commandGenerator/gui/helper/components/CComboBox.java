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

import commandGenerator.arguments.objects.ObjectBase;
import commandGenerator.main.CGConstants;

@SuppressWarnings("serial")
public class CComboBox extends JPanel implements CComponent
{

	private JComboBox<String> box;
	private List<ObjectBase> displayed = new ArrayList<ObjectBase>();
	private String id;
	private CLabel label;
	private ObjectBase[] objects;
	private IBox parent;
	private JTextField text;

	public CComboBox(String id, String title, ObjectBase[] objects, IBox parent)
	{
		super(new GridBagLayout());

		String[] names = new String[objects.length];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = objects[i].getName();
			displayed.add(objects[i]);
		}

		text = new JTextField(18);
		text.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0)
			{}

			public void keyReleased(KeyEvent e)
			{
				search(e);
			}

			public void keyTyped(KeyEvent arg0)
			{}
		});
		text.setPreferredSize(new Dimension(200, 20));
		text.setMinimumSize(new Dimension(200, 20));

		label = new CLabel(title);

		box = new JComboBox<String>(names);
		box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				boxSelect();
			}
		});
		box.setPreferredSize(new Dimension(200, 20));
		box.setMinimumSize(new Dimension(200, 20));

		this.objects = objects;
		this.parent = parent;
		this.id = id;

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

		setPreferredSize(new Dimension(label.getPreferredSize().width + 202, 42));
		setMinimumSize(new Dimension(label.getMinimumSize().width + 202, 42));
	}

	private void boxSelect()
	{
		if (parent != null) parent.updateCombobox();
		ObjectBase selected = displayed.get(box.getSelectedIndex());
		displayed.clear();
		text.setText("");
		String[] names = new String[objects.length];
		for (int i = 0; i < objects.length; i++)
		{
			names[i] = objects[i].getName();
			displayed.add(objects[i]);
		}
		box.setModel(new JComboBox<String>(names).getModel());
		box.setSelectedItem(selected.getName());
	}

	/** Returns the selected index. */
	public int getIndex()
	{
		return box.getSelectedIndex();
	}

	/** Returns the selected Object. */
	public ObjectBase getValue()
	{
		if (box.getSelectedItem() == null) return null;
		return displayed.get(box.getSelectedIndex());
	}

	@Override
	public void reset()
	{
		text.setText("");
		box.setSelectedIndex(0);
	}

	protected void search(KeyEvent key)
	{

		if (text.getText() == null || text.getText() == "") return;

		if (key != null && key.getKeyCode() == 10 && box.getSelectedItem() != null)
		{
			ObjectBase selected = displayed.get(box.getSelectedIndex());
			displayed.clear();
			text.setText("");
			String[] names = new String[objects.length];
			for (int i = 0; i < objects.length; i++)
			{
				names[i] = objects[i].getName();
				displayed.add(objects[i]);
			}
			box.setModel(new JComboBox<String>(names).getModel());
			box.setSelectedItem(selected.getName());
			if (parent != null) parent.updateCombobox();
			return;
		}

		List<String> names = new ArrayList<String>();
		displayed.clear();
		for (ObjectBase test : objects)
		{
			if (test.getName().toLowerCase().contains(text.getText().toLowerCase()))
			{
				names.add(test.getName());
				displayed.add(test);
			}
		}
		box.setModel(new JComboBox<String>(names.toArray(new String[0])).getModel());

	}

	public void setData(ObjectBase[] objects)
	{
		this.objects = objects;
		text.setText("");
		search(null);
		box.setPreferredSize(new Dimension(200, 20));
	}

	public void setEnabledContent(boolean enable)
	{
		label.setEnabled(enable);
		box.setEnabled(enable);
		text.setEnabled(enable);
	}

	public void setSelected(ObjectBase object)
	{
		search(null);
		box.setSelectedItem(object.getName());
	}

	public void setupFrom(Map<String, Object> data)
	{
		if (!id.equals(CGConstants.DATAID_NONE)) setSelected((ObjectBase) data.get(id));
	}

	public void updateLang()
	{
		label.updateLang();
		displayed.clear();
		String[] names = new String[objects.length];
		for (int i = 0; i < names.length; i++)
		{
			names[i] = objects[i].getName();
			displayed.add(objects[i]);
		}
		box.setModel(new JComboBox<String>(names).getModel());
	}

}
