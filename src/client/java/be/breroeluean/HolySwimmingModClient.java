package be.breroeluean;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.lwjgl.glfw.GLFW;

public class HolySwimmingModClient implements ClientModInitializer {
    private static KeyBinding keyBinding;
    private static boolean swimCancelEnabled = true; // toggle state

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "Cancel swimming animation (Toggle)",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "HolySwimmingMod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed()) {
                swimCancelEnabled = !swimCancelEnabled;

                if (client.player != null) {
                    String status = swimCancelEnabled ? "Enabled" : "Disabled";
                    // achievement-like popup
                    client.getToastManager().add(new SystemToast(
                            SystemToast.Type.NARRATOR_TOGGLE,
                            Text.literal("HolySwimmingMod"),
                            Text.literal(status + " animation canceling")
                    ));
                }
            }

            if (client.world != null && swimCancelEnabled) {
                disableSwimming(client.world);
            }
        });
    }

    private static void disableSwimming(World world) {
        for (PlayerEntity player : world.getPlayers()) {
            if (player.isSwimming()) {
                player.setSwimming(false);
                player.setPose(EntityPose.STANDING);
            }
        }
    }
}
