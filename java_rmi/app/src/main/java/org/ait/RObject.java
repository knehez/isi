package org.ait;
import java.rmi.*;

public interface RObject extends Remote {
  // simple parameter passing
  void primitiveArg(int num) throws RemoteException;
  
  // parameter pass by value
  void argumentByValue(Integer num) throws RemoteException;
}