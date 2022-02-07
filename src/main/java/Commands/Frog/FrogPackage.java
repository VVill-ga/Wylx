package Commands.Frog;

import Core.Commands.ServerCommand;
import Core.Events.SilentEvent;
import Core.ProcessPackage.ProcessPackage;

public class FrogPackage extends ProcessPackage {

    public FrogPackage() {
        super(new ServerCommand[]{
                new BonkCommand(),
                new FrogFactCommand(),
                new DrawFrogCardCommand(),
                new ValidateCommand()
        }, new SilentEvent[]{});
    }

    @Override
    public String getHeader() {
        return "Frog facts, frog playing cards, and other wholesome things";
    }
}
