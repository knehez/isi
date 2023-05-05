package org.ait;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RObjectImpl extends UnicastRemoteObject implements RObject {
      private static final long serialVersionUID = 6350331764929058681L;
      public RObjectImpl() throws RemoteException {
      } 
  
      @Override
      public void primitiveArg(int num) throws RemoteException {
         System.out.println(num);
      }

      @Override
      public void argumentByValue(Integer num) throws RemoteException {
         System.out.println(num);
      }
}