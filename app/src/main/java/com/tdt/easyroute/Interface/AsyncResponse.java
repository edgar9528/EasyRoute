package com.tdt.easyroute.Interface;

import org.ksoap2.serialization.SoapObject;

public interface AsyncResponse {
    void processFinish(SoapObject output);
}
