package com.example;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.spongepowered.api.Platform.Type;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.network.ChannelBinding.RawDataChannel;
import org.spongepowered.api.network.ChannelBuf;
import org.spongepowered.api.network.PlayerConnection;
import org.spongepowered.api.network.RawDataListener;
import org.spongepowered.api.network.RemoteConnection;
import org.spongepowered.api.service.economy.EconomyService;
import org.spongepowered.api.service.economy.account.UniqueAccount;

import com.google.inject.Inject;

public class HandlerMoney implements RawDataListener {
	private Logger logger;
	private RawDataChannel channel;
	private EconomyService economyService;
	
	public HandlerMoney(RawDataChannel rawDataChannel, EconomyService economyService) {
		this.channel = rawDataChannel;
		this.economyService = economyService;
	}

	@Override
	public void handlePayload(ChannelBuf data, RemoteConnection connection, Type side) {
		Player player = ((PlayerConnection) connection).getPlayer();
			String money_string = "test";
			Optional<UniqueAccount> uOpt = economyService.getOrCreateAccount(player.getUniqueId());
			if (uOpt.isPresent()) {
			    UniqueAccount acc = uOpt.get();
			    BigDecimal balance = acc.getBalance(economyService.getDefaultCurrency());
			    Float money = balance.floatValue();
			    money_string = Float.toString(money);
			}
			sendPacket(player, money_string);
//		sendPacket(player, money_string);
		
	}
	
	public void sendPacket(Player player, String money_string)
    {
		channel.sendTo(player, buf ->
        {
            buf.writeUTF(money_string);
        });
    }
   
    public void sendPacket(Player player, Consumer<ChannelBuf> payload)
    {
    	channel.sendTo(player, payload);
    }
   
    public void sendToAll(String[] messages)
    {
    	channel.sendToAll(buf ->{
            for(String message : messages)
            {
                buf.writeString(message);
            }
        });
    }
   
    public void sendToAll(Consumer<ChannelBuf> payload)
    {
    	channel.sendToAll(payload);
    }

}
