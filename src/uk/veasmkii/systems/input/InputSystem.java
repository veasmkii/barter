package uk.veasmkii.systems.input;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import uk.veasmkii.component.Coordinate;
import uk.veasmkii.component.Expiry;
import uk.veasmkii.component.Health;
import uk.veasmkii.component.Imageable;
import uk.veasmkii.component.Movement;
import uk.veasmkii.component.Movement.Direction;
import uk.veasmkii.component.Player;
import uk.veasmkii.component.Position;
import uk.veasmkii.component.Size;
import uk.veasmkii.component.Velocity;
import uk.veasmkii.entity.EntityFactory;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;

public class InputSystem extends TickSystem {

	@Mapper private ComponentMapper<Movement> dm;
	@Mapper private ComponentMapper<Coordinate> cm;
	@Mapper private ComponentMapper<Velocity> vm;
	@Mapper private ComponentMapper<Position> pm;

	@SuppressWarnings( "unchecked" )
	public InputSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Coordinate.class, Size.class,
				Position.class, Imageable.class, Player.class ) );
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		final Movement movement = dm.get( e );
		final Coordinate coordinate = cm.get( e );

		final Input input = container.getInput();

		handleKeys( input, coordinate, movement );

		expiry.reduceDuration( world.getDelta() );
	}

	private Direction getDirection( final boolean north, final boolean east,
			final boolean south, final boolean west ) {
		if ( north && east )
			return Direction.NORTH_EAST;
		else if ( north && west )
			return Direction.NORTH_WEST;
		else if ( south && east )
			return Direction.SOUTH_EAST;
		else if ( south && west )
			return Direction.SOUTH_WEST;
		else if ( north )
			return Direction.NORTH;
		else if ( east )
			return Direction.EAST;
		else if ( west )
			return Direction.WEST;
		else if ( south )
			return Direction.SOUTH;
		return null;
	}

	private final Expiry expiry = new Expiry();

	private void handleKeys( final Input input, final Coordinate coordinate,
			final Movement movement ) {

		handleMovement( input, movement );

		// Bullet
		if ( input.isKeyDown( Input.KEY_1 ) ) {
			final Entity entity = EntityFactory.createBullet( world );
			entity.getComponent( Velocity.class ).setVelocity( 0.250F, 0.250F );
			entity.addToWorld();
		}

		// Damage
		if ( input.isKeyDown( Input.KEY_D ) && expiry.isExpired() ) {
			expiry.setDuration( 500 );
			world.getManager( TagManager.class ).getEntity( "You" )
					.getComponent( Health.class ).addHealth( -10 );
		}

		// Heal
		if ( input.isKeyDown( Input.KEY_H ) && expiry.isExpired() ) {
			expiry.setDuration( 500 );
			world.getManager( TagManager.class ).getEntity( "You" )
					.getComponent( Health.class ).addHealth( +10 );
		}

	}

	// FIXME prevent direction changing until movement is expired
	private void handleMovement( final Input input, final Movement movement ) {
		final boolean north = input.isKeyDown( Input.KEY_UP );
		final boolean east = input.isKeyDown( Input.KEY_RIGHT );
		final boolean south = input.isKeyDown( Input.KEY_DOWN );
		final boolean west = input.isKeyDown( Input.KEY_LEFT );

		final boolean directionHeld = north || east || south || west;

		final Direction direction = getDirection( north, east, south, west );
		if ( direction != null )
			movement.setDirection( direction );
		movement.setAmount( directionHeld ? 1 : 0 );
	}
}
