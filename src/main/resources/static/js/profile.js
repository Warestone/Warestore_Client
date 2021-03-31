let lastName
let firstName
let patronymicName
let phone
let address
let email

window.onload = function() {
    lastName = document.getElementById("lastName").textContent
    firstName = document.getElementById("firstName").textContent
    patronymicName = document.getElementById("patronymicName").textContent
    phone = document.getElementById("phone").textContent
    address = document.getElementById("address").textContent
    email = document.getElementById("email").textContent

    if (document.getElementById('products').rows.length>1)
        document.getElementById('ordersTable').style.display="block"
};

function viewEditForm(){
    document.getElementById("firstNameEdit").value=firstName
    document.getElementById("lastNameEdit").value=lastName
    document.getElementById("patronymicNameEdit").value=patronymicName
    document.getElementById("phoneEdit").value=phone
    document.getElementById("addressEdit").value=address
    document.getElementById("emailEdit").value=email
    document.getElementById("editInfo").style.display="block"
}

function viewEditPass(){
    document.getElementById("editPass").style.display="block"
}