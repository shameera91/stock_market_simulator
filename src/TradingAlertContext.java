/**
 * Created by Shameera on Apr, 2019
 */
public class TradingAlertContext {
	private TradingAlertState currentAlertState;

	public TradingAlertContext(){
		currentAlertState = new Initialization();
	}

	public void setState(TradingAlertState state){
		currentAlertState = state;
	}

	public void status(){
		currentAlertState.tradingStatus(this);
	}
}
