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

	@Getter @Setter private Entity[][] tiles;

	@SuppressWarnings( "unchecked" )
	public CoordinatePositioningSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Coordinate.class,
				Position.class, Size.class ) );
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		final int centerX = ( container.getWidth() / 2 - calculateTotalWidth() ) / 2;
		final int centerY = ( container.getHeight() - calculateTotalHeight() );

		final Position position = pm.get( e );
		final Coordinate coordinate = cm.get( e );
		final Size size = sm.get( e );
		final Movement movement = mm.getSafe( e );

		final Point desiredPoint = ( isIsometric() ) ? createIsometric(
				coordinate, size ) : createGrid( coordinate, size );

		desiredPoint.setX( desiredPoint.getX() - centerX );
		desiredPoint.setY( desiredPoint.getY() + centerY / 2 );

		if ( ( movement != null ) && !movement.getExpiry().isExpired() )
			moveTowardsPositon( movement.getExpiry(), position, desiredPoint );
		else
			position.setPosition( desiredPoint.getX(), desiredPoint.getY() );

	}

	private int calculateTotalWidth() {
		return tiles.length * Tile.TILE_WIDTH;
	}

	private int calculateTotalHeight() {
		return tiles.length * Tile.TILE_HEIGHT;
	}

	private void moveTowardsPositon( final Expiry expiry,
			final Position position, final Point desiredPoint ) {

		final float duration = expiry.getDurationDelta();

		if ( position.getX() != desiredPoint.getX() ) {
			final float distanceX = position.getX() - desiredPoint.getX();
			final float movementX = ( distanceX / duration ) * world.getDelta();
			position.addX( -movementX );
		}
		if ( position.getY() != desiredPoint.getY() ) {
			final float distanceY = position.getY() - desiredPoint.getY();
			final float movementY = ( distanceY / duration ) * world.getDelta();
			position.addY( -movementY );
		}

		position.setPosition( position.getX(), position.getY() );
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
		final int x = ( tileWidth * coordinate.getX() )
				- ( size.getWidth() / 2 );
		final int y = ( tileHeight * coordinate.getY() ) - ( size.getHeight() );
		return new Point( x, y );
	}

}
