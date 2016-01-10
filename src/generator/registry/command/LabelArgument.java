package generator.registry.command;

import generator.gui.CTextArea;
import generator.main.GenerationException;
import generator.main.Text;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

/** This is not an actual Argument ; it will only display some text. */
public class LabelArgument extends Argument
{
	/** ID of the text to display. */
	private Text text;
	/** Text Area to display the text. */
	private CTextArea textArea;

	/** Creates a new Label Argument.
	 * 
	 * @param text - The ID of the text to display. */
	public LabelArgument(Text text)
	{
		super(false, 0);
		this.text = text;
	}

	@Override
	public void createGui()
	{
		this.textArea = new CTextArea(this.text.getValue());
		this.textArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}

	@Override
	public String generate() throws GenerationException
	{
		return null;
	}

	@Override
	public Component getComponent()
	{
		return this.textArea;
	}

	@Override
	public void updateLang()
	{
		if (this.textArea != null) this.textArea.setText(this.text.getValue());
	}

}
