package uk.veasmkii.entity;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.particles.ParticleIO;
import org.newdawn.slick.particles.ParticleSystem;

import uk.veasmkii.component.Coordinate;
import uk.veasmkii.component.Drawable;
import uk.veasmkii.component.Expiry;
import uk.veasmkii.component.Health;
import uk.veasmkii.component.Imageable;
import uk.veasmkii.component.Movement;
import uk.veasmkii.component.Player;
import uk.veasmkii.component.Position;
import uk.veasmkii.component.Shapeable;
import uk.veasmkii.component.Size;
import uk.veasmkii.component.Sprite;
import uk.veasmkii.component.Tile;
import uk.veasmkii.component.Title;
import uk.veasmkii.component.Velocity;
import uk.veasmkii.states.Play;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

//TODO Create 'concrete' builders for each entity type?
public class EntityFactory {

	public static Entity createPolygon( final World world ) {
		final Entity entity = world.createEntity();

		entity.addComponent( new Velocity() );
		entity.addComponent( new Position() );
		entity.addComponent( new Expiry( 250 ) );

		final Polygon shape = new Polygon();
		shape.addPoint( 0, 10 );
		shape.addPoint( 10, 10 );
		shape.addPoint( 0, 20 );

		final Drawable drawable = new Drawable();
		drawable.setShape( shape );
		entity.addComponent( drawable );

		return entity;
	}

	public static Entity createSprite( final World world, final String image ) {
		final Entity entity = world.createEntity();
		world.getManager( GroupManager.class )
				.add( entity, Group.SPRITE.name() );

		try {
			final Image spriteImage = new Image( image );
			final SpriteSheet sheet = new SpriteSheet( image,
					spriteImage.getWidth() / 4, spriteImage.getHeight() / 4 );
			final Image firstImage = sheet.getSubImage( 0, 0 );

			entity.addComponent( new Imageable( firstImage ) );
			entity.addComponent( new Size( firstImage.getWidth(), firstImage
					.getHeight() ) );
			entity.addComponent( new Sprite( image ) );
		} catch ( final SlickException e ) {
			e.printStackTrace();
		}

		entity.addComponent( new Health( 100, 100 ) );
		entity.addComponent( new Title() );
		entity.addComponent( new Coordinate() );
		entity.addComponent( new Movement() );
		entity.addComponent( new Velocity() );
		entity.addComponent( new Position() );

		return entity;

	}

	public static Entity createPlayer( final World world, final String image ) {
		final Entity entity = createSprite( world, image );
		world.getManager( GroupManager.class )
				.add( entity, Group.PLAYER.name() );
		world.getManager( TagManager.class ).register( "You", entity );
		entity.addComponent( new Player() );
		entity.getComponent( Title.class ).setColor( Color.cyan );
		return entity;
	}

	public static Entity createTile( final World world ) {
		final Entity entity = world.createEntity();
		world.getManager( GroupManager.class ).add( entity, Group.TILE.name() );
		entity.addComponent( new Tile() );
		entity.addComponent( new Coordinate() );
		final Size size = Play.isIsometric() ? new Size( Tile.TILE_WIDTH,
				Tile.TILE_HEIGHT ) : new Size( 52, 52 );
		entity.addComponent( size );
		entity.addComponent( new Velocity() );
		entity.addComponent( new Position() );
		entity.addComponent( new Imageable() );
		entity.addComponent( new Shapeable() );
		return entity;
	}

	public static Entity createParticle( final World world, final Image image ) {
		final Entity entity = world.createEntity();
		new ParticleSystem( image, 1000 );
		// entity.addComponent( new Spart )
		return entity;
	}

	public static Entity createEmitter( final World world ) throws IOException {
		final Entity entity = world.createEntity();
		world.getManager( GroupManager.class ).add( entity,
				Group.EMITTER.name() );

		final File file = new File( "res/particle/torch.xml" );
		ParticleIO.loadEmitter( file );
		return entity;
	}

	// TODO add component classes here
	public static enum Group {
		PLAYER,
		PARTICLE,
		EMITTER,
		SPRITE,
		TILE;
	}

}
