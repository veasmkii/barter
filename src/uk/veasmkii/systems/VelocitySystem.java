package uk.veasmkii.systems;

import org.newdawn.slick.GameContainer;

import uk.veasmkii.component.Position;
import uk.veasmkii.component.Velocity;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class VelocitySystem extends TickSystem {

	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<Position> pm;

	@SuppressWarnings( "unchecked" )
	public VelocitySystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Velocity.class,
				Position.class ) );
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		final Velocity velocity = vm.get( e );
		final Position position = pm.get( e );

		position.addX( velocity.getX() * world.getDelta() );
		position.addY( velocity.getY() * world.getDelta() );

	}

}
