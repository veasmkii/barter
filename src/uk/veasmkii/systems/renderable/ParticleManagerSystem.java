package uk.veasmkii.systems.renderable;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.particles.ConfigurableEmitter;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import uk.veasmkii.component.Sparticle;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.Entity;

public class ParticleManagerSystem extends TickSystem {

	private ParticleSystem particleSystem;

	@SuppressWarnings( "unchecked" )
	public ParticleManagerSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Sparticle.class ) );
	}

	@Override
	protected void initialize() {
		try {
			new Image( "res/particle/torch.png" );
		} catch ( final SlickException e ) {
			e.printStackTrace();
		}
		particleSystem.setBlendingMode( ParticleSystem.BLEND_ADDITIVE );
	}

	@Override
	protected void process( final Entity e ) {
		// particleSystem.update( world.getDelta() );
	}

	private void loadParticleSystem() {
		try {
			final Image image = new Image( "res/particle/torch.png" );
			final ParticleSystem particleSystem = new ParticleSystem( image,
					1500 );
			final File file = new File( "res/particle/torch.xml" );
			final ConfigurableEmitter emitter = ParticleIO.loadEmitter( file );
			emitter.setPosition( 156, 10 );
			final ConfigurableEmitter emitter2 = emitter.duplicate();
			emitter2.setPosition( 260, 10 );
			particleSystem.addEmitter( emitter2 );
			particleSystem.addEmitter( emitter );
			particleSystem.setBlendingMode( ParticleSystem.BLEND_ADDITIVE );
		} catch ( final IOException e ) {
			e.printStackTrace();
		} catch ( final SlickException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		// TODO Auto-generated method stub

	}

}
