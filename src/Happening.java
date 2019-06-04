/**
 * Created by Shameera on Apr, 2019
 */
public class Happening implements TradingAlertState {

	@Override
	public void tradingStatus(TradingAlertContext ctx) {
		System.out.println("Trading day happening....................");
	}
}
