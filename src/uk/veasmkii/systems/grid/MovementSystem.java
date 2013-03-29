package uk.veasmkii.systems.grid;

import static uk.veasmkii.states.Play.isIsometric;

import org.newdawn.slick.GameContainer;

import uk.veasmkii.component.Coordinate;
import uk.veasmkii.component.Expiry;
import uk.veasmkii.component.Movement;
import uk.veasmkii.component.Movement.Direction;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class MovementSystem extends TickSystem {

	@Mapper private ComponentMapper<Coordinate> cm;
	@Mapper private ComponentMapper<Movement> mm;

	@SuppressWarnings( "unchecked" )
	public MovementSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Coordinate.class,
				Movement.class ) );
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		final Coordinate coordinate = cm.get( e );

		final Movement movement = mm.get( e );
		final Expiry expiry = movement.getExpiry();

		expiry.reduceDuration( world.getDelta() );
		if ( expiry.isExpired() && ( movement.getAmount() > 0 ) ) {
			if ( isIsometric() )
				moveIsometric( movement, coordinate );
			else
				moveGrid( movement, coordinate );
			expiry.setDuration( 250 );
		}

	}

	private void moveIsometric( final Movement movement,
			final Coordinate coordinate ) {
		final Direction direction = movement.getDirection();
		final int directionAmount = movement.getAmount();

		if ( direction == Direction.NORTH_EAST )
			coordinate.addXY( directionAmount, 0 );
		else if ( direction == Direction.NORTH_WEST )
			coordinate.addXY( 0, -directionAmount );
		else if ( direction == Direction.SOUTH_EAST )
			coordinate.addXY( 0, directionAmount );
		else if ( direction == Direction.SOUTH_WEST )
			coordinate.addXY( -directionAmount, 0 );
		else if ( direction == Direction.NORTH )
			coordinate.addXY( directionAmount, -directionAmount );
		else if ( direction == Direction.SOUTH )
			coordinate.addXY( -directionAmount, directionAmount );
		else if ( direction == Direction.WEST )
			coordinate.addXY( -directionAmount, -directionAmount );
		else if ( direction == Direction.EAST )
			coordinate.addXY( directionAmount, directionAmount );

	}

	private void moveGrid( final Movement movement, final Coordinate coordinate ) {

		final Direction direction = movement.getDirection();
		final int directionAmount = movement.getAmount();

		if ( direction == Direction.NORTH_EAST )
			coordinate.addXY( directionAmount, -directionAmount );
		else if ( direction == Direction.NORTH_WEST )
			coordinate.addXY( -directionAmount, -directionAmount );
		else if ( direction == Direction.SOUTH_EAST )
			coordinate.addXY( directionAmount, directionAmount );
		else if ( direction == Direction.SOUTH_WEST )
			coordinate.addXY( -directionAmount, directionAmount );
		else if ( direction == Direction.NORTH )
			coordinate.addY( -directionAmount );
		else if ( direction == Direction.SOUTH )
			coordinate.addY( directionAmount );
		else if ( direction == Direction.WEST )
			coordinate.addX( -directionAmount );
		else if ( direction == Direction.EAST )
			coordinate.addX( directionAmount );
	}

}
