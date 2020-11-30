package com.bank.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
class AbstractDto implements Serializable {

    private Long id;

    private LocalDateTime created;

    private LocalDateTime updated;
}