package org.nell_nell.springboot.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class coinListDto {
    private String coinName;
    private String price;
    private String rateOfChange24;

    public coinListDto(Object coin){
        this.coinName = ((coinListDto) coin).coinName;
        this.price = ((coinListDto) coin).price;
        this.rateOfChange24 = ((coinListDto) coin).rateOfChange24;
    }
}
