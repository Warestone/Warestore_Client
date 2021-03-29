function prevPage(){
    let currentPage = document.getElementById("currentPage");
    if (currentPage.value>0)
        currentPage.value -= 1
}
function nextPage(){
    document.getElementById("currentPage").value+=1;
}