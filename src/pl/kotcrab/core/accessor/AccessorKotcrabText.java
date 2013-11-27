package pl.kotcrab.core.accessor;


import pl.kotcrab.core.KotcrabText;
import aurelienribon.tweenengine.TweenAccessor;

public class AccessorKotcrabText implements TweenAccessor<KotcrabText>
{
	// The following lines define the different possible tween types.
	// It's up to you to define what you need :-)
	
	public static final int POSITION_XY = 1;
	public static final int ALPHA = 2;

	
	// TweenAccessor implementation
	
	@Override
	public int getValues(KotcrabText target, int tweenType, float[] returnValues)
	{
		switch (tweenType)
		{
		case POSITION_XY:
			returnValues[0] = target.getPosition().x;
			returnValues[1] = target.getPosition().y;
			return 2;
		case ALPHA:
			returnValues[0] = target.getColor().a;
			return 1;
		default:
			assert false;
			return -1;
		}
	}
	
	@Override
	public void setValues(KotcrabText target, int tweenType, float[] newValues)
	{
		switch (tweenType)
		{
		case POSITION_XY:
			target.setPosition(newValues[0], newValues[1]);
			break;
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
		default:
			assert false;
			break;
		}
	}
}