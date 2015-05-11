package commandGenerator.gui.helper.components;

import java.awt.Dimension;

import javax.swing.JLabel;

import commandGenerator.Generator;
import commandGenerator.gui.helper.GuiHandler;

@SuppressWarnings("serial")
public class CLabel extends JLabel implements CComponent
{

	private boolean html;
	private String title;

	public CLabel(String title)
	{
		this(title, false);
	}

	public CLabel(String title, boolean html)
	{
		super(title);
		this.title = title;
		this.html = html;

		if (this.html) this.setText("<html>" + Generator.translate(this.title).replaceAll("\n", "<br />") + "</html>");
		else this.setText(Generator.translate(this.title));

		if (!this.html) this.setPreferredSize(new Dimension(this.getText().length() * 7, 20));
		if (!this.html) this.setMinimumSize(new Dimension(this.getText().length() * 7, 20));

		this.setFont(GuiHandler.DEFAULT_FONT);
	}

	@Override
	public void reset()
	{}

	@Override
	public void setEnabledContent(boolean enable)
	{
		this.setEnabled(enable);
	}

	public void setTitle(String id)
	{
		this.title = id;
		this.updateLang();
	}

	@Override
	public void updateLang()
	{
		if (this.html) this.setText("<html>" + Generator.translate(this.title).replaceAll("\n", "<br />") + "</html>");
		else this.setText(Generator.translate(this.title));
	}

	@Override
	public void setSize(Dimension size)
	{
		this.setPreferredSize(size);
		this.setMinimumSize(size);
	}

}
