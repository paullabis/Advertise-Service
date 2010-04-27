package com.advertiseserver.server;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.advertiseserver.domain.Ad;
import com.advertiseserver.service.AdService;

public class AdServerResource extends ServerResource implements AdService{

	private static volatile Ad ad = new Ad("Lot for Sale","200 square meter near SM Bacoor.");
	
	@Delete
	public void remove() {
		ad = null;		
	}

	@Get
	public Ad retrieve() {
		return ad;
	}

	@Post
	public void store(Ad ad) {
		AdServerResource.ad = ad;
	}

}
