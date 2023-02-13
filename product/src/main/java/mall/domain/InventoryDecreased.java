package mall.domain;

import mall.domain.*;
import mall.infra.AbstractEvent;
import java.util.*;
import lombok.*;


@Data
@ToString
public class InventoryDecreased extends AbstractEvent {

    private Long id;
    private String productName;
    private Integer stock;

    public InventoryDecreased(Inventory aggregate){
        super(aggregate);
    }
    public InventoryDecreased(){
        super();
    }
}
