package com.kbtg.bootcamp.posttest.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LotteryRequestDto {

    @NotNull(message = "require ticket")
    @Size(min = 6, max = 6, message = "ticket length should be 6")
    private String ticket;

    @NotNull(message = "require price")
    private int price;

    @NotNull(message = "require amount")
    private int amount;
}
