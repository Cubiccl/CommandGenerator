package generator.gui;

import generator.CommandGenerator;
import generator.interfaces.ITranslate;
import generator.main.Utils;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CLabel extends JLabel implements ITranslate
{
	private String textID;

	public CLabel(String textID)
	{
		super("");
		this.textID = textID;
		this.setFont(Utils.font);
		this.updateLang();
	}

	@Override
	public void setText(String textID)
	{
		this.textID = textID;
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		if (textID != null && !textID.equals("")) super.setText(CommandGenerator.translate(this.textID));
	}

}
