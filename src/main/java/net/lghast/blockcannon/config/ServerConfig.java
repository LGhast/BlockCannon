package net.lghast.blockcannon.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static ModConfigSpec.IntValue BLOCK_CANNON_COOLDOWN_TIME;
    public static ModConfigSpec.DoubleValue BLOCK_CANNON_BASE_DAMAGE;
    public static ModConfigSpec.DoubleValue BLOCK_CANNON_BASE_RADIUS;
    public static ModConfigSpec.DoubleValue BLOCK_CANNON_PROJECTILE_VELOCITY_MAX;
    public static ModConfigSpec.DoubleValue BLOCK_CANNON_PROJECTILE_VELOCITY_MIN;

    static {
        BUILDER.push("方块炮 Block Cannon");
        BLOCK_CANNON_COOLDOWN_TIME = BUILDER
                .comment("冷却时间（刻） Cooldown (in ticks)")
                .defineInRange("block_cannon_cooldown", 20, 0, 72000);
        BLOCK_CANNON_BASE_DAMAGE = BUILDER
                .comment("基础伤害 Base damage")
                .defineInRange("block_cannon_base_damage", 3.0f, 0, 999.0f);
        BLOCK_CANNON_BASE_RADIUS = BUILDER
                .comment("基础范围伤害半径 Base AOE radius")
                .defineInRange("block_cannon_base_radius", 1.8f, 0, 15.0f);
        BLOCK_CANNON_PROJECTILE_VELOCITY_MAX = BUILDER
                .comment("弹射物最大蓄力速度加成 Maximum projectile charge velocity bonus",
                        "此配置控制玩家通过蓄力最多能使弹射物发射速度增加多少。",
                        "This configuration controls how much the player can increase the projectile's launch speed at most by charging.")
                .defineInRange("block_cannon_velocity_bonus_max", 2.3, 0.0, 50.0);
        BLOCK_CANNON_PROJECTILE_VELOCITY_MIN = BUILDER
                .comment("弹射物最小速度 Minimum projectile velocity")
                .defineInRange("block_cannon_velocity_min", 0.8, 0.0, 50.0);
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
