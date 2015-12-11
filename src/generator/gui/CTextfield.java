package generator.gui;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CTextfield extends JTextField
{

	public CTextfield()
	{
		super(15);
		this.setBorder(new RoundedCornerBorder());
	}

}
