package mall.domain;

import mall.domain.InventoryIncreased;
import mall.domain.InventoryDecreased;
import mall.ProductApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Inventory_table")
@Data

public class Inventory  {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Long id;
    private String productName;
    private Integer stock;

    @PostUpdate
    public void onPostUpdate(){

        // InventoryIncreased inventoryIncreased = new InventoryIncreased(this);
        // inventoryIncreased.publishAfterCommit();

        // InventoryDecreased inventoryDecreased = new InventoryDecreased(this);
        // inventoryDecreased.publishAfterCommit();

    }

    public static InventoryRepository repository(){
        InventoryRepository inventoryRepository = ProductApplication.applicationContext.getBean(InventoryRepository.class);
        return inventoryRepository;
    }

    public static void stockDecrease(DeliveryStarted deliveryStarted){

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);

        InventoryDecreased inventoryDecreased = new InventoryDecreased(inventory);
        inventoryDecreased.publishAfterCommit();
        */

        /** Example 2:  finding and process */        
        repository().findById(deliveryStarted.getProductId()).ifPresent(inventory->{
            
            inventory.setStock(inventory.getStock() - deliveryStarted.getQty()); // do something
            repository().save(inventory);

            InventoryDecreased inventoryDecreased = new InventoryDecreased(inventory);
            inventoryDecreased.publishAfterCommit();

         });     
        
    }

    public static void stockIncrease(DeliveryCanceled deliveryCanceled){

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);

        InventoryIncreased inventoryIncreased = new InventoryIncreased(inventory);
        inventoryIncreased.publishAfterCommit();
        */

        /** Example 2:  finding and process */          
        repository().findById(deliveryCanceled.getProductId()).ifPresent(inventory->{
            
            inventory.setStock(inventory.getStock() +  deliveryCanceled.getQty()); // do something
            repository().save(inventory);

            InventoryIncreased inventoryIncreased = new InventoryIncreased(inventory);
            inventoryIncreased.publishAfterCommit();

        });     
    }


}
