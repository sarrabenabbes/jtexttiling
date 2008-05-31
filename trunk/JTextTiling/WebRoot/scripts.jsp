<script type="text/javascript">
    function cambiaDefecto1(){ 
    	var x = valida(document.getElementById('nombre'));
    	var y = valida(document.getElementById('pass'));
    			  
    	if (x & y)
    		document.getElementById('botonEnviar').disabled = false;
    	else document.getElementById('botonEnviar').disabled = true;
    		
    } 
    		
    function vacio(q) {  
        for ( i = 0; i < q.length; i++ ) {  
        	if ( q.charAt(i) != " " ) {  
            	return true;  
            }  
        }  
        return false; 
	}
			
	function valida(F) {  
         if( vacio(F.value) == false ) {  
         	return false;
         } else {  
            return true;
         }  
    }	  
   			
    function cambiaDefecto2(){ 
    	var x = valida(document.getElementById('nombre'));
    	var y = valida(document.getElementById('pass1'));
    	var z = valida(document.getElementById('pass2'));
    	var w = valida(document.getElementById('mail'));
    			  
    	if (x & y & z & w)
    		document.getElementById('botonCrear').disabled = false;
    	else document.getElementById('botonCrear').disabled = true;
    		
    } 
    		
    function vacio(q) {  
        for ( i = 0; i < q.length; i++ ) {  
            if ( q.charAt(i) != " " ) {  
                 return true;  
            }  
        }  
        return false; 
	}
			
	function valida(F) {  
          if( vacio(F.value) == false ) {  
               return false;
           } else {  
               return true;
           }  
    }	  
	
	function cambiaDefecto5() {
		validateRadio(document.getElementsByName('nombreArchivo'));
	}
	
	function validateRadio(x){
		var chks = x;
		if (chks){ 
			var checked = false;
			if (chks.length){ 
				var len = chks.length;
				for (var i=0; i<len; i++){
					if (chks[i].checked){
						checked = true;
						break;
					}
				}
			}
			else{ 
				checked = chks.checked;
			}
			if (!checked){
				document.getElementById('botonVer2').disabled = true;
				return false;
				}	
		}
		document.getElementById('botonVer1').disabled = false;
		return true;
	}
	
	function cambiaDefecto6(){ 
    	var x = valida(document.getElementById('nombre'));
    	var y = valida(document.getElementById('pass1'));
    	var z = valida(document.getElementById('pass2'));
    	var w = valida(document.getElementById('mail'));
    			  
    	if (x | y | z | w)
    		document.getElementById('botonGuardar').disabled = false;
    	else document.getElementById('botonGuardar').disabled = true;
    } 
    		
    function vacio(q) {  
        for ( i = 0; i < q.length; i++ ) {  
            if ( q.charAt(i) != " " ) {  
        		return true;  
            }  
        }  
      	return false; 
	}
			
	function valida(F) {  
         if( vacio(F.value) == false ) {  
              return false;
         } else {  
              return true;
         }  
    }
    
    function cambiaDefecto7() {
    	var x = valida(document.getElementById('nombreArchivo'));
    	if (x)
    		document.getElementById('botonEnviar').disabled = false;
    	else document.getElementById('botonEnviar').disabled = true;
    }	  
    
    function cambiaDefectoCheck(nombreCheck, nombreBoton) {
		validate(document.getElementsByName(nombreCheck),nombreBoton);
	}
			
	function validate(x, nombreBoton){
		var chks = x;
		if (chks){ 
			var checked = false;
			if (chks.length){
				var len = chks.length;
				for (var i=0; i<len; i++){
					if (chks[i].checked){
						checked = true;
						break;
					}
				}
			}
			else{ 
				checked = chks.checked;
			}
			if (!checked){
				document.getElementById(nombreBoton).disabled = true;
				return false;
			}	
		}
		document.getElementById(nombreBoton).disabled = false;
		return true;
	}
	
	function recargar() {
		window.location.reload();
	}
</script>