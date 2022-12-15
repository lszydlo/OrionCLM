package eu.skillcraft.orionclm.preparation;

import java.time.Clock;
import java.time.YearMonth;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumberDecoratorGeneratorFactory {

  private final AuthPort authPort;
  private final ConfigPort configPort;
  private final SequencePort sequencePort;
  private final Clock clock;

  NumberGenerator create() {


    switch (authPort.userType()) {
      case BASIC: return new BaseGenerator(sequencePort.next());
      case VIP: return new PrefixDecorator(new DateDecorator(YearMonth.now(clock), new BaseGenerator(sequencePort.next())), configPort.getPrefix());
      case MEDIUM: return new PrefixDecorator(new BaseGenerator(sequencePort.next()), configPort.getPrefix());
      case PREMIUM:  return new DateDecorator(YearMonth.now(clock), new BaseGenerator(sequencePort.next()));
      case WELL_DONE: return new DemoDecorator(new BaseGenerator(sequencePort.next()), configPort.isDemo());
    }
    throw new IllegalStateException("");
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

@AllArgsConstructor
class DemoDecorator implements NumberGenerator {

  private final NumberGenerator decorated;
  private final boolean isDemo;

  @Override
  public ContractNumberB generate(String type) {
    ContractNumberB numberB = decorated.generate(type);
    return numberB.addPrefix("DEMO/");
  }
}


class ContractNumberB {

  String number;

  public ContractNumberB() {
    this.number = "";

  }

  public ContractNumberB(String s) {
    this.number = s;
  }

  ContractNumberB addPostfix(String postfix) {
    return new ContractNumberB(number + " " + postfix);
  }

  public ContractNumberB addPrefix(String prefix) {
    return new ContractNumberB((prefix + " " + number));
  }
}
