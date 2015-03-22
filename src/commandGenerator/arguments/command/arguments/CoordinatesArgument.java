package commandGenerator.arguments.command.arguments;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import commandGenerator.arguments.command.Argument;
import commandGenerator.arguments.objects.Coordinates;
import commandGenerator.gui.helper.argumentSelection.CoordSelectionPanel;
import commandGenerator.gui.helper.components.CCheckBox;

public class CoordinatesArgument extends Argument
{

	private boolean canBeRelative, canHaveRotation;
	private CoordSelectionPanel panelCoord;
	private CCheckBox checkbox;

	public CoordinatesArgument(String id, boolean isCompulsery, boolean canBeRelative, boolean canHaveRotation)
	{
		super(id, Argument.COORD, isCompulsery, 3);
		this.canBeRelative = canBeRelative;
		this.canHaveRotation = canHaveRotation;

		if (this.canHaveRotation) setMaximumLength(5);
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
		this.panelCoord = new CoordSelectionPanel(getId(), "GUI:" + getId(), canBeRelative, canHaveRotation);
		if (!this.isCompulsery())
		{
			this.checkbox = new CCheckBox(this.getId(), "GUI:" + this.getId() + ".use");
			this.checkbox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					panelCoord.setEnabledContent(checkbox.isSelected());
				}
			});
			this.panelCoord.setEnabledContent(false);
		}
	}

	@Override
	public String generateCommand()
	{
		Coordinates coords = this.panelCoord.generateCoord();
		if (coords == null) return null;
		return coords.commandStructure();
	}

	@Override
	public boolean isUsed()
	{
		return this.isCompulsery() || this.checkbox.isSelected();
	}

}
