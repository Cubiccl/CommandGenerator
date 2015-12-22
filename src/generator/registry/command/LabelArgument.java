package generator.registry.command;

import generator.CommandGenerator;
import generator.gui.CTextArea;
import generator.main.GenerationException;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

public class LabelArgument extends Argument
{
	private CTextArea textArea;
	private String textID;

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
