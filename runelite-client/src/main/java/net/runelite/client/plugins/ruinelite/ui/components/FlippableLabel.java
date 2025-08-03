package net.runelite.client.plugins.ruinelite.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * A JLabel that supports horizontal flipping of its image,
 * useful for animated GIFs or directional icons.
 */
public class FlippableLabel extends JLabel
{
	private boolean flipped = false;

	public FlippableLabel() {}

	public FlippableLabel(Icon icon)
	{
		super(icon);
		setDoubleBuffered(true); // prevent flickering
	}

	/**
	 * Flip the image horizontally.
	 */
	public void flip()
	{
		flipped = !flipped;
		repaint();
	}

	/**
	 * Reposition the label horizontally in a thread-safe way.
	 */
	public void updatePosition(int x)
	{
		SwingUtilities.invokeLater(() ->
			setBounds(x, getY(), getWidth(), getHeight()));
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g.create();

		if (flipped)
		{
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-getWidth(), 0);
			g2d.setTransform(tx);
		}

		super.paintComponent(g2d);
		g2d.dispose();
	}
}
