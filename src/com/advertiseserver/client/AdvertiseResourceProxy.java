package com.advertiseserver.client;

import org.restlet.client.resource.ClientProxy;
import org.restlet.client.resource.Delete;
import org.restlet.client.resource.Get;
import org.restlet.client.resource.Put;
import org.restlet.client.resource.Result;

import com.advertiseserver.domain.Ad;

public interface AdvertiseResourceProxy extends ClientProxy {
    @Get
    public void retrieve(Result<Ad> callback);

    @Put
    public void store(Ad contact, Result<Void> callback);

    @Delete
    public void remove(Result<Void> callback);

}
