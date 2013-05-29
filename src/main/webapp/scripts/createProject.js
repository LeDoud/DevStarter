
		
		$("#addFile").click(function () {
			
				var i=parseInt($("#fileCount").val());
				
				if(i < 5){
					i++;
					var html='<div id="doc'+i+'" class="row-fluid"><div class="span7 offset3"><label class="label-big inline">Title : </label><input class="span19" name="doc'+i+'_title" id="doc'+i+'_title" type="text"/></div><div class="span12 offset1"><label class="label-big inline" style="vertical-align:bottom;">File : </label> <div class="fileupload fileupload-new inline" data-provides="fileupload"> <div class="input-append inline"><div class="uneditable-input span8"><i class="icon-file fileupload-exists"><!-- --></i> <span class="fileupload-preview"> <!-- --></span></div><span class="btn btn-file"><span class="fileupload-new">Select file</span><span class="fileupload-exists">Change</span><input type="file" name="files['+i+']"/></span><a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a></div></div></div>	</div>';
					$("#divAddFile").before(html);
					$("#removeFile").show();
				}
				$("#fileCount").val(i);
		});
		
		$("#removeFile").click(function () {
			
			var i=parseInt($("#fileCount").val());
				$("#doc"+i).remove();
				i--;
			if(i === 0){
				$("#removeFile").hide();
			}
			$("#fileCount").val(i);
	});