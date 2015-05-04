package commandGenerator.gui.helper.components.combobox;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import commandGenerator.gui.helper.components.icomponent.IBox;
import commandGenerator.gui.helper.components.listeners.ClickListener;
import commandGenerator.gui.helper.components.listeners.IClick;

@SuppressWarnings("serial")
public class SearchComboBox extends JPanel implements IClick
{
	private JComboBox<String> combobox;
	private JTextField textfield;
	private String[] values;
	private IBox parent;

	public SearchComboBox(String[] values, IBox parent)
	{
		super(new GridLayout(2, 1));

		this.values = values;
		this.parent = parent;

		this.combobox = new JComboBox<String>(values);
		this.combobox.addActionListener(new ClickListener(this));

		this.textfield = new JTextField(18);
		this.textfield.addKeyListener(new WriteListener(this));

		this.add(this.textfield);
		this.add(this.combobox);
		this.setSize(new Dimension(200, 20));
	}

	@Override
	public void click()
	{
		this.validateSearch();
	}

	public void write(int keyCode)
	{
		if (keyCode == KeyEvent.VK_ENTER) this.validateSearch();
		else this.search();
	}

	private void search()
	{
		String search = this.textfield.getText();
		if (search == null || search.equals(""))
		{
			this.reset();
			return;
		}
		search = search.toLowerCase();

		ArrayList<String> matching = new ArrayList<String>();
		for (String object : this.values)
		{
			if (object.toString().toLowerCase().contains(search)) matching.add(object);
		}

		this.display(matching.toArray(new String[0]));
	}

	public void reset()
	{
		this.combobox.setModel(new JComboBox<String>(this.values).getModel());
		this.textfield.setText("");
	}

	private void validateSearch()
	{
		String selection = (String) this.combobox.getSelectedItem();
		this.reset();
		this.combobox.setSelectedItem(selection);
		this.parent.updateCombobox();
	}

	@Override
	public void setSize(Dimension size)
	{
		this.setPreferredSize(size);
		this.setMinimumSize(size);
	}

	public int getSelectedIndex()
	{
		return this.combobox.getSelectedIndex();
	}

	public Object getSelectedItem()
	{
		return this.combobox.getSelectedItem();
	}

	public void setEnabledContent(boolean enable)
	{
		this.combobox.setEnabled(enable);
		this.textfield.setEnabled(enable);
	}

	public void setSelectedItem(String name)
	{
		this.combobox.setSelectedItem(name);
	}

	private void display(String[] names)
	{
		this.combobox.setModel(new JComboBox<String>(names).getModel());
	}

	public void setValues(String[] values)
	{
		this.values = values;
		this.reset();
	}

}
