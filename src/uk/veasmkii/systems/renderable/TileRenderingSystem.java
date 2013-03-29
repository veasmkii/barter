package uk.veasmkii.systems.renderable;

import static uk.veasmkii.states.Play.isIsometric;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import uk.veasmkii.component.Coordinate;
import uk.veasmkii.component.Position;
import uk.veasmkii.component.Size;
import uk.veasmkii.component.tag.Tile;
import uk.veasmkii.systems.TickSystem;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.managers.TagManager;

public class TileRenderingSystem extends TickSystem {

	@Mapper ComponentMapper<Coordinate> cm;
	@Mapper ComponentMapper<Position> pm;
	@Mapper ComponentMapper<Size> sm;

	@SuppressWarnings( "unchecked" )
	public TileRenderingSystem( final GameContainer container ) {
		super( container, Aspect.getAspectForAll( Coordinate.class, Size.class,
				Position.class, Tile.class ) );
	}

	@Override
	public void process( final GameContainer container, final Entity e ) {
		final Size size = sm.get( e );
		final Position position = pm.get( e );
		final Coordinate coordinates = cm.get( e );

		final Graphics g = container.getGraphics();

		final Coordinate playerCoordinate = world.getManager( TagManager.class )
				.getEntity( "HELENA" ).getComponent( Coordinate.class );

		final boolean highlight = coordinates.equals( playerCoordinate );

		final Shape shape = isIsometric() ? drawIsometric( size )
				: drawGrid( size );

		shape.setX( position.getX() );
		shape.setY( position.getY() );

		final boolean hasMouse = shape.contains( Mouse.getX(), Mouse.getY() );

		final Color color = hasMouse ? Color.white
				: highlight ? Color.lightGray : Color.darkGray;

		g.setColor( Color.green );
		g.fill( shape );
		g.setColor( color );
		g.draw( shape );

	}

	private Shape drawGrid( final Size size ) {
		return new Rectangle( 0, 0, size.getWidth(), size.getHeight() );
	}

	private Shape drawIsometric( final Size size ) {
		return createSurfacePolygon( 0, 0, size.getWidth(), size.getHeight() );
	}

	public Polygon createSurfacePolygon( final float x, final float y,
			final int width, final int height ) {

		final Polygon surfacePoly = new Polygon();
		surfacePoly.addPoint( x, y );
		surfacePoly.addPoint( x - ( width / 2 ), y + ( height / 2 ) );
		surfacePoly.addPoint( x, ( y + height ) );
		surfacePoly.addPoint( x + ( width / 2 ), y + ( height / 2 ) );

		return surfacePoly;
	}

	public Polygon createLeftFacePolygon( final Graphics g, final int x,
			final int y, final int width, final int height ) {
		final Polygon leftPoly = new Polygon();
		leftPoly.addPoint( x - ( width / 2 ), y + ( height / 2 ) );
		leftPoly.addPoint( x - ( width / 2 ), (int) ( y + ( height * 1.5 ) ) );
		leftPoly.addPoint( x, y + ( height * 2 ) );
		leftPoly.addPoint( x, y );
		return leftPoly;
	}

	public Polygon createRightFacePolygon( final Graphics g, final int x,
			final int y, final int width, final int height ) {
		final Polygon leftPoly = new Polygon();
		leftPoly.addPoint( x + ( width / 2 ), y + ( height / 2 ) );
		leftPoly.addPoint( x + ( width / 2 ), (int) ( y + ( height * 1.5 ) ) );
		leftPoly.addPoint( x, y + ( height * 2 ) );
		leftPoly.addPoint( x, y );
		return leftPoly;
	}

}
