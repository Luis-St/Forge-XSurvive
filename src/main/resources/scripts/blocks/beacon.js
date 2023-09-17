function range(beaconLevel) {
	return beaconLevel * 20 + 20;
}

function netheriteRangeModifier(beaconLevel, effectRange) {
	return 3;
}

function netheriteEffectDuration(beaconLevel, effectRange) {
	return (10 + beaconLevel * 10) * 20;
}

function netheriteEffectAmplifier(beaconLevel, effectRange) {
	return beaconLevel;
}

function diamondRangeModifier(beaconLevel, effectRange) {
	return 2;
}

function vanillaEffectAmplifier(beaconLevel, effectRange, sameEffectSelected) {
	if (beaconLevel >= 4 && sameEffectSelected) {
		return 1;
	}
	return 0;
}

function primaryEffectAmplifier(beaconLevel, effectRange, sameEffectSelected, isDiamondBeacon, stackedAmplifier, vanillaAmplifier) {
	return isDiamondBeacon ? beaconLevel : stackedAmplifier;
}

function primaryEffectDuration(beaconLevel, effectRange, sameEffectSelected, isDiamondBeacon) {
	return (10 + beaconLevel * 10) * 20;
}

function secondaryEffectAmplifier(beaconLevel, effectRange, isDiamondBeacon, vanillaAmplifier) {
	if (isDiamondBeacon) {
		return beaconLevel;
	}
	return 0;
}

function secondaryEffectDuration(beaconLevel, effectRange, isDiamondBeacon) {
	return (10 + beaconLevel * 10) * 20;
}
