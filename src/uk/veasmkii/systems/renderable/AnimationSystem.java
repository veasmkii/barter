package uk.veasmkii.systems.renderable;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;

import uk.veasmkii.component.Imageable;
import uk.veasmkii.component.Movement;
import uk.veasmkii.component.Position;
import uk.veasmkii.component.Size;
import uk.veasmkii.component.Sprite;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class AnimationSystem extends TickSystem {

	@Mapper ComponentMapper<Sprite> spm;
	@Mapper ComponentMapper<Imageable> im;
	@Mapper ComponentMapper<Movement> mm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Size> sm;

	@SuppressWarnings( "unchecked" )
	public AnimationSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Sprite.class,
				Imageable.class, Movement.class ) );
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		final Sprite sprite = spm.get( e );
		final Imageable imagable = im.get( e );
		final Movement movement = mm.get( e );

		final Animation animation = sprite.getAnimation( movement
				.getDirection() );
		if ( movement.getExpiry().isExpired() )
			animation.restart();
		else
			animation.update( (long) world.getDelta() );
		imagable.setImage( animation.getCurrentFrame() );
	}
}
