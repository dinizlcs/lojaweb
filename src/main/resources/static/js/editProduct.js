$(document).ready(function() {
    $('#formEditProduct').submit(function(e){
        var selectValue = $('#selectProduct').val();
        if(selectValue > -1){
            return true;
        }else{
            alert('Selecione um produto para editar');
            e.preventDefault();
            return false;
        }
    });

    $('#selectProduct option').each(function() {
        var text = $(this).text();
        if(text.length > 30){
            var maxLenText = text.substr(0, 30) + '...';
        }else{
            var maxLenText = text;
        }
        $(this).text(maxLenText);
        $(this).attr('title', text);
    });

    $('#selectProduct').change(function(){
        var productId = $(this).val();
        if(productId !== '-1'){
            $.ajax({
                type: "GET",
                url: "/product/getProductInfo/" + productId,
                success: function(response){
                    $('#txtId').val(response.id);
                    $('#txtTitle').val(response.title);
                    $('#txtDescription').val(response.description);
                    $('#txtPrice').val(response.price);

                    if(response.base64Image){
                        $('#imgPreview').attr('src', 'data:image/jpeg;base64,' + response.base64Image);
                        $('#imgPreview').show();
                    }else{
                        $('#imgPreview').attr('src', '');
                    }
                }
            });
        }else{
            $('#txtId').val('');
            $('#txtTitle').val('');
            $('#txtDescription').val('');
            $('#txtPrice').val('');
            
            $('#fileImage').val('');
            $('#imgPreview').attr('src', '');
        }
    });
    
    $('#delProduct').click(function(){
        var productId = $('#selectProduct').val();
        if(productId !== '-1'){
            var confirmDelete = confirm("Deseja deletar este produto?\nEsta ação não pode ser revertida.");
            if(confirmDelete){
                $.ajax({
                    type: "DELETE",
                    url: "/product/deleteProduct/" + productId,
                    success: function(response){
                        window.location.href = response;
                    }
                });
            }
        }else{
            alert('Selecione um produto para deletar');
        }
    });
});
