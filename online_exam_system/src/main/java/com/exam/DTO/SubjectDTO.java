package com.exam.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private Integer subId;
    private String subName;
    private Integer majorId;
    private String majorName;
    private Integer current;
    private Integer size;
}
