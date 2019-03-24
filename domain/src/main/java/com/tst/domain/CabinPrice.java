package com.tst.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CabinPrice {

    private String cabinCode;
    private String rateCode;
    private BigDecimal price;
    
}
