package com.cjy.fat.exception;

import com.cjy.fat.data.TransactionContent;

public class FatTransactionException extends RuntimeException{

	private static final long serialVersionUID = 6753994776962949879L;

	private String txKey ; 
	
	public FatTransactionException() {
        super();
    }
	
	public FatTransactionException(String message) {
	    super(message);
	    this.txKey = TransactionContent.getRootTxKey();
	}

	public String getTxKey() {
		return txKey;
	}

	public void setTxKey(String txKey) {
		this.txKey = txKey;
	}
	
	public static FatTransactionException buildRemoteNodeErrorException(String errorServiceName) {
		return new FatTransactionException("remote node named " + errorServiceName + " error when local transaction runnning ");
	}
	
	public static void throwRemoteNodeErrorException(String errorServiceName) {
		throw buildRemoteNodeErrorException(errorServiceName);
	}
	
}
