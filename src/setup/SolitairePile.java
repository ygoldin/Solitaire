package setup;

public abstract class SolitairePile {
	
	/**
	 * checks if the pile is empty
	 * 
	 * @return true if it is empty, false otherwise`
	 */
	public abstract boolean isEmpty();
	
	/**
	 * checks if the pile is empty
	 * 
	 * @throws IllegalStateException if it is
	 */
	protected void exceptionIfEmpty() {
		if(isEmpty()) {
			throw new IllegalStateException("no cards in the " + getClass().getName());
		}
	}
}
