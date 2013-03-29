package uk.veasmkii.systems.renderable;

import org.apache.commons.lang.StringUtils;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import uk.veasmkii.component.Coordinate;
import uk.veasmkii.component.Health;
import uk.veasmkii.component.Imageable;
import uk.veasmkii.component.Position;
import uk.veasmkii.component.Size;
import uk.veasmkii.component.Title;
import uk.veasmkii.component.Velocity;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;

public class SpriteSystem extends TickSystem {

	@Mapper ComponentMapper<Coordinate> cm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Size> sm;
	@Mapper ComponentMapper<Imageable> im;
	@Mapper ComponentMapper<Velocity> vm;
	@Mapper ComponentMapper<Title> tm;
	@Mapper ComponentMapper<Health> hm;

	@SuppressWarnings( "unchecked" )
	public SpriteSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Coordinate.class, Size.class,
				Position.class, Imageable.class, Title.class, Health.class ) );
	}

	@Override
	protected void initialize() {}

	@Override
	public void process( final GameContainer container, final Entity e ) {

		final Position position = pm.get( e );
		final Image image = im.get( e ).getImage();
		final Size size = sm.get( e );
		final Title title = tm.get( e );
		final Health health = hm.get( e );

		final Graphics g = container.getGraphics();

		g.drawImage( image, position.getX(), position.getY() );

		drawTitle( g, title, size, position );
		drawHealth( g, health, size, position );
	}

	private void drawTitle( final Graphics g, final Title title,
			final Size size, final Position position ) {

		final String name = title.getName();
		if ( !StringUtils.isBlank( name ) ) {

			final Font font = g.getFont();

			final float fontWidth = font.getWidth( name );
			final float fontHeight = font.getHeight( name );

			final float titleX = ( position.getX() - ( ( fontWidth - size
					.getWidth() ) / 2 ) );
			final float titleY = ( ( position.getY() - ( size.getHeight() / 2 ) - 5 ) + fontHeight );

			g.setColor( title.getColor() );
			g.drawString( title.getName(), titleX, titleY );
		}

	}

	private void drawHealth( final Graphics g, final Health health,
			final Size size, final Position position ) {

		final float healthScale = (float) health.getHealth()
				/ health.getMaximumHealth();
		final float width = 62, height = 6;
		final float healthWidth = width * healthScale;

		final float x = ( position.getX() - ( ( width - size.getWidth() ) / 2 ) );
		final float y = ( position.getY() + size.getHeight() + height );

		g.setColor( Color.white );
		g.fillRect( x - 1, y - 1, width + 1, height + 1 );

		if ( ( healthScale * 100 ) <= 30 )
			g.setColor( Color.red );
		else if ( ( healthScale * 100 ) <= 50 )
			g.setColor( Color.orange );
		else
			g.setColor( Color.green );

		g.fillRect( x, y, healthWidth - 1, height - 1 );
	}
}
