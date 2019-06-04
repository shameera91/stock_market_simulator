import java.util.List;

/**
 * Created by Shameera on Mar, 2019
 */
public class Company implements StockMarketHandlerr {

	private String id;
	private int shares;
	private int shareValue;

	private int soldShares;
	private int capital;

	private Company(String id,int shares,int shareValue){
		this.id = id;
		this.shares = shares;
		this.shareValue = shareValue;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public int getShareValue() {
		return shareValue;
	}

	public void setShareValue(int shareValue) {
		this.shareValue = shareValue;
	}

	public int getSoldShares() {
		return soldShares;
	}

	public void setSoldShares(int soldShares) {
		this.soldShares = soldShares;
	}

	public int getCapital() {
		return capital;
	}

	public void setCapital(int capital) {
		this.capital = capital;
	}

	/* Using Builder Design pattern to which is creational pattern force
	 * us to create an object by injecting the parameters that we want. without providing those parameters
	 * user cant create object of that class
	 */
	public static class CompanyBuilder{
		private String id;
		private int shares;
		private int shareValue;

		public CompanyBuilder setId(String id){
			this.id = id;
			return this;
		}

		public CompanyBuilder setShares(int shares){
			this.shares = shares;
			return this;
		}

		public CompanyBuilder setShareValue(int shareValue){
			this.shareValue = shareValue;
			return this;
		}

		public Company build(){
			return new Company(id,shares,shareValue);
		}
	}

	@Override
	public int updateInitial(List<?> list) {
		List<Company> companies = (List<Company>) list;
		int initialShares = companies.stream().mapToInt(value -> value.getShares()).sum();
		return initialShares;
	}

	@Override
	public int updateCurrent(List<?> list) {
		List<Company> companies = (List<Company>) list;
		int soldShares = companies.stream().mapToInt(value -> value.getSoldShares()).sum();
		return soldShares;
	}

	@Override
	public void elementDetails(Object obj) {
		System.out.println("Company ID:"+id+"  "+"Shares"+shares);
	}
}
