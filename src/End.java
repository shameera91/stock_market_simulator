/**
 * Created by Shameera on Apr, 2019
 */
public class End implements TradingAlertState {

	@Override
	public void tradingStatus(TradingAlertContext ctx) {
		System.out.println("Trading day end..........................");
	}
}
