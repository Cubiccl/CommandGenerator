package generator.registry.command;

import generator.CommandGenerator;
import generator.gui.CTextArea;
import generator.main.GenerationException;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

/** This is not an actual Argument ; it will only display some text. */
public class LabelArgument extends Argument
{
	/** Text Area to display the text. */
	private CTextArea textArea;
	/** ID of the text to display. */
	private String textID;

	/** Creates a new Label Argument.
	 * 
	 * @param textID - The ID of the text to display. */
	public LabelArgument(String textID)
	{
		super(false, 0);
		this.textID = textID;
	}

	@Override
	public void createGui()
	{
		this.textArea = new CTextArea(CommandGenerator.translate("GUI:" + this.textID));
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
		if (this.textArea != null) this.textArea.setText(CommandGenerator.translate("GUI:" + this.textID));
	}

}
