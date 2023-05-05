package org.ait;

import java.rmi.Naming;

public class RegisterService {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			RObject robj = new RObjectImpl();
			Naming.rebind("rmi://localhost:1099/RObjectServer", robj);
			System.out.println("Registered...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}