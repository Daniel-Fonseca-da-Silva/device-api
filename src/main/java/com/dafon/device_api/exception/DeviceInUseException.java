package com.dafon.device_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class DeviceInUseException extends CustomException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pb.setTitle("Device is in use, it's not possible to delete now");

        return pb;
    }
}
