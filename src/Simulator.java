import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Shameera on Apr, 2019
 */
public class Simulator {
	List<Company> companies = new ArrayList<>();
	List<Investor> investors = new ArrayList<>();
	List<Investor> creditNotEnoughInvestors = new ArrayList<>();
	StockMarket market = new StockMarket();  /*Creating a stock market object to implement composite design pattern usages*/
	TradingAlertContext tradingState = new TradingAlertContext(); /*Contect object is created to check the usage of state design pattern*/

	Investor inv = new Investor.InvestorBuilder().setId("TEST").setBudjet(100).build();  /*Creating Investor object*/
	Company comp = new Company.CompanyBuilder().setId("TEST").setShares(100).setShareValue(10).build();  /*Creating Company object*/

	public void run(){
		//System.out.println("Initiating -------------------------------------------------------------------------------");
		tradingState.status(); /*Usage of the state design pattern*/
		StockMarket market = new StockMarket();
		companies = market.addCompanies();  /*another usage of composite design pattern is to initialize the Lists that we are going to use later*/
		investors = market.addInvestors();

		//System.out.println("Trading Day Starts -----------------------------------------------------------------------");
		tradingState.setState(new Happening()); /*Usage of the state design pattern*/
		tradingState.status();

		int initialShares = comp.updateInitial(companies);
		int soldShare = comp.updateCurrent(companies);
		int investorTotalBudget = inv.updateCurrent(investors);
		//System.out.println("Initial Customer Budget : " + investorTotalBudget);
		//System.out.println("initial share count : " + initialShares);

		while ((initialShares != soldShare) && (investors.size() > 0)) {
			Collections.shuffle(companies);
			Collections.shuffle(investors);
			Company company = companies.get(0);   /*get a random company*/
			Investor investor = investors.get(0); /*get a random investor*/

			if (company.getShares() > company.getSoldShares()) {

				int goingToSpend = (company.getShareValue());
				//System.out.println("One Share Value of the Company :" + company.getShareValue());
				//System.out.println("Available Investor Budget :" + investor.getBudget());

				if (goingToSpend <= investor.getBudget()) {

					//System.out.println("----------------------- Transaction Start ---------------------------------");

					company.setSoldShares(company.getSoldShares() + 1);
					investor.setBudget(investor.getBudget() - goingToSpend);
					investor.setOwnShares(investor.getOwnShares() + 1);
					investor.setAmountSpent(investor.getAmountSpent() + goingToSpend);

					if (!investor.getInvestedCompanies().contains(company)) {
						investor.addInvestedCompany(company);
					}

					//System.out.println("----------------------- Transaction End -----------------------------------\n\n");

					if (company.getSoldShares() == 10) {  /*if company sold ten shares double the share amount*/
						company.setShareValue(company.getShareValue() * 2);
					}

					soldShare = comp.updateCurrent(companies);  /*updating the global sold share count*/

					if (soldShare == 10) { /*reducing share price by half if that company shares nothing*/
						for (Company c : companies) {
							if (c.getSoldShares() == 0) {
								if (c.getShareValue() > 0) {
									c.setShareValue(c.getShareValue() / 2); /*price reduce by half*/
								}

							}
						}
					}

					investorTotalBudget = inv.updateCurrent(investors);  /*updating remaning budjet for all customers*/

				} else {
					/*if an investor doesn't have enough budget then that investor is added to new list calling 'creditNotEnoughInvestors' */

					//System.out.println("You Don't Have Enough Budjet :" + investor.getId());
					investors.remove(0);
					creditNotEnoughInvestors.add(investor);
					//System.out.println(creditNotEnoughInvestors.size() + "  " + investors.size());
				}
			} else {
				System.out.println("All Shares Have Been Sold for Company :" + company.getId());
			}
		}
		//System.out.println("Trading Day Ends ------------------------------------------------------------------------");
		tradingState.setState(new End());/*Usage of the state design pattern*/
		tradingState.status();

		int investorSpentBudget = creditNotEnoughInvestors.stream().mapToInt(i -> i.getAmountSpent()).sum();


		System.out.println("-----------------------------------------------------------------------------------------\n\n");
		System.out.println("--- Initial Shares :" + initialShares);
		System.out.println("--- Sold Shares :" + soldShare);
		System.out.println("--- Customer Spent Budget :" + investorSpentBudget);

		System.out.println("-------------------------------------------------------------------------------------------\n\n");
		Company highestCapitalCompany = companies.get(0);
		List<Company> highestCapitalCompanies = new ArrayList<>();
		for (Company c : companies) {
			c.setCapital(c.getShares() * c.getShareValue());
			if (c.getCapital() > highestCapitalCompany.getCapital()) {
				highestCapitalCompany = c;
			}
		}
		for(Company c:companies){  /*checking for multiple companies with same highest capital*/
			if(highestCapitalCompany.getCapital() == c.getCapital()){
				highestCapitalCompanies.add(c);
			}
		}
		System.out.println("Company/Companies with highest capital:");
		highestCapitalCompanies.stream().forEach(company -> {
			System.out.println("Company ID:"+company.getId()+"  "+"Capital:"+company.getCapital());
		});



		System.out.println("-----------------------------------------------------------------------------------------");
		Company lowestCapitalCompany = companies.get(0);
		List<Company> lowestCapitalCompanies = new ArrayList<>();
		for (Company c : companies) {
			c.setCapital(c.getShares() * c.getShareValue());
			if (c.getCapital() < lowestCapitalCompany.getCapital()) {
				lowestCapitalCompany = c;
			}
		}
		for(Company c:companies){  /*checking for multiple companies with same highest capital*/
			if(lowestCapitalCompany.getCapital() == c.getCapital()){
				lowestCapitalCompanies.add(c);
			}
		}
		System.out.println("Company/Companies with lowest capital:");
		lowestCapitalCompanies.stream().forEach(company -> {
			System.out.println("Company ID:"+company.getId()+"  "+"Capital:"+company.getCapital());
		});



		System.out.println("-----------------------------------------------------------------------------------------");
		Investor highestInvestor = creditNotEnoughInvestors.get(0);
		List<Investor> highestInvestorList = new ArrayList<>();
		for (Investor c : creditNotEnoughInvestors) {
			if (c.getOwnShares() > highestInvestor.getOwnShares()) {
				highestInvestor = c;
			}
		}
		for(Investor i:creditNotEnoughInvestors){
			if(highestInvestor.getOwnShares() == i.getOwnShares()){
				highestInvestorList.add(i);
			}
		}
		System.out.println("Highest Investor/investors ,owned shares:");
		highestInvestorList.stream().forEach(investor -> {
			System.out.println("Investor ID:"+investor.getId()+"  "+"Own Shares:"+investor.getOwnShares());
		});



		System.out.println("-----------------------------------------------------------------------------------------");
		Investor lowestInvestor = creditNotEnoughInvestors.get(0);
		List<Investor> lowestInvestorList = new ArrayList<>();

		for (Investor c : creditNotEnoughInvestors) {
			if (c.getOwnShares() < lowestInvestor.getOwnShares()) {
				lowestInvestor = c;
			}
		}
		for(Investor i:creditNotEnoughInvestors){
			if(lowestInvestor.getOwnShares() == i.getOwnShares()){
				lowestInvestorList.add(i);
			}
		}
		System.out.println("Lowest Investor/investors ,owned shares:");
		lowestInvestorList.stream().forEach(investor -> {
			System.out.println("Investor ID:"+investor.getId()+"  "+"Own Shares:"+investor.getOwnShares());
		});



		System.out.println("-----------------------------------------------------------------------------------------");
		List<Investor> highestInvestedCompaniesList = new ArrayList<>();
		Investor investedInMostCompanies = creditNotEnoughInvestors.get(0);
		for (Investor i : creditNotEnoughInvestors) {
			if (i.getInvestedCompanies().size() > investedInMostCompanies.getInvestedCompanies().size()) {
				investedInMostCompanies = i;
			}
		}
		for(Investor i:creditNotEnoughInvestors){
			if(investedInMostCompanies.getInvestedCompanies().size() == i.getInvestedCompanies().size()){
				highestInvestedCompaniesList.add(i);
			}
		}
		System.out.println("Highest no of companies Investor/investors :");
		highestInvestedCompaniesList.stream().forEach(investor -> {
			System.out.println("Investor ID:"+investor.getId()+"  "+"Invested Companies:"+investor.getInvestedCompanies().size());
		});



		System.out.println("-----------------------------------------------------------------------------------------");
		List<Investor> investedInLowestdCompaniesList = new ArrayList<>();
		Investor investedInLeastCompanies = creditNotEnoughInvestors.get(0);
		for (Investor i : creditNotEnoughInvestors) {      /* should be checked for multiple lowest values ???*/
			if (i.getInvestedCompanies().size() < investedInLeastCompanies.getInvestedCompanies().size()) {
				investedInLeastCompanies = i;
			}
		}
		for(Investor i:creditNotEnoughInvestors){
			if(investedInLeastCompanies.getInvestedCompanies().size() == i.getInvestedCompanies().size()){
				investedInLowestdCompaniesList.add(i);
			}
		}
		System.out.println("Lowest no of companies Investor/investors :");
		investedInLowestdCompaniesList.stream().forEach(investor -> {
			System.out.println("Investor ID:"+investor.getId()+"  "+"Invested Companies:"+investor.getInvestedCompanies().size());
		});


		System.out.println("\n\n");
		//market.elementDetails(comp);/*prints all the companies*/
		//market.elementDetails(inv); /*prints nothing because at that point all the investor budget is over*/
		System.out.println("\n\n");


	}

}
