package uk.veasmkii.systems.grid;

import static uk.veasmkii.states.Play.isIsometric;
import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Point;

import uk.veasmkii.component.Coordinate;
import uk.veasmkii.component.Expiry;
import uk.veasmkii.component.Movement;
import uk.veasmkii.component.Position;
import uk.veasmkii.component.Size;
import uk.veasmkii.component.Tile;
import uk.veasmkii.component.Velocity;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class CoordinatePositioningSystem extends TickSystem {

	@Mapper private ComponentMapper<Coordinate> cm;
	@Mapper private ComponentMapper<Movement> mm;
	@Mapper private ComponentMapper<Position> pm;
	@Mapper private ComponentMapper<Velocity> vm;
	@Mapper private ComponentMapper<Size> sm;

	@Getter @Setter private int tileWidth = Tile.TILE_WIDTH,
			tileHeight = Tile.TILE_HEIGHT;

	// FIXME enforce equal column & row lengths
	@Getter @Setter private Entity[][] tiles;

	@SuppressWarnings( "unchecked" )
	public CoordinatePositioningSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Coordinate.class,
				Position.class, Size.class ) );
	}

	public boolean coordinatExists( final Coordinate coordinate ) {
		return coordinateExists( coordinate, tiles );
	}

	public static boolean coordinateExists( final Coordinate coordinate,
			final Entity[][] tiles ) {
		final int x = coordinate.getX(), y = coordinate.getY();
		if ( x < 0 )
			return false;
		else if ( y < 0 )
			return false;
		else if ( x > tiles.length - 1 )
			return false;
		else if ( y > tiles[x].length - 1 )
			return false;
		return true;
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		final int centerX = ( container.getWidth() - calculateTotalWidth() ) / 2;
		final int centerY = ( container.getHeight() - calculateTotalHeight() ) / 2;

		final Position position = pm.get( e );
		final Coordinate coordinate = cm.get( e );
		final Size size = sm.get( e );
		final Movement movement = mm.getSafe( e );

		final Point desiredPoint = ( isIsometric() ) ? createIsometric(
				coordinate, size ) : createGrid( coordinate, size );

		desiredPoint.setX( desiredPoint.getX() + centerX );
		desiredPoint.setY( desiredPoint.getY() + centerY );

		if ( ( movement != null ) && !movement.getExpiry().isExpired() )
			moveTowardsPositon( movement.getExpiry(), position, desiredPoint );
		else
			position.setPosition( desiredPoint.getX(), desiredPoint.getY() );

	}

	private int calculateTotalWidth() {
		return tiles.length * Tile.TILE_WIDTH;
	}

	private int calculateTotalHeight() {
		return tiles[0].length * Tile.TILE_HEIGHT;
	}

	private void moveTowardsPositon( final Expiry expiry,
			final Position position, final Point desiredPoint ) {

		if ( position.getX() != desiredPoint.getX() )
			position.addX( calculateMovement( position.getX(),
					desiredPoint.getX(), expiry.getDuration() ) );

		if ( position.getY() != desiredPoint.getY() )
			position.addY( calculateMovement( position.getY(),
					desiredPoint.getY(), expiry.getDuration() ) );

		position.setPosition( position.getX(), position.getY() );
	}

	/**
	 * Calculates the required movement in a single direction.
	 * 
	 * @param position
	 *            in pixels
	 * @param desiredPosition
	 *            in pixels
	 * @param duration
	 *            total movement duration
	 * @return amount to move
	 */
	private float calculateMovement( final float position,
			final float desiredPosition, final float duration ) {
		final float distance = position - desiredPosition;
		final float movement = ( distance / duration ) * world.getDelta();
		return -movement;
	}

	private Point createIsometric( final Coordinate coordinate, final Size size ) {
		final int x = ( ( coordinate.getX() * tileWidth ) / 2 )
				+ ( ( coordinate.getY() * tileWidth ) / 2 );
		final int y = ( ( coordinate.getY() * tileHeight ) / 2 )
				- ( ( coordinate.getX() * tileHeight ) / 2 );
		return new Point( x - ( size.getWidth() / 2 ), y
				- ( size.getHeight() / 2 ) );
	}

	private Point createGrid( final Coordinate coordinate, final Size size ) {
		final int x = ( tileWidth * coordinate.getX() ) - ( size.getWidth() );
		final int y = ( tileHeight * coordinate.getY() ) - ( size.getHeight() );
		return new Point( x, y );
	}

}
