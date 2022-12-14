package eu.skillcraft.orionclm.preparation;

import eu.skillcraft.orionclm.preparation.PreparationService.Contract;
import java.util.UUID;

public interface ContractRepo {

  void save(Contract contract);

  Contract load(UUID contractId);
}
