package generator.gui.panel.object;

import generator.gui.checkbox.CCheckbox;
import generator.gui.panel.CPanel;
import generator.gui.textfield.CEntry;
import generator.main.GenerationException;
import generator.main.Text;
import generator.registry.instance.Coordinates;

import javax.swing.BorderFactory;

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
	private Text title;

	public PanelCoordinates(Text title, boolean relative)
	{
		super();
		this.title = title;
		this.relative = relative;

		// Creating components
		this.entryX = new CEntry(new Text("GUI", "coordinates.x"));
		this.entryY = new CEntry(new Text("GUI", "coordinates.y"));
		this.entryZ = new CEntry(new Text("GUI", "coordinates.z"));

		if (this.relative)
		{
			this.checkboxXRel = new CCheckbox(new Text("GUI", "coordinates.relative"));
			this.checkboxYRel = new CCheckbox(new Text("GUI", "coordinates.relative"));
			this.checkboxZRel = new CCheckbox(new Text("GUI", "coordinates.relative"));
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
			throw new GenerationException(new Text("GUI", "error.number", false).addReplacement("<value>", this.entryX.getText()));
		}

		try
		{
			y = Float.parseFloat(this.entryY.getText());
		} catch (Exception e)
		{
			throw new GenerationException(new Text("GUI", "error.number", false).addReplacement("<value>", this.entryY.getText()));
		}

		try
		{
			z = Float.parseFloat(this.entryZ.getText());
		} catch (Exception e)
		{
			throw new GenerationException(new Text("GUI", "error.number", false).addReplacement("<value>", this.entryZ.getText()));
		}

		if (this.relative) return new Coordinates(x, y, z, this.checkboxXRel.isSelected(), this.checkboxYRel.isSelected(), this.checkboxZRel.isSelected());
		return new Coordinates(x, y, z);
	}

	@Override
	public void updateLang()
	{
		super.updateLang();
		this.setBorder(BorderFactory.createTitledBorder(this.title.getValue()));
	}

}
