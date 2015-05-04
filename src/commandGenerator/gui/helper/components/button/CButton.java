package commandGenerator.gui.helper.components.button;

import java.awt.Dimension;

import commandGenerator.main.Lang;

@SuppressWarnings("serial")
public class CButton extends BaseButton
{

	private boolean translates;
	private String text;

	public CButton(String text)
	{
		this(text, true);
	}

	public CButton(String text, boolean translates)
	{
		super(text);
		this.text = text;
		this.translates = translates;
		this.setSize(new Dimension(200, 20));
		this.updateLang();
	}

	public void setTitle(String text)
	{
		this.text = text;
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		if (this.translates) this.setText(Lang.get(this.text));
	}

}
