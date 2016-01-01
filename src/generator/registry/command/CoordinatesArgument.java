package generator.registry.command;

import generator.gui.checkbox.CCheckbox;
import generator.gui.panel.CPanelVertical;
import generator.gui.panel.object.PanelCoordinates;
import generator.interfaces.ClickEvent;
import generator.interfaces.IClickEvent;
import generator.main.GenerationException;

import java.awt.AWTEvent;
import java.awt.Component;

/** An Argument to precise Coordinates in the world. */
public class CoordinatesArgument extends Argument implements IClickEvent
{
	private static final int ENABLE = 0;

	/** Checkbox if not compulsory. */
	private CCheckbox checkbox;
	/** The panel used to input the Coordinates. */
	private PanelCoordinates panelCoordinates;
	/** The main panel for the GUI. */
	private CPanelVertical panelMain;
	/** True if the Coordinates can be relative. */
	private boolean relative;
	/** The text ID of the Coordinates panel's title. */
	private String titleID;

	/** Creates a new Coordinates Argument.
	 * 
	 * @param isCompulsory - True if it is compulsory.
	 * @param titleID - The text ID of the Coordinates panel's title.
	 * @param relative - True if the Coordinates can be relative. */
	public CoordinatesArgument(boolean isCompulsory, String titleID, boolean relative)
	{
		super(isCompulsory, 3);
		this.titleID = "GUI:" + titleID;
		this.relative = relative;
	}

	@Override
	public void createGui()
	{
		this.panelMain = new CPanelVertical();
		this.panelCoordinates = new PanelCoordinates(this.titleID, this.relative);

		if (!this.isCompulsory())
		{
			this.checkbox = new CCheckbox("GUI:coordinates.use");
			this.checkbox.addActionListener(new ClickEvent(this, ENABLE));
			this.panelCoordinates.setEnabled(false);
			this.panelMain.add(this.checkbox);
		}

		this.panelMain.add(this.panelCoordinates);
	}

	@Override
	public String generate() throws GenerationException
	{
		return this.panelCoordinates.generateCoordinates().toCommand();
	}

	@Override
	public Component getComponent()
	{
		return this.panelMain;
	}

	@Override
	public void onEvent(int componentID, AWTEvent event)
	{
		switch (componentID)
		{
			case ENABLE:
				this.panelCoordinates.setEnabled(this.checkbox.isSelected());
				break;

			default:
				break;
		}
	}

	@Override
	public void updateLang()
	{
		if (this.checkbox != null) this.checkbox.updateLang();
		if (this.panelCoordinates != null) this.panelCoordinates.updateLang();
	}

}
