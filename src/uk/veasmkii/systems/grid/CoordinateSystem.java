package uk.veasmkii.systems.grid;

import org.newdawn.slick.GameContainer;

import uk.veasmkii.component.Coordinate;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class CoordinateSystem extends TickSystem {

	@Mapper private ComponentMapper<Coordinate> cm;

	@SuppressWarnings( "unchecked" )
	public CoordinateSystem( GameContainer container ) {
		super( container, Aspect.getAspectForAll( Coordinate.class ) );
	}

	@Override
	public void process( GameContainer container, Entity e ) {
		final Coordinate coordinate = cm.get( e );

		final Entity[][] tiles = world.getSystem(
				CoordinatePositioningSystem.class ).getTiles();

		if ( validCoordinate( coordinate, tiles ) ) {
			final Entity tile = tiles[coordinate.getX()][coordinate.getY()];
			final Coordinate tileCoord = tile.getComponent( Coordinate.class );
			tileCoord.getEntities().clear();
			if ( !tileCoord.contains( e ) )
				tileCoord.addEntity( e );
		}

	}

	private boolean validCoordinate( final Coordinate coordinate,
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

}
