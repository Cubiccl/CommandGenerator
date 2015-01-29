package commandGenerator.gui.helper.argumentSelection;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.tags.TagFloat;
import commandGenerator.gui.helper.components.CCheckBox;
import commandGenerator.gui.helper.components.CEntry;
import commandGenerator.gui.helper.components.HelperPanel;
import commandGenerator.main.CGConstants;
import commandGenerator.main.DisplayHelper;

@SuppressWarnings("serial")
public class CoordSelectionPanel extends HelperPanel
{

	private boolean rotation, relative;
	protected CEntry entryX, entryY, entryZ;
	private CCheckBox checkboxRotation, checkboxRelative;
	private RotationSelectionPanel panelRotation;

	public CoordSelectionPanel(String id, String title, boolean relative, boolean rotation)
	{

		super(id, title, 250, 250);
		this.relative = relative;
		this.rotation = rotation;

		entryX = new CEntry(CGConstants.DATAID_NONE, "GUI:coord.x");
		entryY = new CEntry(CGConstants.DATAID_NONE, "GUI:coord.y");
		entryZ = new CEntry(CGConstants.DATAID_NONE, "GUI:coord.z");

		entryX.setTextField("0");
		entryY.setTextField("0");
		entryZ.setTextField("0");

		if (rotation)
		{
			checkboxRotation = new CCheckBox(CGConstants.DATAID_NONE, "GUI:coord.rotations");
			checkboxRotation.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					panelRotation.setEnabledContent(checkboxRotation.isSelected());
				}
			});
		}

		if (relative) checkboxRelative = new CCheckBox(CGConstants.DATAID_NONE, "GUI:coord.relatives");

		if (rotation)
		{
			panelRotation = new RotationSelectionPanel(id, "GUI:coord.rotation");
			panelRotation.setEnabledContent(false);
		}

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		add(entryX);
		gbc.gridy++;
		add(entryY);
		gbc.gridy++;
		add(entryZ);
		gbc.gridy++;
		if (relative)
		{
			add(checkboxRelative);
			gbc.gridy++;
		}
		if (rotation)
		{
			add(checkboxRotation);
			gbc.gridy++;
			add(panelRotation);
		}

	}

	public Coordinates generateCoord()
	{
		DisplayHelper.log("Generating coordinates");
		double x, y, z;
		boolean areRelative = false, isRotation = false;

		if (relative) areRelative = checkboxRelative.isSelected();
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
				.get(1)).getValue(), areRelative);
		else return new Coordinates(x, y, z, areRelative);
	}

	public void setEnabledContent(boolean enable)
	{

		setEnabled(enable);
		entryX.setEnabledContent(enable);
		entryY.setEnabledContent(enable);
		entryZ.setEnabledContent(enable);
		checkboxRelative.setEnabled(enable);

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
	public void setupFrom(Map<String, Object> data)
	{
		Coordinates coords = (Coordinates) data.get(getPanelId());
		if (coords == null)
		{
			reset();
			return;
		}

		entryX.setTextField(Double.toString(coords.getCoord(Coordinates.X)));
		entryY.setTextField(Double.toString(coords.getCoord(Coordinates.Y)));
		entryZ.setTextField(Double.toString(coords.getCoord(Coordinates.Z)));

		if (relative) checkboxRelative.setSelected(coords.getRelative());
		if (rotation)
		{
			checkboxRotation.setSelected(coords.getRotation());
			panelRotation.setupFrom(data);
		}
	}

}
