package generator.gui.combobox;

import generator.gui.RoundedCornerBorder;
import generator.main.Constants;

import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxUI;

@SuppressWarnings("serial")
public class CCombobox extends JComboBox<String>
{

	public CCombobox(String[] values)
	{
		super(values);
		this.setBorder(new RoundedCornerBorder());
		this.setRenderer(new CListCellRenderer());
		this.setUI(new BasicComboBoxUI());
		this.setBackground(Constants.BACKGROUND_NORMAL);
	}

	public void setValues(String[] values)
	{
		this.setModel(new JComboBox<String>(values).getModel());
	}

}
