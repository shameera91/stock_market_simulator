/**
 * Created by Shameera on Apr, 2019
 */
public class Initialization implements TradingAlertState{

	@Override
	public void tradingStatus(TradingAlertContext ctx) {
		System.out.println("Trading Day Initializing....................");
	}
}
