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
import uk.veasmkii.component.Health;
import uk.veasmkii.component.Tile;
import uk.veasmkii.component.Tile.Biome;
import uk.veasmkii.component.Title;
import uk.veasmkii.entity.EntityFactory;
import uk.veasmkii.systems.DamageSystem;
import uk.veasmkii.systems.ExpirySystem;
import uk.veasmkii.systems.VelocitySystem;
import uk.veasmkii.systems.grid.CoordinatePositioningSystem;
import uk.veasmkii.systems.grid.CoordinateSystem;
import uk.veasmkii.systems.grid.MovementSystem;
import uk.veasmkii.systems.input.InputSystem;
import uk.veasmkii.systems.interval.HealingSystem;
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

		world = new World();

		world.setManager( new GroupManager() );
		world.setManager( new TagManager() );

		world.setSystem( new TileRenderingSystem( container ), true );
		world.setSystem( new SpriteSystem( container ), true );
		world.setSystem( new DrawableSystem( container ), true );

		world.setSystem( new ExpirySystem( container ) );
		world.setSystem( new AnimationSystem( container ) );
		world.setSystem( new CoordinateSystem( container ) );
		world.setSystem( new CoordinatePositioningSystem( container ) );
		world.setSystem( new MovementSystem( container ) );
		world.setSystem( new InputSystem( container ) );
		world.setSystem( new VelocitySystem( container ) );
		world.setSystem( new HealingSystem( 1000 ) );
		world.setSystem( new DamageSystem( container ) );

		createEntities();

		world.initialize();
	}

	private void createEntities() {
		world.getSystem( CoordinatePositioningSystem.class ).setTiles(
				createMap() );
		 createRandomPlayers();
		createYou();
	}

	private Entity[][] createMap() {
		final Random random = new Random();
		final Biome[] biomes = Biome.values();

		final int rows = 20, columns = 20;
		final Entity[][] tiles = new Entity[columns][rows];
		for ( int x = 0; x < columns; x++ ) {
			// excluding null biome
			final Biome biome = biomes[random.nextInt( biomes.length - 1 )];
			for ( int y = 0; y < rows; y++ ) {

				final Entity tile = tiles[x][y] = EntityFactory
						.createTile( world );
				tile.getComponent( Tile.class ).setBiome( biome );
				tile.getComponent( Coordinate.class ).setCoordinates( x, y );
				tile.addToWorld();

			}
		}
		return tiles;
	}

	private void createRandomPlayers() {

		final Random random = new Random();

		for ( int x = 0; x < 20; x++ )
			for ( int y = 0; y < 20; y++ )
				if ( random.nextBoolean() && random.nextBoolean()
						&& random.nextBoolean() ) {

					final Entity sprite = EntityFactory.createSprite( world,
							"res/sprite/tidus.png" );
					sprite.getComponent( Coordinate.class ).setCoordinates( x,
							y );
					sprite.getComponent( Title.class ).setName( "Player" );
					sprite.addToWorld();

				}
	}

	private void createYou() {
		final Entity sprite = EntityFactory.createPlayer( world,
				"res/sprite/germania.png" );

		sprite.getComponent( Coordinate.class ).setCoordinates( 5, 5 );
		sprite.getComponent( Title.class ).setName( "You" );
		sprite.getComponent( Health.class ).setHealth( 30 );
		sprite.getComponent( Health.class ).setMaximumHealth( 100 );

		sprite.addToWorld();
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
