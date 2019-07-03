// Asteroid is a leaf component in the composite structure
public class Asteroid extends BoardComponent
{
	private int height;
	
	public Asteroid(int height)
	{
		super();
		this.height = height;
	}
	
	@Override
	public void Operation()
	{
		// The operation performed by Asteroid objects is to fall from the sky
		// one level at a time, when they hit the ground (height == 0) they impact
		// and destroy whatever buildings are in the square!
		height -= 1;
		if (0 == height)
		{
			// When an Asteroid impacts the ground it needs to send an event to the
			// observer to tell it that it impacted the ground in the square it belongs
			// to.
			// <-- Send event to observer.
			
			// It should then remove itself from its parent, it no longer exists in the
			// hierarchy and should not receive any more operations.
			parent.Remove(this);		
		}
	}

	@Override
	public void Add(BoardComponent child)
	{
		// I'm a leaf!
	}

	@Override
	public void Remove(BoardComponent child)
	{
		// I'm a leaf!
	}	
}