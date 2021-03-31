var cart = new Map();

window.onload = function() {
    cart = getCartFromCookie()
    updateCartView()
};

function addToCart(id, quantity, price, name) {
    let oldQty = document.getElementById(id).textContent;
    oldQty = parseInt(oldQty) + 1;
    if (oldQty<=quantity){
        document.getElementById(id).textContent = oldQty;
        cart.set(id,createProductObject(oldQty,price,name,id));
        updateCartView();
    }
}
function deleteFromCart(id) {
    let qty = document.getElementById(id).textContent;
    qty = parseInt(qty) - 1;
    if (qty>0) {
        document.getElementById(id).textContent = qty;
        let product = cart.get(id);
        product.quantity = qty;
        cart.set(id, product)
        updateCartView();
    }
    if (Number(qty)===0) {
        document.getElementById(id).textContent = qty;
        cart.delete(id);
        updateCartView();
    }
    updateCartView();
}

function updateCartView(){
    if (cart.size!==0) {
        setCookieCart(cartToObject(cart));
        document.getElementById('div_cartFrom').style.display = 'block';
        let table = document.getElementById("bodyCartTable");
        table.innerHTML = "";
        document.getElementById("totalSum").innerText = "";
        for (let item of cart.values()) {
            let row = document.createElement("TR");
            let nameTD = document.createElement("TD");
            let qtyTD = document.createElement("TD");
            let priceTD = document.createElement("TD");
            let cost = Number(item.quantity) * Number(item.price);
            nameTD.appendChild(document.createTextNode(item.name));
            qtyTD.appendChild(document.createTextNode(item.quantity));
            priceTD.appendChild(document.createTextNode(String(cost)));
            row.appendChild(nameTD);
            row.appendChild(qtyTD);
            row.appendChild(priceTD);
            table.appendChild(row);
            cost += Number(document.getElementById("totalSum").innerText)
            document.getElementById("totalSum").innerText = String(cost);
        }
    }
    else{
        document.getElementById('div_cartFrom').style.display = 'none';
        setCookieCart();
    }
}

function createProductObject(quantity, price, name, id){
    return {
        id: id,
        name: name.replace(/=/g, ' '),
        quantity: quantity,
        price: price
    };
}

function addToPurchase(){
    let cookie = getCookie("WarestoreToken")
    if (cookie===undefined || cookie==="")
        alert("Для совершения покупок авторизуйтесь или зарегистрируйтесь в системе.")
    else {
        document.cookie = "WarestoreCart=; Max-Age=-99999999;";
        const cartObject = cartToObject(cart)
        $.ajax({
            type: "POST",
            async: "false",
            contentType: "application/json; charset=utf-8",
            url: "/order",
            data: JSON.stringify(cartObject),
            success: (function () {
                alert("Покупка совершена! Подробности о заказе у Вас на почте.");
                document.location.reload();
            }),
            error: function () {
                alert("Покупка не удалась!\nНедостаточно товара на складе!");
                document.location.reload();
            }
        });
    }
}

function cartToObject(cart){
    const out = Object.create(null)
    cart.forEach((value, key) => {
        out[key] = value
    })
    return out;
}

function getCookie(name) {
    let cookie = {};
    document.cookie.split(';').forEach(function(el) {
        let [k,v] = el.split('=');
        cookie[k.trim()] = v;
    })
    return cookie[name];
}

function setCookieCart(){
    if (cart==="")
        document.cookie = "WarestoreCart="
    else
        document.cookie = "WarestoreCart="+JSON.stringify(Array.from(cart.entries()))
}

function getCartFromCookie(){
    let cookieCart = getCookie("WarestoreCart")
    if (cookieCart!==undefined)
        if (cookieCart!=="")
            return new Map(JSON.parse(getCookie("WarestoreCart")))
    return new Map()
}