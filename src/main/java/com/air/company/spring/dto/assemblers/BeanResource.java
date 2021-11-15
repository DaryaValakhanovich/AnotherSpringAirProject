package com.air.company.spring.dto.assemblers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

@JsonIgnoreProperties({ "id" })
public class BeanResource extends ResourceSupport {

    @JsonUnwrapped
    private Object resorce;

    public BeanResource(Object resorce) {
        this.resorce = resorce;
    }

    public Object getResorce() {
        return resorce;
    }

}
