package generator.gui.combobox;

import generator.gui.RoundedCornerBorder;
import generator.main.Utils;

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
		this.setBackground(Utils.BACKGROUND_NORMAL);
	}

	/** Changes the values of this box.
	 * 
	 * @param values - The new values. */
	public void setValues(String[] values)
	{
		this.setModel(new JComboBox<String>(values).getModel());
	}

}
