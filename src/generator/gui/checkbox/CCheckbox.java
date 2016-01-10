package generator.gui.checkbox;

import generator.gui.RoundedCornerBorder;
import generator.gui.button.CButton;
import generator.interfaces.ITranslate;
import generator.main.Text;
import generator.main.Utils;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class CCheckbox extends JCheckBox implements ITranslate
{
	private Text text;

	public CCheckbox(Text text)
	{
		super("");
		this.text = text;
		this.setFont(Utils.font);
		this.setBorder(new RoundedCornerBorder());
		this.setBorderPainted(true);
		this.setUI(new CCheckboxUI());
		this.updateLang();
	}

	/** @see CButton#setText(Text) */
	@Override
	@Deprecated
	public void setText(String text)
	{}

	public void setText(Text text)
	{
		this.text = text;
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		if (!this.text.getValue().equals("")) super.setText(this.text.getValue());
	}

}
