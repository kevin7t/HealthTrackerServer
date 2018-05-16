package com.kevin.healthtracker.datamodels.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeDTO {
    private int id;
    private int userId;
    private int statusId;
}
