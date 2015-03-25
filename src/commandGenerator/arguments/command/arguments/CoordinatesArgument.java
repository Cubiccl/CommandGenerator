package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.arguments.objects.ObjectCreator;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;

public class CoordinatesArgument extends Argument
{

	private final boolean canBeRelative, canHaveRotation;
	private CCheckBox checkbox;
	private CoordSelectionPanel panelCoord;

	public CoordinatesArgument(String id, boolean isCompulsery, boolean canBeRelative, boolean canHaveRotation)
	{
		super(id, Argument.NORMAL, isCompulsery, 3);
		this.canBeRelative = canBeRelative;
		this.canHaveRotation = canHaveRotation;

		if (this.canHaveRotation) setMaximumLength(5);
	}

	@Override
	public String generateCommand()
	{
		Coordinates coords = this.panelCoord.generateCoord();
		if (coords == null) return null;
		return coords.commandStructure();
	}

	@Override
	public Component generateComponent()
	{
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		if (!this.isCompulsery()) panel.add(this.checkbox, gbc);
		gbc.gridy++;
		panel.add(this.panelCoord, gbc);
		return panel;
	}

	@Override
	public void initGui()
	{
		this.panelCoord = new CoordSelectionPanel("GUI:" + getId(), canBeRelative, canHaveRotation);
		if (!this.isCompulsery())
		{
			this.checkbox = new CCheckBox("GUI:" + this.getId() + ".use");
			this.checkbox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e)
				{
					panelCoord.setEnabledContent(checkbox.isSelected());
				}
			});
			this.panelCoord.setEnabledContent(false);
		}
	}

	@Override
	public boolean isUsed()
	{
		return this.isCompulsery() || this.checkbox.isSelected();
	}

	@Override
	public boolean matches(List<String> data)
	{
		String x = data.get(0), y = data.get(1), z = data.get(2);
		if (x.startsWith("~")) x = x.substring(1);
		if (y.startsWith("~")) y = y.substring(1);
		if (z.startsWith("~")) z = z.substring(1);
		try
		{
			Float.parseFloat(x);
			Float.parseFloat(y);
			Float.parseFloat(z);

			if (this.canHaveRotation)
			{
				Float.parseFloat(data.get(3));
				Float.parseFloat(data.get(4));
			}
		} catch (Exception e)
		{
			return false;
		}
		return true;
	}

	@Override
	public void setupFrom(List<String> data)
	{
		Coordinates coords = ObjectCreator.generateCoordinates(data.get(0), data.get(1), data.get(2));
		if (data.size() >= 5) coords = ObjectCreator.generateCoordinatesWithRotations(data.get(0), data.get(1), data.get(2), Float.parseFloat(data.get(3)),
				Float.parseFloat(data.get(4)));
		this.panelCoord.setupFrom(coords);
	}

}
