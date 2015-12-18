package generator.registry.command;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

import generator.gui.CTextArea;
import generator.gui.panel.CPanelVertical;
import generator.main.GenerationException;
import generator.main.Utils;
import generator.registry.ObjectDescribed;

/** A single way to use a Command. */
public class Structure extends ObjectDescribed
{
	private CPanelVertical component;
	private CTextArea textAreaDescription;

	public Structure(String id)
	{
		super(Utils.STRUCTURE, id);
		this.component = new CPanelVertical();
		this.textAreaDescription = new CTextArea();
		this.textAreaDescription.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.component.add(this.textAreaDescription);
	}

	/** @return The Component used to select the structure details. */
	public CPanelVertical getComponent()
	{
		return this.component;
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		this.textAreaDescription.setText(this.getDescription());
	}

	/** @return The generated command. */
	public String generate() throws GenerationException
	{
		// TODO Generate
		return "/command arg1 arg2 arg3";
	}

}
