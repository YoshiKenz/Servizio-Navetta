package tests;
import persistence.utility.IdProvider;

public class MainTestMaterializedView {

	
	public static void main(String[] args) {
		
		int nextIndex = IdProvider.getInstance().getNextId("Persona");
		System.out.println(nextIndex);
		
		nextIndex = IdProvider.getInstance().getNextId("Feedback");
		System.out.println(nextIndex);
		nextIndex = IdProvider.getInstance().getNextId("domanda_riabilitazione");
		System.out.println(nextIndex);
		nextIndex = IdProvider.getInstance().getNextId("navetta");
		System.out.println(nextIndex);
	}
}
