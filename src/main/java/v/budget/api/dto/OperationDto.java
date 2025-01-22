package v.budget.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationDto {
    Long id;
    Long userId;
    String categoryName;
    Double amount;
    String type;
}
