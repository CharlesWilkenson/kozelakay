 function successMessage (result){
    document.getElementById("alert-danger").style.display = "none";
    document.getElementById("alert-success").style.display = "inline";
    document.getElementById("alert-success").innerHTML = result.message;
}

 function  errorMessage(error)  {
    const obj = JSON.parse(error.responseText);
    document.getElementById("alert-success").style.display = "none";
    document.getElementById("alert-danger").style.display = "inline";
    document.getElementById("alert-danger").innerHTML = obj.message;
    console.log(obj.message)

}