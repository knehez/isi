package org.ait;

import java.rmi.Naming;

public class Client {
	public static void main(String[] args) {
		try {
			// Távoli objektum lekérése a registry-ből
			RObject robj = (RObject) Naming.lookup("rmi://localhost:1099/RObjectServer");

			// Egyszerű argumentum
			robj.primitiveArg(2012);

			// Serilaizált argumentum
			robj.argumentByValue(new Integer(2012));

		} catch (Exception e) {
			e.printStackTrace();
			;
		}
	}
}