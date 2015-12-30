package generator.gui.textfield;

import generator.gui.RoundedCornerBorder;

import java.awt.Color;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CTextfield extends JTextField
{
	public static final Color BACKGROUND = Color.WHITE, BACKGROUND_DISABLED = Color.GRAY;

	public CTextfield()
	{
		super(15);
		this.setBorder(new RoundedCornerBorder());
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		super.setEnabled(enabled);
		if (this.isEnabled()) this.setBackground(BACKGROUND);
		else this.setBackground(BACKGROUND_DISABLED);
	}

}
