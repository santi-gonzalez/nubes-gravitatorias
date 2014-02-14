package cat.santi.nubes.bichos;

import android.graphics.Canvas;
import android.graphics.Color;
import cat.santi.nubes.logic.Logic;

public class Slingshot extends AbsBicho {

	private float mRadius = 10f;
	
	public Slingshot() {
		init();
	}
	
	@Override
	public void init() {

		mPaint.setColor(Color.BLACK);
		mPosX = 200f;
		mPosY = Logic.screenHeight - 200f;
	}

	@Override
	public void draw(Canvas canvas) {
		
		canvas.drawCircle(mPosX, mPosY, mRadius, mPaint);
	}
}
