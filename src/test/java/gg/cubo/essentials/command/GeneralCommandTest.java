package gg.cubo.essentials.command;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import be.seeseemelk.mockbukkit.inventory.PlayerInventoryMock;
import gg.cubo.essentials.EssentialsTest;
import gg.cubo.essentials.types.GameModes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.*;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class GeneralCommandTest {

    private static ServerMock server;
    private static EssentialsTest plugin;
    private static PlayerMock player;

    @BeforeAll
    public static void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(EssentialsTest.class);
        player = server.addPlayer();
    }

    @AfterAll
    public static void tearDown() {
        MockBukkit.unload();
    }

    @Test
    @DisplayName("Test the /hat command and see if the player helmet is changed")
    public void testHatCommand() {
        // Set the diamond het to the player armor inventory
        player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));

        // Set a wool item in player main hand
        player.getInventory().setItemInHand(new ItemStack(Material.WOOL));

        PlayerInventoryMock inventory = (PlayerInventoryMock) player.getInventory();

        ItemStack hand = player.getItemInHand();
        ItemStack helmet = inventory.getHelmet();

        if (hand != null && hand.getType() != Material.AIR) {
            // Define the helmet item to the hand item
            inventory.setHelmet(hand);

            // Define the hand item to the helmet
            player.setItemInHand(helmet);
        }

        assertTrue(inventory.getHelmet().isSimilar(hand), "O item esperado não é o mesmo da variável 'hand'");
    }

    @Test
    @DisplayName("Test the /gm command and see if the player gamemode is changed")
    public void testGamemodeCommand() {
        // Generate some random game mode id
        int random = ThreadLocalRandom.current().nextInt(0, 3);
        GameModes mode = GameModes.findById(random);

        // Assert that the mode is not null
        assertNotNull(mode);

        // Set the player game mode
        player.setGameMode(mode.getMode());

        // assert that the player gamemode is changed
        player.assertGameMode(mode.getMode());
    }
}
