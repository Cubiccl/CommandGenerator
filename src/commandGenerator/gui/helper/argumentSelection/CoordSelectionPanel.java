package commandGenerator.gui.helper.argumentSelection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.button.CButton;
import commandGenerator.gui.helper.components.button.LoadButton;
import commandGenerator.gui.helper.components.button.SaveButton;
import commandGenerator.gui.helper.components.icomponent.ISave;
import commandGenerator.gui.helper.components.panel.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class CoordSelectionPanel extends HelperPanel implements ISave
{

	private CButton buttonSave, buttonLoad;
	private CCheckBox checkboxRotation, checkboxRelativeAll, checkRelX, checkRelY, checkRelZ, checkboxFloat;
	protected CEntry entryX, entryY, entryZ;
	private RotationSelectionPanel panelRotation;
	private boolean rotation, relative;

	public CoordSelectionPanel(String title, boolean relative, boolean rotation)
	{
		super(title, relative, rotation);
	}

	@Override
	protected void addComponents()
	{
		if (relative)
		{
			addLine(entryX, checkRelX);
			addLine(entryY, checkRelY);
			addLine(entryZ, checkRelZ);
			addLine(checkboxFloat, checkboxRelativeAll);
		} else
		{
			add(entryX);
			add(entryY);
			add(entryZ);
			add(checkboxFloat);
		}

		if (rotation)
		{
			add(checkboxRotation);
			add(panelRotation);
		}

		addLine(buttonSave, buttonLoad);
	}

	@Override
	protected void createComponents()
	{
		buttonSave = new SaveButton(CGConstants.OBJECT_COORD, this);
		buttonLoad = new LoadButton(CGConstants.OBJECT_COORD, this);

		entryX = new CEntry("GUI:coord.x", "0");
		entryY = new CEntry("GUI:coord.y", "0");
		entryZ = new CEntry("GUI:coord.z", "0");

		checkboxFloat = new CCheckBox("GUI:coord.float");

		if (rotation)
		{
			checkboxRotation = new CCheckBox("GUI:coord.rotations");
			checkboxRotation.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					panelRotation.setEnabledContent(checkboxRotation.isSelected());
				}
			});
		}

		if (relative)
		{
			checkboxRelativeAll = new CCheckBox("GUI:coord.relatives");
			checkRelX = new CCheckBox("GUI:coord.relative");
			checkRelY = new CCheckBox("GUI:coord.relative");
			checkRelZ = new CCheckBox("GUI:coord.relative");
		}

		if (rotation)
		{
			panelRotation = new RotationSelectionPanel("GUI:coord.rotation");
			panelRotation.setEnabledContent(false);
		}
	}

	@Override
	protected void createListeners()
	{
		if (relative)
		{
			checkboxRelativeAll.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					checkRelX.setEnabled(!checkboxRelativeAll.isSelected());
					checkRelY.setEnabled(!checkboxRelativeAll.isSelected());
					checkRelZ.setEnabled(!checkboxRelativeAll.isSelected());
				}
			});
			checkboxRelativeAll.setSelected(true);
		}
	}

	public Coordinates generateCoord()
	{
		DisplayHelper.log("Generating coordinates");
		double x, y, z;
		boolean isRotation = false;
		boolean[] relativeness = { false, false, false };

		if (relative)
		{
			relativeness[0] = checkRelX.isSelected() || checkboxRelativeAll.isSelected();
			relativeness[1] = checkRelY.isSelected() || checkboxRelativeAll.isSelected();
			relativeness[2] = checkRelZ.isSelected() || checkboxRelativeAll.isSelected();
		}
		if (rotation) isRotation = checkboxRotation.isSelected();

		try
		{
			x = Double.parseDouble(entryX.getText());
			y = Double.parseDouble(entryY.getText());
			z = Double.parseDouble(entryZ.getText());
		} catch (Exception ex)
		{
			DisplayHelper.warningInteger();
			return null;
		}

		if (isRotation) return new Coordinates(x, y, z, ((TagFloat) panelRotation.getRotations().get(0)).getValue(), ((TagFloat) panelRotation.getRotations()
				.get(1)).getValue(), relativeness, checkboxFloat.isSelected());
		else return new Coordinates(x, y, z, relativeness, checkboxFloat.isSelected());
	}

	@Override
	public Object getObjectToSave()
	{
		return generateCoord();
	}

	@Override
	public void load(Object object)
	{
		setupFrom((Coordinates) object);
	}

	@Override
	public void setEnabledContent(boolean enable)
	{

		super.setEnabledContent(enable);

		if (rotation && enable && checkboxRotation.isSelected())
		{
			checkboxRotation.setEnabled(enable);
			panelRotation.setEnabledContent(true);
		} else if (rotation)
		{
			checkboxRotation.setEnabled(enable);
			panelRotation.setEnabledContent(false);
		}

	}

	@Override
	protected void setupDetails(Object[] details)
	{
		relative = (boolean) details[0];
		rotation = (boolean) details[1];
	}

	public void setupFrom(Coordinates coords)
	{
		if (coords == null)
		{
			reset();
			return;
		}

		entryX.setTextField(Double.toString(coords.getCoord(Coordinates.X)));
		entryY.setTextField(Double.toString(coords.getCoord(Coordinates.Y)));
		entryZ.setTextField(Double.toString(coords.getCoord(Coordinates.Z)));

		if (relative)
		{
			checkRelX.setSelected(coords.isRelative(Coordinates.X));
			checkRelY.setSelected(coords.isRelative(Coordinates.Y));
			checkRelZ.setSelected(coords.isRelative(Coordinates.Z));
			checkboxRelativeAll.setSelected(checkRelX.isSelected() && checkRelY.isSelected() && checkRelZ.isSelected());
		}
		if (rotation)
		{
			checkboxRotation.setSelected(coords.getRotation());
			panelRotation.setupFrom(coords);
		}
	}

}
