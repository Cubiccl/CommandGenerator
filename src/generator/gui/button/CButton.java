package generator.gui.button;

import generator.gui.RoundedCornerBorder;
import generator.interfaces.ITranslate;
import generator.main.Text;
import generator.main.Utils;

import javax.swing.JButton;

/** Basic button. */
@SuppressWarnings("serial")
public class CButton extends JButton implements ITranslate
{
	/** The ID of the text to translate, or the text to display itself. */
	private Text text;

	public CButton(Text text)
	{
		super("");
		this.text = text;
		this.setFont(Utils.font);
		this.setBorder(new RoundedCornerBorder());
		this.setUI(new CButtonUI());
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
		if (!text.getValue().equals("")) super.setText(this.text.getValue());
	}

}
