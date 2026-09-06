package techguns.api.tginventory;

import techguns.util.TextUtil;

public enum TGSlotType {
	NORMAL,
	FACESLOT,
	BACKSLOT,
	HANDSLOT,
	AMMOSLOT,
	FOODSLOT,
	HEALSLOT,
	TURRETARMOR,
	REACTION_CHAMBER_FOCUS,
	DRILL_SMALL,
	DRILL_MEDIUM,
	DRILL_LARGE,
	ARMOR_UPGRADE;

	@Override
	public String toString() {
        return switch (this) {
            case FACESLOT -> trans("face");
            case BACKSLOT -> trans("back");
            case HANDSLOT -> trans("hands");
            case AMMOSLOT -> trans("ammo");
            case FOODSLOT -> trans("food");
            case HEALSLOT -> trans("healing");
            case TURRETARMOR -> trans("turret_armor");
            case REACTION_CHAMBER_FOCUS -> trans("reaction_focus");
            case DRILL_SMALL -> trans("small_drill");
            case DRILL_MEDIUM -> trans("medium_drill");
            case DRILL_LARGE -> trans("large_drill");
            case ARMOR_UPGRADE -> trans("armor_ugpgrade");
            default -> trans("normal_item");
        };
	}
	
	private String trans(String key) {
		return TextUtil.transTG("tooltip.tgslottype."+key);
	}
	
}
