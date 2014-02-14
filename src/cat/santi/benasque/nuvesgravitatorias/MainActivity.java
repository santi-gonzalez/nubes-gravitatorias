package cat.santi.benasque.nuvesgravitatorias;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import cat.santi.benasque.nuvesgravitatorias.interfaces.OnScoreChangeListener;
import cat.santi.benasque.nuvesgravitatorias.manager.SoundManager;
import cat.santi.benasque.nuvesgravitatorias.view.GameView;

public class MainActivity extends Activity implements OnScoreChangeListener {

	private GameView mGameView;
	private TextView mGameRiemann;
	private TextView mGameTarget;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        mGameView = (GameView)findViewById(R.id.game_main);
        mGameRiemann = (TextView)findViewById(R.id.game_riemann);
        mGameTarget = (TextView)findViewById(R.id.game_target);
        
        mGameView.setOnScoreChangeListener(this);
    }

	@Override
	public void onRiemannScoreChange(int score) {
	
		if(mGameRiemann != null)
			mGameRiemann.setText("Anulaciones de la función Z de Riemann: " + score);
	}

	@Override
	public void onMainScoreChange(int score) {
	
		if(mGameTarget != null)
			mGameTarget.setText("Número de dianas: " + score);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		SoundManager.release();
	}
}
