package generator.gui.combobox;

import generator.gui.RoundedCornerBorder;
import generator.interfaces.IMouseManager;
import generator.interfaces.MouseManager;
import generator.main.Utils;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class CCombobox extends JComboBox<String> implements IMouseManager
{
	private MouseManager mouseManager;

	public CCombobox(String[] values)
	{
		super(values);
		this.setBorder(new RoundedCornerBorder());
		this.setRenderer(new CListCellRenderer());
		this.setUI(new CComboBoxUI());
		this.setBackground(Utils.BACKGROUND_NORMAL);
		this.mouseManager = new MouseManager(this);
		this.addMouseListener(this.mouseManager);
	}

	@Override
	public boolean isClicked()
	{
		return this.mouseManager.isClicked() || ((IMouseManager) this.ui).isClicked();
	}

	@Override
	public boolean isHovered()
	{
		return this.mouseManager.isHovered() || ((IMouseManager) this.ui).isHovered();
	}

	/** Changes the values of this box.
	 * 
	 * @param values - The new values. */
	public void setValues(String[] values)
	{
		this.setModel(new JComboBox<String>(values).getModel());
	}

}
