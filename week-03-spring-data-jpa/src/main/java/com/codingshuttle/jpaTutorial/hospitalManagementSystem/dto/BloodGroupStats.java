package com.codingshuttle.jpaTutorial.hospitalManagementSystem.dto;

import com.codingshuttle.jpaTutorial.hospitalManagementSystem.entity.type.BloodGroupType;
import lombok.Data;

@Data
public class BloodGroupStats {
    private final BloodGroupType bloodGroupType;
    private final Long count;
}
