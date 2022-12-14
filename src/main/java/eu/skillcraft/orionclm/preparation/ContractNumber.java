package eu.skillcraft.orionclm.preparation;

import java.time.YearMonth;

public class ContractNumber {

  final private String number;

  public ContractNumber(boolean demo, String prefix, String type, Integer next, String phase,
      YearMonth now, boolean auditor, String userType) {
      if (userType.equals("BASIC")) {
        number = getNumber(next);
      } else  if (userType.equals("PREMIUM")) {
        number = getNumber(next) + " " + now.getYear() + "/" + now.getMonthValue();
      } else  if (userType.equals("VIP")) {
        number = prefix + getNumber(next) + " " + now.getYear() + "/" + now.getMonthValue();
      } else {
        throw new IllegalStateException("");
    }

  }

  private String getNumber(Integer next) {
    return  String.valueOf(Math.addExact(next,3));
  }
}
