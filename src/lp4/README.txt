G21: 
Shruthi Ramesh, Nayak: sxn145130
Tejasvee Bolisetty: txb140830

ABSTRACT: 

PROBLEM STATEMENT: 
Multi-dimensional search: Consider the web site of a seller like Amazon. They carry tens of thousands of products, and each product has many attributes (Name, Size, Description, Keywords, Manufacturer, Price, etc.). The search engine allows users to specify attributes of products that they are seeking, and shows products that have most of those attributes. To make search efficient, the data is organized using appropriate data structures, such as balanced trees, and hashing. But, if products are organized by Name, how can search by price implemented efficiently? The solution, called indexing in databases, is to create a new set of references to the objects for each search field, and organize them to implement search operations on that field efficiently. As the objects change, these access structures have to be kept consistent.
In this project, each object has 3 attributes: id (long int), description (one or more long ints), and price (dollars and cents). The following operations are supported:

Insert(id,price,description): insert a new item. If an entry with the same id already exists, its description and price are replaced by the new values. If description is empty, then just the price is updated. Returns 1 if the item is new, and 0 otherwise.
Find(id): return price of item with given id (or 0, if not found).
Delete(id): delete item from storage. Returns the sum of the long ints that are in the description of the item deleted (or 0, if such an id did not exist).
FindMinPrice(n): given a long int n, find items whose description contains n (exact match with one of the long ints in the item's description), and return lowest price of those items.
FindMaxPrice(n): given a long int n, find items whose description contains n, and return highest price of those items.
FindPriceRange(n,low,high): given a long int n, find the number of items whose description contains n, and their prices fall within the given range, [low, high].
PriceHike(l,h,r): increase the price of every product, whose id is in the range [l,h], by r% Discard any fractional pennies in the new prices of items. Returns the sum of the net increases of the prices.
Range(low, high): number of items whose price is at least "low" and at most "high".
SameSame(): Find the number of items that satisfy all of the following conditions:
The description of the item contains 8 or more numbers, and,
The description of the item contains exactly the same set of numbers as another item.

METHODOLOGY:
1. Used TreeMap for storing Item object as <Id, ItemObj> as <Key,Value>
	It gives O(log(n)) running time for finding any object from the Map.
2. descItem<key, List<Item>>: A map that has description number against all the items that has <key> as a part of their description.
3. For SameSame, XOR all the elements of the description array. This will produce guaranteed unique value for that particular combination irrespective of the order.

Since the input has minimal number of FindMinPrice, FindMaxPrice and FindPriceRange, we have implmented linear search on items that satisfy the conditions.
If this was not the case, we have a different design.
Store descItem as
descItem<key, Map<Long, Item>>
        <descriptionNumber, ItemIdMap<item_id,ItemObject>>
Store MIN at key -2L at ItemIdMap. Keep a track of the minimum priced item while inserting into the Map
and the Item at location MIN.
Repeat for MAX at key -1L.
This gives us O(1) access for MinPrice and MaxPrice.

Rearranging MIN and MAX iff the item being changed is MIN or MAX of some description list.
private void rearrangeMaxMin(Item item, int i) {
        Map<Long, Item> longItemTreeMap = descItem.get(item.desc[i]);
        Item max = new Item();
        Item min = new Item();
        max.setPrice(Double.MIN_VALUE);
        min.setPrice(Double.MAX_VALUE);
        longItemTreeMap.put(MAX, max);
        longItemTreeMap.put(MIN, min);
        for (Long key : longItemTreeMap.keySet()) {
            if (key != MAX && key != MIN) {
                if (longItemTreeMap.get(key).getPrice() > max.getPrice()) {
                    longItemTreeMap.put(MAX, longItemTreeMap.get(key));
                    max = longItemTreeMap.get(key);
                }
                if (longItemTreeMap.get(key).getPrice() < min.getPrice()) {
                    longItemTreeMap.put(MIN, longItemTreeMap.get(key));
                    min = longItemTreeMap.get(key);
                }
            }
        }
    }
