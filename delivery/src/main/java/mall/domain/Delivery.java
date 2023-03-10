package mall.domain;

import mall.domain.DeliveryStarted;
import mall.domain.DeliveryConfirmed;
import mall.domain.DeliveryCanceled;
import mall.domain.DeliveryReturned;
import mall.DeliveryApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Delivery_table")
@Data
public class Delivery  {    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer qty;
    private String productName;
    private String status;

    @PostPersist
    public void onPostPersist(){
        DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        deliveryStarted.publishAfterCommit();
    }
    @PostUpdate
    public void onPostUpdate(){
        
        // DeliveryStarted deliveryStarted = new DeliveryStarted(this);
        // deliveryStarted.publishAfterCommit();

        DeliveryCanceled deliveryCanceled = new DeliveryCanceled(this);
        deliveryCanceled.publishAfterCommit();

        // DeliveryCanceled deliveryCanceled = new DeliveryCanceled(this);
        // deliveryCanceled.publishAfterCommit();

    }

    @PreUpdate
    public void onPreUpdate(){
    }

    public static DeliveryRepository repository(){
        DeliveryRepository deliveryRepository = DeliveryApplication.applicationContext.getBean(DeliveryRepository.class);
        return deliveryRepository;
    }

    public static void deliveryStart(OrderPlaced orderPlaced){

        /** Example 1:  new item */
        Delivery delivery = new Delivery();
        delivery.setOrderId(orderPlaced.getId());
        delivery.setProductId(orderPlaced.getProductId());
        delivery.setProductName(orderPlaced.getProductName());
        delivery.setQty(orderPlaced.getQty());
        delivery.setStatus("DeliveryStarted");

        repository().save(delivery);

        // DeliveryStarted deliveryStarted = new DeliveryStarted(delivery);
        // deliveryStarted.publishAfterCommit();
        

        /** Example 2:  finding and process
        
        repository().findById(orderPlaced.get???()).ifPresent(delivery->{
            
            delivery // do something
            repository().save(delivery);

            DeliveryStarted deliveryStarted = new DeliveryStarted(delivery);
            deliveryStarted.publishAfterCommit();

         });
        */
        
    }
    public static void deliveryCancel(OrderCanceled orderCanceled){

        /** Example 1:  new item 
        Delivery delivery = new Delivery();
        repository().save(delivery);

        DeliveryCanceled deliveryCanceled = new DeliveryCanceled(delivery);
        deliveryCanceled.publishAfterCommit();
        */

        /** Example 2:  finding and process*/
        
        repository().findByOrderId(orderCanceled.getId()).ifPresent(delivery->{
            
            delivery.setStatus("DeliveryCanceled"); // do something
            repository().save(delivery);

            // DeliveryCanceled deliveryCanceled = new DeliveryCanceled(delivery);
            // deliveryCanceled.publishAfterCommit();

         });
    }

}
