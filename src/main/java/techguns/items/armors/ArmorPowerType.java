package techguns.items.armors;

import techguns.util.TextUtil;

public enum ArmorPowerType {

	STEAM,
	RF;

	@Override
	public String toString() {
        return switch (this) {
            case STEAM -> TextUtil.transTG("tooltip.powertype.Steam");
            case RF -> TextUtil.transTG("tooltip.powertype.RF");
        };
	}
}
