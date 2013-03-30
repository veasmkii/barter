package uk.veasmkii.systems.interval;

import uk.veasmkii.component.Health;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.IntervalEntityProcessingSystem;

public class HealingSystem extends IntervalEntityProcessingSystem {

	@Mapper private ComponentMapper<Health> hm;

	@SuppressWarnings( "unchecked" )
	public HealingSystem( float interval ) {
		super( Aspect.getAspectForAll( Health.class ), interval );
	}

	@Override
	protected void process( Entity e ) {
		final Health health = hm.get( e );
		health.getDamageCooldown().reduceDuration( world.getDelta() );

		if ( !health.recentlyDamaged() )
			health.addHealth( +10 );
	}

}
