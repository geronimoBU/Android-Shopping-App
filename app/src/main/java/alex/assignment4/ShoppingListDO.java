package alex.assignment4;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "csa-mobilehub-801554381-ShoppingList")

public class ShoppingListDO {
    private String _userId;
    private String _itemName;
    private Double _price;
    private int _priority;
    private int _quantity;

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
    }
    @DynamoDBRangeKey(attributeName = "Item Name")
    @DynamoDBAttribute(attributeName = "Item Name")
    public String getItemName() {
        return _itemName;
    }

    public void setItemName(final String _itemName) {
        this._itemName = _itemName;
    }
    @DynamoDBAttribute(attributeName = "Price")
    public Double getPrice() {
        return _price;
    }

    public void setPrice(final Double _price) {
        this._price = _price;
    }
    @DynamoDBAttribute(attributeName = "Priority")
    public int getPriority() {
        return _priority; }

    public void setPriority(final int _priority) {
        this._priority = _priority;
    }
    @DynamoDBAttribute(attributeName = "Quantity")
    public int getQuantity() {
        return _quantity;
    }

    public void setQuantity(final int _quantity) {
        this._quantity = _quantity;
    }

    public void setUserId() {
    }
}
