package eu.skillcraft.orionclm.preparation;

import java.time.Clock;
import java.time.YearMonth;

public class NumberGeneratorFactory {

  private final SequencePort sequencePort;
  private final Clock clock;

  public NumberGeneratorFactory(SequencePort sequencePort, Clock clock) {
    this.sequencePort = sequencePort;
    this.clock = clock;
  }

  NumberGenerator create() {
    return new DateDecorator(YearMonth.now(clock), new BaseGenerator(sequencePort.next()));
  }

}

interface NumberGenerator {
  ContractNumberB generate(String type);
}

class BaseGenerator implements NumberGenerator {

  private final Integer sequence;

  BaseGenerator(Integer sequence) {
    this.sequence = sequence;
  }

  @Override
  public ContractNumberB generate(String type) {
    return new ContractNumberB(sequence.toString());
  }
}

class DateDecorator implements NumberGenerator {

  private final YearMonth yearMonth;
  private final NumberGenerator decorated;

  DateDecorator(YearMonth yearMonth, NumberGenerator decorated) {
    this.yearMonth = yearMonth;
    this.decorated = decorated;
  }

  @Override
  public ContractNumberB generate(String type) {
    ContractNumberB numberB = decorated.generate(type);
    return numberB.addPostfix(yearMonth.getYear() + "/" + yearMonth.getMonthValue());
  }
}

class ContractNumberB {

  private final String number;

  public ContractNumberB(String sequence) {
    this.number = sequence;
  }

  public ContractNumberB addPostfix(String postfix) {
    return new ContractNumberB(number + " " + postfix);
  }
}
