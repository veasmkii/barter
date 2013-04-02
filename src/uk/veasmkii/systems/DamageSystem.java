package uk.veasmkii.systems;

import org.newdawn.slick.GameContainer;

import uk.veasmkii.component.Damage;
import uk.veasmkii.component.Health;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class DamageSystem extends TickSystem {

	@Mapper private ComponentMapper<Damage> dm;
	@Mapper private ComponentMapper<Health> hm;

	@SuppressWarnings( "unchecked" )
	public DamageSystem( GameContainer container ) {
		super( container, Aspect.getAspectForAll( Damage.class, Health.class ) );
	}

	@Override
	public void process( GameContainer container, Entity e ) {
		final Damage damage = dm.get( e );
		final Health health = hm.get( e );
		health.addHealth( (int) -damage.getDamage() );
		e.removeComponent( damage );
		e.changedInWorld();
	}

}
