package generator.gui.button;

import generator.CommandGenerator;
import generator.gui.RoundedCornerBorder;
import generator.interfaces.ITranslate;
import generator.main.Utils;

import javax.swing.JButton;

/** Basic button. */
@SuppressWarnings("serial")
public class CButton extends JButton implements ITranslate
{
	/** The ID of the text to translate, or the text to display itself. */
	private String textID;
	/** True if the text should be translated. */
	private boolean translated;

	public CButton(String textID)
	{
		this(textID, true);
	}

	public CButton(String textID, boolean translated)
	{
		super("");
		this.textID = textID;
		this.translated = translated;
		this.setFont(Utils.font);
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
		if (!this.translated) super.setText(this.textID);
		else if (textID != null && !textID.equals("")) super.setText(CommandGenerator.translate(this.textID));
	}

}
