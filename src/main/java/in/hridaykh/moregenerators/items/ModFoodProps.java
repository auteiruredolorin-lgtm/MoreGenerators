package in.hridaykh.moregenerators.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProps {

	public static final FoodProperties RADISH = new FoodProperties.Builder().nutrition(3).saturationModifier(0.25f).fast().alwaysEdible()
			.effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 3, true, false, false), 1.0f).build();

}
