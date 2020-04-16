package com.example;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.spongepowered.api.Game;
import org.spongepowered.api.Platform.Type;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.network.ChannelBinding;
import org.spongepowered.api.network.ChannelBinding.RawDataChannel;
import org.spongepowered.api.network.ChannelBuf;
import org.spongepowered.api.network.PlayerConnection;
import org.spongepowered.api.network.RawDataListener;
import org.spongepowered.api.network.RemoteConnection;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.economy.EconomyService;

import com.google.inject.Inject;

@Plugin(id = "exampleplugin", name = "Example Plugin", version = "1.0", description = "Example")
public class Main {
	@Inject
    public static Logger logger;
	RawDataChannel rawDataChannel;
	public static EconomyService economyService;
	@Inject Game game;
	@Listener
    public void onInit(GameStartedServerEvent event)
    {
		Optional<EconomyService> serviceOpt = Sponge.getServiceManager().provide(EconomyService.class);
		if (!serviceOpt.isPresent()) {
		    logger.info("LOL");
		}
		EconomyService economyService = serviceOpt.get();
        rawDataChannel = this.game.getChannelRegistrar().createRawChannel(this, "bijis:money");
        rawDataChannel.addListener(Type.SERVER, new HandlerMoney(rawDataChannel, economyService));
    }
   
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event)
    {
//        logger.info("sending packet to : " + event.getTargetEntity().getName());
//        sendPacket(event.getTargetEntity(), new String[] {"Test"});
    }

	
}
