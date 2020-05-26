package com.gl.glcart.InventoryService.model;

/*@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
*/
public class Inventory {
	private String orderId;
	

	public Inventory(String orderId) {
		super();
		this.orderId = orderId;
	}
	
	public Inventory() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
