package com.atixlabs.challenge.linkedlinecommonlog.data;

import com.atixlabs.challenge.linkedlinecommonlog.controller.service.util.SHA256Helper;

public class Line {
	
	private String previousHash;
	private String message;
	private int nonce;
	private String hash;
	
   public Line(String previousHash, String message) {
	   this.previousHash = previousHash;
	   this.message = message;
	   
   }
   
   public void generateHash() {
		String dataToHash = this.previousHash+this.message+this.nonce;
		String hashValue = SHA256Helper.generateHash(dataToHash);
		this.hash = hashValue;
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
	public void setMessage(String message) {
		this.message = message;
	}
	public int getNonce() {
		return nonce;
	}
	
	public void incrementNonce() {
		this.nonce++;
	}
}
