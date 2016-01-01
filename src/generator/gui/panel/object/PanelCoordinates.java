package generator.gui.panel.object;

import javax.swing.BorderFactory;

import generator.CommandGenerator;
import generator.gui.checkbox.CCheckbox;
import generator.gui.panel.CPanel;
import generator.gui.textfield.CEntry;
import generator.main.GenerationException;
import generator.registry.instance.Coordinates;

@SuppressWarnings("serial")
public class PanelCoordinates extends CPanel
{
	/** Checkboxes to indicate if x, y or z are relative. */
	private CCheckbox checkboxXRel, checkboxYRel, checkboxZRel;
	/** Entries to input the x, y and z positions. */
	private CEntry entryX, entryY, entryZ;
	/** True if the Coordinates can be relative. */
	private boolean relative;
	/** The ID of the text for this panel's title. */
	private String titleID;

	public PanelCoordinates(String titleID, boolean relative)
	{
		super();
		this.titleID = titleID;
		this.relative = relative;

		// Creating components
		this.entryX = new CEntry("GUI:coordinates.x");
		this.entryY = new CEntry("GUI:coordinates.y");
		this.entryZ = new CEntry("GUI:coordinates.z");

		if (this.relative)
		{
			this.checkboxXRel = new CCheckbox("GUI:coordinates.relative");
			this.checkboxYRel = new CCheckbox("GUI:coordinates.relative");
			this.checkboxZRel = new CCheckbox("GUI:coordinates.relative");
		}

		// Adding components
		this.add(this.entryX, this.gbc);
		this.gbc.gridy++;
		this.add(this.entryY, this.gbc);
		this.gbc.gridy++;
		this.add(this.entryZ, this.gbc);

		if (this.relative)
		{
			this.gbc.gridx++;
			this.gbc.gridy = 0;
			this.add(this.checkboxXRel, this.gbc);
			this.gbc.gridy++;
			this.add(this.checkboxYRel, this.gbc);
			this.gbc.gridy++;
			this.add(this.checkboxZRel, this.gbc);
		}

		this.updateLang();
	}

	public Coordinates generateCoordinates() throws GenerationException
	{
		float x = 0, y = 0, z = 0;

		try
		{
			x = Float.parseFloat(this.entryX.getText());
		} catch (Exception e)
		{
			throw new GenerationException(CommandGenerator.translate("GUI:error.number").replaceAll("<value>", this.entryX.getText()));
		}

		try
		{
			y = Float.parseFloat(this.entryY.getText());
		} catch (Exception e)
		{
			throw new GenerationException(CommandGenerator.translate("GUI:error.number").replaceAll("<value>", this.entryY.getText()));
		}

		try
		{
			z = Float.parseFloat(this.entryZ.getText());
		} catch (Exception e)
		{
			throw new GenerationException(CommandGenerator.translate("GUI:error.number").replaceAll("<value>", this.entryZ.getText()));
		}

		if (this.relative) return new Coordinates(x, y, z, this.checkboxXRel.isSelected(), this.checkboxYRel.isSelected(), this.checkboxZRel.isSelected());
		return new Coordinates(x, y, z);
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		this.setBorder(BorderFactory.createTitledBorder(CommandGenerator.translate(this.titleID)));
	}

}
