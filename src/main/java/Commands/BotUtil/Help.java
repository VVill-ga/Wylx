package Commands.BotUtil;

import Core.Events.Commands.CommandContext;
import Core.Events.Commands.ServerCommand;
import Core.Events.Event;
import Core.Events.ServerEventManager;
import Core.Events.SilentEvents.SilentEvent;
import Core.Events.EventPackage;
import Core.Processing.MessageProcessing;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.regex.Matcher;

public class Help extends ServerCommand {
    public Help() {
        super("help", CommandPermission.EVERYONE, "Provides lists and descriptions of commands");
    }

    @Override
    public void runCommand(CommandContext ctx) {
        MessageReceivedEvent event = ctx.event();
        String[] args = ctx.args();

        //If there are more than two args the user provided invalid input, correct them
        if(args.length > 2){
            event.getMessage().reply("Usage: %{p}help <Keyword>".replaceAll("%\\{p}",
                    Matcher.quoteReplacement(ctx.prefix()))).queue();
            return;
        }

        //If there is only one arg print out the complete list of commands
        if(args.length == 1) {
            StringBuilder helpMessage = new StringBuilder();
            helpMessage.append("```diff\n");
            ServerEventManager eventManager = ServerEventManager.getServerEventManager(ctx.guildID());
            for (EventPackage eventPackage : MessageProcessing.eventPackages) {
                helpMessage.append(eventPackage.getDescription(eventManager));
            }
            helpMessage.append("```");
            event.getChannel().sendMessage(helpMessage).queue();
            return;
        } else if(MessageProcessing.eventMap.containsKey(args[1])){
            Event serverEvent = MessageProcessing.eventMap.get(args[1]);
            event.getChannel().sendMessage(serverEvent.getKeyword() + ": " + serverEvent.getDescription(ctx.prefix())).queue();
            return;
        }

        event.getChannel().sendMessage("Unable to find command: " + args[1]).queue();
    }
}
