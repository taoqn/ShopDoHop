$(document).ready(function(ev){
	
    $('#list').click(function(){event.preventDefault();$('#products .item').addClass('list-group-item');});
    $('#grid').click(function(){event.preventDefault();$('#products .item').removeClass('list-group-item');});
    
    $("#immersive_slider").immersive_slider({
    	container: ".main"
    });
    
});