package ru.fastdelivery.domain.delivery.pack;

import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 * @param volume объем упаковки
 */
public record Pack(Weight weight, Volume volume) {}
