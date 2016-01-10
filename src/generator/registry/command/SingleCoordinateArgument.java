package generator.registry.command;

import generator.gui.checkbox.CCheckbox;
import generator.main.GenerationException;
import generator.main.Text;

/** A single coordinate (x, y or z) */
public class SingleCoordinateArgument extends NumberArgument
{
	/** Checkbox to indicate it the coordinate is relative. */
	private CCheckbox checkboxRelative;

	/** Creates a new Single Coordinate argument
	 * 
	 * @param isCompulsory - True if it is compulsory.
	 * @param textID - The ID of the text. */
	public SingleCoordinateArgument(boolean isCompulsory, Text textID)
	{
		super(isCompulsory, textID);
	}

	@Override
	public void createGui()
	{
		super.createGui();
		this.checkboxRelative = new CCheckbox(new Text("GUI", "coordinates.relative"));
		this.panel.add(this.checkboxRelative);
	}

	@Override
	public String generate() throws GenerationException
	{
		return this.checkboxRelative.isSelected() ? "~" + super.generate() : super.generate();
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		if (this.checkboxRelative != null) this.checkboxRelative.updateLang();
	}

}
