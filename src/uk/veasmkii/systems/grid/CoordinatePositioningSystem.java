package uk.veasmkii.systems.grid;

import static uk.veasmkii.states.Play.isIsometric;
import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Point;

import uk.veasmkii.component.Coordinate;
import uk.veasmkii.component.Movement;
import uk.veasmkii.component.Position;
import uk.veasmkii.component.Size;
import uk.veasmkii.component.Velocity;
import uk.veasmkii.component.tag.Tile;
import uk.veasmkii.entity.EntityFactory;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class CoordinatePositioningSystem extends TickSystem {

	@Mapper ComponentMapper<Coordinate> cm;
	@Mapper ComponentMapper<Movement> mm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<Size> sm;

	@Getter @Setter private int tileWidth = Tile.TILE_WIDTH,
			tileHeight = Tile.TILE_HEIGHT;

	@Getter @Setter private int offsetX = 0, offsetY = 0;
	private Entity[][] tiles;

	@SuppressWarnings( "unchecked" )
	public CoordinatePositioningSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Coordinate.class,
				Position.class, Size.class ) );
	}

	@Override
	protected void initialize() {
		final int rows = 10, columns = 10;
		tiles = new Entity[columns][rows];
		for ( int x = 0; x < columns; x++ )
			for ( int y = 0; y < rows; y++ ) {
				final Entity tile = tiles[x][y] = EntityFactory
						.createTile( world );
				tile.getComponent( Coordinate.class ).setCoordinates( x, y );
				tile.addToWorld();
				added( tile );
			}
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		offsetX = ( container.getWidth() / 2 ) - totalWidthOfGrid();
		offsetY = container.getHeight() / 2;

		final Position position = pm.get( e );
		final Coordinate coordinate = cm.get( e );
		final Size size = sm.get( e );
		final Movement movement = mm.getSafe( e );

		final Point desiredPoint = ( isIsometric() ) ? createIsometric(
				coordinate, size ) : createGrid( coordinate, size );

		desiredPoint.setX( desiredPoint.getX() + ( offsetX * 2 ) );
		desiredPoint.setY( desiredPoint.getY() + ( offsetY ) );

		if ( ( movement != null ) && !movement.getExpiry().isExpired() )
			moveTowardsPositon( position, desiredPoint );
		else
			position.setPosition( desiredPoint.getX(), desiredPoint.getY() );

	}

	private void moveTowardsPositon( final Position position,
			final Point desiredPoint ) {

		if ( position.getX() < desiredPoint.getX() )
			position.addX( 1 * 3 );
		else if ( position.getX() > desiredPoint.getX() )
			position.addX( -1 * 3 );

		if ( position.getY() < desiredPoint.getY() )
			position.addY( 1 * 3 );
		else if ( position.getY() > desiredPoint.getY() )
			position.addY( -1 * 3 );

		position.setPosition( position.getX(), position.getY() );
	}

	private int totalWidthOfGrid() {
		return ( tileWidth * tiles.length ) / 2;
	}

	private Point createIsometric( final Coordinate coordinate, Size size ) {
		final int x = ( ( coordinate.getX() * tileWidth ) / 2 )
				+ ( ( coordinate.getY() * tileWidth ) / 2 );
		final int y = ( ( coordinate.getY() * tileHeight ) / 2 )
				- ( ( coordinate.getX() * tileHeight ) / 2 );
		return new Point( x - ( size.getWidth() / 2 ), y
				- ( size.getHeight() / 2 ) );
	}

	private Point createGrid( final Coordinate coordinate, Size size ) {
		final int x = tileWidth * coordinate.getX() - ( size.getWidth() / 2 );
		final int y = tileHeight * coordinate.getY() - ( size.getHeight() );
		return new Point( x, y );
	}

}
