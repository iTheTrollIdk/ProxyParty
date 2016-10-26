package me.heyimblake.proxyparty.Commands.Subcommands;

import me.heyimblake.proxyparty.Commands.AnnotatedPartySubCommand;
import me.heyimblake.proxyparty.Commands.PartySubCommandExecutor;
import me.heyimblake.proxyparty.Commands.PartySubCommandHandler;
import me.heyimblake.proxyparty.PartyUtils.Party;
import me.heyimblake.proxyparty.PartyUtils.PartyManager;
import me.heyimblake.proxyparty.ProxyParty;
import me.heyimblake.proxyparty.Utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

/**
 * Created by heyimblake on 10/26/2016.
 *
 * @author heyimblake
 *         Copyright (c) 2016 heyimblake.
 *         All rights reserved.
 */
@PartySubCommandExecutor(subCommand = "retract",
        syntax = "/party retract <Name>",
        description = "Retracts an invite from a player.",
        requiresArgumentCompletion = true,
        leaderExclusive = true,
        mustBeInParty = true)
public class RetractSubCommand extends AnnotatedPartySubCommand {

    public RetractSubCommand(PartySubCommandHandler handler) {
        super(handler);
    }

    @Override
    public void runProxiedPlayer() {
        ProxiedPlayer player = ((ProxiedPlayer) getHandler().getCommandSender());
        ProxiedPlayer target = ProxyParty.getInstance().getProxy().getPlayer(getHandler().getArguments()[0]);
        if (target == null) {
            TextComponent msg = new TextComponent("The specified player could not be found.");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        Party party = PartyManager.getInstance().getPartyOf(player);
        if (!party.getInvited().contains(target)) {
            TextComponent msg = new TextComponent(target.getName() + " is not invited to your party.");
            msg.setColor(ChatColor.RED);
            player.sendMessage(Constants.TAG, msg);
            return;
        }
        party.retractInvite(target);
        TextComponent msg = new TextComponent("You retracted the invite of " + target.getName());
        msg.setColor(ChatColor.AQUA);
        player.sendMessage(Constants.TAG, msg);
    }

    @Override
    public void runConsole() {

    }
}
