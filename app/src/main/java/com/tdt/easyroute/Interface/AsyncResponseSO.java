package com.tdt.easyroute.Interface;

import org.ksoap2.serialization.SoapObject;

public interface AsyncResponseSO {
    void processFinish(SoapObject output, String conexion);
}
