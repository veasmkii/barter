package uk.veasmkii.component;

import lombok.Getter;
import lombok.Setter;

import com.artemis.Component;

public class Health extends Component {

	@Getter private int maximumHealth = 0;
	@Getter private int health = 0;
	/*
	 * FIXME I don't know why the delta wont accept millis directly in the
	 * HealingSystem.
	 */
	@Getter @Setter private int damageCooldownDelta = 5000 / 100;
	@Getter private final Expiry damageCooldown = new Expiry();

	public Health() {}

	public Health( final int maximumHealth, final int health ) {
		setMaximumHealth( maximumHealth );
		setHealth( health );
	}

	public void setMaximumHealth( final int maximumHealth ) {
		this.maximumHealth = maximumHealth;
		if ( health > maximumHealth )
			health = maximumHealth;
	}

	public void setHealth( final int health ) {
		if ( this.health > maximumHealth )
			this.health = maximumHealth;
		else if ( health < 0 )
			this.health = 0;
		else
			this.health = health;
	}

	public int getPercentage() {
		return (int) ( ( Float.valueOf( health ) / Float
				.valueOf( maximumHealth ) ) * 100 );
	}

	public void addHealth( final int health ) {
		final int tempHealth = this.health + health;
		if ( tempHealth < this.health )
			damageCooldown.setDuration( damageCooldownDelta );
		if ( tempHealth > maximumHealth )
			this.health = maximumHealth;
		else if ( tempHealth < 0 )
			this.health = 0;
		else
			this.health = tempHealth;
	}

	public boolean recentlyDamaged() {
		return !damageCooldown.isExpired();
	}

	public void reset() {
		health = maximumHealth;
	}

	public boolean isAlive() {
		return health > 0;
	}

	@Override
	public String toString() {
		return "Health: " + health + " - " + maximumHealth + " ("
				+ getPercentage() + ") isAlive:" + isAlive();
	}
}
