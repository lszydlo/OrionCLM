package eu.skillcraft.orionclm.preparation;

import java.time.Clock;
import java.time.YearMonth;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
public class NumberGeneratorFactory {

  private final AuthPort authPort;
  private final ConfigPort configPort;
  private final SequencePort sequencePort;
  private final Clock clock;

  NumberGenerator create() {
    if (authPort.userType().equals("BASIC")) {
      return new BaseGenerator(sequencePort.next());
    }
    if (authPort.userType().equals("PREMIUM")) {
      return new DateDecorator(YearMonth.now(clock), new BaseGenerator(sequencePort.next()));
    }
    if (authPort.userType().equals("VIP")) {
      return new PrefixDecorator(new DateDecorator(YearMonth.now(clock), new BaseGenerator(sequencePort.next())), configPort.getPrefix());
    }
    if (authPort.userType().equals("MEDIUM")) {
      return new PrefixDecorator(new BaseGenerator(sequencePort.next()), configPort.getPrefix());
    }

    throw new IllegalArgumentException("");

  }

}

interface NumberGenerator {

  ContractNumberB generate(String type);
}

@AllArgsConstructor
class BaseGenerator implements NumberGenerator {

  private final Integer sequence;

  @Override
  public ContractNumberB generate(String type) {
    return new ContractNumberB(sequence.toString());
  }
}

@AllArgsConstructor
class DateDecorator implements NumberGenerator {

  private final YearMonth yearMonth;
  private final NumberGenerator decorated;

  @Override
  public ContractNumberB generate(String type) {
    ContractNumberB numberB = decorated.generate(type);
    return numberB.addPostfix(yearMonth.getYear() + "/" + yearMonth.getMonthValue());
  }
}

@AllArgsConstructor
class PrefixDecorator implements NumberGenerator {

  private final NumberGenerator decorated;
  private final String prefix;

  @Override
  public ContractNumberB generate(String type) {
    ContractNumberB numberB = decorated.generate(type);
    return numberB.addPrefix(prefix);
  }
}

@Value
class ContractNumberB {

  String number;

  ContractNumberB addPostfix(String postfix) {
    return new ContractNumberB(number + " " + postfix);
  }

  public ContractNumberB addPrefix(String prefix) {
    return new ContractNumberB((prefix + " " + number));
  }
}
