package mall.domain;

import mall.domain.*;
import mall.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class DeliveryConfirmed extends AbstractEvent {

    private Long id;
    private Long orderId;
    private Long productId;
    private Integer qty;
    private String productName;
    private String status;

    public DeliveryConfirmed(Delivery aggregate){
        super(aggregate);
    }
    public DeliveryConfirmed(){
        super();
    }
}
