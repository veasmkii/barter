package uk.veasmkii.systems.renderable;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import uk.veasmkii.component.Coordinate;

import com.artemis.managers.TagManager;
import com.artemis.systems.VoidEntitySystem;

public class DebugSystem extends VoidEntitySystem {

	private final GameContainer container;

	public DebugSystem( GameContainer container ) {
		this.container = container;
	}

	@Override
	protected void processSystem() {
		final Graphics g = container.getGraphics();
		g.setColor( Color.white );

		g.drawString( getMousePosition(), 10, 20 );
		g.drawString( getPlayerCoordinate(), 10, 30 );
	}

	private String getMousePosition() {
		return String.format( "Mouse X:%s,Y:%s", Mouse.getX(), Mouse.getY() );
	}

	private String getPlayerCoordinate() {
		final Coordinate coordinate = world.getManager( TagManager.class )
				.getEntity( "You" ).getComponent( Coordinate.class );
		return String.format( "You X:%s,Y:%s", coordinate.getX(),
				coordinate.getY() );
	}
}
