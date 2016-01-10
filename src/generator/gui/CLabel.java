package generator.gui;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;
import generator.main.Text;
import generator.main.Utils;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CLabel extends JLabel implements ITranslate
{
	private Text text;

	public CLabel(Text text)
	{
		super("");
		this.text = text;
		this.setFont(Utils.font);
		this.updateLang();
	}

	/** @see CLabel#setText(Text) */
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
		if (text != null && !text.equals("")) super.setText(CommandGenerator.translate(this.text));
	}

}
