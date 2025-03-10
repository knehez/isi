package org.ait;

import java.rmi.Naming;

public class Client {
	public static void main(String[] args) {
		try {
			// lookup the remote object
			RObject robj = (RObject) Naming.lookup("rmi://localhost:1099/RObjectServer");

			// simpe argument
			robj.primitiveArg(1234);

			// serializable argument
			robj.argumentByValue(new Integer(1234));

		} catch (Exception e) {
			e.printStackTrace();
			;
		}
	}
}