package eu.skillcraft.orionclm.preparation;

import eu.skillcraft.orionclm.preparation.PreparationService.ContractTemplate;
import java.util.UUID;

public interface TemplateRepo {

  ContractTemplate load(UUID templateId);

}
