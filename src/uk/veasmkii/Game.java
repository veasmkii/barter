package uk.veasmkii;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import uk.veasmkii.states.Menu;
import uk.veasmkii.states.Play;

public class Game extends StateBasedGame {

	public static final String name = "JDice";

	public static void main( final String[] args ) {

		try {
			final AppGameContainer container = new AppGameContainer( new Game(
					name ) );
			// container.setDisplayMode( 640, 360, false );
			// container.setDisplayMode( 1024, 768, false );
			// container.setDisplayMode( 2960, 1050, false );
			container.setSmoothDeltas( true );
			container.setVSync( true );
			container.setAlwaysRender( true );
			container.start();
		} catch ( final SlickException e ) {
			e.printStackTrace();
		}
	}

	public Game( final String name ) {
		super( name );
		addState( new Play() );
		addState( new Menu() );
		this.enterState( 2 );

	}

	@Override
	public void initStatesList( final GameContainer container )
			throws SlickException {
		// this.getState( 2 ).init( container, this );
		// this.enterState( 2 );
	}

}
