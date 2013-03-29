package uk.veasmkii.states;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

	private String mouse = "No input";

	private float faceY = 0, faceX = 0;

	public Menu() {}

	@Override
	public void init( final GameContainer container, final StateBasedGame game )
			throws SlickException {

	}

	@Override
	public void render( final GameContainer container,
			final StateBasedGame game, final Graphics g ) throws SlickException {

		g.drawString( mouse, 50, 50 );
		g.drawString( "Test", 0, 0 );
		g.drawRect( 50, 50, 50, 50 );
		g.drawRect( 50, 50, 50, 60 );

		final Image image = new Image( "res/tidus.png" );
		g.drawImage( image, faceX, faceY );

	}

	@Override
	public void update( final GameContainer container,
			final StateBasedGame game, final int delta ) throws SlickException {
		final int xpos = Mouse.getX();
		final int ypos = Mouse.getY();
		mouse = "Mouse Position:" + xpos + "," + ypos;

		final Input input = container.getInput();
		if ( input.isKeyDown( Input.KEY_UP ) )
			faceY -= 1;
		if ( input.isKeyDown( Input.KEY_DOWN ) )
			faceY += 1;
		if ( input.isKeyDown( Input.KEY_LEFT ) )
			faceX -= 1;
		if ( input.isKeyDown( Input.KEY_RIGHT ) )
			faceX += 1;

		if ( input.isMouseButtonDown( Input.MOUSE_RIGHT_BUTTON ) )
			game.enterState( 2 );
	}

	@Override
	public int getID() {
		return 1;
	}

}
