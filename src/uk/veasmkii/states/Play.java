package uk.veasmkii.states;

import java.util.Random;

import lombok.Getter;
import lombok.Setter;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import uk.veasmkii.component.Coordinate;
import uk.veasmkii.component.Title;
import uk.veasmkii.entity.EntityFactory;
import uk.veasmkii.systems.ExpirySystem;
import uk.veasmkii.systems.VelocitySystem;
import uk.veasmkii.systems.grid.CoordinatePositioningSystem;
import uk.veasmkii.systems.grid.CoordinateSystem;
import uk.veasmkii.systems.input.InputSystem;
import uk.veasmkii.systems.renderable.AnimationSystem;
import uk.veasmkii.systems.renderable.DrawableSystem;
import uk.veasmkii.systems.renderable.SpriteSystem;
import uk.veasmkii.systems.renderable.TileRenderingSystem;

import com.artemis.Entity;
import com.artemis.World;
import com.artemis.managers.GroupManager;
import com.artemis.managers.TagManager;

public class Play extends BasicGameState {

	private World world;

	@Getter @Setter private static boolean isometric = true;

	public Play() {}

	@Override
	public void init( final GameContainer container, final StateBasedGame game )
			throws SlickException {

		System.out.println( "Game initialised" );
		world = new World();

		world.setManager( new GroupManager() );
		world.setManager( new TagManager() );

		world.setSystem( new TileRenderingSystem( container ), true );
		world.setSystem( new SpriteSystem( container ), true );
		world.setSystem( new DrawableSystem( container ), true );

		world.setSystem( new ExpirySystem( container ) );
		world.setSystem( new AnimationSystem( container ) );
		world.setSystem( new CoordinatePositioningSystem( container ) );
		world.setSystem( new CoordinateSystem( container ) );
		world.setSystem( new InputSystem( container ) );
		world.setSystem( new VelocitySystem( container ) );

		loadPlayers();

		world.initialize();
	}

	private void loadPlayers() {

		final Random random = new Random();

		for ( int x = 0; x < 10; x++ )
			for ( int y = 0; y < 10; y++ )
				if ( random.nextBoolean() && random.nextBoolean()
						&& random.nextBoolean() ) {

					final Entity sprite = EntityFactory.createSprite( world,
							"res/sprite/tidus.png" );
					sprite.getComponent( Coordinate.class ).setCoordinates( x,
							y );

					sprite.getComponent( Title.class ).setName( "Tidus" );
					sprite.addToWorld();
				}
	}

	@Override
	public void update( final GameContainer container,
			final StateBasedGame game, final int delta ) throws SlickException {
		world.setDelta( delta );
		world.process();
		isometric = false;
	}

	@Override
	public void render( final GameContainer container,
			final StateBasedGame game, final Graphics g ) throws SlickException {

		world.getSystem( TileRenderingSystem.class ).process();
		world.getSystem( SpriteSystem.class ).process();
		world.getSystem( DrawableSystem.class ).process();

	}

	@Override
	public void keyPressed( final int key, final char c ) {

		if ( key == Input.KEY_R ) {

		}
	}

	@Override
	public int getID() {
		return 2;
	}

}
