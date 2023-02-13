package mall.domain;

import mall.infra.AbstractEvent;
import lombok.Data;
import java.util.*;


@Data
public class OrderPlaced extends AbstractEvent {

    private Long id;
    private String productName;
    private String customerId;
    private String productId;
    private Integer qty;
    private String status;
}
