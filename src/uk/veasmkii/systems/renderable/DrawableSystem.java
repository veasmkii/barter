package uk.veasmkii.systems.renderable;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import uk.veasmkii.component.Drawable;
import uk.veasmkii.component.Expiry;
import uk.veasmkii.component.Position;
import uk.veasmkii.component.Velocity;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class DrawableSystem extends TickSystem {

	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<Drawable> dm;
	@Mapper ComponentMapper<Expiry> em;

	@SuppressWarnings( "unchecked" )
	public DrawableSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Velocity.class,
				Position.class, Drawable.class, Expiry.class ) );
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {

		final Drawable drawable = dm.get( e );
		final Position position = pm.get( e );
		final Expiry expiry = em.get( e );

		final Graphics g = container.getGraphics();

		final Shape shape = drawable.getShape();
		shape.setX( position.getX() );
		shape.setY( position.getY() );

		g.setColor( Color.blue );
		g.draw( shape );

		expiry.reduceDuration( world.getDelta() );
		if ( expiry.isExpired() )
			e.deleteFromWorld();
	}

}
