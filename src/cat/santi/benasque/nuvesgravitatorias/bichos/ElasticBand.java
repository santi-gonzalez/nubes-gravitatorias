package cat.santi.benasque.nuvesgravitatorias.bichos;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;

public class ElasticBand extends AbsBicho {

	private PointF mPointA = new PointF();
	private PointF mPointB = new PointF();
	
	public ElasticBand() {
		init();
	}
	
	@Override
	public void init() {
		
		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth(4f);
	}

	@Override
	public void draw(Canvas canvas) {
		
		canvas.drawLine(mPointA.x, mPointA.y, mPointB.x, mPointB.y, mPaint);
	}
	
	public void setPoints(PointF pointA, PointF pointB) {
		mPointA = pointA;
		mPointB = pointB;
	}
}
