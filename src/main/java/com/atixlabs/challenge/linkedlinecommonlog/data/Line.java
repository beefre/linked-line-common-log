package com.atixlabs.challenge.linkedlinecommonlog.data;

public class Line {
	
	private String previousHash;
	private String message;
	private int nonce;
	private String hash;
	
   public Line(String previousHash, String message) {
	   this.previousHash = previousHash;
	   this.message = message;
   }
   
	public String getHash() {
		return this.hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getPreviousHash() {
		return previousHash;
	}
	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}
	public String getMessage() {
		return message;
	}

	public int getNonce() {
		return nonce;
	}
	
	public void incrementNonce() {
		this.nonce++;
	}
}
