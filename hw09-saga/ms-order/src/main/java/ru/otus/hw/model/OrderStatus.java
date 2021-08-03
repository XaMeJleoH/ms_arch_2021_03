package ru.otus.hw.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
    IN_PROGRESS,
    FAILED,
    CANCELED,
    FINISH;
}
