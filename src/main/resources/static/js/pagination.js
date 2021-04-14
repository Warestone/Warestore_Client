function prevPage(){
    let currentPage = parseInt(document.getElementById("currentPage").value) ;
    if (currentPage>0)
        document.getElementById("currentPage").value = currentPage-1;

}
function nextPage(){
    let tableLength = document.getElementById('products').rows.length;
    if (tableLength===6){
        document.getElementById("currentPage").value=parseInt(document.getElementById("currentPage").value) + 1;
    }

}