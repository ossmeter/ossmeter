package sparky;

public abstract class Scale<T> {
	
	protected T maxDomain;
	protected T minDomain;
	protected double maxRange;
	protected double minRange;
	
	public Scale(T maxDomain, T minDomain, double maxRange, double minRange) {
		this.maxDomain = maxDomain;
		this.minDomain = minDomain;
		this.maxRange = maxRange;
		this.minRange = minRange;
	}

	public abstract int scale(T value);

}