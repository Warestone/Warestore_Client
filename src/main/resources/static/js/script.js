const cart = new Map();

function addToCart(id, quantity, price, name) {
    let oldQty = document.getElementById(id).textContent;
    oldQty = parseInt(oldQty) + 1;
    if (oldQty<=quantity){
        document.getElementById(id).textContent = oldQty;
        cart.set(id,createProductObject(oldQty,price,name));
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
}

function updateCartView(){
    if (cart.size!==0) {
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
    else
        document.getElementById('div_cartFrom').style.display = 'none';
}

function createProductObject(quantity, price, name){
    return {
        quantity: quantity,
        price: price,
        name: name
    };
}

function prevPage(){
    let currentPage = document.getElementById("currentPage");
    if (currentPage.value>0)
        currentPage.value -= 1
}
function nextPage(){
    document.getElementById("currentPage").value+=1;
}

function addToPurchase(){
    //send Map.cart to controller
    alert("Успешно добавлено в корзину.");
    document.location.reload();
}

function registerUser(){
    document.getElementById("userRegistrationForm").submit();
}

function authenticateUser(){
    document.getElementById("userAuthorizationForm").submit();
}

function logout(){
    if (document.getElementById("logout").innerText=="Выйти из аккаунта")
    {
        document.cookie = "WarestoreToken="
        alert("Вы успешно вышли из аккаунта.")
    }
    document.location.reload();
}