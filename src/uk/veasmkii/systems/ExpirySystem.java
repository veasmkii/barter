package uk.veasmkii.systems;

import org.newdawn.slick.GameContainer;

import uk.veasmkii.component.Expiry;
import uk.veasmkii.component.Movement;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class ExpirySystem extends TickSystem {

	@Mapper ComponentMapper<Expiry> em;
	@Mapper ComponentMapper<Movement> mm;

	@SuppressWarnings( "unchecked" )
	public ExpirySystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Expiry.class, Movement.class ) );
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		final Expiry expiry = em.get( e );
		expiry.reduceDuration( world.getDelta() );
	}

}
