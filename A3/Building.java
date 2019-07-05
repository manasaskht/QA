// Building is the leaf node for the composite pattern, Square's can have MULTIPLE Buildings
// Buildings cannot have children.
public class Building extends BoardComponent
{
	private int buildingHealth;
	
	public Building()
	{
		super();
		buildingHealth = 2;
	}

	@Override
	public void Operation()
	{
		// Buildings just stand there, they don't do anything.
	}

	@Override
	public void Add(BoardComponent child)
	{
		// Do nothing, I'm a leaf.
	}

	@Override
	public void Remove(BoardComponent child)
	{
		// Do nothing, I'm a leaf.
	}
	@Override
	public void Update(BoardComponent asteroidHit) {

		// Health  decrements by 1, for the building when it is hit by an asteroid
		this.buildingHealth -= 1;
		if (this.buildingHealth == 0) {

			// If building health is 0 or goes to 0, decrement the total building count in the square by 1 and remove the building from the parent square
			parent.Remove(this);

			GameBoard.Instance().DecrementBuildingCount();

		}

	}
}