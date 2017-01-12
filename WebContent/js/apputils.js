function getLastActionResponse(successCallBack,errorCallBack)
{	showSpinner();
	$.ajax({
        type: 'POST',
        url:  '_get_last_model_data.wss',
        data: { }
     })
        .done( function (responseText) {
           // Triggered if response status code is 200 (OK)
           console.log('Response received:' + responseText);
           successCallBack(eval("("+responseText+")"));
        })
        .fail( function (jqXHR, status, error) {
           errorCallBack(jqXHR.responseText);
        })
        .always( function() {
           // Always run after .done() or .fail()
           console.log("Nothing to cover up");
           hideSpinner();
        });
		
}


function showSpinner()
{
	$('#spinner').modal('show');
}
function hideSpinner()
{
	$('#spinner').modal('hide');
}
