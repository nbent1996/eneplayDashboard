/*Funciones de Validación*/
function validarAltaUsuario(form){
    var resultado="";  
    //Largo caracteres
    if($("#txtbxUserUsuarioAlta").length>35){
        resultado+="El usuario no puede tener más de 35 caracteres.\n";
    }
    if($("#txtbxNombreCompletoUsuarioAlta").length>50){
        resultado+="El nombre completo no puede tener más de 50 caracteres.\n";
    }
    
    //Campos numericos
    
    if(resultado!=""){
        form.preventDefault();
        invocarModal(resultado);
        return false;
    }
    return true;
}
function validarAltaCliente(form){
    var resultado="";
    //No nulo
    if($("#selTipoCliente").val()=="Secundario" && $("#txtbxNroDocPrincipalClienteAlta").val()==""){
        resultado+="El número de documento de la cuenta principal es un campo obligatorio.\n";
    }
    //Largo Caracteres
    if($("#txtbxNombreCompletoClienteAlta").length>50){
        resultado+="El nombre completo no puede tener más de 50 caracteres.\n";
    }
    if($("#txtbxEmailClienteAlta").length>45){
        resultado+="El email no puede tener más de 45 caracteres.\n";
    }
    if($("#txtbxTelefonoClienteAlta").length>50){
        resultado+="El teléfono no puede tener más de 50 caracteres.\n";
    }
    //Campos numéricos
    if(isNaN($("#txtbxNroDocumentoClienteAlta").val())){
        resultado+="El número de documento debe ser un campo numérico.\n";
    }
    if(isNaN($("#txtbxTelefonoClienteAlta").val())){
        resultado+="El teléfono debe ser un campo numérico.\n";
    }
    if($("#selTipoCliente").val()=="Secundario" && isNaN($("#txtbxNroDocPrincipalClienteAlta").val())){
        resultado+="El número de documento de la cuenta principal debe ser numérico.\n";
    }
    if(resultado!==""){
        invocarModal(resultado);
        form.preventDefault();
        return false;
    }
    return true;
}
function validarAltaDispositivo(form){
    var resultado = "";
    
    //Largo caracteres
        if($("#txtbxNroSerieDispositivoAlta").length>20){
        resultado+="El número de serie no puede tener más de 20 caracteres";
        }
    //Campos numericos
    
    if(resultado!=""){
        form.preventDefault();
        invocarModal(resultado);
        return false;
    }
    return true;
}
function validarAltaSuscripcion(form){
    var resultado="";
    //Largo caracteres
    
    //Campos expresamente numericos
    
    if(resultado!=""){
        form.preventDefault();
        invocarModal(resultado);
        return false;
    }
    return true;
}
/*Funciones de Validación*/

