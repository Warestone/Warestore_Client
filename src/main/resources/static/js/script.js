function addToCart(id, quantity) {
    let oldQty = document.getElementById(id).textContent;
    oldQty = parseInt(oldQty) + 1;
    if (oldQty<=quantity)
        document.getElementById(id).textContent = oldQty;
    //call rest for adding new item to cart
}
function deleteFromCart(id) {
    let qty = document.getElementById(id).textContent;
    qty = parseInt(qty) - 1;
    if (qty>=0)
        document.getElementById(id).textContent = qty;
    //call rest for deleting item from cart (decrement qty )
}