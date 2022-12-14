package eu.skillcraft.orionclm.preparation;

import java.time.Clock;
import java.time.YearMonth;
import java.util.UUID;

public class NumberFactory {

  private final AuthPort authPort;
  private final ConfigPort configPort;
  private final SequencePort sequencePort;
  private final Clock clock;
  private final MoonPhasePort moonPhasePort;


  public NumberFactory(AuthPort authPort,
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

  public static class ContractNumber {

    final private String number;

    public ContractNumber(boolean demo, String prefix, String type, Integer next, String phase,
        YearMonth now, boolean auditor, UserType userType) {
        if (userType.equals(UserType.BASIC)) {
          number = getNumber(next);
        } else  if (userType.equals(UserType.PREMIUM)) {
          number = getNumber(next) + " " + now.getYear() + "/" + now.getMonthValue();
        } else  if (userType.equals(UserType.VIP)) {
          number = prefix + getNumber(next) + " " + now.getYear() + "/" + now.getMonthValue();
        } else {
          throw new IllegalStateException("");
      }

    }

    private String getNumber(Integer next) {
      return  String.valueOf(Math.addExact(next,3));
    }
  }
}

interface AuthPort {
  boolean isAuditor();

  UserType userType();

  UUID userId();
}

interface ConfigPort {
  boolean isDemo();

  boolean hasPrefix();

  String getPrefix();

  NumberConfig getUserNumberConfig(UUID userId);

}


interface SequencePort {

  Integer next();
}
interface MoonPhasePort {

  String getPhase();
}

