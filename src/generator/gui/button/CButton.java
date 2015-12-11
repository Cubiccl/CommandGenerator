package generator.gui.button;

import generator.CommandGenerator;
import generator.gui.RoundedCornerBorder;
import generator.interfaces.ITranslate;
import generator.main.Constants;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class CButton extends JButton implements ITranslate
{
	private String textID;

	public CButton(String textID)
	{
		super("");
		this.textID = textID;
		this.setFont(Constants.font);
		this.setBorder(new RoundedCornerBorder());
		this.setUI(new CButtonUI());
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
