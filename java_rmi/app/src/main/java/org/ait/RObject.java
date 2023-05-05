package org.ait;
import java.rmi.*;

public interface RObject extends Remote {
  // egyszerű paraméterátadás
  void primitiveArg(int num) throws RemoteException;
  
  // érték szerinti paraméterátadás
  void argumentByValue(Integer num) throws RemoteException;
}