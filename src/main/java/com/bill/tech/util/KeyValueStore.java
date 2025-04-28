package com.bill.tech.util;

import lombok.Data;

@Data
public class KeyValueStore {
	 private final String accessKey;
	    private final String secretKey;
	    private final String region;
	    private final String bucketName;

	    public KeyValueStore(String accessKey, String secretKey, String region, String bucketName) {
	        this.accessKey = accessKey;
	        this.secretKey = secretKey;
	        this.region = region;
	        this.bucketName = bucketName;
	    }

//	    name=dhfz7sasz
//	    	api-ky=514176814984393
//	    		cloudini-secASret=qLih9n4PeRPU99I4EWDu69W6y4M
}
