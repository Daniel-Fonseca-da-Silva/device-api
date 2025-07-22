package com.dafon.device_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class NotFoundException extends CustomException{

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pb.setTitle("The field was not found in the database.");
        return pb;
    }
}
