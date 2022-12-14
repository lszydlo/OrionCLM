package eu.skillcraft.orionclm.preparation;

import java.time.Clock;
import java.time.YearMonth;

public class ContractNumberFactory {

  private final AuthPort authPort;
  private final ConfigPort configPort;
  private final SequencePort sequencePort;
  private final Clock clock;
  private final MoonPhasePort moonPhasePort;


  public ContractNumberFactory(AuthPort authPort,
      ConfigPort configPort, SequencePort sequencePort, Clock clock,
      MoonPhasePort moonPhasePort) {
    this.authPort = authPort;
    this.configPort = configPort;
    this.sequencePort = sequencePort;
    this.clock = clock;
    this.moonPhasePort = moonPhasePort;
  }

  public ContractNumber next(String type) {

    return new ContractNumber(
        configPort.isDemo(),
        configPort.getPrefix(),
        type,
        sequencePort.next(),
        moonPhasePort.getPhase(),
        YearMonth.now(clock),
        authPort.isAuditor(),
        authPort.userType());
  }
}

interface AuthPort {
  boolean isAuditor();

  String userType();
}

interface ConfigPort {
  boolean isDemo();

  boolean hasPrefix();

  String getPrefix();
}


interface SequencePort {

  Integer next();
}
interface MoonPhasePort {

  String getPhase();
}

