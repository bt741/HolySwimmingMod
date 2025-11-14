package be.breroeluean.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.entity.EntityPose;

@Environment(EnvType.CLIENT)
public class SwimCancelEventListener {
    public static void register() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client == null || client.player == null) {
                return;
            }
            if (client.player.isSwimming()) {
                client.player.setPose(EntityPose.STANDING);
            }
        });
    }
}
