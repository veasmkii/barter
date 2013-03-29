package uk.veasmkii.systems;

import org.newdawn.slick.GameContainer;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

public abstract class TickSystem extends EntityProcessingSystem {

	protected final GameContainer container;

	public TickSystem( final GameContainer container, final Aspect aspect ) {
		super( aspect );
		this.container = container;
	}

	@Override
	protected void process( final Entity e ) {
		process( container, e );
	}

	public abstract void process( final GameContainer container, final Entity e );

}
