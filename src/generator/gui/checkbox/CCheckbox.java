package generator.gui.checkbox;

import generator.CommandGenerator;
import generator.gui.RoundedCornerBorder;
import generator.interfaces.ITranslate;
import generator.main.Constants;

import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class CCheckbox extends JCheckBox implements ITranslate
{
	private String textID;

	public CCheckbox(String textID)
	{
		super("");
		this.textID = textID;
		this.setFont(Constants.font);
		this.setBorder(new RoundedCornerBorder());
		this.setBorderPainted(true);
		this.setUI(new CCheckboxUI());
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
