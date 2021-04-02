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

function isValidEditInfoForm(){
    let firstNameEdit = document.getElementById("firstNameEdit").value
    let lastNameEdit = document.getElementById("lastNameEdit").value
    let patronymicNameEdit = document.getElementById("patronymicNameEdit").value
    let phoneEdit =document.getElementById("phoneEdit").value
    let addressEdit = document.getElementById("addressEdit").value
    let emailEdit = document.getElementById("emailEdit").value
    document.getElementById("username").value = document.getElementById("login").textContent
    document.getElementById("password").value = "password"
    if (firstNameEdit===firstName && lastNameEdit === lastName
    && patronymicNameEdit === patronymicName && phoneEdit === phone
    && addressEdit === address && emailEdit === email){
        alert("Ни одно из полей не было изменено!")
        return false
    }
    else return true;
}

function isValidEditPasswordForm(){
    let newPass = document.getElementById("newPass").value
    let oldPass = document.getElementById("oldPass").value
    if (newPass===oldPass){
        alert("Новый пароль не должен быть таким же как текущий!")
        return false;
    }
    if (newPass===document.getElementById("newPass2").value) {
        return true
    }
    else{
        alert("Новые пароли не совпадают!")
        return false;
    }
}