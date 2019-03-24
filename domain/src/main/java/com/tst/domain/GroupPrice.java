package com.tst.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupPrice {

    private String cabinCode;
    private String rateCode;
    private BigDecimal price;
    private String rateGroup;

}
