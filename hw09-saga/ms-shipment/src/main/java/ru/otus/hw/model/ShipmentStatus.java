package ru.otus.hw.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShipmentStatus {
    FAILED,
    CANCELED,
    FINISH;
}
