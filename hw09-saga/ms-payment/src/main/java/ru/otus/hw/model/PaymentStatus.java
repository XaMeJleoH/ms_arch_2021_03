package ru.otus.hw.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {
    FAILED,
    CANCELED,
    FINISH;
}
