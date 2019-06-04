import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shameera on Mar, 2019
 */
public class Investor implements StockMarketHandlerr {

	private String id;
	private int budget;

	private int amountSpent;
	private int ownShares;
	private List<Company> investedCompanies = new ArrayList<>();

	private Investor(String id,int budget){
		this.id = id;
		this.budget = budget;
	}

	public void addInvestedCompany(Company c){
		investedCompanies.add(c);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budjet) {
		this.budget = budjet;
	}

	public int getAmountSpent() {
		return amountSpent;
	}

	public void setAmountSpent(int amountSpent) {
		this.amountSpent = amountSpent;
	}

	public int getOwnShares() {
		return ownShares;
	}

	public void setOwnShares(int ownShares) {
		this.ownShares = ownShares;
	}

	public List<Company> getInvestedCompanies() {
		return investedCompanies;
	}

	/* Using Builder Design pattern to which is a creational pattern force
	 * us to create an object by injecting the parameters that we want. without providing those parameters
	 * user cant create object of that class
	*/
	public static class InvestorBuilder{
		private String id;
		private int budget;

		public InvestorBuilder setId(String id){
			this.id = id;
			return this;
		}

		public InvestorBuilder setBudjet(int budget){
			this.budget = budget;
			return this;
		}

		public Investor build(){
			return new Investor(id,budget);
		}
	}

	@Override
	public int updateInitial(List<?> list) {
		//List<Investor> investors = (List<Investor>) list;
		//int allInvestorBudget = investors.stream().mapToInt(value -> value.getBudget()).sum();
		return 1;
	}

	@Override
	public int updateCurrent(List<?> list) {
		List<Investor> investors = (List<Investor>) list;
		int currentInvestorsBudget = investors.stream().mapToInt(investor -> investor.getBudget()).sum();
		return currentInvestorsBudget;
	}

	@Override
	public void elementDetails(Object obj) {
		System.out.println("Investor ID:"+id+"  "+"Budget:"+budget);
	}
}
